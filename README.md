# spring-boot-microservice
# cd C:\kafka_2.12-3.5.0\bin\windows
1. Start Zookeeper Server
   For Windows:
   C:\kafka_2.12-3.5.0\bin\windows>zookeeper-server-start.bat C:\kafka_2.12-3.5.0\config\zookeeper.properties

For Linux/Mac
sh bin/zookeeper-server.sh config/zookeeper.properties

2. Start Kafka server/Broker
   For Windows:
   C:\kafka_2.12-3.5.0\bin\windows>kafka-server-start.bat C:\kafka_2.12-3.5.0\config\server.properties

For Linux/Mac
sh bin/kafka-server-start.sh config/server.properties

3. Create topic
   For Windows:
   C:\kafka_2.12-3.5.0\bin\windows>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic NewTopic --partitions 3 --replication-factor 1

For Linux/Mac
sh bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic NewTopic --partitions 3 --replication-factor 1

4. List out all topic names
   For Windows:
   C:\kafka_2.12-3.5.0\bin\windows>kafka-topics.bat --bootstrap-server localhost:9092 --list

For Linux/Mac
sh bin/kafke-topics.sh --bootstrap-server localhost:9092 --list

5. Describe topics
   For Windows:
   C:\kafka_2.12-3.5.0\bin\windows>kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic NewTopic

For Linux/Mac
sh bin/kafka-topics --bootstrap-server localhost:9092 --describe --topic NewTopic

6. Produce message

For Windows:
C:\kafka_2.12-3.5.0\bin\windows>kafka-console-producer.bat --broker-list localhost:9092 --topic NewTopic

For Linux/Mac
sh bin/kafka-console-producer.sh --broker-list localhost:9092 --topic NewTopic

7. Consume message
   For Windows:
   C:\kafka_2.12-3.5.0\bin\windows>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic NewTopic --from-beginning

For Linux/Mac
sh bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NewTopic --from-beginning