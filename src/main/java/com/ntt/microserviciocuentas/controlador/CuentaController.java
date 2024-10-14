package com.ntt.microserviciocuentas.controlador;

import com.ntt.microserviciocuentas.DTO.CuentaDTO;
import com.ntt.microserviciocuentas.entidad.Cuenta;
import com.ntt.microserviciocuentas.servicio.CuentaService;
import com.ntt.microserviciocuentas.servicio.MovimientoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ElySanchez
 */
@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService, MovimientoService movimientoService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerTodasCuentas() {
        List<Cuenta> cuentas = cuentaService.getAllCuenta();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Integer id) {
        try {
            Cuenta cuenta = cuentaService.getCuentaById(id);
            return ResponseEntity.ok(cuenta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody CuentaDTO cuentaDto) {
        try {
            Cuenta nuevaCuenta = cuentaService.addCuenta(cuentaDto);
            return new ResponseEntity<>(nuevaCuenta, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Actualizar una cuenta existente
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Integer id, @RequestBody CuentaDTO cuentaAcualizada) {
        try {
            Cuenta cuenta = cuentaService.updateCuenta(id, cuentaAcualizada);
            return ResponseEntity.ok(cuenta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Eliminar una cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Integer id) {
        if (cuentaService.deleteCuenta(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
