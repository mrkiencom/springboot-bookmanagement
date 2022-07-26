package com.novahub.javatrain.javaspringbookmanagement.services;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.BookFromStoreDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BookExcelExporter {
    private XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private List<BookFromStoreDTO> listBooks;

    public BookExcelExporter(List<BookFromStoreDTO> listBooks) {
        this.listBooks = listBooks;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Books");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Title", style);
        createCell(row, 1, "Subtitle", style);
        createCell(row, 2, "Image", style);
        createCell(row, 3, "Isbn13", style);
        createCell(row, 4, "Price", style);
        createCell(row, 5, "Url", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (BookFromStoreDTO book : listBooks) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, book.getTitle(), style);
            createCell(row, columnCount++, book.getSubtitle(), style);
            createCell(row, columnCount++, book.getImage(), style);
            createCell(row, columnCount++, book.getIsbn13(), style);
            createCell(row, columnCount++, book.getPrice(), style);
            createCell(row, columnCount++, book.getUrl(), style);
        }
    }

    public void export(FileOutputStream file) throws IOException {
        writeHeaderLine();
        writeDataLines();
        workbook.write(file);
        workbook.close();
    }
}
