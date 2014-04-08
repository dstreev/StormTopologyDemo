#!/bin/bash

cd /usr/lib/kafka

nohup bin/zookeeper-server-start.sh config/zookeeper.properties &
nohup bin/kafka-server-start.sh config/server.properties &

# Check the messages on our kafka topic
#bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic firehose --from-beginning

# Start the Storm Enviroment
cd /usr/lib/storm
nohup storm nimbus &
nohup storm supervisor &
nohup storm ui &

