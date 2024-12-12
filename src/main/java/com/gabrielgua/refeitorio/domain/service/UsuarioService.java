package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.UserNotFoundException;
import com.gabrielgua.refeitorio.domain.model.Usuario;
import com.gabrielgua.refeitorio.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    public Usuario findByCredential(String credential) {
        return repository.findById(credential).orElseThrow(() -> new UserNotFoundException(credential));
    }

    @Transactional(readOnly = true)
    public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional(readOnly = true)
    public List<Usuario> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findRandom() {
        var users = listAll();

        var rand = new Random();
        return users.get(rand.nextInt(users.size()));
    }
}