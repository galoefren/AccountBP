package com.bp.AccountBP.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MovimientoDTO {

    private Long id;
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
}
