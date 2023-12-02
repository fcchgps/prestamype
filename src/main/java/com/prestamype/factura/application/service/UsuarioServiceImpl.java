package com.prestamype.factura.application.service;


import com.prestamype.factura.application.usecases.UsuarioService;
import com.prestamype.factura.infraestructure.adapter.entity.UsuarioEntity;
import com.prestamype.factura.infraestructure.adapter.entity.UsuarioRolEntity;
import com.prestamype.factura.infraestructure.adapter.repository.RolRepository;
import com.prestamype.factura.infraestructure.adapter.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public UsuarioEntity guardarUsuario(UsuarioEntity usuario, Set<UsuarioRolEntity> usuarioRoles) throws Exception {
        Optional<UsuarioEntity> usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());

        if(!usuarioLocal.isEmpty()){
            System.out.println("El usuario ya existe");
            throw new NoSuchElementException("El usuario ya esta presente");
        }
        else{
            for(UsuarioRolEntity usuarioRol:usuarioRoles){
                rolRepository.save(usuarioRol.getRol());
            }
            usuario.getUsuarioRoles().addAll(usuarioRoles);
           // usuarioLocal = usuarioRepository.save(usuario);
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioEntity obtenerUsuario(String username) {

        return usuarioRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public void eliminarUsuario(Long usuarioId) {

        usuarioRepository.deleteById(usuarioId);
    }

}