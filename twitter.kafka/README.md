# Notes

I initially attempted to go with Kafka 0.7.2, simply because the Storm-Kafka connector (HDP version) only supports 0.7.  But I found this to be a confusing effort.

Finding documentation for Kafka 0.7 was nearly impossible.  I was looking for code examples on how to connect and send a message.  Everything I found, seemed to point to 0.8.  And 0.8, seems to have been a near re-write of Kafka, so the API are very different.  I can respect that, been there, done that.  But our commitment in HDP to initially support 0.7, mades this task much more difficult. So..  I bailed and when with 0.8 and instead of using the contrib/storm-kafka spout, I'm using the following for the support, and will continue forward with Kafka 0.8.

https://github.com/wurstmeister/storm-kafka-0.8-plus

Download and build this to get the libraries for your project.

# Other references/examples

https://github.com/NFLabs/kafka-twitter