package com.gabrielgua.refeitorio.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreResponse {

    private Long id;
    private String name;
}