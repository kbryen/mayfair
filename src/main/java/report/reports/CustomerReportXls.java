/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.reports;

import java.util.Map;
import java.util.TreeMap;
import javafx.util.Pair;
import static main.java.MayfairStatic.CUSTOMER_REPORTS_DIR;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author kian_bryen
 */
public class CustomerReportXls extends ReportXls
{

    private final String outputDir = CUSTOMER_REPORTS_DIR;
    private String fileNameExtras;
    // Cust num -> (Cust name, (Prod num -> Quant ordered))
    private Map<Integer, Pair<String, Map<Integer, Integer>>> customers = new TreeMap();
    // Prod num -> (Code, Sales Price, Purchase Price)
    private Map<Integer, String[]> products = new TreeMap();
    // Prod num -> (Code, Sales Price, Purchase Price)
    private Map<Integer, String[]> disconProducts = new TreeMap();
    private final String minLetter = "F";
    private String maxLetter = "F";

    public CustomerReportXls()
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
        HSSFCell cell = row.createCell(cellCount++);
        cell.setCellValue("Product Number");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(cellCount++);
        cell.setCellValue("Product Code");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(cellCount++);
        cell.setCellValue("Sales Price");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(cellCount++);
        cell.setCellValue("Purchase Price");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(cellCount++);
        cell.setCellValue("Total");
        cell.setCellStyle(getStyle(BOLD));
        for (Map.Entry<Integer, Pair<String, Map<Integer,Integer>>> customer : customers.entrySet())
        {
            cell = row.createCell(cellCount++);
            cell.setCellValue(customer.getKey() + " - " + customer.getValue().getKey());
            cell.setCellStyle(getStyle(BOLD + ROTATE));
        }

        for (Map.Entry<Integer, String[]> product : products.entrySet())
        {
            rowCount = addProductLine(product, sheet, rowCount);
        }

        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Discontinued Products");
        cell.setCellStyle(getStyle(BOLD));
        
        for (Map.Entry<Integer, String[]> disconProduct : disconProducts.entrySet())
        {
            rowCount = addProductLine(disconProduct, sheet, rowCount);
        }
        
        autoSizeColumns(sheet, customers.size() + 5);
    }

    private int addProductLine(Map.Entry<Integer, String[]> product, HSSFSheet sheet, int rowCount)
    {
        HSSFRow row = sheet.createRow(rowCount++);
        int prod_num = product.getKey();
        int cellCount = 0;

        // prod num
        HSSFCell cell = row.createCell(cellCount++);
        cell.setCellValue(prod_num);
        // prod code
        cell = row.createCell(cellCount++);
        cell.setCellValue(product.getValue()[0]);
        // sales price
        cell = row.createCell(cellCount++);
        cell.setCellValue(product.getValue()[1]);
        // purchase price
        cell = row.createCell(cellCount++);
        cell.setCellValue(product.getValue()[2]);
        // grand total
        cell = row.createCell(cellCount++);
        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula("SUM(" + minLetter + rowCount + ":" + maxLetter + rowCount + ")");

        // customer totals
        for (Pair<String, Map<Integer, Integer>> totals : customers.values())
        {
            cell = row.createCell(cellCount++);
            cell.setCellValue(totals.getValue().get(prod_num));
        }
        return rowCount;
    }

    @Override
    public String getFilename()
    {
        return outputDir + getReportName() + fileNameExtras + "-test" + EXTENSION;
    }

    public void setFileNameExtras(String fileNameExtras)
    {
        this.fileNameExtras = fileNameExtras;
    }

    public void setCustomers(Map<Integer, Pair<String, Map<Integer, Integer>>> customers)
    {
        this.customers = customers;
        this.maxLetter = CellReference.convertNumToColString(customers.size() + 5);
    }

    public void setProducts(Map<Integer, String[]> products)
    {
        this.products = products;
    }

    public void setDisconProducts(Map<Integer, String[]> disconProducts)
    {
        this.disconProducts = disconProducts;
    }

}
