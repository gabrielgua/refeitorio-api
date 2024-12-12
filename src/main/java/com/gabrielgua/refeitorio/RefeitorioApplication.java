package com.gabrielgua.refeitorio;

import com.gabrielgua.refeitorio.api.security.TokenService;
import com.gabrielgua.refeitorio.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RefeitorioApplication {
	public static void main(String[] args) {SpringApplication.run(RefeitorioApplication.class, args);}
}