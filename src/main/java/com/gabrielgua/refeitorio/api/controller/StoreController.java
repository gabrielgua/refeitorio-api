package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.StoreMapper;
import com.gabrielgua.refeitorio.api.model.StoreResponse;
import com.gabrielgua.refeitorio.domain.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;
    private final StoreMapper storeMapper;

    @GetMapping
    public List<StoreResponse> getStores() {
        return storeMapper.toCollectionResponse(storeService.findAll());
    }
}