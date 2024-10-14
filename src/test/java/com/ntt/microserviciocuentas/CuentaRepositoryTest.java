package com.ntt.microserviciocuentas;


import com.ntt.microserviciocuentas.entidad.Cuenta;
import com.ntt.microserviciocuentas.repositorio.CuentaRepository;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author ElySanchez
 */
//@SpringBootTest(classes = MicroservicioClientePersona.class)
@SpringBootTest
@ActiveProfiles("test")
public class CuentaRepositoryTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    void addCuentaTest() {
        
        Cuenta nuevaCuenta = new Cuenta(123123, "AHORROS", new BigDecimal("185.6"), "ACTIVO", 1);

        assertNotNull(cuentaRepository.save(nuevaCuenta));
    }
}
