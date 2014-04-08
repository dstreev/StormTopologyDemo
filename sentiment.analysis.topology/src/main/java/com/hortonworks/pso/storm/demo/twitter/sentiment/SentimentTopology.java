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

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.Broker;
import storm.kafka.*;
import storm.kafka.trident.GlobalPartitionInformation;

public class SentimentTopology {

    private BrokerHosts brokerHosts = null;

    public SentimentTopology() {

        GlobalPartitionInformation hostsAndPartitions = new GlobalPartitionInformation();
//        hostsAndPartitions.addPartition(0, new ZkHosts("localhost"));
        hostsAndPartitions.addPartition(0, new Broker("localhost", 9092));
        brokerHosts = new StaticHosts(hostsAndPartitions);

//        brokerHosts = new ZkHosts("localhost");
    }

    public StormTopology buildTopology() {
        SpoutConfig kafkaConfig = new SpoutConfig(brokerHosts, "firehose", "", "storm-twitter");
//        kafkaConfig.scheme.

//        kafkaConfig.startOffsetTime = 0;
        kafkaConfig.forceFromStart = true;

        KafkaSpout kafkaSpout = new KafkaSpout(kafkaConfig);


        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("tweets", kafkaSpout, 3);
        builder.setBolt("scorer", new SentimentBolt(), 2).shuffleGrouping("tweets");
        return builder.createTopology();
    }

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        SentimentTopology topology = new SentimentTopology();
        Config config = new Config();

        // Example of sending parameters from a commandline, to your topology parts.
        if (args.length > 0) {
            config.put("test.value", args[0]);
        }

        String name = "Tweet-Sentiment";

        try {
            StormSubmitter.submitTopology(name, config, topology.buildTopology());
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        }


    }
}
