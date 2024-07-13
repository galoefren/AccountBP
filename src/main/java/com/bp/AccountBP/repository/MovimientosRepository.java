package com.bp.AccountBP.repository;

import com.bp.AccountBP.model.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {


}
