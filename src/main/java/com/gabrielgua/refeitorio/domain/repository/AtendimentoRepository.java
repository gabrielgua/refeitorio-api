package com.gabrielgua.refeitorio.domain.repository;

import com.gabrielgua.refeitorio.domain.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    @Query("""
    SELECT a FROM Atendimento a
    WHERE
        (:currentTime BETWEEN a.timeStart AND a.timeEnd)
        OR
        (a.timeStart > a.timeEnd AND (:currentTime >= a.timeStart OR :currentTime <= a.timeEnd))
    """)
    Optional<Atendimento> findCurrent(LocalTime currentTime);

    Optional<Atendimento> findFirstByTimeStartAfterOrderByTimeStartAsc(LocalTime currentTime);
    Optional<Atendimento> findFirstByTimeStartBeforeOrderByTimeStartAsc(LocalTime currentTime);

    Optional<Atendimento> findFirstByTimeEndBeforeOrderByTimeEndDesc(LocalTime currentTime);
    Optional<Atendimento> findFirstByTimeEndAfterOrderByTimeEndDesc(LocalTime currentTime);

}