package com.niyiment.fitness.report;

public class ReportGenerationFactory {

    public static ReportGenerator createReportGenerator(ReportFormat format) {
        return switch (format) {
            case CSV -> new CsvReportGenerator();
            case EXCEL -> new ExcelReportGenerator();
            case PDF -> new PdfReportGenerator();
        };
    }
}
