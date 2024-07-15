package com.bp.AccountBP.dto;

import com.bp.AccountBP.model.Movimientos;

import java.util.List;
import java.util.stream.Collectors;

public class MovimientoMapper {
    public static MovimientoDTO toDTO(Movimientos movimientos) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setId(movimientos.getId());
        movimientoDTO.setFecha(movimientos.getFecha());
        movimientoDTO.setTipoMovimiento(movimientos.getTipoMovimiento());
        movimientoDTO.setValor(movimientos.getValor());
        movimientoDTO.setSaldo(movimientos.getSaldo());

        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setNumeroCuenta(movimientos.getCuenta().getNumeroCuenta());
        cuentaDTO.setTipoCuenta(movimientos.getCuenta().getTipoCuenta());
        cuentaDTO.setSaldoInicial(movimientos.getCuenta().getSaldoInicial());
        cuentaDTO.setEstado(movimientos.getCuenta().isEstado());

        return movimientoDTO;
    }

    public static List<MovimientoDTO> toDTOs(List<Movimientos> movimientosList) {
        return movimientosList.stream()
                .map(MovimientoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
