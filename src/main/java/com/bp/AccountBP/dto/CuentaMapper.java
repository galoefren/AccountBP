package com.bp.AccountBP.dto;

import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.model.Movimientos;

import java.util.List;
import java.util.stream.Collectors;

public class CuentaMapper {

    public static CuentaDTO toDTO(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setClienteid(cuenta.getClienteid());
        cuentaDTO.setId(cuenta.getId());
        cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaDTO.setEstado(cuenta.isEstado());

        List<MovimientoDTO> movimientosDTO = cuenta.getMovimientos().stream()
                .map(movimiento -> {
                    MovimientoDTO movimientoDTO = new MovimientoDTO();
                    movimientoDTO.setId(movimiento.getId());
                    movimientoDTO.setFecha(movimiento.getFecha());
                    movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
                    movimientoDTO.setValor(movimiento.getValor());
                    movimientoDTO.setSaldo(movimiento.getSaldo());
                    return movimientoDTO;
                })
                .collect(Collectors.toList());

        cuentaDTO.setMovimientos(movimientosDTO);

        return cuentaDTO;

    }

    public static List<CuentaDTO> toDTOs(List<Cuenta> cuentaList) {
        return cuentaList.stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

}