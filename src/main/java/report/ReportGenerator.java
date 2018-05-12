/*
 * Mayfair Stock Control.
 *
 */
package main.java.report;

import java.awt.Component;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.util.Pair;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.ALL_PURCHASE_TEMPLATE;
import static main.java.MayfairStatic.ALL_SALES_TEMPLATE;
import static main.java.MayfairStatic.CAL_DATE_FORMAT;
import static main.java.MayfairStatic.CUSTOMERS_TABLE;
import static main.java.MayfairStatic.CUSTOMER_CUSTNUM;
import static main.java.MayfairStatic.CUSTOMER_DELADDRESS;
import static main.java.MayfairStatic.CUSTOMER_NAME;
import static main.java.MayfairStatic.CUSTOMER_REFERENCE;
import static main.java.MayfairStatic.FILE_DATE_FORMAT;
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
import static main.java.MayfairStatic.PRODUCT_DISCON;
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
import main.java.report.reports.AvailableStockReportXls;
import main.java.report.reports.CustomerReportXls;
import main.java.report.reports.DiscontinuedStockReportXls;
import main.java.report.reports.DispatchNoteXls;
import main.java.report.reports.OutOfStockReportXls;
import main.java.report.reports.PurchaseOrderSummaryReportXls;
import main.java.report.reports.SalesOrderSummaryReportXls;
import main.java.report.reports.StockReportXls;
import main.java.report.reports.ReportXls;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

                Set<String[]> products = new TreeSet();
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

                Map<String, Integer> products = new TreeMap();
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
            Set<Map<String, String>> products = new HashSet();
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                // Get a list of all discontinued products
                ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", " + PRODUCT_CODE + ", " + PRODUCT_INSTOCK + " "
                        + "FROM " + PRODUCTS_TABLE + " "
                        + "WHERE " + PRODUCT_DISCON + " = true");
                while (rs.next())
                {
                    Map<String, String> product = new HashMap();
                    product.put("Product Number", rs.getString(PRODUCT_PRODNUM));
                    product.put("Product Code", rs.getString(PRODUCT_CODE));
                    product.put("In Stock", rs.getString(PRODUCT_INSTOCK));
                    products.add(product);
                }
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(component, e);
            }

            StockReportXls stockReport = new StockReportXls();
            stockReport.setReportName("Discontinued Stock Report");
            stockReport.setProducts(products);
            stockReport.setHeaders(new String[]
            {
                "Product Number", "Product Code", "In Stock"
            });
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
        if (MayfairStatic.outputConfirm(component, "Warehouse Stock Report", "Are you sure you want to create an warehouse stock report?") == JOptionPane.YES_OPTION)
        {
            Set<Map<String, String>> products = new HashSet();
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                List<String> prod_nums = new ArrayList();
                ResultSet rs = statement.executeQuery("SELECT prod_num FROM products");
                while (rs.next())
                {
                    prod_nums.add(rs.getString("prod_num"));
                }

                for (String prod_num : prod_nums)
                {
                    rs = statement.executeQuery("SELECT products.prod_num, "
                            + "products.code, "
                            + "(IFNULL(SUM(sales_order_details.fromStock), 0) + products.in_stock) AS warehouseStock,"
                            + "products.in_stock "
                            + "FROM sales_order_details "
                            + "JOIN sales_order "
                            + "ON sales_order_details.ord_num=sales_order.ord_num "
                            + "JOIN products "
                            + "ON sales_order_details.prod_num=products.prod_num "
                            + "WHERE sales_order.dispatched = false "
                            + "AND sales_order.delivered = false "
                            + "AND products.prod_num = " + prod_num + " "
                            + "AND sales_order_details.prod_num = " + prod_num);
                    rs.next();
                    Map<String, String> product = new HashMap();
                    product.put("Product Number", rs.getString("prod_num"));
                    product.put("Product Code", rs.getString("code"));
                    product.put("Warehouse Stock", rs.getString("warehouseStock"));
                    product.put("Avaliable", rs.getString("in_stock"));
                    products.add(product);
                }
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(component, ex);
            }

            StockReportXls stockReport = new StockReportXls();
            stockReport.setReportName("Warehouse Stock Report");
            stockReport.setProducts(products);
            stockReport.setHeaders(new String[]
            {
                "Product Number", "Product Code", "Warehouse Stock", "Avaliable"
            });
            stockReport.generate(component);
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
                        HSSFWorkbook workBook = ReportXls.createHSSFWorkbook(ALL_PURCHASE_TEMPLATE);

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
                        HSSFWorkbook workBook = ReportXls.createHSSFWorkbook(ALL_SALES_TEMPLATE);
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

                            HSSFWorkbook workBook = ReportXls.createHSSFWorkbook(PROD_SALES_TEMPLATE);
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
        if (MayfairStatic.outputConfirm(component, "Customer Report", "Are you sure you want to create a customer report?") == JOptionPane.YES_OPTION)
        {
            if (!reference.isEmpty()
                    || MayfairStatic.outputConfirm(component, "Warning", "<html><b>WARNING:</b>This report can take up to <b>3 minutes</b> to create.</html>\nPlease do not use the system and ensure no one else is while the report is created.") == JOptionPane.YES_OPTION)
            {
                try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
                {
                    long elapsedTime = 0 - System.nanoTime();

                    // Products
                    Map<Integer, String[]> products = new TreeMap();
                    Map<Integer, String[]> disconProducts = new TreeMap();
                    ResultSet rs = statement.executeQuery("SELECT prod_num, code, sales_price, purchase_price, discon FROM products");
                    while (rs.next())
                    {
                        int prod_num = rs.getInt("prod_num");
                        String[] details = new String[]
                        {
                            rs.getString("code"),
                            "£" + rs.getString("sales_price"),
                            "£" + rs.getString("purchase_price")
                        };
                        boolean discon = rs.getBoolean("discon");
                        if (discon)
                        {
                            disconProducts.put(prod_num, details);
                        }
                        else
                        {
                            products.put(prod_num, details);
                        }
                    }

                    StringBuilder fileNameExtras = new StringBuilder();
                    String whereClauseReference = "";
                    if (!reference.isEmpty())
                    {
                        fileNameExtras.append(" - ").append(reference);
                        whereClauseReference = " WHERE reference = '" + reference + "'";
                    }

                    Map<Integer, Pair<String, Map<Integer, Integer>>> customers = new TreeMap();
                    rs = statement.executeQuery("SELECT cust_num, name FROM customers" + whereClauseReference);
                    while (rs.next())
                    {
                        customers.put(rs.getInt("cust_num"), new Pair(rs.getString("name"), new TreeMap()));
                    }

                    // BUILD SQL
                    StringBuilder sql = new StringBuilder("SELECT ");
                    for (Integer customer : customers.keySet())
                    {
                        for (Integer product : products.keySet())
                        {
                            sql.append("SUM(CASE WHEN sales_order.cust_num = ")
                                    .append(customer)
                                    .append(" AND sales_order_details.prod_num = ")
                                    .append(product)
                                    .append(" THEN quantity ELSE 0 END) AS C")
                                    .append(customer)
                                    .append("P")
                                    .append(product)
                                    .append(", ");
                        }
                        for (Integer product : disconProducts.keySet())
                        {
                            sql.append("SUM(CASE WHEN sales_order.cust_num = ")
                                    .append(customer)
                                    .append(" AND sales_order_details.prod_num = ")
                                    .append(product)
                                    .append(" THEN quantity ELSE 0 END) AS C")
                                    .append(customer)
                                    .append("P")
                                    .append(product)
                                    .append(", ");
                        }
                    }
                    sql.deleteCharAt(sql.length() - 2);
                    sql.append("FROM sales_order_details "
                            + "JOIN sales_order "
                            + "ON sales_order_details.ord_num = sales_order.ord_num");
                    if (startDate != null && endDate != null)
                    {
                        fileNameExtras.append(" - ").append(FILE_DATE_FORMAT.format(startDate)).append(" ").append(FILE_DATE_FORMAT.format(endDate));
                        sql.append(" AND del_date >= '")
                                .append(CAL_DATE_FORMAT.format(startDate))
                                .append("' AND del_date <= '")
                                .append(CAL_DATE_FORMAT.format(endDate))
                                .append("'");
                    }

                    rs = statement.executeQuery(sql.toString());
                    rs.next();
                    for (Map.Entry<Integer, Pair<String, Map<Integer, Integer>>> customer : customers.entrySet())
                    {
                        int cust_num = customer.getKey();
                        Map<Integer, Integer> prodCounts = customer.getValue().getValue();
                        for (Integer prod_num : products.keySet())
                        {
                            prodCounts.put(prod_num, rs.getInt("C" + cust_num + "P" + prod_num));
                        }
                        for (Integer prod_num : disconProducts.keySet())
                        {
                            prodCounts.put(prod_num, rs.getInt("C" + cust_num + "P" + prod_num));
                        }
                    }

                    CustomerReportXls customerReport = new CustomerReportXls();
                    customerReport.setReportName("Customer Report");
                    customerReport.setProducts(products);
                    customerReport.setDisconProducts(disconProducts);
                    customerReport.setCustomers(customers);
                    customerReport.setFileNameExtras(fileNameExtras.toString());
                    customerReport.generate(component);

                    elapsedTime += System.nanoTime();
                    MayfairStatic.outputMessage(component, "Report Time", "Report completed in:\n" + elapsedTime, INFORMATION_MESSAGE);
                }
                catch (Exception ex)
                {
                    MayfairStatic.outputMessage(component, ex);
                }
            }
        }
    }
}
