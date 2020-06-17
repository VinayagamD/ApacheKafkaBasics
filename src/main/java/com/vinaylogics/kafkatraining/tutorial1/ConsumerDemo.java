package com.vinaylogics.kafkatraining.tutorial1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDemo {

    /*
    * 1. Create Consumer Config
    *
    * 2. Create Consumer
    *
    * 3. subscribe consumer to our topic
    *
    * 4. poll for new data
    *
    * * */

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemo.class);
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "my-fourth-application";
    private static final String RESET_CONFIG = "earliest";
    private static final String FIRST_TOPIC = "first_topic";

    public static void main(String[] args) {

        // create consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, RESET_CONFIG);
        // create consumer
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Collections.singleton(FIRST_TOPIC));

        // poll for new data
        while (true){
           ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100)); // new in kafka 2.0.0
            records.forEach(record->{
                LOGGER.info("Key: " + record.key() + ", Value : " + record.value());
                LOGGER.info("Partition: "+ record.partition() + ", Offset : "+ record.offset());
            });
        }
    }
}
