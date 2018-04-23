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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.CUSTOMERS_TABLE;
import static main.java.MayfairStatic.CUSTOMER_CUSTNUM;
import static main.java.MayfairStatic.CUSTOMER_DELADDRESS;
import static main.java.MayfairStatic.CUSTOMER_NAME;
import static main.java.MayfairStatic.CUSTOMER_REFERENCE;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRICE;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.POD_QUANTITY;
import static main.java.MayfairStatic.PO_DELDATE;
import static main.java.MayfairStatic.PO_ORDNUM;
import static main.java.MayfairStatic.PO_PRICE;
import static main.java.MayfairStatic.PO_TOTALUNITS;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_PURCHASEPRICE;
import static main.java.MayfairStatic.PRODUCT_SALESPRICE;
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
import main.java.report.reports.DispatchNoteXls;
import main.java.report.reports.PurchaseOrderSummaryReportXls;
import main.java.report.reports.SalesOrderSummaryReportXls;

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
}
