package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.domain.model.User;
import com.gabrielgua.refeitorio.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
}