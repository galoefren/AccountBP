package com.bp.AccountBP.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CuentaDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;
    private List<MovimientoDTO> movimientos;
}
