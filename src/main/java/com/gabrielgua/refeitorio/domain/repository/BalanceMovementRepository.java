package com.gabrielgua.refeitorio.domain.repository;

import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceMovementRepository extends JpaRepository<BalanceMovement, Long> {

    List<BalanceMovement> findByClientCredential(String credential);
}