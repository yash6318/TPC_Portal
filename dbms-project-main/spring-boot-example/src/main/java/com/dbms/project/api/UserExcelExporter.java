package com.dbms.project.api;

import com.dbms.project.model.Resume;
import com.dbms.project.model.Student;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Student> students;
    private List<Resume> resumes;

    public UserExcelExporter(List<Student> students, List<Resume> resumes) {
        this.students = students;
        this.resumes = resumes;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value) {
        sheet.autoSizeColumn(columnCount);
        CellStyle style = workbook.createCellStyle();
        Cell cell = row.createCell(columnCount);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Roll Number");
        createCell(row, 1, "Full Name");
        createCell(row, 2, "Email ID");
        createCell(row, 3, "Resume");
    }

    private void writeDataLines() {
        int rowCount = 1;

        for (int i = 0; i < students.size(); i++) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            String name = students.get(i).getFirstName() + " " +students.get(i).getMiddleName() + " " + students.get(i).getLastName();
            String link = "https://drive.google.com/file/d/" + resumes.get(i).getResumeLink() + "/view?usp=drive_link";
            createCell(row, columnCount++, students.get(i).getRollNo());
            createCell(row, columnCount++, name);
            createCell(row, columnCount++, students.get(i).getInstituteID());


            CellStyle style = workbook.createCellStyle();
            Cell cell = row.createCell(columnCount);
            XSSFFont font = workbook.createFont();
            font.setFontHeight(14);

            sheet.autoSizeColumn(columnCount);
            cell.setCellValue(resumes.get(i).getResumeName());
            final Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
            href.setAddress(link);
            cell.setHyperlink(href);
            font.setColor(IndexedColors.BLUE.getIndex());
            font.setUnderline(XSSFFont.U_SINGLE);
            style.setFont(font);
            cell.setCellStyle(style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

}
