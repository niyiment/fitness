package com.niyiment.fitness.report;

import com.niyiment.fitness.dto.ReportData;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Generates a CSV report using OpenCSV.
 */
@Component
public class CsvReportGenerator implements ReportGenerator {

    @Override
    public byte[] generateReport(ReportData reportData) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8))) {

            // Write title on its own line.
            writer.writeNext(new String[]{reportData.title()});
            writer.writeNext(new String[]{}); // blank line

            // Write header row.
            writer.writeNext(reportData.headers().toArray(new String[0]));

            // Write each row.
            for (List<String> row : reportData.rows()) {
                writer.writeNext(row.toArray(new String[0]));
            }

            writer.flush();
            return baos.toByteArray();
        }
    }
}