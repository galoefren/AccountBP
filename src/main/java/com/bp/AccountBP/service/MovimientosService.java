package com.bp.AccountBP.service;

import com.bp.AccountBP.dto.MovimientoDTO;
import com.bp.AccountBP.dto.MovimientoMapper;
import com.bp.AccountBP.handleError.InsufficientBalanceException;
import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.model.Movimientos;
import com.bp.AccountBP.repository.CuentaRepository;
import com.bp.AccountBP.repository.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.List;


@Service
public class MovimientosService {

    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaConsumer kafkaConsumer;

    public MovimientoDTO createMovimiento(Movimientos movimiento) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        // Validar tipoMovimiento
        String tipoMovimiento = movimiento.getTipoMovimiento().toLowerCase();
        if (!tipoMovimiento.equals("deposito") && !tipoMovimiento.equals("retiro")) {
            throw new IllegalArgumentException("Tipo de movimiento inválido. Debe ser 'deposito' o 'retiro'.");
        }

        // Calcular el nuevo saldo
        BigDecimal nuevoSaldo;
        if (tipoMovimiento.equals("deposito")) {
            nuevoSaldo = cuenta.getSaldoInicial().add(movimiento.getValor());
        } else {
            nuevoSaldo = cuenta.getSaldoInicial().subtract(movimiento.getValor());
        }

        // Verificar si el saldo es suficiente para un retiro
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        // Actualizar el saldo de la cuenta
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        // Actualizar el saldo del movimiento
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);
        movimientosRepository.save(movimiento);
        return MovimientoMapper.toDTO(movimiento);
    }

    public MovimientoDTO updateMovimiento(Long id, Movimientos movimientoDetails) {
        Movimientos movimiento = movimientosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        BigDecimal nuevoValor = movimiento.getValor();
        BigDecimal nuevoCambio = movimientoDetails.getValor();
        BigDecimal nuevoSaldo = new BigDecimal(0);
        if(movimiento.getTipoMovimiento().contains("deposito")){
            nuevoSaldo = movimiento.getSaldo().subtract(nuevoValor);
            nuevoSaldo = nuevoSaldo.add(nuevoCambio);
        }else {
            nuevoSaldo = movimiento.getSaldo().add(nuevoValor);
            nuevoSaldo = nuevoSaldo.subtract(nuevoCambio);
        }

        movimiento.setFecha(movimientoDetails.getFecha());
        movimiento.setTipoMovimiento(movimientoDetails.getTipoMovimiento());
        movimiento.setValor(movimientoDetails.getValor());
        movimiento.setSaldo(nuevoSaldo);
        cuenta.setSaldoInicial(nuevoSaldo);
        movimientosRepository.save(movimiento);
        cuentaRepository.save(cuenta);
        return MovimientoMapper.toDTO(movimiento);
    }

    public List<MovimientoDTO> getAllMovimientos() {

        List<Movimientos> movimientosList = movimientosRepository.findAll();
        return MovimientoMapper.toDTOs(movimientosList);

    }

    public MovimientoDTO getMovimientoById(Long id) {
        Movimientos movimientos =  movimientosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));
        return MovimientoMapper.toDTO(movimientos);
    }

    public void deleteMovimiento(Long id) {
        Movimientos movimiento = movimientosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));
        Cuenta cuenta = movimiento.getCuenta();
        BigDecimal valor = cuenta.getSaldoInicial().subtract(movimiento.getValor());
        cuenta.setSaldoInicial(valor);
        cuentaRepository.save(cuenta);
        movimientosRepository.delete(movimiento);
    }

    public List<MovimientoDTO> findMovimientosByCuentaNumeroCuenta(String numeroCuenta) {
        List<Movimientos> movimientosList = movimientosRepository.findByCuentaNumeroCuenta(numeroCuenta);
        return MovimientoMapper.toDTOs(movimientosList);
    }



}
