package com.prestamype.factura.infraestructure.adapter.repository;

import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<FacturaEntity,Long> {
    Optional<FacturaEntity> findFirstByCodigoAndRucEmisorAndRucProveedor(String codigo, String rucEmisor, String rucProveedor );

}
