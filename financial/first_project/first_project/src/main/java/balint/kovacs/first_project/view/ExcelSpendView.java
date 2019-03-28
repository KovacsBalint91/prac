package balint.kovacs.first_project.view;

import balint.kovacs.first_project.model.Spend;
import balint.kovacs.first_project.service.CategoryService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelSpendView extends AbstractXlsView {

    private CategoryService categoryService;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {


        response.setHeader("Content-Disposition", "attachment;filename=\"spends.xls\"");
        List<Spend> spendList = (List<Spend>) model.get("spendList");
        Sheet sheet = workbook.createSheet("Spend Data");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Date");
        header.createCell(1).setCellValue("Value");
        header.createCell(2).setCellValue("Category");

        int rowNum = 1;
        for (Spend spend : spendList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(spend.getDate().toString());
            row.createCell(1).setCellValue(spend.getValue());
            row.createCell(2).setCellValue(spend.getCategory_id());
        }

    }
}
