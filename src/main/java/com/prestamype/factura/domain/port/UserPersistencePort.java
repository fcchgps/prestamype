package com.prestamype.factura.domain.port;

import com.prestamype.factura.infraestructure.adapter.entity.UsuarioEntity;

import java.util.Optional;

public interface UserPersistencePort {
   UsuarioEntity saveUser(UsuarioEntity factura);
   Optional<UsuarioEntity> findByUsername(String username);
   }
