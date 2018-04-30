/*
 * Mayfair Stock Control.
 *
 */
package main.java.report;

import java.awt.Component;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.ALL_PURCHASE_TEMPLATE;
import static main.java.MayfairStatic.ALL_SALES_TEMPLATE;
import static main.java.MayfairStatic.CUSTOMERS_TABLE;
import static main.java.MayfairStatic.CUSTOMERS_TEMPLATE;
import static main.java.MayfairStatic.CUSTOMER_CUSTNUM;
import static main.java.MayfairStatic.CUSTOMER_DELADDRESS;
import static main.java.MayfairStatic.CUSTOMER_NAME;
import static main.java.MayfairStatic.CUSTOMER_REFERENCE;
import static main.java.MayfairStatic.CUSTOMER_REPORTS_DIR;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRICE;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.POD_QUANTITY;
import static main.java.MayfairStatic.PO_DELDATE;
import static main.java.MayfairStatic.PO_DELIVERED;
import static main.java.MayfairStatic.PO_ORDNUM;
import static main.java.MayfairStatic.PO_PRICE;
import static main.java.MayfairStatic.PO_TOTALUNITS;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_INSTOCK;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_PURCHASEPRICE;
import static main.java.MayfairStatic.PRODUCT_SALESPRICE;
import static main.java.MayfairStatic.PROD_SALES_ORDERS_DIR;
import static main.java.MayfairStatic.PROD_SALES_TEMPLATE;
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SOD_ORDNUM;
import static main.java.MayfairStatic.SOD_PRICE;
import static main.java.MayfairStatic.SOD_PRODNUM;
import static main.java.MayfairStatic.SOD_QUANTITY;
import static main.java.MayfairStatic.SO_CUSTNUM;
import static main.java.MayfairStatic.SO_DELDATE;
import static main.java.MayfairStatic.SO_ORDNUM;
import static main.java.MayfairStatic.SO_PRICE;
import static main.java.MayfairStatic.SO_TOTALUNITS;
import static main.java.MayfairStatic.STOCK_REPORTS_DIR;
import static main.java.MayfairStatic.WHS_REPORT_TEMPLATE;
import main.java.report.reports.AvailableStockReportXls;
import main.java.report.reports.DiscontinuedStockReportXls;
import main.java.report.reports.DispatchNoteXls;
import main.java.report.reports.OutOfStockReportXls;
import main.java.report.reports.PurchaseOrderSummaryReportXls;
import main.java.report.reports.SalesOrderSummaryReportXls;
import main.java.report.reports.XlsReport;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD;

/**
 *
 * @author kian_bryen
 */
public class ReportGenerator
{

    public static void createSalesOrderSummaryReport(Component component, int ord_num)
    {
        if (MayfairStatic.outputConfirm(component, "Summary Report", "Are you sure you want to create a Summary Report for order number " + ord_num + "?") == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                SalesOrderSummaryReportXls summaryReport = new SalesOrderSummaryReportXls();
                summaryReport.setReportName("Sales Order Summary");
                summaryReport.setOrd_num(ord_num);

                ResultSet rs = statement.executeQuery("SELECT " + MayfairStatic.sqlDateFormat(SO_DELDATE, "%d/%m/%Y") + ", "
                        + SO_CUSTNUM + ", "
                        + CUSTOMER_NAME + ", "
                        + SO_PRICE + ", "
                        + SO_TOTALUNITS + " "
                        + "FROM " + SALES_ORDER_TABLE + " "
                        + "JOIN " + CUSTOMERS_TABLE + " "
                        + "ON " + SO_CUSTNUM + "=" + CUSTOMER_CUSTNUM
                        + "WHERE " + SO_ORDNUM + " = " + ord_num);
                rs.next();
                summaryReport.setDel_date(rs.getString(SO_DELDATE));
                summaryReport.setCust_num(rs.getInt(SO_CUSTNUM));
                summaryReport.setCust_name(rs.getString(CUSTOMER_NAME));
                summaryReport.setTotal_price(rs.getDouble(SO_PRICE));
                summaryReport.setTotal_units(rs.getInt(SO_TOTALUNITS));

                List<String[]> products = new ArrayList();
                rs = statement.executeQuery("SELECT " + PRODUCT_CODE + ", "
                        + SOD_QUANTITY + ", "
                        + PRODUCT_SALESPRICE + ", "
                        + SOD_PRICE + " "
                        + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + PRODUCTS_TABLE + " "
                        + "ON " + SOD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                        + "WHERE " + SOD_ORDNUM + " = '" + ord_num + "'");
                while (rs.next())
                {
                    String[] entry = new String[]
                    {
                        rs.getString(PRODUCT_CODE),
                        rs.getString(SOD_QUANTITY),
                        rs.getString(PRODUCT_SALESPRICE),
                        rs.getString(SOD_PRICE)
                    };
                    products.add(entry);
                }
                summaryReport.setProducts(products);

                summaryReport.generate(component);
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(component, ex);
            }
        }
    }

    public static void createDispatchNote(Component component, int ord_num, String cust_name)
    {
        if (MayfairStatic.outputConfirm(component, "Dispatch Note", "<html> Are you sure you want to create a dispatch note for order <b>" + ord_num + "</b>?") == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                DispatchNoteXls dispatchNote = new DispatchNoteXls();

                ResultSet rs = statement.executeQuery("SELECT " + MayfairStatic.sqlDateFormat(SO_DELDATE) + ", "
                        + CUSTOMER_CUSTNUM + ", "
                        + CUSTOMER_REFERENCE + ", "
                        + CUSTOMER_DELADDRESS + " "
                        + "FROM " + SALES_ORDER_TABLE + " "
                        + "JOIN " + CUSTOMERS_TABLE + " "
                        + "ON " + SO_CUSTNUM + "=" + CUSTOMER_CUSTNUM + " "
                        + "WHERE " + SO_ORDNUM + " = " + ord_num);
                rs.next();
                dispatchNote.setDel_date(rs.getString(SO_DELDATE));
                dispatchNote.setCust_num(rs.getInt(CUSTOMER_CUSTNUM));
                dispatchNote.setCust_reference(rs.getString(CUSTOMER_REFERENCE));
                dispatchNote.setDel_address(rs.getString(CUSTOMER_DELADDRESS));

                Map<String, Integer> products = new HashMap();
                rs = statement.executeQuery("SELECT " + PRODUCT_CODE + ", " + SOD_QUANTITY + " "
                        + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + PRODUCTS_TABLE + " "
                        + "ON " + SOD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                        + "WHERE " + SOD_ORDNUM + " = " + ord_num);
                while (rs.next())
                {
                    products.put(rs.getString(PRODUCT_CODE), rs.getInt(SOD_QUANTITY));
                }
                dispatchNote.setProducts(products);

                dispatchNote.setReportName("Dispatch Note");
                dispatchNote.setOrd_num(ord_num);
                dispatchNote.setCust_name(cust_name);
                dispatchNote.generate(component);
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(component, ex);
            }
        }
    }

    public static void createPurchaseOrderSummaryReport(Component component, String ord_num)
    {
        if (MayfairStatic.outputConfirm(component, "Summary Report", "Are you sure you want to create a Summary Report for " + ord_num + "?") == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                PurchaseOrderSummaryReportXls summaryReport = new PurchaseOrderSummaryReportXls();
                summaryReport.setReportName("Purchase Order Summary");
                summaryReport.setOrd_num(ord_num);

                ResultSet rs = statement.executeQuery("SELECT " + MayfairStatic.sqlDateFormat(PO_DELDATE, "%d/%m/%Y") + ", "
                        + PO_PRICE + ", "
                        + PO_TOTALUNITS + " "
                        + "FROM " + PURCHASE_ORDER_TABLE + " "
                        + "WHERE " + PO_ORDNUM + " = '" + ord_num + "'");
                rs.next();
                summaryReport.setDel_date(rs.getString(PO_DELDATE));
                summaryReport.setTotal_price(rs.getDouble(PO_PRICE));
                summaryReport.setTotal_units(rs.getInt(PO_TOTALUNITS));

                List<String[]> products = new ArrayList();
                rs = statement.executeQuery("SELECT " + PRODUCT_CODE + ", "
                        + POD_QUANTITY + ", "
                        + POD_AVALIABLE + ", "
                        + "(" + POD_QUANTITY + " - " + POD_AVALIABLE + ") AS SOLD "
                        + PRODUCT_PURCHASEPRICE + ", "
                        + POD_PRICE + " "
                        + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + PRODUCTS_TABLE + " "
                        + "ON " + POD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                        + "WHERE " + POD_ORDNUM + " = '" + ord_num + "'");
                while (rs.next())
                {
                    String[] entry = new String[]
                    {
                        rs.getString(PRODUCT_CODE),
                        rs.getString(POD_QUANTITY),
                        rs.getString("SOLD"),
                        rs.getString(POD_AVALIABLE),
                        rs.getString(PRODUCT_PURCHASEPRICE),
                        rs.getString(POD_PRICE)
                    };
                    products.add(entry);
                }
                summaryReport.setProducts(products);

                summaryReport.generate(component);
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(component, ex);
            }
        }
    }

    public static void createDiscontinuedStockReport(Component component)
    {
        if (MayfairStatic.outputConfirm(component, "Discontinued Stock Report", "Are you sure you want to create a discontinued stock report?") == JOptionPane.YES_OPTION)
        {
            Map<String, Integer> products = new HashMap();
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                // Get a list of all discontinued products
                ResultSet rs = statement.executeQuery("SELECT code, in_stock "
                        + "FROM products WHERE discon = true");
                while (rs.next())
                {
                    String code = rs.getString("code");
                    int in_stock = rs.getInt("in_stock");
                    products.put(code, in_stock);
                }
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(component, e);
            }

            DiscontinuedStockReportXls stockReport = new DiscontinuedStockReportXls();
            stockReport.setReportName("Discontinued Stock Report");
            stockReport.setProducts(products);
            stockReport.generate(component);
        }
    }

    public static void createAvailableStockReport(Component component)
    {
        if (MayfairStatic.outputConfirm(component, "Available Stock Report", "Are you sure you want to create an available stock report?") == JOptionPane.YES_OPTION)
        {
            Map<String, Map<String, Integer>> productCounts = new HashMap();
            List<String> purchaseOrders = new ArrayList();
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                // Get a list of all undelivered po's
                ResultSet rs = statement.executeQuery("SELECT " + PO_ORDNUM + " "
                        + "FROM " + PURCHASE_ORDER_TABLE + " "
                        + "WHERE " + PO_DELIVERED + " = false");
                while (rs.next())
                {
                    purchaseOrders.add(rs.getString(PO_ORDNUM));
                }

                // Get a list of all products
                Map<Integer, String> prodNumToCode = new HashMap();
                rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", " + PRODUCT_CODE + ", " + PRODUCT_INSTOCK + " "
                        + "FROM " + PRODUCTS_TABLE);
                while (rs.next())
                {
                    int prod_num = rs.getInt(PRODUCT_PRODNUM);
                    String code = rs.getString(PRODUCT_CODE);
                    prodNumToCode.put(prod_num, code);

                    Map<String, Integer> productCount = new HashMap();
                    productCounts.put(code, productCount);
                    int in_stock = rs.getInt(PRODUCT_INSTOCK);
                    productCount.put("In Stock", in_stock);

                    for (String purchaseOrder : purchaseOrders)
                    {
                        productCount.put(purchaseOrder, 0);
                    }
                }

                // Update po avaliability for products
                rs = statement.executeQuery("SELECT " + PO_ORDNUM + ", " + POD_PRODNUM + ", " + POD_AVALIABLE + " "
                        + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + PURCHASE_ORDER_TABLE + " "
                        + "ON " + POD_ORDNUM + "=" + PO_ORDNUM + " "
                        + "WHERE " + PO_DELIVERED + " = false");
                while (rs.next())
                {
                    String ord_num = rs.getString(PO_ORDNUM);
                    int prod_num = rs.getInt(POD_PRODNUM);
                    int avaliable = rs.getInt(POD_AVALIABLE);

                    String code = prodNumToCode.get(prod_num);
                    productCounts.get(code).put(ord_num, avaliable);
                }

                // Calculate totals / out of stocks
                List<String> outOfStocks = new ArrayList();
                for (Map.Entry<String, Map<String, Integer>> products : productCounts.entrySet())
                {
                    String code = products.getKey();
                    Map<String, Integer> counts = products.getValue();

                    int total = 0;
                    for (Integer value : counts.values())
                    {
                        total += value;
                    }

                    if (total != 0)
                    {
                        counts.put("Total", total);
                    }
                    else
                    {
                        outOfStocks.add(code);
                    }
                }

                // Remove out of stocks
                for (String outOfStock : outOfStocks)
                {
                    productCounts.remove(outOfStock);
                }
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(component, ex);
            }

            AvailableStockReportXls stockReport = new AvailableStockReportXls();
            stockReport.setReportName("Available Stock Report");
            stockReport.setProductCounts(productCounts);
            stockReport.setPurchaseOrders(purchaseOrders);
            stockReport.generate(component);
        }
    }

    public static void createWarehouseStockReport(Component component)
    {
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to create a warehouse stock report?", "Warehouse Stock Report", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String fileName = STOCK_REPORTS_DIR + "Warehouse Stock Report " + date + ".xls";
            try (FileOutputStream fileOut = new FileOutputStream(fileName))
            {
                HSSFWorkbook workBook = XlsReport.createHSSFWorkbook(WHS_REPORT_TEMPLATE);
                HSSFSheet sheet = workBook.getSheet("Warehouse Stock Report");

                HSSFCellStyle numberStyle = workBook.createCellStyle();
                numberStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));

                // Create bold style
                HSSFCellStyle bold = workBook.createCellStyle();
                HSSFFont boldFont = workBook.createFont();
                boldFont.setBoldweight(BOLDWEIGHT_BOLD);
                bold.setFont(boldFont);

                try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
                {
                    HSSFRow row;
                    int rowCount = 2;
                    HSSFCell cell;

                    // Date row
                    row = sheet.getRow(0);
                    cell = row.getCell(1);
                    cell.setCellValue(date);

                    List<String> prodNums = new ArrayList();
                    ResultSet rs = statement.executeQuery("SELECT prod_num FROM products");
                    while (rs.next())
                    {
                        prodNums.add(rs.getString("prod_num"));
                    }

                    for (String prodNum : prodNums)
                    {
                        rs = statement.executeQuery("SELECT products.prod_num AS prodNum, products.code AS code, (IFNULL(SUM(sales_order_details.fromStock), 0) + products.in_stock) AS warehouseStock FROM sales_order_details LEFT JOIN sales_order ON sales_order_details.ord_num=sales_order.ord_num RIGHT JOIN products ON sales_order_details.prod_num=products.prod_num WHERE sales_order.dispatched = false AND sales_order.delivered = false AND products.prod_num = " + prodNum + " AND sales_order_details.prod_num = " + prodNum);
                        HashMap<Integer, Pair<String, Integer>> products = new HashMap();
                        while (rs.next())
                        {
                            int prod_num = rs.getInt("prodNum");
                            String code = rs.getString("code");
                            int warehouse = rs.getInt("warehouseStock");

                            // Reset cell count 
                            row = sheet.createRow(rowCount++);

                            // Cell 1 - prod num
                            cell = row.createCell(0);
                            cell.setCellValue(prod_num);
                            cell.setCellStyle(numberStyle);

                            // Cell 2 - prod code
                            cell = row.createCell(1);
                            cell.setCellValue(code);

                            // Cell 3 - warehouse
                            cell = row.createCell(2);
                            cell.setCellValue(warehouse);
                            cell.setCellStyle(numberStyle);
                        }
                    }
                }
                catch (SQLException ex)
                {
                    MayfairStatic.outputMessage(component, ex);
                }

                // Auto Size Columns
                for (int i = 0; i < 3; i++)
                {
                    sheet.autoSizeColumn(i);
                }

                workBook.write(fileOut);
                fileOut.flush();
                fileOut.close();

                JOptionPane.showMessageDialog(component, "<html> <b>Stock report created successfully.</b> \n<html> <i> " + fileName + " </i>", "Report Created", INFORMATION_MESSAGE);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(component, "<html> Error while creating stock report, please try again.\n<html> <i> If error continues to happen please contact Kian. </i>", "Error", ERROR_MESSAGE);
                JOptionPane.showMessageDialog(component, e.getStackTrace(), "Message for Kian:", ERROR_MESSAGE);
            }
        }
    }

    public static void createOutOfStockReport(Component component)
    {
        if (MayfairStatic.outputConfirm(component, "Out of Stock Report", "Are you sure you want to create an out of stock report?") == JOptionPane.YES_OPTION)
        {
            Map<Integer, String> products = new HashMap();
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", " + PRODUCT_CODE + " "
                        + "FROM " + PRODUCTS_TABLE + " "
                        + "WHERE " + PRODUCT_INSTOCK + " = 0");
                while (rs.next())
                {
                    products.put(rs.getInt(PRODUCT_PRODNUM), rs.getString(PRODUCT_CODE));
                }
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(component, ex);
            }

            OutOfStockReportXls stockReport = new OutOfStockReportXls();
            stockReport.setReportName("Out Of Stock Report");
            stockReport.setProducts(products);
            stockReport.generate(component);
        }
    }

    public static void createPurchaseOrderReportByDates(Component component, Date startDate, Date endDate)
    {
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to create a purchase orders report?", "Purchase Orders Report", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yy");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

                ResultSet rs = statement.executeQuery("SELECT products.prod_num, products.code, purchase_order_details.quantity FROM purchase_order_details JOIN purchase_order ON purchase_order_details.ord_num = purchase_order.ord_num JOIN products ON purchase_order_details.prod_num = products.prod_num WHERE del_date >= '" + df2.format(startDate) + "' AND del_date <= '" + df2.format(endDate) + "'");
                if (rs.isBeforeFirst())
                {
                    String fileName = "S&PDIR" + "Purchase Orders " + df1.format(startDate) + " " + df1.format(endDate) + ".xls";
                    try (FileOutputStream fileOut = new FileOutputStream(fileName))
                    {
                        HSSFWorkbook workBook = XlsReport.createHSSFWorkbook(ALL_PURCHASE_TEMPLATE);

                        HSSFCellStyle numberStyle = workBook.createCellStyle();
                        numberStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));

                        HSSFSheet sheet = workBook.getSheet("Purchase Orders Made");

                        HSSFRow row;
                        int rowCount = 0;
                        HSSFCell cell;

                        // Start date
                        row = sheet.getRow(rowCount++);
                        cell = row.getCell(5);
                        cell.setCellValue(df1.format(startDate));

                        // End date
                        row = sheet.getRow(rowCount++);
                        cell = row.getCell(5);
                        cell.setCellValue(df1.format(endDate));

                        // Fill table
                        while (rs.next())
                        {
                            // num
                            cell = row.createCell(0);
                            cell.setCellValue(rs.getInt("prod_num"));
                            cell.setCellStyle(numberStyle);
                            // code
                            cell = row.createCell(1);
                            cell.setCellValue(rs.getString("code"));
                            // quant
                            cell = row.createCell(2);
                            cell.setCellValue(rs.getInt("quantity"));
                            cell.setCellStyle(numberStyle);

                            row = sheet.createRow(rowCount++);
                        }

                        // Auto Size Columns
                        for (int i = 0; i < 3; i++)
                        {
                            sheet.autoSizeColumn(i);
                        }

                        workBook.write(fileOut);
                        fileOut.flush();
                        fileOut.close();

                        JOptionPane.showMessageDialog(component, "<html> <b>Purchase orders report created successfully.</b> \n<html> <i> " + fileName + " </i>", "Report Created", INFORMATION_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(component, "<html> No purchase orders made between <b> " + df1.format(startDate) + " </b> and <b> " + df1.format(endDate) + " </b>", "Report not created", INFORMATION_MESSAGE);
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(component, "<html> Error while creating purchase orders report, please try again.\n<html> <i> If error continues to happen please contact Kian. </i>", "Error", ERROR_MESSAGE);
                JOptionPane.showMessageDialog(component, e.getStackTrace(), "Message for Kian:", ERROR_MESSAGE);
            }
        }
    }

    public static void createSalesOrderReportByDates(Component component, Date startDate, Date endDate)
    {
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to create a sales orders report?", "Sales Orders Report", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yy");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

                ResultSet rs = statement.executeQuery("SELECT products.prod_num, products.code, sales_order_details.quantity FROM sales_order_details JOIN sales_order ON sales_order_details.ord_num = sales_order.ord_num JOIN products ON sales_order_details.prod_num = products.prod_num WHERE del_date >= '" + df2.format(startDate) + "' AND del_date <= '" + df2.format(endDate) + "'");
                if (rs.isBeforeFirst())
                {
                    String fileName = "S&PDIR" + "Sales Orders " + df1.format(startDate) + " " + df1.format(endDate) + ".xls";
                    try (FileOutputStream fileOut = new FileOutputStream(fileName))
                    {
                        HSSFWorkbook workBook = XlsReport.createHSSFWorkbook(ALL_SALES_TEMPLATE);
                        HSSFSheet sheet = workBook.getSheet("Sales Orders Made");

                        HSSFCellStyle numberStyle = workBook.createCellStyle();
                        numberStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));

                        HSSFRow row;
                        int rowCount = 0;
                        HSSFCell cell;

                        // Start date
                        row = sheet.getRow(rowCount++);
                        cell = row.getCell(5);
                        cell.setCellValue(df1.format(startDate));

                        // End date
                        row = sheet.getRow(rowCount++);
                        cell = row.getCell(5);
                        cell.setCellValue(df1.format(endDate));

                        // Fill table
                        while (rs.next())
                        {
                            // num
                            cell = row.createCell(0);
                            cell.setCellValue(rs.getInt("prod_num"));
                            cell.setCellStyle(numberStyle);
                            // code
                            cell = row.createCell(1);
                            cell.setCellValue(rs.getString("code"));
                            // quant
                            cell = row.createCell(2);
                            cell.setCellValue(rs.getInt("quantity"));
                            cell.setCellStyle(numberStyle);

                            row = sheet.createRow(rowCount++);
                        }

                        // Auto Size Columns
                        for (int i = 0; i < 3; i++)
                        {
                            sheet.autoSizeColumn(i);
                        }

                        workBook.write(fileOut);
                        fileOut.flush();
                        fileOut.close();

                        JOptionPane.showMessageDialog(component, "<html> <b>Sales orders report created successfully.</b> \n<html> <i> " + fileName + " </i>", "Report Created", INFORMATION_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(component, "<html> No sales orders made between <b> " + df1.format(startDate) + " </b> and <b> " + df1.format(endDate) + " </b>", "Report not created", INFORMATION_MESSAGE);
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(component, "<html> Error while creating sales orders report, please try again.\n<html> <i> If error continues to happen please contact Kian. </i>", "Error", ERROR_MESSAGE);
                JOptionPane.showMessageDialog(component, e.getStackTrace(), "Message for Kian:", ERROR_MESSAGE);
            }
        }
    }

    public static void createSalesOrderReportByProduct(Component component, String prodCode)
    {
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to create a sales orders report?", "Sales Orders Report", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = statement.executeQuery("SELECT prod_num FROM products where code = '" + prodCode + "'");
                if (rs.next())
                {
                    String prodNum = rs.getString("prod_num");

                    rs = statement.executeQuery("SELECT sales_order_details.ord_num, sales_order_details.quantity, sales_order.cust_num, customers.name, sales_order.ord_date, sales_order.del_date, sales_order.dispatched, sales_order.delivered FROM sales_order JOIN sales_order_details ON sales_order.ord_num=sales_order_details.ord_num JOIN customers ON sales_order.cust_num=customers.cust_num WHERE sales_order_details.prod_num = " + prodNum);
                    if (rs.isBeforeFirst())
                    {
                        String fileName = PROD_SALES_ORDERS_DIR + "Sales Orders " + prodCode.replaceAll("/", "") + ".xls";
                        try (FileOutputStream fileOut = new FileOutputStream(fileName))
                        {

                            HSSFWorkbook workBook = XlsReport.createHSSFWorkbook(PROD_SALES_TEMPLATE);
                            HSSFSheet sheet = workBook.getSheet("Sales Orders Made");

                            HSSFCellStyle numberStyle = workBook.createCellStyle();
                            numberStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));

                            HSSFRow row;
                            int rowCount = 3;
                            HSSFCell cell;

                            // Prod code
                            row = sheet.getRow(0);
                            cell = row.getCell(1);
                            cell.setCellValue(prodCode);

                            // Fill table
                            while (rs.next())
                            {
                                row = sheet.createRow(rowCount++);

                                cell = row.createCell(0);
                                cell.setCellValue(rs.getInt("ord_num"));
                                cell.setCellStyle(numberStyle);

                                cell = row.createCell(1);
                                cell.setCellValue(rs.getString("name"));
                                cell = row.createCell(2);
                                cell.setCellValue(rs.getString("ord_date"));

                                cell = row.createCell(3);
                                cell.setCellValue(rs.getString("del_date"));

                                cell = row.createCell(4);
                                if (rs.getInt("dispatched") == 1)
                                {
                                    cell.setCellValue("yes");
                                }
                                else
                                {
                                    cell.setCellValue("no");
                                }

                                cell = row.createCell(5);
                                if (rs.getInt("delivered") == 1)
                                {
                                    cell.setCellValue("yes");
                                }
                                else
                                {
                                    cell.setCellValue("no");
                                }

                                cell = row.createCell(6);
                                cell.setCellValue(rs.getInt("quantity"));
                                cell.setCellStyle(numberStyle);
                            }

                            // Auto Size Columns
                            for (int i = 0; i < 7; i++)
                            {
                                sheet.autoSizeColumn(i);
                            }

                            workBook.write(fileOut);
                            fileOut.flush();
                            fileOut.close();

                            JOptionPane.showMessageDialog(component, "<html> <b>Sales orders report created successfully.</b> \n<html> <i> " + fileName + " </i>", "Report Created", INFORMATION_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(component, "<html> No sales orders made for <b> " + prodCode + " </b>", "Report not created", INFORMATION_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(component, "<html>Product does not exist - <b>" + prodCode + "</b> \nPlease select from drop down box", "Unknown Product", WARNING_MESSAGE);
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(component, "<html> Error while creating sales orders report, please try again.\n<html> <i> If error continues to happen please contact Kian. </i>", "Error", ERROR_MESSAGE);
                JOptionPane.showMessageDialog(component, e.getStackTrace(), "Message for Kian:", ERROR_MESSAGE);
            }
        }
    }

    public static void createCustomerReport(Component component, Date startDate, Date endDate, String reference)
    {
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to create a customer report?", "Customer Report", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            if (reference.isEmpty())
            {
                JOptionPane.showMessageDialog(component, "<html><b>WARNING:</b>This report can take up to <b>3 minutes</b> to create.</html>\nPlease do not use the system and ensure no one else is while the report is created.", "Warning", WARNING_MESSAGE);
            }

            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yy");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

                StringBuilder fileName = new StringBuilder();
                fileName.append(CUSTOMER_REPORTS_DIR).append("Customer Report");

                String whereClauseReference = "";
                if (!reference.isEmpty())
                {
                    fileName.append(" - ").append(reference);
                    whereClauseReference = " WHERE reference = '" + reference + "'";
                }

                String whereClauseDates = "";
                if (startDate != null && endDate != null)
                {
                    fileName.append(" - ").append(df1.format(startDate)).append(" ").append(df1.format(endDate));
                    whereClauseDates = " AND del_date >= '" + df2.format(startDate) + "' AND del_date <= '" + df2.format(endDate) + "'";
                }

                fileName.append(".xls");

                try (FileOutputStream fileOut = new FileOutputStream(fileName.toString()))
                {
                    HSSFWorkbook workBook = XlsReport.createHSSFWorkbook(CUSTOMERS_TEMPLATE);
                    HSSFSheet sheet = workBook.getSheet("Customers");

                    HSSFRow row;
                    HSSFCell cell;

                    // Products
                    int rowCount = 1;
                    Map<Integer, Integer> prodCellIndexes = new HashMap();
                    Map<Integer, String> disconProds = new HashMap();
                    ResultSet rs = statement.executeQuery("SELECT prod_num, code, sales_price, purchase_price, discon FROM products ORDER BY prod_num");
                    while (rs.next())
                    {
                        int prodNum = rs.getInt("prod_num");
                        String code = rs.getString("code");
                        String salesPrice = "£" + rs.getBigDecimal("sales_price").setScale(2, RoundingMode.CEILING).toPlainString();
                        String purchasePrice = "£" + rs.getBigDecimal("purchase_price").setScale(2, RoundingMode.CEILING).toPlainString();
                        boolean discon = rs.getBoolean("discon");

                        if (discon)
                        {
                            disconProds.put(prodNum, code + "," + salesPrice + "," + purchasePrice);
                        }
                        else
                        {
                            prodCellIndexes.put(prodNum, rowCount);

                            row = sheet.createRow(rowCount++);
                            cell = row.createCell(0);
                            cell.setCellValue(prodNum);
                            cell = row.createCell(1);
                            cell.setCellValue(code);
                            cell = row.createCell(2);
                            cell.setCellValue(salesPrice);
                            cell = row.createCell(3);
                            cell.setCellValue(purchasePrice);
                        }
                    }

                    // Discontinuted                  
                    rowCount++;
                    row = sheet.createRow(rowCount++);
                    cell = row.createCell(0);
                    cell.setCellValue("DISCONTINUED");
                    rowCount++;
                    for (Map.Entry<Integer, String> discon : disconProds.entrySet())
                    {
                        int prodNum = discon.getKey();
                        prodCellIndexes.put(prodNum, rowCount);

                        String prod = discon.getValue();
                        row = sheet.createRow(rowCount++);
                        int cellCount = 0;

                        cell = row.createCell(cellCount++);
                        cell.setCellValue(prodNum);
                        for (String info : prod.split(","))
                        {
                            cell = row.createCell(cellCount++);
                            cell.setCellValue(info);
                        }
                    }

                    // Auto Size Columns
                    for (int i = 0; i < 5; i++)
                    {
                        sheet.autoSizeColumn(i);
                    }

                    // Customers
                    row = sheet.getRow(0);
                    HSSFCellStyle rotate = workBook.createCellStyle();
                    rotate.setRotation((short) 90);
                    int columnCount = 5;
                    Map<Integer, Integer> custCellIndexes = new HashMap();
                    rs = statement.executeQuery("SELECT cust_num, name FROM customers" + whereClauseReference + " ORDER BY cust_num");
                    while (rs.next())
                    {
                        int custNum = rs.getInt("cust_num");
                        String name = rs.getString("name");
                        custCellIndexes.put(custNum, columnCount);
                        cell = row.createCell(columnCount++);
                        cell.setCellValue(custNum + " - " + name);
                        cell.setCellStyle(rotate);
                        sheet.autoSizeColumn(columnCount - 1);
                    }

                    // Orders made
                    for (Map.Entry<Integer, Integer> product : prodCellIndexes.entrySet())
                    {
                        int prodNum = product.getKey();
                        int rowNum = product.getValue();
                        int total = 0;
                        for (Map.Entry<Integer, Integer> customer : custCellIndexes.entrySet())
                        {
                            int custNum = customer.getKey();
                            int columnNum = customer.getValue();
                            rs = statement.executeQuery("SELECT SUM(quantity) AS total FROM sales_order_details JOIN sales_order ON sales_order_details.ord_num = sales_order.ord_num WHERE sales_order_details.prod_num = " + prodNum + " AND sales_order.cust_num = " + custNum + whereClauseDates);
                            if (rs.isBeforeFirst())
                            {
                                rs.next();
                                int quantity = rs.getInt("total");
                                if (quantity != 0)
                                {
                                    cell = sheet.getRow(rowNum).createCell(columnNum);
                                    cell.setCellValue(quantity);
                                    total += quantity;
                                }
                            }
                        }
                        cell = sheet.getRow(rowNum).createCell(4);
                        cell.setCellValue(total);
                    }

                    workBook.write(fileOut);
                    fileOut.flush();
                    fileOut.close();

                    JOptionPane.showMessageDialog(component, "<html> <b>Customer report created successfully.</b> \n<html> <i> " + fileName.toString() + " </i>", "Report Created", INFORMATION_MESSAGE);
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(component, "<html> Error while creating customer report, please try again.\n<html> <i> If error continues to happen please contact Kian. </i>", "Error", ERROR_MESSAGE);
                JOptionPane.showMessageDialog(component, e.getStackTrace(), "Message for Kian:", ERROR_MESSAGE);
            }
        }
    }
}
