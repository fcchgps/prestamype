package com.prestamype.factura.application.usecases;

import com.prestamype.factura.infraestructure.adapter.entity.UsuarioEntity;
import com.prestamype.factura.infraestructure.adapter.entity.UsuarioRolEntity;

import java.util.Set;

public interface UsuarioService {

    public UsuarioEntity guardarUsuario(UsuarioEntity usuario, Set<UsuarioRolEntity> usuarioRoles) throws Exception;

    public UsuarioEntity obtenerUsuario(String username);

    public void eliminarUsuario(Long usuarioId);
}
