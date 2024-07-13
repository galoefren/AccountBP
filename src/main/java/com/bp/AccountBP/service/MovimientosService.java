package com.bp.AccountBP.service;

import com.bp.AccountBP.repository.CuentaRepository;
import com.bp.AccountBP.repository.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientosService {

    @Autowired
    private MovimientosRepository movimientosRepository;
    @Autowired
    private CuentaRepository cuentaRepository;


}
