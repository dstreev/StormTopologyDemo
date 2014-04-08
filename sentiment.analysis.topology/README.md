# Getting the preview Libraries...

- From the source and build
- From an install of Storm 0.9.1 from Baikal
    - Manually add to Local Maven Repo.

# Reference Projects

https://github.com/wurstmeister/storm-kafka-0.8-plus

# Running the Topology

storm jar target/demo.twitter.sentiment.anaylsis-1.0-SNAPSHOT-shaded.jar com.hortonworks.pso.storm.demo.twitter.sentiment.SentimentTopology "test" "chuck"