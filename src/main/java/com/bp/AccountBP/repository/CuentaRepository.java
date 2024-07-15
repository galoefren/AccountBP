package com.bp.AccountBP.repository;

import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.model.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta,Long> {

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    @Query("SELECT c FROM Cuenta c JOIN c.movimientos m WHERE c.clienteid = :clientId AND m.fecha BETWEEN :startDate AND :endDate")
    List<Cuenta> findByFechaBetween(@Param("clientId") Long clientId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
