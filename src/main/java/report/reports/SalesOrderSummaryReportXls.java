/*
 * Mayfair Stock Control.
 *
 */
package main.java.report.reports;

import java.util.ArrayList;
import java.util.Set;
import static main.java.MayfairStatic.SALES_ORDERS_DIR;
import static main.java.report.reports.ReportXls.BOLD;
import static main.java.report.reports.ReportXls.EXTENSION;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author kian_bryen
 */
public class SalesOrderSummaryReportXls extends ReportXls
{

    private final String outputDir = SALES_ORDERS_DIR;

    private int ord_num;
    private String del_date;
    private int cust_num;
    private String cust_name;
    private int total_units;
    private double total_price;
    private Set<String[]> products;

    public SalesOrderSummaryReportXls()
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
        cell.setCellValue("Customer Number");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue(cust_num);
        cell.setCellStyle(getStyle(BOLD));

        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Customer Name");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue(cust_name);
        cell.setCellStyle(getStyle(BOLD));

        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue("Product Code");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(1);
        cell.setCellValue("Quantity");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(2);
        cell.setCellValue("Unit Price");
        cell.setCellStyle(getStyle(BOLD));
        cell = row.createCell(3);
        cell.setCellValue("Price");
        cell.setCellStyle(getStyle(BOLD));

        for (String[] product : products)
        {
            row = sheet.createRow(rowCount++);
            for (int i = 0; i < 4; i++)
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

        autoSizeColumns(sheet, 4);
    }

    @Override
    public String getFilename()
    {
        return outputDir + getReportName() + " - " + ord_num + EXTENSION;
    }

    public void setOrd_num(int ord_num)
    {
        this.ord_num = ord_num;
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

    public void setTotal_units(int total_units)
    {
        this.total_units = total_units;
    }

    public void setTotal_price(double total_price)
    {
        this.total_price = total_price;
    }

    public void setProducts(Set<String[]> products)
    {
        this.products = products;
    }

}
