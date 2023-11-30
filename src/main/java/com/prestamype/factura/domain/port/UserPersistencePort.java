package com.prestamype.factura.domain.port;

import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.entity.UserEntity;

import java.util.Optional;

public interface UserPersistencePort {
   UserEntity saveUser(UserEntity factura);
   Optional<UserEntity> findByUsername(String username);
   }
