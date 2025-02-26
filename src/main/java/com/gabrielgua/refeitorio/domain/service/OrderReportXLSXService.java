package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.Order;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderReportXLSXService {

    private final OrderService orderService;
    private static final String[] headers = {"Id", "Número", "Matrícula", "Atendimento", "Subtotal", "Preço descontado", "Preço final", "Data"};

    public void generateOrderReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + getFileName());

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Order History");

            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);

            createTitleRow(sheet, titleStyle);
            createHeaderRow(sheet, headerStyle);

            List<Order> orders = orderService.findAll();
            populateDataRows(sheet, orders, dateStyle, currencyStyle);

            autoSizeColumns(sheet);

            workbook.write(response.getOutputStream());
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        var titleStyle = createHeaderStyle(workbook);
        titleStyle.setVerticalAlignment(VerticalAlignment.TOP);
        return titleStyle;
    }

    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss"));
        return style;
    }

    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("R$ #,##0.00"));
        return style;
    }

    private void createTitleRow(Sheet sheet, CellStyle titleStyle) {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeight((short) 1000);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Histórico de vendas");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
    }

    private void createHeaderRow(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        sheet.setAutoFilter(new CellRangeAddress(1, 1, 0, headers.length - 1));
    }

    private void populateDataRows(Sheet sheet, List<Order> orders, CellStyle dateStyle, CellStyle currencyStyle) {
        int rowNum = 2;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getNumber());
            row.createCell(2).setCellValue(order.getClient().getCredential());
            row.createCell(3).setCellValue(order.getAtendimento().getName());

            createStyledCell(row, 4, order.getOriginalPrice().doubleValue(), currencyStyle);
            createStyledCell(row, 5, order.getDiscountedPrice().doubleValue(), currencyStyle);
            createStyledCell(row, 6, order.getFinalPrice().doubleValue(), currencyStyle);

            Cell createdAtCell = row.createCell(7);
            var zoneId = ZoneId.systemDefault();
            createdAtCell.setCellValue(Date.from(order.getCreatedAt().atZoneSameInstant(zoneId).toInstant()));
            createdAtCell.setCellStyle(dateStyle);
        }
    }

    private void createStyledCell(Row row, int column, double value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            int minWidth = 4000; // Approx. 10-12 characters wide
            if (sheet.getColumnWidth(i) < minWidth) {
                sheet.setColumnWidth(i, minWidth);
            }
        }
    }

    private String getFileName() {
        var formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HHmmss");
        return "ORDER_HISTORY_" + formatter.format(LocalDateTime.now()) + ".xlsx";
    }
}