package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class PageableMapper {

    public <T, K> PagedResponse<K> toModel(Page<T> page, Function<T, K> mapper) {
        List<K> content = page.getContent().stream()
                .map(mapper)
                .toList();

        return PagedResponse.<K>builder()
                .content(content)
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .pageNumber(page.getTotalPages() == 0 ? 0 : page.getNumber() + 1)
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}