package com.novahub.javatrain.javaspringbookmanagement.services;

import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BookExcelExporter {
    private XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private List<Book> listBooks;

    public BookExcelExporter(List<Book> listBooks) {
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
        createCell(row, 0, "Id", style);
        createCell(row, 1, "Title", style);
        createCell(row, 2, "Subtitle", style);
        createCell(row, 3, "Author", style);
        createCell(row, 4, "Description", style);
        createCell(row, 5, "CreateAt", style);
        createCell(row, 6, "UpdateAt", style);
        createCell(row, 7, "Image", style);
        createCell(row, 8, "Isbn13", style);
        createCell(row, 9, "Price", style);
        createCell(row, 10, "Url", style);
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

        for (Book book : listBooks) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(book.getId()), style);
            createCell(row, columnCount++, book.getTitle(), style);
            createCell(row, columnCount++, book.getSubtitle(), style);
            createCell(row, columnCount++, book.getAuthor(), style);
            createCell(row, columnCount++, book.getDescription(), style);
            createCell(row, columnCount++, book.getCreatedAt().toString(), style);
            createCell(row, columnCount++, book.getUpdatedAt().toString(), style);
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
