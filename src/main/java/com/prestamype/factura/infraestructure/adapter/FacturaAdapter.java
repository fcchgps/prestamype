package com.prestamype.factura.infraestructure.adapter;

import com.prestamype.factura.domain.port.FacturaPersistencePort;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.repository.FacturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacturaAdapter implements FacturaPersistencePort {

    @Autowired
    FacturaRepository facturaRepository1;

    @Override
    public FacturaEntity saveFactura(FacturaEntity factura) {

        return facturaRepository1.save(factura);
    }

    @Override
    public Optional<FacturaEntity> findFirstByCodigoAndRucEmisorAndRucProveedor(String codigo, String rucEmisor, String rucProveedor) {

        return  facturaRepository1.findFirstByCodigoAndRucEmisorAndRucProveedor(codigo,rucEmisor,rucProveedor);
    }


    @Override
    public List<FacturaEntity> consultarFacturasPorUsuario(String usuario,String codigo,String rucEmisor,String rucProveedor) {
        System.out.println("-------------------------------------------");
        System.out.println(usuario+'-'+codigo+'-'+rucEmisor+'-'+rucProveedor);
        return facturaRepository1.findByFilters( usuario, codigo, rucEmisor, rucProveedor);

    }


}
