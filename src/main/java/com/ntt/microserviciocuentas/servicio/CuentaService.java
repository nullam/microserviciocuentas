package com.ntt.microserviciocuentas.servicio;

import com.ntt.microserviciocuentas.DTO.ClienteDTO;
import com.ntt.microserviciocuentas.DTO.CuentaDTO;
import com.ntt.microserviciocuentas.entidad.Cuenta;
import com.ntt.microserviciocuentas.repositorio.CuentaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ElySanchez
 */
@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public Cuenta addCuenta(CuentaDTO cuentaDTO) {
        //TODO: insertar la comunicacion para transaccionar con el servicio de cliente (?)
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/clientes/" + cuentaDTO.getIdCliente();
        ClienteDTO cliente;

        try {
            ResponseEntity<ClienteDTO> response = restTemplate.getForEntity(url, ClienteDTO.class);
            cliente = response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Cliente no encontrado");
        }

        if (cliente != null) {
            Cuenta cuentaNueva = new Cuenta();
            cuentaNueva.setEstado(cuentaDTO.getEstado());
            cuentaNueva.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
            cuentaNueva.setSaldoInicial(cuentaDTO.getSaldoInicial());
            cuentaNueva.setTipoCuenta(cuentaDTO.getTipoCuenta());
            //cuentaNueva.setIdCliente(clienteExistente.get().getIdCliente());
            return cuentaRepository.save(cuentaNueva);
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }

    @Transactional
    public Cuenta updateCuenta(Integer id, CuentaDTO cuentaDTO) {
        Cuenta cuentaExistente = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuentaExistente.setEstado(cuentaDTO.getEstado());
        cuentaExistente.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuentaExistente.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuentaExistente.setTipoCuenta(cuentaDTO.getTipoCuenta());

        return cuentaRepository.save(cuentaExistente);
    }

    public boolean deleteCuenta(Integer id) {
        if (!cuentaRepository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada");
        }
        cuentaRepository.deleteById(id);
        return true;
    }

    public Cuenta getCuentaById(Integer id) {
        return cuentaRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Cuenta no encontrada");
        });
    }

    public List<Cuenta> getAllCuenta() {
        return cuentaRepository.findAll();
    }
}
