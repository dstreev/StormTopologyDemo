# Demo Components

1. Storm Cluster
    a. storm nimbus
    b. storm supervisor
    c. storm ui
2. Kafka Cluster ( Running 0.8.x)
    a. Starting the Kafka Server
        i. > bin/zookeeper-server-start.sh config/zookeeper.properties
        i. > bin/kafka-server-start.sh config/server.properties
    b. Test Consumer
        i. > bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic firehose --from-beginning
