/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.reports;

import java.util.HashSet;
import java.util.Set;
import static main.java.MayfairStatic.PROD_ANALYSIS_DIR;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author kian_bryen
 */
public class OrdersPerProductReportXls extends ReportXls
{

    private final String outputDir = PROD_ANALYSIS_DIR;
    private String startDate;
    private String endDate;
    private Set<String[]> products = new HashSet();

    public OrdersPerProductReportXls()
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
        cell.setCellValue(getReportName());
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue(startDate);
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(2);
        cell.setCellValue(endDate);
        cell.setCellStyle(getStyle(BOLD));

        // Headings
        rowCount++;
        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Product Number");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue("Product Code");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(2);
        cell.setCellValue("Quantity");
        cell.setCellStyle(getStyle(BOLD));

        // Products
        for (String[] product : products)
        {
            row = sheet.createRow(rowCount++);
            int cellCount = 0;
            for (String prod : product)
            {
                cell = row.createCell(cellCount++);
                cell.setCellValue(prod);
            }
        }

        autoSizeColumns(sheet, 3);
    }

    @Override
    public String getFilename()
    {
        return outputDir + getReportName() + " - " + startDate + " " + endDate + EXTENSION;
    }

    public void setProducts(Set<String[]> products)
    {
        this.products = products;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

}
