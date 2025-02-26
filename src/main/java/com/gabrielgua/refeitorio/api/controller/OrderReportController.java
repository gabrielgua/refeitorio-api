package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.domain.service.OrderReportService;
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

    private final OrderReportService orderReportService;

    @GetMapping("/csv")
    public void generateOrderHistoryReportCSV(HttpServletResponse response) throws IOException {
        orderReportService.generateOrderReportCSV(response);
    }

    @GetMapping("/excel")
    public void generateOrderHistoryReportEXCEL(HttpServletResponse response) throws IOException {
        orderReportService.generateOrderReportEXCEL(response);
    }
}