package com.gabrielgua.refeitorio.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PagedResponse<T> {

    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNumber;
    private Integer size;
    private Boolean hasNext;
    private Boolean hasPrevious;
}