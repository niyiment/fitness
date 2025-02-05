package com.niyiment.fitness.report;

import com.niyiment.fitness.dto.ReportData;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.lang.reflect.Field;

/**
 * Generates an Excel report using Apache POI.
 */
@Slf4j
@Component
public class ExcelReportGenerator implements ReportGenerator {
    private static final String TEMP_DIR = System.getProperty("user.dir");
    private static final  String BASE_DIR = "/report/";

    @Override
    public byte[] generateReport(ReportData reportData) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");

            int rowIndex = 0;

            // Write title.
            Row titleRow = sheet.createRow(rowIndex++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(reportData.title());

            // Insert a blank row.
            rowIndex++;

            // Write headers.
            Row headerRow = sheet.createRow(rowIndex++);
            for (int i = 0; i < reportData.headers().size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(reportData.headers().get(i));
            }

            // Write each row.
            for (var rowData : reportData.rows()) {
                Row row = sheet.createRow(rowIndex++);
                for (int i = 0; i < rowData.size(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(rowData.get(i));
                }
            }

            // Auto-size all columns.
            for (int i = 0; i < reportData.headers().size(); i++) {
                sheet.autoSizeColumn(i);
            }

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                workbook.write(baos);
                return baos.toByteArray();
            }
        }
    }

    public <T> void writeToExcelMultipleSheet(final String filePath, final String sheetName,
                                              final List<T> data) {

        File file = new File(filePath);
        XSSFWorkbook workbook;
        if (file.exists()) {
            try  {
                workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));
                writeToNewExcelSheet(workbook, file, sheetName, data);
            } catch (Exception e) {
                log.error("Error writing to the file: {}", e.getMessage());
            }
        } else {
            try {
                workbook = new XSSFWorkbook();
                writeToNewExcelSheet(workbook, file, sheetName, data);
            } catch (Exception e) {
               log.error("Error writing to a new file. {}", e.getMessage());
            }
        }
    }

    public <T> void writeToNewExcelSheet(Workbook workbook, File file, final String sheetName,
                                         final List<T> data) {
        try {
            OutputStream fos = null;
            if (!data.isEmpty()) {
                Sheet sheet = workbook.createSheet(sheetName);
                List<String> fieldNames = getFieldNamesForClass(data.getFirst().getClass());
                int rowCount = 0;
                int columnCount;
                Row row = sheet.createRow(rowCount++);
                writeHeader(fieldNames, row);
                Class<?> classObject = data.getFirst().getClass();
                for (T t : data) {
                    row = sheet.createRow(rowCount++);
                    columnCount = 0;
                    for (String fieldName : fieldNames) {
                        Cell cell = row.createCell(columnCount);
                        Method method = getMethod(fieldName, classObject);
                        Object value = method.invoke(t, (Object[]) null);
                        if (value != null) {
                            switch (value) {
                                case String s -> cell.setCellValue(s);
                                case Long l -> cell.setCellValue(l);
                                case Integer i -> cell.setCellValue(i);
                                case Double v -> cell.setCellValue(v);
                                default -> cell.setCellValue(value.toString());
                            }
                        }
                        columnCount++;
                    }
                }
                fos = new FileOutputStream(file);
                workbook.write(fos);
            }
            assert fos != null;
            fos.flush();
        } catch (Exception e) {
            log.error("Error writing to next sheet. {}", e.getMessage());
        }
    }

    private void writeHeader(List<String> fieldNames, Row row) {
        int columnCount = 0;
        for (String fieldName : fieldNames) {
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(fieldName);
        }
    }

    private Method getMethod(String fieldName, Class<? extends Object> classObject) throws NoSuchMethodException {
        Method method;
        try {
            method = classObject.getMethod("get" + capitalize(fieldName));
        } catch (NoSuchMethodException nme) {
            method = classObject.getMethod("get" + fieldName);
        }
        return method;
    }

    public void readExcelFile(String fileName) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(fileName));
        workbook.forEach(sheet ->  log.info("=> {}", sheet.getSheetName()));
        Sheet sheet = workbook.getSheetAt(0);
        sheet.forEach(row -> row.forEach(ExcelReportGenerator::printCellValue));

        workbook.close();
    }

    // retrieve field names from a POJO class
    private List<String> getFieldNamesForClass(Class<?> clazz) {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }

        return fieldNames;
    }

    public String capitalize(String text) {
        if (text == null || text.isEmpty())  return text;

        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private static void printCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case BOOLEAN:
                log.info(String.valueOf(cell.getBooleanCellValue()));
                break;
            case STRING:
                log.info(cell.getRichStringCellValue().getString());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    log.info(cell.getDateCellValue().toString());
                } else {
                   log.info(String.valueOf(cell.getNumericCellValue()));
                }
                break;
            case FORMULA:
                log.info(cell.getCellFormula());
                break;
            case BLANK:
            default:
                log.info("");
        }

    }

    private Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }
        return fileList;
    }

    @SneakyThrows
    public ByteArrayOutputStream downloadFile(String file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String folder = TEMP_DIR + BASE_DIR;
        try {
            Optional<String> fileToDownload = listFilesUsingDirectoryStream(folder).stream()
                    .filter(f -> f.equals(file))
                    .findFirst();
            fileToDownload.ifPresent(s -> {
                try (InputStream is = new FileInputStream(folder + s)) {
                    IOUtils.copy(is, baos);
                } catch (IOException exception) {
                    log.error("Error reading file: {}", exception.getMessage());
                }
            });

        } catch (Exception exception) {
            log.error("Downloading file: {}", exception.getMessage());
        }
        return baos;
    }

    @PostConstruct
    public void init() throws IOException {
        String folder = TEMP_DIR + BASE_DIR;
        boolean isCreated = new File(folder).mkdirs();
        if (isCreated) {
            FileUtils.cleanDirectory(new File(folder));
        }
    }
}