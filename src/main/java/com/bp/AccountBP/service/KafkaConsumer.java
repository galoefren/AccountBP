package com.bp.AccountBP.service;

import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaConsumer {

    private Long clienteId;
    private Cuenta cuenta;
    private String nombre;

    @Autowired
    private CuentaRepository cuentaRepository;
    private CountDownLatch latch = new CountDownLatch(1);


    @KafkaListener(topics = "cliente_id_respuesta", groupId = "group_id")
    public void consume(String message) {

            this.clienteId = Long.parseLong(message);
            cuenta.setClienteid(clienteId);
            cuentaRepository.save(cuenta);
    }

    @KafkaListener(topics = "cliente_id_nombre", groupId = "group_id")
    public void consumeCliente(String message) {

            String[] parts = message.split("_");
            if (parts.length == 2) {
                this.clienteId = Long.parseLong(parts[0]); ;
                this.nombre = parts[1];
                latch.countDown(); // bandera de de ack
            }
    }

    public boolean awaitMessage(long timeout, TimeUnit unit) throws InterruptedException {
        return latch.await(timeout, unit);
    }

    public void setCuentaListener(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getNombre() {
        return nombre;
    }
}
