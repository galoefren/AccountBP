package com.bp.AccountBP.service;

import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private Long clienteId;
    private Cuenta cuenta;

    @Autowired
    private CuentaRepository cuentaRepository;

    //private CuentaService cuentaService;


    @KafkaListener(topics = "cliente_id_respuesta", groupId = "group_id")
    public void consume(String message) {
        this.clienteId = Long.parseLong(message);
        cuenta.setClienteid(clienteId);
        cuentaRepository.save(cuenta);

    }
    public void setCuentaListener(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
