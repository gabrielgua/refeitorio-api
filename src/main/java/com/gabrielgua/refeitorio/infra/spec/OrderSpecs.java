package com.gabrielgua.refeitorio.infra.spec;

import com.gabrielgua.refeitorio.domain.filter.OrderFilter;
import com.gabrielgua.refeitorio.domain.model.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OrderSpecs {

    public static Specification<Order> filtering(OrderFilter filter) {
        return (root, query, criteriaBuilder) ->  {
            var predicates = new ArrayList<Predicate>();

            if (filter.getDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filter.getDateFrom()));
            }

            if (filter.getDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filter.getDateTo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}