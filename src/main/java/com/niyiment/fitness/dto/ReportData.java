package com.niyiment.fitness.dto;

import java.util.List;

/**
 * A generic carrier for report data.
 *
 * @param title   The title of the report.
 * @param headers A list of header labels.
 * @param rows    A list of rows, where each row is a list of cell values (as strings).
 */
public record ReportData(
    String title,
    List<String> headers,
    List<List<String>> rows
) {}