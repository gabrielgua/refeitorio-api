package com.gabrielgua.refeitorio.domain.repository;

import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BalanceMovementRepository extends JpaRepository<BalanceMovement, Long> {

    Page<BalanceMovement> findByClientCredential(String credential, Pageable pageable);
}