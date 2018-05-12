/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.reports;

import java.util.ArrayList;
import java.util.List;
import static main.java.MayfairStatic.PURCHASE_ORDERS_DIR;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author kian_bryen
 */
public class PurchaseOrderSummaryReportXls extends ReportXls
{

    private final String outputDir = PURCHASE_ORDERS_DIR;

    private String ord_num;
    private String del_date;
    private int total_units;
    private double total_price;
    private List<String[]> products = new ArrayList();

    public PurchaseOrderSummaryReportXls()
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
        cell.setCellValue("Order Number");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue(ord_num);
        cell.setCellStyle(getStyle(BOLD));

        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Delivery Date");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue(del_date);
        cell.setCellStyle(getStyle(BOLD));

        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Product Code");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue("Quantity");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(2);
        cell.setCellValue("Sold");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(3);
        cell.setCellValue("Avaliable");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(4);
        cell.setCellValue("Unit Price");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(5);
        cell.setCellValue("Price");
        cell.setCellStyle(getStyle(BOLD));

        for (String[] product : products)
        {
            row = sheet.createRow(rowCount++);
            for (int i = 0; i < 6; i++)
            {
                cell = row.createCell(i);
                cell.setCellValue(product[i]);
            }
        }

        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Total Price");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue(total_price);
        cell.setCellStyle(getStyle(BOLD));

        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Total Units");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue(total_units);
        cell.setCellStyle(getStyle(BOLD));

        autoSizeColumns(sheet, 6);
    }

    @Override
    public String getFilename()
    {
        return outputDir + getReportName() + " - " + ord_num + EXTENSION;
    }

    public void setOrd_num(String ord_num)
    {
        this.ord_num = ord_num;
    }

    public void setDel_date(String del_date)
    {
        this.del_date = del_date;
    }

    public void setTotal_units(int total_units)
    {
        this.total_units = total_units;
    }

    public void setTotal_price(double total_price)
    {
        this.total_price = total_price;
    }

    public void setProducts(List<String[]> products)
    {
        this.products = products;
    }

}
