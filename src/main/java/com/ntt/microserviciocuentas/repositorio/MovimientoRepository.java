package com.ntt.microserviciocuentas.repositorio;

import com.ntt.microserviciocuentas.entidad.Cuenta;
import com.ntt.microserviciocuentas.entidad.Movimiento;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ElySanchez
 */
@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    List<Movimiento> findByIdCuenta(Cuenta idCuenta);

}
