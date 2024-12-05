package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.LunchMapper;
import com.gabrielgua.refeitorio.api.model.LunchConfirmRequest;
import com.gabrielgua.refeitorio.api.model.LunchRequest;
import com.gabrielgua.refeitorio.api.model.LunchResponse;
import com.gabrielgua.refeitorio.domain.model.Lunch;
import com.gabrielgua.refeitorio.domain.model.LunchStatus;
import com.gabrielgua.refeitorio.domain.service.LunchService;
import com.gabrielgua.refeitorio.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lunch")
public class LunchController {

    private final UserService userService;
    private final LunchService lunchService;
    private final LunchMapper lunchMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LunchResponse readLunch(@Valid @RequestBody LunchRequest request) {
        var user = userService.findByCredential(request.getCredential());
        var price = lunchService.calculatePrice(user.getSalary(), request.getWeight());

        var lunch = lunchMapper.toEntity(request, user, price);
        return lunchMapper.toResponse(lunchService.save(lunch), price);
    }

    @PutMapping("/confirm/{lunchId}")
    public ResponseEntity<?> confirmLunch(@PathVariable Long lunchId) {
        var lunch = lunchService.findById(lunchId);
        lunchService.confirm(lunch);

        return ResponseEntity.ok("Lunch confirmed!");
    }

    @DeleteMapping("/cancel/{lunchId}")
    public ResponseEntity<?> cancelLunch(@PathVariable Long lunchId) {
        var lunch = lunchService.findById(lunchId);
        lunchService.cancel(lunch);

        return ResponseEntity.ok("Lunch canceled!");
    }
}