package com.bp.AccountBP.service;

import com.bp.AccountBP.dto.CuentaDTO;
import com.bp.AccountBP.dto.CuentaMapper;
import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaConsumer kafkaConsumer;

    public List<CuentaDTO> getAllCuentas() {

        List<Cuenta> cuentas = cuentaRepository.findAll();
        return CuentaMapper.toDTOs(cuentas);

    }

    public CuentaDTO  getCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con Id: " + id));
        return CuentaMapper.toDTO(cuenta);

    }

    public String createCuenta(Cuenta cuenta,String numeroCedula) {

        // Enviar mensaje a Kafka para buscar clienteId por numeroCedula,

        kafkaProducer.sendMessage(numeroCedula);
        kafkaConsumer.setCuentaListener(cuenta);
        return "Se creo la cuenta";
    }

    public void createCuentaFromListener(Cuenta cuenta) {
        cuentaRepository.save(cuenta);
    }

    public CuentaDTO  updateCuenta(Long id, Cuenta cuentaDetails) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta not found"));
        cuenta.setNumeroCuenta(cuentaDetails.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDetails.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDetails.getSaldoInicial());
        cuenta.setEstado(cuentaDetails.isEstado());
        cuentaRepository.save(cuenta);

        return CuentaMapper.toDTO(cuenta);
    }

    public void deleteCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta not found"));
        cuentaRepository.delete(cuenta);
    }

    public List<CuentaDTO> findMovimientosBetweenDates(String numeroCedula, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            kafkaProducer.sendMessageCliente(numeroCedula);
            // Esperar la respuesta del clienteId (con un tiempo de espera de 10 segundos)
            boolean messageReceived = kafkaConsumer.awaitMessage(10, TimeUnit.SECONDS);

            if (!messageReceived) {
                throw new IllegalArgumentException("No se recibió respuesta del clienteId en el tiempo esperado");
            }

            Long clienteId = kafkaConsumer.getClienteId();
            String nombreCliente = kafkaConsumer.getNombre();

            if (clienteId == null) {
                throw new IllegalArgumentException("Cliente no encontrado");
            }

            List<Cuenta> cuentaList =  cuentaRepository.findByFechaBetween(clienteId,startDate,endDate);

            return CuentaMapper.toDTOs(cuentaList);

        } catch (InterruptedException e) {
            throw new IllegalArgumentException("Error el generar reporte");
        }

    }

}
