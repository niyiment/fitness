package com.niyiment.fitness.service.report;

import com.niyiment.fitness.dto.ReportData;

/**
 * Defines the contract for generating reports in a specific file format.
 */
public interface ReportGenerator {

    /**
     * Generates a report based on the provided data.
     *
     * @param reportData The report data (title, headers, rows).
     * @return The generated report as a byte array.
     * @throws Exception if an error occurs during generation.
     */
    byte[] generateReport(ReportData reportData) throws Exception;
}