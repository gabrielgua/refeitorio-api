package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.UserNotFoundException;
import com.gabrielgua.refeitorio.domain.model.User;
import com.gabrielgua.refeitorio.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public User findByCredential(String credential) {
        return repository.findById(credential).orElseThrow(() -> new UserNotFoundException(credential));
    }

    @Transactional(readOnly = true)
    public List<User> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User findRandom() {
        var users = listAll();

        var rand = new Random();
        return users.get(rand.nextInt(users.size()));
    }
}