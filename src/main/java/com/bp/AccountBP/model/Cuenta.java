package com.bp.AccountBP.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Cuenta", schema = "account_schema")
@Data
@Slf4j
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCuenta;
    private Long clienteid;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Movimientos> movimientos;

}