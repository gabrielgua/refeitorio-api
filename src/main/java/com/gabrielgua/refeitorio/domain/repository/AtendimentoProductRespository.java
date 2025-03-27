package com.gabrielgua.refeitorio.domain.repository;

import com.gabrielgua.refeitorio.domain.model.AtendimentoProduct;
import com.gabrielgua.refeitorio.domain.model.AtendimentoProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtendimentoProductRespository extends JpaRepository<AtendimentoProduct, AtendimentoProductId> {
    List<AtendimentoProduct> findByAtendimentoIdAndIsMandatoryTrue(Long atendimentoId);
}