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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrderReportService {

    private final OrderService orderService;
    private static final String[] headers = {"Id", "Número", "Matrícula", "Atendimento", "Subtotal", "Preço descontado", "Preço final", "Data"};

    public void generateOrderReportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + getFileName(".csv"));

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

    public void generateOrderReportEXCEL(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + getFileName(".xlsx"));

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Order History");

        //Title Row Style
        var titleStyle = workbook.createCellStyle();
        var titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setVerticalAlignment(VerticalAlignment.TOP);

        var titleRow = sheet.createRow(0);
        titleRow.setHeight((short) 1000);

        var titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Histórico de vendas");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));

        // Header Row Style
        var headerStyle = workbook.createCellStyle();
        var headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        sheet.setAutoFilter(new CellRangeAddress(1, 1, 0, headers.length - 1));

        var orders = orderService.findAll();
        int rowNum = 2;

        var dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy HH:mm"));

        var currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("R$ #,##0.00"));

        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getNumber());
            row.createCell(2).setCellValue(order.getClient().getCredential());
            row.createCell(3).setCellValue(order.getAtendimento().getName());

            var originalPrice = row.createCell(4);
            originalPrice.setCellValue(order.getOriginalPrice().doubleValue());
            originalPrice.setCellStyle(currencyStyle);

            var discountedPrice = row.createCell(5);
            discountedPrice.setCellValue(order.getDiscountedPrice().doubleValue());
            discountedPrice.setCellStyle(currencyStyle);

            var finalPrice = row.createCell(6);
            finalPrice.setCellValue(order.getFinalPrice().doubleValue());
            finalPrice.setCellStyle(currencyStyle);

            var createdAt = row.createCell(7);
            createdAt.setCellValue(Date.from(order.getCreatedAt().toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()));
            createdAt.setCellStyle(dateStyle);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            int minWidth = 4000; // Approx. 10-12 characters wide
            if (sheet.getColumnWidth(i) < minWidth) {
                sheet.setColumnWidth(i, minWidth);
            }
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private String formatCurrency(BigDecimal value) {
        var brFormat = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        return brFormat.format(value);
    }

    private String formatDate(OffsetDateTime date) {
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(date);
    }

    private String getFileName(String extension) {
        var formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HHmmss");
        return "ORDER_HISTORY_" + formatter.format(LocalDateTime.now()) + extension;
    }
}