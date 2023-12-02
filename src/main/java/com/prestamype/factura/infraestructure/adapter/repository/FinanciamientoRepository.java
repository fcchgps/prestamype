package com.prestamype.factura.infraestructure.adapter.repository;

import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.entity.FinanciamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinanciamientoRepository extends JpaRepository<FinanciamientoEntity,Long> {
    Optional<FinanciamientoEntity> findFirstByFacturaAndUsuario(String codigo, String usuario );
    @Query(value="SELECT fi.* from financiamiento fi INNER JOIN factura fa on fi.usuario=fa.usuario and fi.factura=fa.codigo where (:usuario is null or fi.usuario=:usuario) and (:factura is null or fi.factura=:factura) and (:rucproveedor is null or fa.rucproveedor=:rucproveedor)" ,nativeQuery = true)
    public List<FinanciamientoEntity> consultarFinanciamientoPorUsuario(@Param("usuario") String usuario,
                                             @Param("factura") String factura,
                                             @Param("rucproveedor") String rucproveedor);

    @Query(value="SELECT fi.* from financiamiento fi INNER JOIN factura fa on fi.usuario=fa.usuario and fi.factura=fa.codigo where (:factura is null or fi.factura=:factura) and (:rucproveedor is null or fa.rucproveedor=:rucproveedor)" ,nativeQuery = true)
    public List<FinanciamientoEntity> consultarSolicitudFinanciamiento(@Param("factura") String factura,
                                                                       @Param("rucproveedor") String rucproveedor);

    }

