package com.vinaylogics.kafkatraining.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.stream.IntStream;

public class ProducerDemoWithCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDemoWithCallback.class);
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    /*
     * 1 . Create Producer Properties
     *
     * 2. Create the Producer
     *
     * 3. Send Data
     * */

    public static void main(String[] args) {
        // Create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);



        // send data async call
        IntStream.range(0, 10).forEach(i -> {
            // Create Produce record
            ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "hello world "+ i);
            producer.send(record, (recordMetadata, e) -> {
                // executes every time a record is successfully sent or an exception is thrown
                if (e == null) {
                    // the record was successfully sent
                    LOGGER.info("Received new metadata: \n" +
                            "Topic : " + recordMetadata.topic() + "\n" +
                            "Partition : " + recordMetadata.partition() + "\n" +
                            "Offsets : " + recordMetadata.offset() + "\n" +
                            "Timestamp : " + recordMetadata.timestamp());
                } else {
                    LOGGER.error("Error while producing ", e);
                }
            });
        });

        // flush data
        producer.flush();

        // flush data and close
        producer.close();

    }
}
