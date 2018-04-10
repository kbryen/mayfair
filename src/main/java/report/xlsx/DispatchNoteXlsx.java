/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.xlsx;

import java.util.Map;
import java.util.Map.Entry;
import static main.java.MayfairStatic.DISPATCH_NOTES_DIR;
import static main.java.MayfairStatic.DISPATCH_NOTE_TEMPLATE;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;


/**
 *
 * @author kian_bryen
 */
public class DispatchNoteXlsx extends XlsxReport
{

    private final String outputDir = DISPATCH_NOTES_DIR;
    private final String sheetName = "Dispatch Note";
    private String del_date = "";
    private int cust_num = 0;
    private String cust_name = "";
    private String del_address = "";
    private Map<String, Integer> products;
    private int total_quant = 0;
    private int ord_num = 0;
    private String cust_reference = "";

    public DispatchNoteXlsx()
    {
        super(XlsxReport.getXSSFWorkbook(DISPATCH_NOTE_TEMPLATE));
    }

    public void populateWorkbook()
    {
        XSSFSheet sheet = getWorkbook().getSheet(sheetName);
        int rowCount = 5;

        // --- Order Details ---
        // Delivery date
        XSSFRow row = sheet.getRow(rowCount++);
        XSSFCell cell = row.createCell(1);
        cell.setCellStyle(getStyle(RIGHT));
        cell.setCellValue(del_date);

        // Customer Number
        row = sheet.getRow(rowCount++);
        cell = row.createCell(1);
        cell.setCellStyle(getStyle(RIGHT));
        cell.setCellValue(cust_num);

        // Customer Name 
        row = sheet.getRow(rowCount++);
        cell = row.createCell(1);
        cell.setCellStyle(getStyle(RIGHT));
        cell.setCellValue(cust_name);

        // Order Number
        row = sheet.getRow(rowCount++);
        cell = row.createCell(1);
        cell.setCellStyle(getStyle(RIGHT));
        cell.setCellValue(ord_num);

        // Delivery Address
        rowCount = 5;
        String[] del_address_parts = del_address.split(",");
        for (String del_address_part : del_address_parts)
        {
            row = sheet.getRow(rowCount++);
            cell = row.createCell(3);
            cell.setCellValue(del_address_part.trim());
            cell.setCellStyle(getStyle(RIGHT));
        }

        // Products
        rowCount = 13;
        int maxRowCount = 44;

        row = sheet.createRow(rowCount++);
        addProductsHeader(row);
       
        for (Entry<String, Integer> product : products.entrySet())
        {
            if (rowCount > maxRowCount)
            {
                rowCount = 55;
                row = sheet.createRow(rowCount++);
                addProductsHeader(row);
            }

            int quant = product.getValue();
            total_quant += quant;

            row = sheet.createRow(rowCount++);
            cell = row.createCell(0);
            cell.setCellValue(product.getKey());
            cell = row.createCell(1);
            cell.setCellValue(quant);
        }

        // Total Units
        row = sheet.createRow(++rowCount);
        cell = row.createCell(0);
        cell.setCellStyle(getStyle(BOLD + LEFT));
        cell.setCellValue("Total Units");
        cell = row.createCell(1);
        cell.setCellStyle(getStyle(BOLD + RIGHT));
        cell.setCellValue(total_quant);

        autoSizeColumns(sheet, 4);
    }

    private void addProductsHeader(XSSFRow row)
    {
        XSSFCell cell = row.createCell(0);
        cell.setCellStyle(getStyle(BOLD + LEFT));
        cell.setCellValue("Product Code");

        cell = row.createCell(1);
        cell.setCellStyle(getStyle(BOLD + RIGHT));
        cell.setCellValue("Quantity");
    }

    public void setDel_date(String del_date)
    {
        this.del_date = del_date;
    }

    public void setCust_num(int cust_num)
    {
        this.cust_num = cust_num;
    }

    public void setCust_name(String cust_name)
    {
        this.cust_name = cust_name;
    }

    public void setDel_address(String del_address)
    {
        this.del_address = del_address;
    }

    public void setProducts(Map<String, Integer> products)
    {
        this.products = products;
    }

    public void setOrd_num(int ord_num)
    {
        this.ord_num = ord_num;
    }

    public void setCust_reference(String cust_reference)
    {
        this.cust_reference = cust_reference;
    }

    public String getFilename()
    {
        return outputDir + ord_num + "-" + cust_reference + " " + del_date.replace('/', '-') + ".xls";
    }
}
