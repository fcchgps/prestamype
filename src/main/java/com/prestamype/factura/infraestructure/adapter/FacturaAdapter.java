package com.prestamype.factura.infraestructure.adapter;

import com.prestamype.factura.domain.model.dto.request.FinanciamientoRequest;
import com.prestamype.factura.domain.port.FacturaPersistencePort;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.entity.FinanciamientoEntity;
import com.prestamype.factura.infraestructure.adapter.repository.FacturaRepository;
import com.prestamype.factura.infraestructure.adapter.repository.FinanciamientoRepository;
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
    FacturaRepository facturaRepository;

    @Autowired
    FinanciamientoRepository financiamientoRepository;

    @Override
    public FacturaEntity saveFactura(FacturaEntity factura) {

        return facturaRepository.save(factura);
    }

    @Override
    public Optional<FacturaEntity> findFirstByCodigoAndRucEmisorAndRucProveedor(String codigo, String rucEmisor, String rucProveedor) {
        return  facturaRepository.findFirstByCodigoAndRucEmisorAndRucProveedor(codigo,rucEmisor,rucProveedor);
    }

    @Override
    public List<FacturaEntity> consultarFacturasPorUsuario(String usuario,String codigo,String rucEmisor,String rucProveedor) {

        return facturaRepository.findByFilters( usuario, codigo, rucEmisor, rucProveedor);
    }

    @Override
    public Optional<FinanciamientoEntity> findFirstByFacturaAndUsuario(String codigo, String usuario) {
        return  financiamientoRepository.findFirstByFacturaAndUsuario(codigo,usuario);
    }

    @Override
    public void saveFinanciamiento(FinanciamientoEntity financiamientoEntity) {
        financiamientoRepository.save( financiamientoEntity);
    }

    @Override
    public FacturaEntity findByCodigoAndUsuario(String codigo, String usuario) {
        return  facturaRepository.findByCodigoAndUsuario(codigo,usuario);
    }

    @Override
    public List<FinanciamientoEntity> consultarFinanciamientoPorUsuario(String usuario, String factura, String rucProveedor) {
       return financiamientoRepository.consultarFinanciamientoPorUsuario(usuario,factura,rucProveedor);
    }

    @Override
    public List<FinanciamientoEntity> consultarSolicitudFinanciamiento(String factura, String rucProveedor) {
        return financiamientoRepository.consultarSolicitudFinanciamiento(factura,rucProveedor);
    }




    @Override
    public Optional<FinanciamientoEntity> findById(Long idFinanciamiento) {
        return financiamientoRepository.findById(idFinanciamiento);
    }


}
