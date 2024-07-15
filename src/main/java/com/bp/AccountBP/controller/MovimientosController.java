package com.bp.AccountBP.controller;

import com.bp.AccountBP.dto.MovimientoDTO;
import com.bp.AccountBP.model.Movimientos;
import com.bp.AccountBP.repository.CuentaRepository;
import com.bp.AccountBP.service.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    @Autowired
    private MovimientosService movimientosService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
        return ResponseEntity.ok(movimientosService.getAllMovimientos());
    }

    @GetMapping("/get/{id}")
    public MovimientoDTO getMovimientoById(@PathVariable Long id) {
        MovimientoDTO movimiento = movimientosService.getMovimientoById(id);
        return movimiento;
    }

    @PostMapping("/post")
    public ResponseEntity<MovimientoDTO> createMovimiento(@RequestBody Movimientos movimientos) {
        MovimientoDTO savedMovimiento = movimientosService.createMovimiento(movimientos);
        return ResponseEntity.ok(savedMovimiento);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<MovimientoDTO> updateMovimiento(@PathVariable Long id, @RequestBody Movimientos movimientoDetails) {
        MovimientoDTO updatedMovimiento = movimientosService.updateMovimiento(id, movimientoDetails);
        return ResponseEntity.ok(updatedMovimiento);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        movimientosService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }

   @GetMapping("/search")
    public ResponseEntity<List<MovimientoDTO>> getMovimientosByCuentaNumeroCuenta(@RequestParam String numeroCuenta) {
        List<MovimientoDTO> movimientos = movimientosService.findMovimientosByCuentaNumeroCuenta(numeroCuenta);
        return ResponseEntity.ok(movimientos);
    }
}