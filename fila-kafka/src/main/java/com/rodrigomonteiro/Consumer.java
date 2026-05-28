package com.rodrigomonteiro;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class Consumer {

    public static void main(String[] args) {

        Properties props = new Properties();

        props.put(
                "bootstrap.servers",
                "localhost:9092"
        );

        // grupo único toda vez
        props.put(
                "group.id",
                "pagamentos-consumer"
                //UUID.randomUUID().toString()
        );

        props.put(
                "key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer"
        );

        props.put(
                "value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer"
        );

        props.put(
                "auto.offset.reset",
                "earliest"
        );

        KafkaConsumer<String, String> consumer =
                new KafkaConsumer<>(props);

        consumer.subscribe(
                Collections.singletonList("teste")
        );

        System.out.println("Aguardando mensagens...");

        while (true) {

            ConsumerRecords<String, String> mensagens =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> mensagem : mensagens) {

                System.out.println(
                        "Mensagem recebida: "
                                + mensagem.value()
                );

                System.out.println(
                        "ordem na fila: "
                                + mensagem.offset()
                );
            }
        }
    }
}