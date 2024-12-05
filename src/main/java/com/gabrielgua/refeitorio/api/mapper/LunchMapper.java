package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.LunchRequest;
import com.gabrielgua.refeitorio.api.model.LunchResponse;
import com.gabrielgua.refeitorio.domain.model.Lunch;
import com.gabrielgua.refeitorio.domain.model.LunchStatus;
import com.gabrielgua.refeitorio.domain.model.Price;
import com.gabrielgua.refeitorio.domain.model.User;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class LunchMapper {
    public Lunch toEntity(LunchRequest request, User user, Price price) {
        return Lunch.builder()
                .user(user)
                .weight(request.getWeight())
                .status(LunchStatus.CREATED)
                .created_at(OffsetDateTime.now())
                .price(price.getFinal_price()).build();
    }

    public LunchResponse toResponse(Lunch lunch, Price price) {
        return LunchResponse.builder()
                .id(lunch.getId())
                .user(lunch.getUser())
                .price(price)
                .weight(lunch.getWeight())
                .created_at(lunch.getCreated_at())
                .build();
    }
}