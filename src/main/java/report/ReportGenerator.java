/*
 * Mayfair Stock Control.
 *
 */
package main.java.report;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
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
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SOD_FROMSTOCK;
import static main.java.MayfairStatic.SOD_ORDNUM;
import static main.java.MayfairStatic.SOD_PRICE;
import static main.java.MayfairStatic.SOD_PRODNUM;
import static main.java.MayfairStatic.SOD_QUANTITY;
import static main.java.MayfairStatic.SO_CUSTNUM;
import static main.java.MayfairStatic.SO_DELDATE;
import static main.java.MayfairStatic.SO_DELIVERED;
import static main.java.MayfairStatic.SO_DISPATCHED;
import static main.java.MayfairStatic.SO_ORDDATE;
import static main.java.MayfairStatic.SO_ORDNUM;
import static main.java.MayfairStatic.SO_PRICE;
import static main.java.MayfairStatic.SO_TOTALUNITS;
import main.java.report.reports.AvailableStockReportXls;
import main.java.report.reports.CustomerReportXls;
import main.java.report.reports.DispatchNoteXls;
import main.java.report.reports.OutOfStockReportXls;
import main.java.report.reports.PurchaseOrderSummaryReportXls;
import main.java.report.reports.SalesOrderSummaryReportXls;
import main.java.report.reports.StockReportXls;
import main.java.report.reports.OrdersPerProductReportXls;
import main.java.report.reports.SalesOrdersForProductReportXls;

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
            catch (Exception ex)
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
            catch (Exception ex)
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
            catch (Exception ex)
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
                ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", " 
                        + PRODUCT_CODE + ", " 
                        + PRODUCT_INSTOCK + " "
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
            catch (Exception ex)
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
                ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + " "
                        + "FROM " + PRODUCTS_TABLE);
                while (rs.next())
                {
                    prod_nums.add(rs.getString(PRODUCT_PRODNUM));
                }

                for (String prod_num : prod_nums)
                {
                    rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", "
                             + PRODUCT_CODE + ", "
                            + "(IFNULL(SUM(" + SOD_FROMSTOCK + "), 0) + " + PRODUCT_INSTOCK + ") AS warehouseStock,"
                            + PRODUCT_INSTOCK + " "
                            + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                            + "JOIN " + SALES_ORDER_TABLE + " "
                            + "ON " + SOD_ORDNUM + "=" + SO_ORDNUM + " "
                            + "JOIN " + PRODUCTS_TABLE + " "
                            + "ON " + SOD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                            + "WHERE " + SO_DISPATCHED + " = false "
                            + "AND " + SO_DELIVERED + " = false "
                            + "AND " + PRODUCT_PRODNUM + " = " + prod_num + " "
                            + "AND " + SOD_PRODNUM + " = " + prod_num);
                    rs.next();
                    Map<String, String> product = new HashMap();
                    product.put("Product Number", rs.getString(PRODUCT_PRODNUM));
                    product.put("Product Code", rs.getString(PRODUCT_CODE));
                    product.put("Warehouse Stock", rs.getString("warehouseStock"));
                    product.put("Avaliable", rs.getString(PRODUCT_INSTOCK));
                    products.add(product);
                }
            }
            catch (Exception ex)
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
            catch (Exception ex)
            {
                MayfairStatic.outputMessage(component, ex);
            }

            OutOfStockReportXls stockReport = new OutOfStockReportXls();
            stockReport.setReportName("Out Of Stock Report");
            stockReport.setProducts(products);
            stockReport.generate(component);
        }
    }

    public static void createOrdersPerProductReport(Component component, String type, Date startDate, Date endDate)
    {
        if (MayfairStatic.outputConfirm(component, type + " Orders Made Report", "Are you sure you want to create a " + type.toLowerCase() + " orders made report?") == JOptionPane.YES_OPTION)
        {
            Set<String[]> products = new HashSet();
            String quantity;
            String sql = "SELECT " + PRODUCT_PRODNUM + ", " + PRODUCT_CODE + ", ";
            if (type.equals("Sales"))
            {
                quantity = SOD_QUANTITY;
                sql += quantity + " FROM " + SALES_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + SALES_ORDER_TABLE + " "
                        + "ON " + SOD_ORDNUM + "=" + SO_ORDNUM + " "
                        + "JOIN " + PRODUCTS_TABLE + " "
                        + "ON " + SOD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                        + "WHERE " + SO_DELDATE + " ";
            }
            else
            {
                quantity = POD_QUANTITY;
                sql += quantity + " FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + PURCHASE_ORDER_TABLE + " "
                        + "ON " + POD_ORDNUM + "=" + PO_ORDNUM + " "
                        + "JOIN " + PRODUCTS_TABLE + " "
                        + "ON " + POD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                        + "WHERE " + PO_DELDATE + " ";
            }
            sql += "BETWEEN '" + CAL_DATE_FORMAT.format(startDate) + "' AND '" + CAL_DATE_FORMAT.format(endDate) + "'";

            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next())
                {
                    products.add(new String[]
                    {
                        rs.getString(PRODUCT_PRODNUM),
                        rs.getString(PRODUCT_CODE),
                        rs.getString(quantity)
                    });
                }

                OrdersPerProductReportXls ordersPerReport = new OrdersPerProductReportXls();
                ordersPerReport.setReportName(type + " Orders Made");
                ordersPerReport.setProducts(products);
                ordersPerReport.setStartDate(FILE_DATE_FORMAT.format(startDate));
                ordersPerReport.setEndDate(FILE_DATE_FORMAT.format(endDate));
                ordersPerReport.generate(component);
            }
            catch (Exception ex)
            {
                MayfairStatic.outputMessage(component, ex);
            }
        }
    }

    public static void createSalesOrderReportByProduct(Component component, String prodCode)
    {
        if (MayfairStatic.outputConfirm(component, "Sales Orders Made Report", "<html>Are you sure you want to create a sales orders made for <b>" + prodCode + "</b> report?") == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + " "
                        + "FROM " + PRODUCTS_TABLE + " "
                        + "WHERE " + PRODUCT_CODE + " = '" + prodCode + "'");
                if (rs.next())
                {
                    String prod_num = rs.getString(PRODUCT_PRODNUM);

                    rs = statement.executeQuery("SELECT " + SOD_ORDNUM + ", "
                            + SOD_QUANTITY + ", "
                            + SO_CUSTNUM + ", "
                            + CUSTOMER_NAME + ", "
                            + MayfairStatic.sqlDateFormat(SO_ORDDATE, "%d/%m/%Y") + ", "
                            + MayfairStatic.sqlDateFormat(SO_DELDATE, "%d/%m/%Y") + ", "
                            + SO_DISPATCHED + ", "
                            + SO_DELIVERED + " "
                            + "FROM " + SALES_ORDER_TABLE + " "
                            + "JOIN " + SALES_ORDER_DETAILS_TABLE + " "
                            + "ON " + SO_ORDNUM + "=" + SOD_ORDNUM + " "
                            + "JOIN " + CUSTOMERS_TABLE + " "
                            + "ON " + SO_CUSTNUM + "=" + CUSTOMER_CUSTNUM + " "
                            + "WHERE " + SOD_PRODNUM + " = " + prod_num);
                    if (rs.isBeforeFirst())
                    {
                        Set<String[]> orders = new HashSet();
                        while (rs.next())
                        {
                            orders.add(new String[]
                            {
                                rs.getString(SOD_ORDNUM),
                                rs.getString(CUSTOMER_NAME),
                                rs.getString(SO_ORDDATE),
                                rs.getString(SO_DELDATE),
                                rs.getBoolean(SO_DISPATCHED) ? "yes" : "no",
                                rs.getBoolean(SO_DELIVERED) ? "yes" : "no",
                                rs.getString(SOD_QUANTITY)
                            });
                        }

                        SalesOrdersForProductReportXls ordersPerReport = new SalesOrdersForProductReportXls();
                        ordersPerReport.setReportName("Sales Orders Made");
                        ordersPerReport.setProdCode(prodCode);
                        ordersPerReport.setOrders(orders);
                        ordersPerReport.generate(component);
                    }
                    else
                    {
                        MayfairStatic.outputMessage(component, "Report not created", "<html> No sales orders made for <b> " + prodCode + " </b>", WARNING_MESSAGE);
                    }
                }
                else
                {
                    MayfairStatic.outputMessage(component, "Unknown Product", "<html>Product does not exist - <b>" + prodCode + "</b> \nPlease select from drop down box", WARNING_MESSAGE);
                }
            }
            catch (Exception ex)
            {
                MayfairStatic.outputMessage(component, ex);
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
                    Map<Integer, String[]> products = new TreeMap();
                    Map<Integer, String[]> disconProducts = new TreeMap();
                    ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", " 
                            + PRODUCT_CODE + ", " 
                            + PRODUCT_SALESPRICE + ", " 
                            + PRODUCT_PURCHASEPRICE + ", " 
                            + PRODUCT_DISCON + " "
                            + "FROM " + PRODUCTS_TABLE + "");
                    while (rs.next())
                    {
                        int prod_num = rs.getInt(PRODUCT_PRODNUM);
                        String[] details = new String[]
                        {
                            rs.getString(PRODUCT_CODE),
                            "£" + rs.getString(PRODUCT_SALESPRICE),
                            "£" + rs.getString(PRODUCT_PURCHASEPRICE)
                        };
                        
                        if (rs.getBoolean(PRODUCT_DISCON))
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
                        whereClauseReference = " WHERE " + CUSTOMER_REFERENCE + " = '" + reference + "'";
                    }

                    Map<Integer, Pair<String, Map<Integer, Integer>>> customers = new TreeMap();
                    rs = statement.executeQuery("SELECT " + CUSTOMER_CUSTNUM + ", " + CUSTOMER_NAME + " "
                            + "FROM " + CUSTOMERS_TABLE 
                            + whereClauseReference);
                    while (rs.next())
                    {
                        customers.put(rs.getInt(CUSTOMER_CUSTNUM), new Pair(rs.getString(CUSTOMER_NAME), new TreeMap()));
                    }

                    String sqlStart = "SELECT SUM(" + SOD_QUANTITY + ") AS quantity "
                            + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                            + "JOIN " + SALES_ORDER_TABLE + " "
                            + "ON " + SOD_ORDNUM + "=" + SO_ORDNUM + " ";

                    String sqlEnd = "";
                    if (startDate != null && endDate != null)
                    {
                        fileNameExtras.append(" - ")
                                .append(FILE_DATE_FORMAT.format(startDate))
                                .append(" ")
                                .append(FILE_DATE_FORMAT.format(endDate));
                        sqlEnd += " AND " + SO_DELDATE + " >= '"
                                + CAL_DATE_FORMAT.format(startDate)
                                + "' AND " + SO_DELDATE + " <= '"
                                + CAL_DATE_FORMAT.format(endDate) + "'";
                    }

                    for (Map.Entry<Integer, Pair<String, Map<Integer, Integer>>> customer : customers.entrySet())
                    {
                        int cust_num = customer.getKey();
                        Map<Integer, Integer> prodCounts = customer.getValue().getValue();
                        for (Integer product : products.keySet())
                        {
                            rs = statement.executeQuery(sqlStart
                                    + "WHERE " + SO_CUSTNUM + " = "
                                    + cust_num
                                    + " AND " + SOD_PRODNUM + " = "
                                    + product
                                    + sqlEnd);
                            rs.next();
                            prodCounts.put(product, rs.getInt("quantity"));
                        }
                        for (Integer product : disconProducts.keySet())
                        {
                            rs = statement.executeQuery(sqlStart
                                    + "WHERE " + SO_CUSTNUM + " = "
                                    + cust_num
                                    + " AND " + SOD_PRODNUM + " = "
                                    + product
                                    + sqlEnd);
                            rs.next();
                            prodCounts.put(product, rs.getInt("quantity"));
                        }
                    }

                    CustomerReportXls customerReport = new CustomerReportXls();
                    customerReport.setReportName("Customer Report");
                    customerReport.setProducts(products);
                    customerReport.setDisconProducts(disconProducts);
                    customerReport.setCustomers(customers);
                    customerReport.setFileNameExtras(fileNameExtras.toString());
                    customerReport.generate(component);
                }
                catch (Exception ex)
                {
                    MayfairStatic.outputMessage(component, ex);
                }
            }
        }
    }
}
