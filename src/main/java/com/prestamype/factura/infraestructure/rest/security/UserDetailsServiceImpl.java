package com.prestamype.factura.infraestructure.rest.security;


import com.prestamype.factura.infraestructure.adapter.entity.UsuarioEntity;
import com.prestamype.factura.infraestructure.adapter.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuario = usuarioRepository.findByUsername(username);

        if(usuario.isEmpty()){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario.get();
    }

}
