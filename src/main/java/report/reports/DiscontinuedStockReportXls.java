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
public class DiscontinuedStockReportXls extends XlsReport
{

    private final String outputDir = STOCK_REPORTS_DIR;
    private final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    private Map<String, Integer> products = new HashMap();

    public DiscontinuedStockReportXls()
    {
        super(new HSSFWorkbook());
    }

    @Override
    public void populateWorkbook()
    {
        HSSFSheet sheet = getWorkbook().createSheet(getReportName());
        int rowCount = 0;
        HSSFRow row = sheet.createRow(rowCount++);
        int cellCount = 0;
        HSSFCell cell = row.createCell(cellCount);

        // Title
        cell.setCellValue(getReportName() + " " + date);
        cell.setCellStyle(getStyle(BOLD));

        // Headings
        row = sheet.createRow(rowCount++);
        cell = row.createCell(cellCount++);
        cell.setCellValue("Product Code");
        cell.setCellStyle(getStyle(BOLD));
        
        cell = row.createCell(cellCount++);
        cell.setCellValue("In Stock");
        cell.setCellStyle(getStyle(BOLD));

        // Products
        for (Map.Entry<String, Integer> product : products.entrySet())
        {
            row = sheet.createRow(rowCount++);
            
            cellCount = 0;
            cell = row.createCell(cellCount++);
            cell.setCellValue(product.getKey());
            
            cell = row.createCell(cellCount++);
            cell.setCellValue(product.getValue());
        }
        
        autoSizeColumns(sheet, cellCount);
    }

    @Override
    public String getFilename()
    {
        return outputDir + getReportName() + " " + date + EXTENSION;
    }

    public void setProducts(Map<String, Integer> products)
    {
        this.products = products;
    }

}
