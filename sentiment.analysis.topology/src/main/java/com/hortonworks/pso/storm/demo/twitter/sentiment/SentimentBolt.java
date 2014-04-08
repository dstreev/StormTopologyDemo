/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hortonworks.pso.storm.demo.twitter.sentiment;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.hortonworks.pso.storm.demo.sentiment.Scorer;

import java.util.Map;

public class SentimentBolt extends BaseRichBolt {
    private int myCount = 0;
    private OutputCollector collector;
    private Scorer scorer = Scorer.getInstance();
    private Object clMessage = "not-set";

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        if (stormConf.get("test.value") != null) {
            clMessage = stormConf.get("test.value");
        }
    }

    @Override
    public void execute(Tuple input) {
        // Get the string from the input tuple that's being delivered from the KafkaSpout.
        // This string represents the String placed on the Kafka Queue by the Twitter Firehouse.
        String raw = input.toString();
        // Split the text into fields again.
        String[] fields = raw.split("\t");

        int score = scorer.score(fields[11]);
        System.out.println(clMessage.toString() + "++++++ Score: " + score);
//        System.out.println(raw);
        collector.emit(new Values(fields[0], fields[1], fields[2], fields[3], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8], fields[9], fields[10], fields[11], score));
        collector.ack(input);

    }

    /*
        0sb.append(MESSAGE_VERSION).append(DELIMITER);
        1sb.append(df.format(status.getCreatedAt())).append(DELIMITER);
        2sb.append(status.getUser().getScreenName()).append(DELIMITER);
        3sb.append(df.format(status.getUser().getCreatedAt())).append(DELIMITER);
        4sb.append(status.getUser().getDescription()).append(DELIMITER);
        5sb.append(status.getUser().getFollowersCount()).append(DELIMITER);
        6sb.append(status.getUser().getFriendsCount()).append(DELIMITER);

        7sb.append(status.getGeoLocation().getLatitude()).append(DELIMITER);
        8sb.append(status.getGeoLocation().getLongitude()).append(DELIMITER);
        9sb.append(status.getPlace().getFullName()).append(DELIMITER);
        10sb.append(status.getPlace().getCountryCode()).append(DELIMITER);

        11sb.append(status.getText());
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("version", "created", "user.screen.name", "user.created", "user.description", "user.follower.count", "user.friend.count","message.geo.lat", "message.geo.long", "message.geo.location", "message.geo.countrycode", "message.text", "sentiment.score"));
    }
}
