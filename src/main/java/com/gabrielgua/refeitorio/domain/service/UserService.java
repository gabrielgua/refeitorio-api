package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.UserNotFoundException;
import com.gabrielgua.refeitorio.domain.model.User;
import com.gabrielgua.refeitorio.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public User findByCredential(String credential) {
        return repository.findById(credential).orElseThrow(() -> new UserNotFoundException(credential));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
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

    @Transactional(readOnly = true)
    public BigDecimal getDiscount(User user) {
        //TODO: implement discount logic
        return BigDecimal.valueOf(0.25);
    }
}