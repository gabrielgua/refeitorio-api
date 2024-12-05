package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.UserNotFoundException;
import com.gabrielgua.refeitorio.domain.model.User;
import com.gabrielgua.refeitorio.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public User findByCredential(UUID credential) {
        return repository.findById(credential).orElseThrow(() -> new UserNotFoundException(credential));
    }
}