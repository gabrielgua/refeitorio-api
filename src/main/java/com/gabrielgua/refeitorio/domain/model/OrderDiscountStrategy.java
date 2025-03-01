package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "order_discount_strategies")
public class OrderDiscountStrategy {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //private Store store;

    @ManyToOne
    private CredentialRange credentialRange;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;

    @OneToMany
    private Set<OrderDiscountRule> discountRules = new HashSet<>();

    @CreationTimestamp
    private OffsetDateTime createdAt;

    public Boolean salaryApplies(BigDecimal salary) {
        return salaryMax.compareTo(salary) >= 0 && salaryMin.compareTo(salary) <= 0;
    }
}