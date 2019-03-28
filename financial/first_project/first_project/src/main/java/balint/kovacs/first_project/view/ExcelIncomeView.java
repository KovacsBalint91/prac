package balint.kovacs.first_project.view;

import balint.kovacs.first_project.model.Income;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelIncomeView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=\"incomes.xls\"");
        List<Income> incomeList = (List<Income>) model.get("incomeList");
        Sheet sheet = workbook.createSheet("Income Data");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Income Date");
        header.createCell(1).setCellValue("Income Value");

        int rowNum = 1;
        for (Income income : incomeList) {
            Row row = sheet.createRow(rowNum++);

            Cell date = row.createCell(0);
            date.setCellValue(income.getDate().toString());

            row.createCell(1).setCellValue(income.getValue());
        }

    }
}

