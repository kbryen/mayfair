/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.reports;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
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
public class WarehouseStockReportXls extends ReportXls
{

    private final String outputDir = STOCK_REPORTS_DIR;
    private final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    private Set<String[]> products = new HashSet();

    public WarehouseStockReportXls()
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

        // Title
        cell.setCellValue(getReportName() + " " + date);
        cell.setCellStyle(getStyle(BOLD));

        // Headings
        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Product Code");
        cell.setCellStyle(getStyle(BOLD));

        cell = row.createCell(1);
        cell.setCellValue("Warehouse Stock");
        cell.setCellStyle(getStyle(BOLD));

        cell = row.createCell(2);
        cell.setCellValue("Avaliable");
        cell.setCellStyle(getStyle(BOLD));

        // Products
        int cellCount = 0;
        for (String[] product : products)
        {
            row = sheet.createRow(rowCount++);
            for (String prod : product)
            {
                cell = row.createCell(cellCount++);
                cell.setCellValue(prod);
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

    public void setProducts(Set<String[]> products)
    {
        this.products = products;
    }

}
