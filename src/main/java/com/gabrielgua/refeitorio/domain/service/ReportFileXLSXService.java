package com.gabrielgua.refeitorio.domain.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportFileXLSXService<T> {

    public void generateReportFile(
            HttpServletResponse response,
            String filename,
            String sheetTitle,
            List<String> headers,
            List<T> data,
            Function<T, List<Object>> rowMapper,
            Map<String, String> info
    ) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + getFileName(filename));

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetTitle);

            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle infoStyle = createInfoStyle(workbook);
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);

            createTitleRow(sheet, titleStyle, sheetTitle, headers.size());
            createInfoRow(sheet, infoStyle, info, headers.size());
            createHeaderRow(sheet, headerStyle, headers);
            populateDataRows(sheet, data, rowMapper, dateStyle, currencyStyle);
            autoSizeColumns(sheet, headers.size());

            workbook.write(response.getOutputStream());
        }
    }

    private void createTitleRow(Sheet sheet, CellStyle titleStyle, String title, int columnCount) {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeight((short) 500);
        Cell titleCell = titleRow.createCell(0);
        title = title + " | Data: " + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(OffsetDateTime.now(ZoneId.systemDefault()));
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));
    }

    private void createInfoRow(Sheet sheet, CellStyle infoStyle, Map<String, String> info, int columnCount) {
        Row infoRow = sheet.createRow(1);
        infoRow.setHeight((short) 500);
        String joined = info.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(" | "));

        Cell cell = infoRow.createCell(0);
        cell.setCellValue("Filtros:   " + joined);
        cell.setCellStyle(infoStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnCount - 1));
    }



    private void createHeaderRow(Sheet sheet, CellStyle headerStyle, List<String> headers) {
        Row headerRow = sheet.createRow(2);
        headerRow.setHeight((short) 500);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }
        sheet.setAutoFilter(new CellRangeAddress(2, 2, 0, headers.size() - 1));
    }

    private void populateDataRows(Sheet sheet, List<T> data, Function<T, List<Object>> rowMapper, CellStyle dateStyle, CellStyle currencyStyle) {
        int rowNum = 3;
        for (T item : data) {
            List<Object> values = rowMapper.apply(item);
            Row row = sheet.createRow(rowNum++);
            for (int col = 0; col < values.size(); col++) {
                Object value = values.get(col);
                Cell cell = row.createCell(col);

                if (value instanceof java.math.BigDecimal bigDecimal) {
                    cell.setCellValue(bigDecimal.doubleValue());
                    cell.setCellStyle(currencyStyle);
                } else if (value instanceof java.time.OffsetDateTime offsetDateTime) {
                    Date date = Date.from(offsetDateTime.toInstant());
                    cell.setCellValue(date);
                    cell.setCellStyle(dateStyle);
                } else {
                    cell.setCellValue(value != null ? value.toString() : "");
                }
            }
        }
    }

    private void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
            int minWidth = 4000;
            if (sheet.getColumnWidth(i) < minWidth) {
                sheet.setColumnWidth(i, minWidth);
            }
        }
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createInfoStyle(Workbook workbook) {
        CellStyle style = createTitleStyle(workbook);
        Font font = workbook.createFont();
        font.setBold(false);
        style.setFont(font);
        return style;
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

    private String getFileName(String fileName) {
        var formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HHmmss");
        return fileName.toUpperCase().replaceAll("\\s+", "_") + "_" + formatter.format(LocalDateTime.now()) + ".xlsx";
    }
}