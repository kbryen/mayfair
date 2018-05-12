/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.reports;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import static main.java.MayfairStatic.STOCK_REPORTS_DIR;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author kian_bryen
 */
public class OutOfStockReportXls extends ReportXls
{

    private final String outputDir = STOCK_REPORTS_DIR;
    private final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    private Map<Integer, String> products = new HashMap();

    public OutOfStockReportXls()
    {
        super(new HSSFWorkbook());
    }

    @Override
    public void populateWorkbook()
    {
        HSSFSheet sheet = getWorkbook().createSheet(getReportName());
        int rowCount = 0;
        HSSFRow row = sheet.createRow(rowCount++);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(getReportName() + " " + date);
        cell.setCellStyle(getStyle(BOLD));

        // Headings
        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Product Number");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue("Product Code");
        cell.setCellStyle(getStyle(BOLD));

        // Products
        for (Map.Entry<Integer, String> product : products.entrySet())
        {
            row = sheet.createRow(rowCount++);
            cell = row.createCell(0);
            cell.setCellValue(product.getKey());
            cell = row.createCell(1);
            cell.setCellValue(product.getValue());
        }

        autoSizeColumns(sheet, 2);
    }

    @Override
    public String getFilename()
    {
        return outputDir + getReportName() + " " + date + EXTENSION;
    }

    public void setProducts(Map<Integer, String> products)
    {
        this.products = products;
    }

}
