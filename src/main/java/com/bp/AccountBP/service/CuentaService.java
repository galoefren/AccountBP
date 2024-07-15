package com.bp.AccountBP.service;

import com.bp.AccountBP.dto.CuentaDTO;
import com.bp.AccountBP.dto.CuentaMapper;
import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return cuentas.stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());

    }

    public CuentaDTO  getCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con Id: " + id));
        return CuentaMapper.toDTO(cuenta);

    }

    public String createCuenta(Cuenta cuenta,String numeroCedula) {

        // Enviar mensaje a Kafka para buscar clienteId por numeroCedula
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

}
