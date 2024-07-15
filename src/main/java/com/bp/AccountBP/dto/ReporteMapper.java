package com.bp.AccountBP.dto;

import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.model.Movimientos;

import java.util.List;
import java.util.stream.Collectors;

public class ReporteMapper {
    
    public static ReporteDTO toDTO(Cuenta cuenta,String nombre) {
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setNombre(nombre);
        reporteDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        reporteDTO.setTipoCuenta(cuenta.getTipoCuenta());
        reporteDTO.setSaldoInicial(cuenta.getSaldoInicial());
        reporteDTO.setEstado(cuenta.isEstado());

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

        reporteDTO.setMovimientos(movimientosDTO);

        return reporteDTO;

    }

    public static List<ReporteDTO> toDTOs(List<Cuenta> cuentaList,String nombre) {
        return cuentaList.stream()
                .map(ReporteMapper::toDTO)
                .collect(Collectors.toList());
    }
    
}
