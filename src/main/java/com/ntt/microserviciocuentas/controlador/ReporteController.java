package com.ntt.microserviciocuentas.controlador;

import com.ntt.microserviciocuentas.DTO.MovimientoDTO;
import com.ntt.microserviciocuentas.servicio.MovimientoService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ElySanchez
 */
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final MovimientoService movimientosService;

    @Autowired
    public ReporteController(MovimientoService movimientosService) {
        this.movimientosService = movimientosService;
    }

//    @GetMapping
//    public ResponseEntity<List<MovimientoDTO>> generarReporte(@RequestParam("fecha") String rangoFechas, @PathVariable Integer idCuenta) {
//        String[] fechas = rangoFechas.split("to");
//        LocalDate fechaInicio = LocalDate.parse(fechas[0]);
//        LocalDate fechaFin = LocalDate.parse(fechas[1]);
//
//        List<MovimientoDTO> reporte = movimientosService.generarReporte(idCuenta, fechaInicio, fechaFin);
//        return ResponseEntity.ok(reporte);
//    }

}
