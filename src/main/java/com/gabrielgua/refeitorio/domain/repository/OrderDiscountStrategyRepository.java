package com.gabrielgua.refeitorio.domain.repository;

import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import com.gabrielgua.refeitorio.domain.model.OrderDiscountStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDiscountStrategyRepository extends JpaRepository<OrderDiscountStrategy, Long> {

    List<OrderDiscountStrategy> findByCredentialRange(CredentialRange credentialRange);


}