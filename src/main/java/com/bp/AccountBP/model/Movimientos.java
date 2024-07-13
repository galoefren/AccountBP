package com.bp.AccountBP.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Movimientos", schema = "schema2")
@Data
@Slf4j
public class Movimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

}
