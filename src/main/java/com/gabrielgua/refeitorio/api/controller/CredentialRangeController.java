package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.CredentialRangeMapper;
import com.gabrielgua.refeitorio.api.mapper.PageableMapper;
import com.gabrielgua.refeitorio.api.model.CredentialRangeResponse;
import com.gabrielgua.refeitorio.api.model.PagedResponse;
import com.gabrielgua.refeitorio.domain.filter.CredentialRangeFilter;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import com.gabrielgua.refeitorio.domain.model.PaymentType;
import com.gabrielgua.refeitorio.domain.service.CredentialRangeService;
import com.gabrielgua.refeitorio.domain.service.ReportFileXLSXService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credential-ranges")
public class CredentialRangeController {

    private final CredentialRangeService service;
    private final CredentialRangeMapper mapper;
    private final PageableMapper pageableMapper;
    private final ReportFileXLSXService<CredentialRange> xlsxService;

    @GetMapping
    public PagedResponse<CredentialRangeResponse> findAll(@PageableDefault(size = 10) Pageable pageable, CredentialRangeFilter filter) {
        var page = service.findAll(pageable, filter);
        return pageableMapper.toModel(page, mapper::toResponse);
    }

    @GetMapping("/xlsx")
    public void generateExcel(@PageableDefault(size = 10) Pageable pageable, HttpServletResponse response, CredentialRangeFilter filter) throws IOException {
        Page<CredentialRange> page = service.findAll(pageable, filter);
        var headers = List.of("# ID", "Nome", "Mínimo", "Máximo", "Tipo de pagamento", "Criado em");

        Function<CredentialRange, List<Object>> mapper = credentialRange -> List.of(
                credentialRange.getId(),
                credentialRange.getName(),
                credentialRange.getMin(),
                credentialRange.getMax(),
                credentialRange.getPaymentType().getLabel().toUpperCase(),
                credentialRange.getCreatedAt()
        );

        xlsxService.generateReportFile(
                response,
                "credential_ranges_report",
                "Relatório das Ranges de Crachá",
                headers,
                page.getContent(),
                mapper,
                generateInfoMap(filter));

    }

    private Map<String, String> generateInfoMap(CredentialRangeFilter filter) {
        Map<String, String> map = new HashMap<>();
        if (filter.getPaymentType() != null) {
            map.put("Tipo de pagamento",  filter.getPaymentType().getLabel().toUpperCase());
        }
        return map;
    }


}