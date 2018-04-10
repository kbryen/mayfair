/*
 * Mayfair Stock Control.
 *
 */
package main.java;

import java.awt.Component;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kian_bryen
 */
public class MayfairStatic
{

    // ----------------------- DATABASE ----------------------- 
    public static final String LOG_SEPERATOR = "-----------------------------";
    public static final String PRODUCTS_TABLE = "products";
    public static final String PRODUCT_CODE = PRODUCTS_TABLE + ".code";
    public static final String PRODUCT_BARCODE = PRODUCTS_TABLE + ".barcode";
    public static final String PRODUCT_LEATHER = PRODUCTS_TABLE + ".leather";
    public static final String PRODUCT_STYLE = PRODUCTS_TABLE + ".style";
    public static final String PRODUCT_COLOUR = PRODUCTS_TABLE + ".colour";
    public static final String PRODUCT_SALESPRICE = PRODUCTS_TABLE + ".sales_price";
    public static final String PRODUCT_PURCHASEPRICE = PRODUCTS_TABLE + ".purchase_price";
    public static final String PRODUCT_SEASON = PRODUCTS_TABLE + ".SSAW";
    public static final String PRODUCT_INSTOCK = PRODUCTS_TABLE + ".in_stock";
    public static final String PRODUCT_ONORDER = PRODUCTS_TABLE + ".in_order";
    public static final String PRODUCT_COMMENTS = PRODUCTS_TABLE + ".comments";
    
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
    public static final String SALES_ORDER_TABLE = "sales_order";
    public static final String SALES_ORDER_DETAILS_TABLE = "sales_order_details";
    public static final String PURCHASE_ORDER_TABLE = "purchase_order";
    public static final String PURCHASE_ORDER_DETAILS_TABLE = "purchase_order_details";

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

    public static void fillTable(JTable table, ResultSet rs) throws SQLException
    {
        while (table.getRowCount() > 0)
        {
            ((DefaultTableModel) table.getModel()).removeRow(0);
        }
        int columns = rs.getMetaData().getColumnCount();
        while (rs.next())
        {
            Object[] row = new Object[columns];

            for (int i = 1; i <= columns; i++)
            {
                row[i - 1] = rs.getObject(i);
            }
            ((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
        }
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
        JOptionPane.showMessageDialog(component, "Message for Kian", ex.getLocalizedMessage(), ERROR_MESSAGE);
    }

    public static void outputMessage(Component component, String title, String message, int type)
    {
        JOptionPane.showMessageDialog(component, title, message, type);
    }
}
