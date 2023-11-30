package com.prestamype.factura.infraestructure.adapter;

import com.prestamype.factura.domain.port.FacturaPersistencePort;
import com.prestamype.factura.domain.port.UserPersistencePort;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.entity.UserEntity;
import com.prestamype.factura.infraestructure.adapter.repository.FacturaRepository;
import com.prestamype.factura.infraestructure.adapter.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserAdapter implements UserPersistencePort {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);

    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return  userRepository.findByUsername(username);

    }
}
