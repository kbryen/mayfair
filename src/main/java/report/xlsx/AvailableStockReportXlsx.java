/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.xlsx;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static main.java.MayfairConstants.STOCK_REPORTS_DIR;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author kian_bryen
 */
public class AvailableStockReportXlsx extends XlsxReport
{

    private final String outputDir = STOCK_REPORTS_DIR;
    private final String sheetName = "Stock Report";
    private final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    private Map<String, Map<String, Integer>> productCounts = new HashMap();
    private List<String> purchaseOrders = new ArrayList();

    public AvailableStockReportXlsx()
    {
        super(new XSSFWorkbook());
    }

    public void populateWorkbook()
    {
        XSSFSheet sheet = getWorkbook().createSheet(sheetName);
        int rowCount = 0;
        XSSFRow row = sheet.createRow(rowCount++);
        int cellCount = 0;
        XSSFCell cell = row.createCell(cellCount);

        // Title
        cell.setCellValue("Available Stock Report " + date);
        cell.setCellStyle(getStyle(BOLD));

        // Headings
        row = sheet.createRow(rowCount++);
        cell = row.createCell(cellCount++);
        cell.setCellValue("Product Code");
        cell.setCellStyle(getStyle(BOLD));

        for (String purchaseOrder : purchaseOrders)
        {
            cell = row.createCell(cellCount++);
            cell.setCellValue(purchaseOrder);
            cell.setCellStyle(getStyle(BOLD));
        }

        cell = row.createCell(cellCount);
        cell.setCellValue("Total Units");
        cell.setCellStyle(getStyle(BOLD));

        // Products
        for (Map.Entry<String, Map<String, Integer>> product : productCounts.entrySet())
        {
            row = sheet.createRow(rowCount++);
            
            cellCount = 0;
            cell = row.createCell(cellCount++);
            cell.setCellValue(product.getKey());
            
            for (String purchaseOrder : purchaseOrders)
            {
                cell = row.createCell(cellCount++);
                cell.setCellValue(product.getValue().get(purchaseOrder));
            }
            
            cell = row.createCell(cellCount++);
            cell.setCellValue(product.getValue().get("Total"));
        }
        
        autoSizeColumns(sheet, cellCount);
    }

    public String getFilename()
    {
        return outputDir + "Available Stock Report " + date + ".xlsx";
    }

    public void setProductCounts(Map<String, Map<String, Integer>> productCounts)
    {
        this.productCounts = productCounts;
    }

    public void setPurchaseOrders(List<String> purchaseOrders)
    {
        this.purchaseOrders = purchaseOrders;
    }
}
