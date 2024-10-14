package com.ntt.microserviciocuentas.repositorio;

import com.ntt.microserviciocuentas.entidad.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ElySanchez
 */
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

}
