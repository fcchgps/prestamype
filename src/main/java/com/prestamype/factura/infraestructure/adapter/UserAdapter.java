package com.prestamype.factura.infraestructure.adapter;

import com.prestamype.factura.domain.port.UserPersistencePort;
import com.prestamype.factura.infraestructure.adapter.entity.UsuarioEntity;
import com.prestamype.factura.infraestructure.adapter.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserAdapter implements UserPersistencePort {

    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public UsuarioEntity saveUser(UsuarioEntity user) {
        return usuarioRepository.save(user);

    }

    @Override
    public Optional<UsuarioEntity> findByUsername(String username) {
        return  usuarioRepository.findByUsername(username);

    }
}
