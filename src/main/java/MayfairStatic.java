/*
 * Mayfair Stock Control.
 *
 */
package main.java;

import java.awt.Component;
import java.beans.PropertyVetoException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author kian_bryen
 */
public class MayfairStatic
{

    // ----------------------- DATABASE ----------------------- 
    public static final String PRODUCTS_TABLE = "products";
    public static final String PRODUCT_PRODNUM = PRODUCTS_TABLE + ".prod_num";
    public static final String PRODUCT_CODE = PRODUCTS_TABLE + ".code";
    public static final String PRODUCT_BARCODE = PRODUCTS_TABLE + ".barcode";
    public static final String PRODUCT_LEATHER = PRODUCTS_TABLE + ".leather";
    public static final String PRODUCT_STYLE = PRODUCTS_TABLE + ".style";
    public static final String PRODUCT_COLOUR = PRODUCTS_TABLE + ".colour";
    public static final String PRODUCT_SALESPRICE = PRODUCTS_TABLE + ".sales_price";
    public static final String PRODUCT_PURCHASEPRICE = PRODUCTS_TABLE + ".purchase_price";
    public static final String PRODUCT_SEASON = PRODUCTS_TABLE + ".SSAW";
    public static final String PRODUCT_INSTOCK = PRODUCTS_TABLE + ".in_stock";
    public static final String PRODUCT_INORDER = PRODUCTS_TABLE + ".in_order";
    public static final String PRODUCT_COMMENTS = PRODUCTS_TABLE + ".comments";
    public static final String PRODUCT_TOTAL = PRODUCTS_TABLE + ".total";
    public static final String PRODUCT_SSAW = PRODUCTS_TABLE + ".SSAW";

    public static final String CUSTOMERS_TABLE = "customers";
    public static final String CUSTOMER_NUM = CUSTOMERS_TABLE + ".cust_num";
    public static final String CUSTOMER_REFERENCE = CUSTOMERS_TABLE + ".reference";
    public static final String CUSTOMER_NAME = CUSTOMERS_TABLE + ".name";
    public static final String CUSTOMER_CONTACT = CUSTOMERS_TABLE + ".contact";
    public static final String CUSTOMER_TEL = CUSTOMERS_TABLE + ".tel";
    public static final String CUSTOMER_FAX = CUSTOMERS_TABLE + ".fax";
    public static final String CUSTOMER_EMAIL = CUSTOMERS_TABLE + ".email";
    public static final String CUSTOMER_COUNTRY = CUSTOMERS_TABLE + ".country";
    public static final String CUSTOMER_COMMENTS = CUSTOMERS_TABLE + ".comments";
    public static final String CUSTOMER_DELIVERY = CUSTOMERS_TABLE + ".delivery";
    public static final String CUSTOMER_PROFORMA = CUSTOMERS_TABLE + ".proforma";
    public static final String CUSTOMER_DELADDRESS = CUSTOMERS_TABLE + ".del_address";
    public static final String CUSTOMER_BILLINGADDRESS = CUSTOMERS_TABLE + ".billing_address";

    public static final String SUPPLIERS_TABLE = "suppliers";
    public static final String SUPPLIER_NAME = SUPPLIERS_TABLE + ".name";
    public static final String SUPPLIER_SUPPNUM = SUPPLIERS_TABLE + ".supp_num";

    public static final String SALES_ORDER_TABLE = "sales_order";

    public static final String SALES_ORDER_DETAILS_TABLE = "sales_order_details";
    public static final String SOD_ORDNUM = SALES_ORDER_DETAILS_TABLE + ".ord_num";
    public static final String SOD_PRODNUM = SALES_ORDER_DETAILS_TABLE + ".prod_num";
    public static final String SOD_QUANTITY = SALES_ORDER_DETAILS_TABLE + ".quantity";
    public static final String SOD_FROMSTOCK = SALES_ORDER_DETAILS_TABLE + ".fromStock";
    public static final String SOD_FROMORDER = SALES_ORDER_DETAILS_TABLE + ".fromOrder";

    public static final String PURCHASE_ORDER_TABLE = "purchase_order";
    public static final String PO_ORDNUM = PURCHASE_ORDER_TABLE + ".ord_num";
    public static final String PO_ORDDATE = PURCHASE_ORDER_TABLE + ".ord_date";
    public static final String PO_DELIVERED = PURCHASE_ORDER_TABLE + ".delivered";
    public static final String PO_DELDATE = PURCHASE_ORDER_TABLE + ".del_date";
    public static final String PO_PRICE = PURCHASE_ORDER_TABLE + ".price";
    public static final String PO_DISPATCHED = PURCHASE_ORDER_TABLE + ".dispatched";
    public static final String PO_DISPATCHDATE = PURCHASE_ORDER_TABLE + ".dispatched_date";
    public static final String PO_SUPPNUM = PURCHASE_ORDER_TABLE + ".supp_num";
    public static final String PO_TOTALUNITS = PURCHASE_ORDER_TABLE + ".total_units";
    public static final String PO_COMMENTS = PURCHASE_ORDER_TABLE + ".comments";

    public static final String PURCHASE_ORDER_DETAILS_TABLE = "purchase_order_details";
    public static final String POD_CODE = PURCHASE_ORDER_DETAILS_TABLE + ".code";
    public static final String POD_ORDNUM = PURCHASE_ORDER_DETAILS_TABLE + ".ord_num";
    public static final String POD_PRODNUM = PURCHASE_ORDER_DETAILS_TABLE + ".prod_num";
    public static final String POD_QUANTITY = PURCHASE_ORDER_DETAILS_TABLE + ".quantity";
    public static final String POD_AVALIABLE = PURCHASE_ORDER_DETAILS_TABLE + ".avaliable";
    public static final String POD_PRICE = PURCHASE_ORDER_DETAILS_TABLE + ".price";

    public static final String PURCHASE_SALES_ORDER_TABLE = "purchase_sales_order";
    public static final String PS_PONUM = PURCHASE_SALES_ORDER_TABLE + ".po_num";
    public static final String PS_SONUM = PURCHASE_SALES_ORDER_TABLE + ".so_num";
    public static final String PS_PRODNUM = PURCHASE_SALES_ORDER_TABLE + ".prod_num";
    public static final String PS_QUANTITY = PURCHASE_SALES_ORDER_TABLE + ".quantity";

    // ----------------------- TEMPLATES ----------------------- 
    public static final String TEMPLATES_DIR = "S://MayfairApplication/dist/templates/";
    public static final String DISPATCH_NOTE_TEMPLATE = TEMPLATES_DIR + "DispatchNoteTemplateV3.xls";
    public static final String ALL_PURCHASE_TEMPLATE = TEMPLATES_DIR + "AllPurchaseOrders.xls";
    public static final String ALL_SALES_TEMPLATE = TEMPLATES_DIR + "AllSalesOrders.xls";
    public static final String OUT_OF_STOCK_REPORT_TEMPLATE = TEMPLATES_DIR + "OutOfStockReport.xls";
    public static final String STOCK_REPORT_TEMPLATE = TEMPLATES_DIR + "StockReport.xls";
    public static final String WHS_REPORT_TEMPLATE = TEMPLATES_DIR + "WarehouseStockReport.xls";
    public static final String PROD_SALES_TEMPLATE = TEMPLATES_DIR + "ProdSalesOrders.xls";
    public static final String CUSTOMERS_TEMPLATE = TEMPLATES_DIR + "Customers.xls";

    public static final String DISPATCH_NOTE_TEMPLATE_XLSX = TEMPLATES_DIR + "DispatchNoteTemplateV3.xlsx";
    public static final String ALL_PURCHASE_TEMPLATE_XLSX = TEMPLATES_DIR + "AllPurchaseOrders.xlsx";
    public static final String ALL_SALES_TEMPLATE_XLSX = TEMPLATES_DIR + "AllSalesOrders.xlsx";
    public static final String OUT_OF_STOCK_REPORT_TEMPLATE_XLSX = TEMPLATES_DIR + "OutOfStockReport.xlsx";
    public static final String STOCK_REPORT_TEMPLATE_XLSX = TEMPLATES_DIR + "StockReport.xlsx";
    public static final String WHS_REPORT_TEMPLATE_XLSX = TEMPLATES_DIR + "WarehouseStockReport.xlsx";
    public static final String PROD_SALES_TEMPLATE_XLSX = TEMPLATES_DIR + "ProdSalesOrders.xlsx";
    public static final String CUSTOMERS_TEMPLATE_XLSX = TEMPLATES_DIR + "Customers.xlsx";

    // ----------------------- REPORTS ----------------------- 
    public static final String REPORTS_DIR = "S://STOCK SYSTEM/";
    public static final String PROD_SALES_ORDERS_DIR = REPORTS_DIR + "Product Sales Orders/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String PURCHASE_ORDERS_DIR = REPORTS_DIR + "Purchase Orders/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String SALES_PURCHASE_ORDERS_DIR = REPORTS_DIR + "Sales & Purchase Order Reports/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String STOCK_REPORTS_DIR = REPORTS_DIR + "Stock Reports/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String DISPATCH_NOTES_DIR = REPORTS_DIR + "Dispatch Notes/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String CUSTOMER_REPORTS_DIR = REPORTS_DIR + "Customer Reports/" + Calendar.getInstance().get(Calendar.YEAR) + "/";

    public static final String LOG_SEPERATOR = "-----------------------------";
    public static final String LOG_FILE = "S:/MayfairApplication/log/CurrentLog.txt";
    public static final String HOST = "jdbc:mysql://192.168.200.2:3306/mayfair";
    public static final String USERNAME = "mayfair";
    public static final String PASSWORD = "mayfair";

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy EEE");
    public static final Comparator DATE_COMPARATOR = (Comparator) (Object o1, Object o2)
            -> LocalDate.parse(o1.toString(), DATE_FORMAT).compareTo(LocalDate.parse(o2.toString(), DATE_FORMAT));

    public static void addDateSorter(JTable table, int[] indexes)
    {
        for (Integer index : indexes)
        {
            addDateSorter(table, index);
        }
    }

    public static void addDateSorter(JTable table, int index)
    {
        TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
        sorter.setComparator(index, DATE_COMPARATOR);
    }

    public static Connection getConnection()
    {
        try
        {
            return DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(null, ex);
            return null;
        }
    }

    public static void writeToLog(String message)
    {
        try (FileWriter fw = new FileWriter(LOG_FILE, true))
        {
            fw.append("[" + new Date().toString() + "] " + message + "\n");
        }
        catch (IOException ex)
        {
            MayfairStatic.outputMessage(null, ex);
        }
    }

    public static void fillTable(JTable table, ResultSet rs) throws SQLException
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (table.getRowCount() > 0)
        {
            model.removeRow(0);
        }
        int columns = rs.getMetaData().getColumnCount();
        while (rs.next())
        {
            Object[] row = new Object[columns];

            for (int i = 1; i <= columns; i++)
            {
                row[i - 1] = rs.getObject(i);
            }
            model.insertRow(rs.getRow() - 1, row);
        }
    }

    public static String sqlDateFormat(String date)
    {
        return sqlDateFormat(date, "%d/%m/%Y %a");
    }

    public static String sqlDateFormat(String date, String format)
    {
        return "DATE_FORMAT(" + date + ", '" + format + "') AS " + date;
    }

    public static void setMaximum(JInternalFrame jframe)
    {
        try
        {
            jframe.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            outputMessage(jframe, ex);
        }
    }

    public static void outputMessage(Component component, Exception ex)
    {
        outputMessage(component, "Message for Kian", ex.getLocalizedMessage(), ERROR_MESSAGE);
    }

    public static void outputMessage(Component component, String title, String message, int type)
    {
        JOptionPane.showMessageDialog(component, message, title, type);
    }

    public static int outputConfirm(Component component, String title, String message)
    {
        return JOptionPane.showConfirmDialog(component, message, title, YES_NO_OPTION);
    }

    public static String outputInput(Component component, String title, String message)
    {
        return JOptionPane.showInputDialog(component, message, title, QUESTION_MESSAGE);
    }

    public static void updatePurchaseOrderDetails(String ord_num, JLabel units, JLabel price, Component component)
    {
        try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = selectStatement.executeQuery("SELECT SUM(" + POD_PRICE + ") AS total_price, "
                    + "SUM(" + POD_QUANTITY + ") as total_quantity "
                    + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                    + "WHERE " + POD_ORDNUM + " = '" + ord_num + "'");
            rs.next();

            int total_units = rs.getInt("total_units");
            double total_price = rs.getDouble("total_price");
            units.setText("Total Units: " + total_units);
            price.setText("Order Total: £" + String.format("%.02f", total_price));

            try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
            {
                updateStatement.executeUpdate("UPDATE " + PURCHASE_ORDER_TABLE + " "
                        + "SET " + PO_PRICE + " = " + total_price + ", "
                        + PO_TOTALUNITS + " = " + total_units + " "
                        + "WHERE " + PO_ORDNUM + " = '" + ord_num + "'");
            }
        }
        catch (SQLException ex)
        {
            outputMessage(component, ex);
        }
    }
}
