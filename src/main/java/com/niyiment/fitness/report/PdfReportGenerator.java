package com.niyiment.fitness.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.niyiment.fitness.dto.ReportData;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Generates a PDF report using iTextPDF.
 */
@Component
public class PdfReportGenerator implements ReportGenerator {
    private final Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private final Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private final Font cellFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    @Override
    public byte[] generateReport(ReportData reportData) throws Exception {
       try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
           Document document = new Document();
           PdfWriter.getInstance(document, byteArrayOutputStream);
           document.open();

           //Add title
           Paragraph title = new Paragraph(reportData.title(), titleFont);
           title.setAlignment(Element.ALIGN_CENTER);
           document.add(title);
           document.add(new Paragraph(" "));

           // Create table
           PdfPTable table = new PdfPTable(reportData.headers().size());
           table.setWidthPercentage(100);

           //Add headers
           for (String header : reportData.headers()) {
               PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
               headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
               headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
               headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
               table.addCell(headerCell);
           }

           //Add rows
           for (List<String> row : reportData.rows()) {
               for (String cellValue : row) {
                   PdfPCell cell = new PdfPCell(new Phrase(cellValue, cellFont));
                   cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                   table.addCell(cell);
               }
           }
           document.add(table);
           document.close();

           return byteArrayOutputStream.toByteArray();
       }
    }

}