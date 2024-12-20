package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.UserMapper;
import com.gabrielgua.refeitorio.api.model.UserModel;
import com.gabrielgua.refeitorio.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping("/{credential}")
    private UserModel getByCredential(@PathVariable String credential) {
        return mapper.toAtendimentoResponse(userService.findByCredential(credential));
    }
}