package com.vinaylogics.kafkatraining.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDemo.class);
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

        // Create Produce record
        ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "hello world");

        // send data async call
        producer.send(record);

        // flush data
        producer.flush();

        // flush data and close
        producer.close();

    }
}
