package com.rodrigomonteiro;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

import static java.time.LocalTime.now;

public class Producer {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu nome para entrar na fila (ENTER para enviar):");

        String nome = scanner.nextLine();

        Properties props = new Properties();

        props.put(
                "bootstrap.servers",
                "localhost:9092"
        );

        props.put(
                "key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer"
        );

        props.put(
                "value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer"
        );

        KafkaProducer<String, String> producer =
                new KafkaProducer<>(props);

        ProducerRecord<String, String> mensagem =
                new ProducerRecord<>(
                        "teste",
                        nome
                );

        producer.send(mensagem).get();

        System.out.println("Usuario adicionado a fila!");

        producer.close();
    }
}