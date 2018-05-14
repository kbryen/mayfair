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
public class SalesOrdersForProductReportXls extends ReportXls
{

    private final String outputDir = PROD_ANALYSIS_DIR;
    private String prodCode;
    private Set<String[]> orders = new HashSet();

    public SalesOrdersForProductReportXls()
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
        cell.setCellValue(prodCode);
        cell.setCellStyle(getStyle(BOLD));

        // Headings
        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Order Number");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue("Customer Name");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(2);
        cell.setCellValue("Order Date");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(3);
        cell.setCellValue("Delivery Date");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(4);
        cell.setCellValue("Dispatched");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(5);
        cell.setCellValue("Delivered");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(6);
        cell.setCellValue("Quantity");
        cell.setCellStyle(getStyle(BOLD));

        // Products
        for (String[] order : orders)
        {
            row = sheet.createRow(rowCount++);
            int cellCount = 0;
            for (String ord : order)
            {
                cell = row.createCell(cellCount++);
                cell.setCellValue(ord);
            }
        }

        autoSizeColumns(sheet, 7);
    }

    @Override
    public String getFilename()
    {
        return outputDir + getReportName() + " - " + prodCode + EXTENSION;
    }

    public void setProdCode(String prodCode)
    {
        this.prodCode = prodCode;
    }

    public void setOrders(Set<String[]> orders)
    {
        this.orders = orders;
    }

}
