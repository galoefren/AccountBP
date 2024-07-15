package com.bp.AccountBP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final String TOPIC = "buscar_cliente";
    private static final String TOPICCLIENTE = "buscar_cliente_nombre";


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendMessageCliente(String message) {
        kafkaTemplate.send(TOPICCLIENTE, message);
    }





}
