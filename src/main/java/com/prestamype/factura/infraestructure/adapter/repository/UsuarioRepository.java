package com.prestamype.factura.infraestructure.adapter.repository;

import com.prestamype.factura.infraestructure.adapter.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {
    Optional <UsuarioEntity> findByUsername(String username);

}
