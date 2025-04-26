package com.gabrielgua.refeitorio.domain.repository;

import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CredentialRangeRepository extends JpaRepository<CredentialRange, Long>, JpaSpecificationExecutor<CredentialRange> {

    @Query("SELECT cr FROM CredentialRange cr WHERE :credential BETWEEN cr.min AND cr.max")
    Optional<CredentialRange> findByCredential(Integer credential);

    @Query("""
    SELECT cr FROM CredentialRange cr
    WHERE (:id IS NULL OR cr.id <> :id)
      AND (:min <= cr.max AND :max >= cr.min)
    """)
    List<CredentialRange> findOverlappingRanges(Integer min, Integer max, Long id);
}