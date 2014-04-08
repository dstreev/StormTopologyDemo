package com.hortonworks.pso.storm.demo.twitter;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Connect to and start a Twitter Firehose.
 * <p/>
 * You'll need to specify your twitter oauth via -D options. IE:
 * -Dtwitter4j.oauth.consumerKey=...
 * -Dtwitter4j.oauth.consumerSecret=...
 * -Dtwitter4j.oauth.access.token=...
 * -Dtwitter4j.oauth.access.token.secret=..
 */
public class FirehoseToKafka {
    static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static final String DELIMITER = "\t";
    static final String MESSAGE_VERSION = "v1";

    static String template = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=false";

    public static void main(String[] args) throws TwitterException {

        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        final Producer<Integer, String> producer = new Producer<Integer, String>(new ProducerConfig(props));

        final String topic = "firehose";

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        System.out.println(System.getProperty("twitter4j.oauth.consumerKey"));

        AccessToken accessToken = new AccessToken(System.getProperty("twitter4j.oauth.access.token"), System.getProperty("twitter4j.oauth.access.token.secret"));
        twitterStream.setOAuthAccessToken(accessToken);
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                StringBuilder sb = new StringBuilder();

                if (status.getGeoLocation() != null && status.getLang().equals("en")) {

                    sb.append(MESSAGE_VERSION).append(DELIMITER);
                    sb.append(df.format(status.getCreatedAt())).append(DELIMITER);
                    sb.append(status.getUser().getScreenName()).append(DELIMITER);
                    sb.append(df.format(status.getUser().getCreatedAt())).append(DELIMITER);
                    sb.append(status.getUser().getDescription()).append(DELIMITER);
                    sb.append(status.getUser().getFollowersCount()).append(DELIMITER);
                    sb.append(status.getUser().getFriendsCount()).append(DELIMITER);

                    sb.append(status.getGeoLocation().getLatitude()).append(DELIMITER);
                    sb.append(status.getGeoLocation().getLongitude()).append(DELIMITER);
                    sb.append(status.getPlace().getFullName()).append(DELIMITER);
                    sb.append(status.getPlace().getCountryCode()).append(DELIMITER);

                    sb.append(status.getText());

//                    System.out.println(status.);

                    System.out.println(sb.toString());

                    KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, sb.toString());
                    producer.send(data);
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//                System.out.println(statusDeletionNotice.getUserId() + " deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
//                    System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
//                    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
//                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        twitterStream.sample();
    }
}
