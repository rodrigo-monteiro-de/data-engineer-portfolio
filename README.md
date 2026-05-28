# 📌 Kafka Java Lab — Producer & Consumer with Docker

A simple hands-on project **Apache Kafka** using:

- Kafka running in Docker
- Plain Java (Maven)
- Basic Producer and Consumer
- Event-driven queue simulation

---

# 🚀 Goal

This project helps you understand in practice:

- What a **Producer** is
- What a **Consumer** is
- How a **Topic** works
- The concept of **Consumer Groups**
- How Kafka processes messages asynchronously

---

# 🧱 Architecture


Java Producer → Kafka Topic (queue) → Java Consumer


Simulation flow:


User types a name
→ Producer sends it to Kafka
→ Consumer receives it
→ Prints "Person in the queue"


---

# ⚙️ Technologies Used

- Java 21+
- Maven
- Apache Kafka (via Docker)
- Kafka Client 3.9.1
- Docker Desktop

---

# 🐳 Running Kafka with Docker

## 1. Create `docker-compose.yml`

```yaml
services:

  kafka:
    image: apache/kafka:3.7.1
    container_name: kafka

    ports:
      - "9092:9092"

    environment:
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_LISTENERS: PLAINTEXT://:9092,CONTROLLER://:9093
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@localhost:9093
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
2. Start Kafka
docker compose up -d
3. Verify container
docker ps
📦 Create Topic
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
--create \
--topic queue \
--bootstrap-server localhost:9092
☕ Java Project (Maven)
📄 Dependency (pom.xml)
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>3.9.1</version>
</dependency>
📤 Producer (send names to Kafka)
KafkaProducer<String, String> producer = new KafkaProducer<>(props);

Scanner scanner = new Scanner(System.in);

while (true) {
    System.out.print("Name: ");
    String name = scanner.nextLine();

    ProducerRecord<String, String> record =
            new ProducerRecord<>("queue", name);

    producer.send(record);

    System.out.println("Sent: " + name);
}
📥 Consumer (process messages)
KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

consumer.subscribe(Collections.singletonList("queue"));

while (true) {

    ConsumerRecords<String, String> records =
            consumer.poll(Duration.ofMillis(100));

    for (ConsumerRecord<String, String> record : records) {

        System.out.println(
                "Person in queue: " + record.value() +
                " | Offset: " + record.offset()
        );
    }
}
🧠 Key Concepts Learned
📌 Topic

A Kafka message queue

📌 Producer

Responsible for sending events

📌 Consumer

Continuously reads messages

📌 Consumer Group

Defines how consumers share messages

📌 Offset

Position of a message inside a partition

🔁 System Flow
User → Producer → Kafka Topic → Consumer → Processing
🧪 Example Usage
Input:
John
Mary
Carlos
Output:
Person in queue: John | Offset: 0
Person in queue: Mary | Offset: 1
Person in queue: Carlos | Offset: 2
💡 Key Takeaways

✔ Kafka is not a traditional queue
✔ Consumer runs continuously
✔ Messages are stored by offset
✔ Consumer Groups control distribution
✔ Key can influence partitioning

🚀 Possible Improvements
JSON instead of String messages
MySQL integration
Multiple consumers (load balancing)
Retry + Dead Letter Queue (DLQ)
Priority queue (hospital/banking system)
Spring Boot + Kafka

👨‍💻 Author
Rodrigo Monteiro  
Data Engineer | Java & Kafka Learner  

- GitHub: https://github.com/rodrigo-monteiro-de
- LinkedIn: https://www.linkedin.com/in/rodrigo-monteiro-697b0452/

Project created for learning purposes:

Distributed messaging
Event-driven architecture
Apache Kafka
Java + Docker





