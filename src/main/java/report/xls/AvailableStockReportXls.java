/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.xls;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class AvailableStockReportXls extends XlsReport
{

    private final String outputDir = STOCK_REPORTS_DIR;
    private final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    private Map<String, Map<String, Integer>> productCounts = new HashMap();
    private List<String> purchaseOrders = new ArrayList();

    public AvailableStockReportXls()
    {
        super(new HSSFWorkbook());
    }

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
            
            cell = row.createCell(cellCount++);
            cell.setCellValue(product.getValue().get("In Stock"));
            
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
        return outputDir + getReportName() + " " + date + ".xls";
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
