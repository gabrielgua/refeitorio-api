package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.Order;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrderReportCSVService {

    private final OrderService orderService;
    private static final String[] headers = {"Id", "Número", "Matrícula", "Atendimento", "Subtotal", "Preço descontado", "Preço final", "Data"};

    public void generateOrderReport(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + getFileName());

        PrintWriter writer = response.getWriter();

        writer.println("Histórico de vendas");
        writer.println("Período: " + formatDate(OffsetDateTime.now()));

        writer.println(String.join(",", headers));

        var orders = orderService.findAll();
        orders.forEach(order -> {
            writer.println(order.getId() + "," + order.getNumber() + "," + order.getClient().getCredential() + ","
                    + order.getAtendimento().getName() + "," + formatCurrency(order.getOriginalPrice()) + "," + formatCurrency(order.getDiscountedPrice())
                    + "," + formatCurrency(order.getFinalPrice()) + "," + formatDate(order.getCreatedAt()));
        });

        writer.flush();
        writer.close();
    }


    private String formatCurrency(BigDecimal value) {
        var brFormat = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        return brFormat.format(value);
    }

    private String formatDate(OffsetDateTime date) {
        ZoneId zone = ZoneId.systemDefault();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date.atZoneSameInstant(zone));
    }

    private String getFileName() {
        var formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HHmmss");
        return "ORDER_HISTORY_" + formatter.format(LocalDateTime.now()) + ".csv";
    }
}