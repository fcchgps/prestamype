package com.prestamype.factura.infraestructure.adapter.repository;

import com.prestamype.factura.domain.model.dto.request.FinanciamientoRequest;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaEntity,Long> {
    Optional<FacturaEntity> findFirstByCodigoAndRucEmisorAndRucProveedor(String codigo, String rucEmisor, String rucProveedor );

    @Query(value="SELECT f.* from factura f where (:usuario is null or f.usuario=:usuario) and (:codigo is null or f.codigo=:codigo) and (:rucemisor is null or f.rucemisor=:rucemisor) and (:rucproveedor is null or f.rucproveedor=:rucproveedor)", nativeQuery = true)
    public List<FacturaEntity> findByFilters(@Param("usuario") String usuario,
                                      @Param("codigo") String codigo,
                                      @Param("rucemisor") String rucemisor,
                                      @Param("rucproveedor") String rucproveedor
                                      );


    //FacturaEntity findByCodigoAndUsuario(String codigo, String usuario);
    Optional<FacturaEntity> findByCodigoAndUsuario(String codigo, String usuario);
    }
