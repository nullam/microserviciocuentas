package com.ntt.microserviciocuentas.servicio;

import com.ntt.microserviciocuentas.entidad.Cuenta;
import com.ntt.microserviciocuentas.entidad.Movimiento;
import com.ntt.microserviciocuentas.repositorio.MovimientoRepository;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ElySanchez
 */
@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private CuentaService cuentaServicio;

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional
    public Movimiento addMovimiento(Integer idCuenta, String tipoMovimiento, BigDecimal valor) {
        Cuenta cuenta = cuentaServicio.getCuentaById(idCuenta);
        List<Movimiento> listaMovimiento = movimientoRepository.findByIdCuenta(cuenta);

        if (listaMovimiento.isEmpty()) {
            Cuenta cuentaExistente = cuentaServicio.getCuentaById(idCuenta);
            Movimiento movimientoNuevo;
            if (cuentaExistente == null) {
                throw new RuntimeException("Cuenta no existente");
            }

            movimientoNuevo = new Movimiento();
            if (tipoMovimiento.toUpperCase().equals("DEBITO")) {
                BigDecimal tieneSaldoDisponible = saldoDisponibleParaDebito(cuentaExistente.getSaldoInicial(), valor);

                if ( cuentaExistente.getSaldoInicial().compareTo(valor) > 0) {
                    movimientoNuevo.setTipoMovimiento("DEBITO");
                    movimientoNuevo.setValor(tieneSaldoDisponible);
                } else {
                    throw new RuntimeException("Saldo no disponible para transaccion");
                }
            } else if (tipoMovimiento.toUpperCase().equals("CREDITO")) {
                BigDecimal acreditarSaldo = cuentaExistente.getSaldoInicial().add(valor);
                movimientoNuevo.setSaldo(acreditarSaldo);
                movimientoNuevo.setTipoMovimiento("CREDITO");
            }
            movimientoNuevo.setFecha(new Date());
            movimientoNuevo.setIdCuenta(cuentaExistente);
            return movimientoRepository.save(movimientoNuevo);
        } else {
            Optional<Movimiento> ultimoMovimiento = listaMovimiento.stream().max(Comparator.comparing(Movimiento::getFecha));

            if (ultimoMovimiento.isPresent()) {
                Movimiento nuevoMovimiento = new Movimiento();
                if (tipoMovimiento.toUpperCase().equals("DEBITO")) {
                    BigDecimal tieneSaldoDisponible = saldoDisponibleParaDebito(ultimoMovimiento.get().getSaldo(), valor);

                    if (ultimoMovimiento.get().getSaldo().compareTo(valor) > 0) {
                        nuevoMovimiento.setTipoMovimiento("DEBITO");
                        nuevoMovimiento.setValor(tieneSaldoDisponible);
                    } else {
                        throw new RuntimeException("Saldo no disponible para transaccion");
                    }
                } else if (tipoMovimiento.toUpperCase().equals("CREDITO")) {
                    BigDecimal acreditarSaldo = ultimoMovimiento.get().getSaldo().add(valor);
                    nuevoMovimiento.setSaldo(acreditarSaldo);
                    nuevoMovimiento.setTipoMovimiento("CREDITO");
                }
            } else {
                throw new RuntimeException("La cuenta no tiene movimientos");
            }
            return ultimoMovimiento.get();
        }
    }

    public BigDecimal saldoDisponibleParaDebito(BigDecimal saldoInicial, BigDecimal valorTrx) {
        // Realizar la resta (saldo inicial - monto a retirar)
        BigDecimal saldoFinal = saldoInicial.subtract(valorTrx);
        return saldoFinal;
    }

    public void updateMovimiento(Movimiento movimiento) {
        movimientoRepository.save(movimiento);
    }

    public void deleteMovimiento(Integer id) {
        movimientoRepository.deleteById(id);
    }

    public Movimiento getMovimientoById(Integer id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    public List<Movimiento> getAllMovimientosPorCuenta(Integer idCuenta) {
        Cuenta cuenta = cuentaServicio.getCuentaById(idCuenta);
        return movimientoRepository.findByIdCuenta(cuenta);
    }

    //reporte
//    public List<MovimientoDTO> generarReporte(Integer idCuenta, LocalDate fechaInicio, LocalDate fechaFin) {
//        List<MovimientoDTO> datosReporte = new ArrayList<>();
//
//        Cuenta cuenta = cuentaServicio.getCuentaById(idCuenta);
//        Cliente cliente = clieneServicio.getClienteById(cuenta.getIdCliente()).get();
//
//        Date dateIni = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        Date dateFin = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        List<Movimientos> listaMovimientos = movimientoRepository.findByFechayCuenta(dateIni, dateFin, cuenta);
//
//        datosReporte = listaMovimientos.stream()
//                .map(movimiento -> new MovimientosDTO(
//                movimiento.getFecha(),
//                movimiento.getTipoMovimiento().toString(),
//                movimiento.getValor(),
//                movimiento.getSaldo(),
//                movimiento.getIdCuenta().getNumeroCuenta()))
//                .collect(Collectors.toList());
//        return datosReporte;
//    }
}
