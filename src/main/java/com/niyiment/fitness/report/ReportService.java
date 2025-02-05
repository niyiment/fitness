package com.niyiment.fitness.report;

import com.niyiment.fitness.dto.ReportData;

/**
 * Service interface for report generation
 */
public interface ReportService {
    /**
     * Generate a report in the specified format
     * @param reportFormat the prot format (CSV, PDF, EXCEL)
     * @param reportData the report data (title, header, rows)
     * @return
     * @throws Exception
     */
    byte[] generateReport(ReportFormat reportFormat, ReportData reportData) throws Exception;
}
