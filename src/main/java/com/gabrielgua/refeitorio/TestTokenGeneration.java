package com.gabrielgua.refeitorio;

import com.gabrielgua.refeitorio.api.security.TokenService;
import com.gabrielgua.refeitorio.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TestTokenGeneration implements ApplicationRunner {

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        var user = userService.findByCredential("13445");
//        var claims = new HashMap<String, Object>();
//
//        claims.put("credential", user.getCredential());
//        claims.put("role", user.getRole());
//        System.out.println(tokenService.generateToken(user, claims));


    }
}