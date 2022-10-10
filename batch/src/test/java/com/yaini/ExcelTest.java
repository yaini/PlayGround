package com.yaini;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

@Disabled
public class ExcelTest {

  private static final String FILE_PATH = "excel/";
  private static final String FILE_NAME = "test.xlsx";

  @Test
  public void createExcel() throws IOException {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("data");

    List<Column> lists =
        List.of(
            new Column(1L, "name", "name@email.com"),
            new Column(2L, "name2", "name2@email.com"),
            new Column(3L, "name3", "name3@email.com"));

    int rowCnt = 0;
    for (Column data : lists) {
      Row row = sheet.createRow(rowCnt++);
      Cell idCell = row.createCell(0);
      idCell.setCellValue(data.id);

      Cell nameCell = row.createCell(1);
      nameCell.setCellValue(data.name);

      Cell emailCell = row.createCell(2);
      emailCell.setCellValue(data.email);
    }

    ClassPathResource resource = new ClassPathResource(FILE_PATH);
    Path path = Paths.get(resource.getURI());
    FileOutputStream out = new FileOutputStream(new File(path.toString(), FILE_NAME));
    workbook.write(out);
    out.close();
  }

  @Test
  public void readExcel() throws IOException {
    ClassPathResource resource = new ClassPathResource(FILE_PATH);
    Path path = Paths.get(resource.getURI());
    FileInputStream in = new FileInputStream(new File(path.toString(), FILE_NAME));

    XSSFWorkbook workbook = new XSSFWorkbook(in);
    XSSFSheet sheet = workbook.getSheet("data");
    DataFormatter formatter = new DataFormatter();
    FormulaEvaluator formula = new XSSFFormulaEvaluator(workbook);

    Iterator<Row> rowIterator = sheet.iterator();

    List<Column> lists = new ArrayList<>();

    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();

      Iterator<Cell> cellIterator = row.cellIterator();

      row.getCell(0);

      lists.add(
          new Column(
              Long.parseLong(formatter.formatCellValue(row.getCell(0), formula)),
              formatter.formatCellValue(row.getCell(1), formula),
              formatter.formatCellValue(row.getCell(2), formula)));
    }

    in.close();
  }

  class Column {
    Long id;
    String name;
    String email;

    Column(final Long id, final String name, final String email) {
      this.id = id;
      this.name = name;
      this.email = email;
    }
  }
}
