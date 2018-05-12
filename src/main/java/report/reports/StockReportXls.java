/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.reports;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import static main.java.MayfairStatic.STOCK_REPORTS_DIR;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author kian_bryen
 */
public class StockReportXls extends ReportXls
{

    private final String outputDir = STOCK_REPORTS_DIR;
    private final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    // Header name -> value
    private Set<Map<String, String>> products;
    private String[] headers;

    public StockReportXls()
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
        HSSFCell cell = row.createCell(0);

        // Title
        cell.setCellValue(getReportName() + " " + date);
        cell.setCellStyle(getStyle(BOLD));

        // Headings
        row = sheet.createRow(rowCount++);
        for (String header : headers)
        {
            cell = row.createCell(cellCount++);
            cell.setCellValue(header);
            cell.setCellStyle(getStyle(BOLD));
        }

        // Products
        for (Map<String, String> product : products)
        {
            row = sheet.createRow(rowCount++);
            for (String header : headers)
            {
                cell = row.createCell(cellCount++);
                cell.setCellValue(product.get(header));
            }
            cellCount = 0;
        }

        autoSizeColumns(sheet, 3);
    }

    @Override
    public String getFilename()
    {
        return outputDir + getReportName() + " " + date + EXTENSION;
    }

    public void setProducts(Set<Map<String, String>> products)
    {
        this.products = products;
    }

    public void setHeaders(String[] headers)
    {
        this.headers = headers;
    }

}
