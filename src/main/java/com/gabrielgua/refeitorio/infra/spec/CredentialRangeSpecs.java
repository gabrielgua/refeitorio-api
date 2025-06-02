package com.gabrielgua.refeitorio.infra.spec;

import com.gabrielgua.refeitorio.domain.filter.CredentialRangeFilter;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class CredentialRangeSpecs {

    public static Specification<CredentialRange> filter(CredentialRangeFilter filter) {
        return (root, query, criteriaBuilder) ->  {
            var predicates = new ArrayList<Predicate>();

            if (filter.getPaymentType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("paymentType"), filter.getPaymentType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}