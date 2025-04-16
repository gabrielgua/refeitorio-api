package com.gabrielgua.refeitorio.infra.spec;

import com.gabrielgua.refeitorio.domain.filter.BalanceMovementFilter;
import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class BalanceMovementSpecs {

    public static Specification<BalanceMovement> filter(BalanceMovementFilter filter) {
        return (root, query, criteriaBuilder) ->  {
            var predicates = new ArrayList<Predicate>();

            if (filter.getDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), filter.getDateFrom()));
            }

            if (filter.getDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), filter.getDateTo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}