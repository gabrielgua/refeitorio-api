package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.BalanceMovementMapper;
import com.gabrielgua.refeitorio.api.mapper.PageableMapper;
import com.gabrielgua.refeitorio.api.model.BalanceMovementRequest;
import com.gabrielgua.refeitorio.api.model.BalanceMovementResponse;
import com.gabrielgua.refeitorio.api.model.PagedResponse;
import com.gabrielgua.refeitorio.domain.filter.BalanceMovementFilter;
import com.gabrielgua.refeitorio.domain.filter.OrderFilter;
import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import com.gabrielgua.refeitorio.domain.model.BalanceMovementType;
import com.gabrielgua.refeitorio.domain.service.BalanceMovementService;
import com.gabrielgua.refeitorio.domain.service.ClientBalanceService;
import com.gabrielgua.refeitorio.domain.service.ClientService;
import com.gabrielgua.refeitorio.domain.service.ReportFileXLSXService;
import com.gabrielgua.refeitorio.infra.spec.BalanceMovementSpecs;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients/{credential}/balance-movements")
public class BalanceMovementController {

    private final BalanceMovementService service;
    private final BalanceMovementMapper mapper;
    private final ClientService clientService;
    private final ClientBalanceService clientBalanceService;
    private final PageableMapper pageableMapper;

    private final ReportFileXLSXService<BalanceMovement> xlsxService;


    @GetMapping
    public PagedResponse<BalanceMovementResponse> listByCredential(@PathVariable String credential, @Valid BalanceMovementFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        var client = clientService.findByCredential(credential);
        return pageableMapper.toModel(createPage(client.getCredential(), filter, pageable), mapper::toResponse);
    }

    @PostMapping
    public BalanceMovementResponse adjustBalance(@PathVariable String credential, @Valid @RequestBody BalanceMovementRequest request) {
        var client = clientService.findByCredential(credential);
        return mapper.toResponse(clientBalanceService.adjust(client, request.getAmount()));
    }

    @GetMapping("/xlsx")
    public void gerateBalanceMovementsXLSXReportFile(@PathVariable String credential, HttpServletResponse response, BalanceMovementFilter filter, @PageableDefault(size = 10) Pageable pageable) throws IOException {
        var client = clientService.findByCredential(credential);
        Page<BalanceMovement> page = createPage(client.getCredential(),  filter, pageable);

        List<String> headers = List.of("# ID", "Crachá", "Saldo anterior", "Saldo atual", "Valor", "Motivo", "Data");

        Function<BalanceMovement, List<Object>> mapper = mov -> List.of(
                mov.getId(),
                mov.getClient().getCredential(),
                mov.getOldBalance(),
                mov.getNewBalance(),
                mov.getAmount(),
                mov.getType().equals(BalanceMovementType.ADJUSTMENT) ? "AJUSTE" : "COMPRA",
                mov.getTimestamp()
        );

        xlsxService.generateReportFile(
                response,
                "balance_movements_report",
                "Histórico de movimentações de Saldo",
                headers,
                page.getContent(),
                mapper,
                generateInfoMap(filter));
    }

    private Map<String, String> generateInfoMap(BalanceMovementFilter filter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
        Map<String, String> info = new LinkedHashMap<>();

        if (filter.getDateFrom() != null) {
            info.put("De", filter.getDateFrom().format(formatter));
        }

        if (filter.getDateTo() != null) {
            info.put("Até", filter.getDateTo().format(formatter));
        }

        if (filter.getSort() != null) {
            info.put("Exibir", filter.getSort().equals("DESC") ? "Mais novos primeiro" : "Mais antigos primeiro");
        }

        return info;
    }

    private Page<BalanceMovement> createPage(String credential, BalanceMovementFilter filter, Pageable pageable) {
        return service.findByClientCredential(credential, sortPageable(pageable, filter), BalanceMovementSpecs.filter(filter));
    }

    private Pageable sortPageable(Pageable pageable, BalanceMovementFilter filter) {
        Sort.Direction direction;
        //handles invalid sort field and defaults it to DESC
        try {
            direction = Sort.Direction.fromString(filter.getSort());
        } catch (IllegalArgumentException | NullPointerException e) {
            direction = Sort.Direction.DESC;
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, "timestamp"));
    }
}