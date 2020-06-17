package com.vinaylogics.kafkatraining.tutorial1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class ConsumerDemoWithThread {

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemoWithThread.class);
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "my-sixth-application";
    private static final String RESET_CONFIG = "earliest";
    private static final String FIRST_TOPIC = "first_topic";

    public static void main(String[] args) {
        ConsumerDemoWithThread consumerDemoWithThread = new ConsumerDemoWithThread();
        consumerDemoWithThread.run();

    }

    public void run() {

        // latch for dealing multiple threads
        CountDownLatch latch =  new CountDownLatch(1);

        // create the consumer runnable
        LOGGER.info("Creating the consumer thread");
        Runnable myConsumerRunnable = new ConsumerRunnable(
                BOOTSTRAP_SERVERS,
                GROUP_ID,
                FIRST_TOPIC,
                latch
        );

        // start the thread
        Thread myThread = new Thread(myConsumerRunnable);
        myThread.start();

        // add a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            LOGGER.info("Caught shutdown hook");
            ((ConsumerRunnable)myConsumerRunnable).shutdown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Application has exited");
        }));

        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.error("Application got interrupted", e);
        } finally {
            LOGGER.info("Application Closing");
        }
    }

    public static class ConsumerRunnable implements Runnable {
        private CountDownLatch latch;
        private KafkaConsumer<String,String> consumer;
        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRunnable.class);
        public ConsumerRunnable(String bootstrapServers,
                                String groupId,
                                String topic,
                                CountDownLatch latch){
            this.latch = latch;
            // create consumer config
            Properties properties = new Properties();
            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, RESET_CONFIG);

            // create consumer
             consumer = new KafkaConsumer<>(properties);

            // subscribe consumer to our topic(s)
            consumer.subscribe(Collections.singleton(topic));
        }

        @Override
        public void run() {

            // poll for new data
            try {
                while (true){
                    ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100)); // new in kafka 2.0.0
                    records.forEach(record->{
                        LOGGER.info("Key: " + record.key() + ", Value : " + record.value());
                        LOGGER.info("Partition: "+ record.partition() + ", Offset : "+ record.offset());
                    });
                }
            } catch (WakeupException e) {
                LOGGER.info("Received Shutdown Signal!");
            } finally {
                consumer.close();
                latch.countDown();
            }
        }

        public void shutdown(){
            // the wakeup() method is a special method to interrupt consumer.poll().
            // it will throw exception WakeUpException
            consumer.wakeup();
        }
    }
}
