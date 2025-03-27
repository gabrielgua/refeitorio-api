package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.StoreResponse;
import com.gabrielgua.refeitorio.domain.model.Store;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreMapper {

    public StoreResponse toModel(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .build();
    }

    public List<StoreResponse> toCollectionResponse(List<Store> stores) {
        return stores.stream()
                .map(this::toModel)
                .toList();
    }
}