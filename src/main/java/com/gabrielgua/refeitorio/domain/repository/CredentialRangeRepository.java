package com.gabrielgua.refeitorio.domain.repository;

import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRangeRepository extends JpaRepository<CredentialRange, Long> {

    @Query("SELECT cr FROM CredentialRange cr WHERE :credential BETWEEN cr.min AND cr.max")
    Optional<CredentialRange> findByCredential(Integer credential);
}