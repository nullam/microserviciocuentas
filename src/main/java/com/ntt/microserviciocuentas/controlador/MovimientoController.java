package com.ntt.microserviciocuentas.controlador;

import com.ntt.microserviciocuentas.DTO.MovimientoDTO;
import com.ntt.microserviciocuentas.entidad.Movimiento;
import com.ntt.microserviciocuentas.servicio.MovimientoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ElySanchez
 */
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientosService;

    @Autowired
    public MovimientoController(MovimientoService movimientosService) {
        this.movimientosService = movimientosService;
    }

    //obtener movimientos por cuenta
    @GetMapping
    public ResponseEntity<List<Movimiento>> obtenerTodosMovimientos(@PathVariable Integer idCuenta) {
        List<Movimiento> movimientos = movimientosService.getAllMovimientosPorCuenta(idCuenta);
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody MovimientoDTO movimientoDTO, @PathVariable Integer idCuenta) {
        try {
            Movimiento movimiento = movimientosService.addMovimiento(idCuenta, movimientoDTO.getTipoMovimiento(), movimientoDTO.getValor());
            return ResponseEntity.ok(movimiento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
