package com.prestamype.factura.infraestructure.adapter.repository;

import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
