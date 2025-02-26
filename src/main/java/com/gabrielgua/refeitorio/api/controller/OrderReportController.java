package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.domain.service.OrderReportCSVService;
import com.gabrielgua.refeitorio.domain.service.OrderReportXLSXService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/orders/reports")
@RequiredArgsConstructor
public class OrderReportController {

    private final OrderReportCSVService reportCSVService;
    private final OrderReportXLSXService reportXLSXService;

    @GetMapping("/csv")
    public void generateOrderHistoryReportCSV(HttpServletResponse response) throws IOException {
        reportCSVService.generateOrderReport(response);
    }

    @GetMapping("/xlsx")
    public void generateOrderHistoryReportXLSX(HttpServletResponse response) throws IOException {
        reportXLSXService.generateOrderReport(response);
    }
}