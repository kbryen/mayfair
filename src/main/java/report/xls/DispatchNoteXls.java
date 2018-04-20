/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.xls;

import java.util.Map;
import java.util.Map.Entry;
import static main.java.MayfairStatic.DISPATCH_NOTES_DIR;
import static main.java.MayfairStatic.DISPATCH_NOTE_TEMPLATE;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 *
 * @author kian_bryen
 */
public class DispatchNoteXls extends XlsReport
{

    private final String outputDir = DISPATCH_NOTES_DIR;
    private final String sheetName = "Dispatch Note";
    private String del_date = "";
    private int cust_num = 0;
    private String cust_name = "";
    private String del_address = "";
    private Map<String, Integer> products;
    private int ord_num = 0;
    private String cust_reference = "";

    public DispatchNoteXls()
    {
        super(XlsReport.getHSSFWorkbook(DISPATCH_NOTE_TEMPLATE));
    }

    public void populateWorkbook()
    {
        HSSFSheet sheet = getWorkbook().getSheet(sheetName);
        int rowCount = 5;

        // --- Order Details ---
        // Delivery date
        HSSFRow row = sheet.getRow(rowCount++);
        HSSFCell cell = row.createCell(1);
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
        String[] del_address_parts = del_address.split(",");
        for (String del_address_part : del_address_parts)
        {
            row = sheet.getRow(rowCount++);
            cell = row.createCell(1);
            cell.setCellValue(del_address_part.trim());
            cell.setCellStyle(getStyle(RIGHT));
        }

        // Products
        int maxProdsPerPage = 41;
        int prodsCount = 12;

        rowCount = 19;
//        row = sheet.createRow(rowCount++);
//        addProductsHeader(row);

        for (Entry<String, Integer> product : products.entrySet())
        {
            prodsCount++;
            if (prodsCount == maxProdsPerPage)
            {
                prodsCount = 0;
                rowCount += 8;
                row = sheet.createRow(rowCount++);

                cell = row.createCell(0);
                cell.setCellStyle(getStyle(BOLD + LEFT + BORDER + BOTTOM));
                cell.setCellValue("Product Code");

                cell = row.createCell(1);
                cell.setCellStyle(getStyle(BOLD + RIGHT + BORDER + BOTTOM));
                cell.setCellValue("Quantity");
            }

            row = sheet.createRow(rowCount++);
            cell = row.createCell(0);
            cell.setCellValue(product.getKey());
            cell.setCellStyle(getStyle(BORDER + BOTTOM));
            cell = row.createCell(1);
            cell.setCellStyle(getStyle(BORDER + BOTTOM));
            cell.setCellValue(product.getValue());
        }

        // Total Units
        row = sheet.createRow(++rowCount);
        cell = row.createCell(0);
        cell.setCellStyle(getStyle(BOLD + LEFT));
        cell.setCellValue("Total Units");
        cell = row.createCell(1);
        cell.setCellStyle(getStyle(BOLD + RIGHT));
        cell.setCellValue(products.values().stream().mapToInt(Integer::intValue).sum());

//        autoSizeColumns(sheet, 4);
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
        return outputDir + ord_num + " - " + cust_reference + " " + del_date.replace('/', '-') + EXTENSION;
    }
}
