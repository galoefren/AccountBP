package com.bp.AccountBP.controller;

import com.bp.AccountBP.dto.CuentaDTO;
import com.bp.AccountBP.model.Cuenta;
import com.bp.AccountBP.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/getAll")
    public List<CuentaDTO> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/get/{id}")
    public CuentaDTO getCuentaById(@PathVariable Long id) {
        return cuentaService.getCuentaById(id);
    }

    @PostMapping("/post")
    public String createCuenta(@RequestBody Cuenta cuenta,@RequestParam String numeroCedula) {
        return cuentaService.createCuenta(cuenta,numeroCedula);
    }

    @PutMapping("/put/{id}")
    public CuentaDTO  updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuentaDetails) {
        return cuentaService.updateCuenta(id, cuentaDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
    }

    @GetMapping("/reporte")
    public List<CuentaDTO> createReporte(@RequestParam String numeroCedula, @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return cuentaService.findMovimientosBetweenDates(numeroCedula,startDate,endDate);
    }

}
