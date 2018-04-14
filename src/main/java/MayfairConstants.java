/*
 * Mayfair Stock Control.
 *
 */
package main.java;

import java.awt.Component;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author kian_bryen
 */
public class MayfairConstants
{

    public static final String LOG_SEPERATOR = "-----------------------------";
    public static final String PRODUCTS_CODE = "code";
    public static final String PRODUCTS_BARCODE = "barcode";
    public static final String PRODUCTS_LEATHER = "leather";
    public static final String PRODUCTS_STYLE = "style";
    public static final String PRODUCTS_COLOUR = "colour";
    public static final String PRODUCTS_SALESPRICE = "sales_price";
    public static final String PRODUCTS_PURCHASEPRICE = "purchase_price";
    public static final String PRODUCTS_SEASON = "SSAW";
    public static final String PRODUCTS_INSTOCK = "in_stock";
    public static final String PRODUCTS_ONORDER = "in_order";
    public static final String PRODUCTS_COMMENTS = "comments";

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

    public static final String REPORTS_DIR = "S://STOCK SYSTEM/";
    public static final String PROD_SALES_ORDERS_DIR = REPORTS_DIR + "Product Sales Orders/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String PURCHASE_ORDERS_DIR = REPORTS_DIR + "Purchase Orders/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String SALES_PURCHASE_ORDERS_DIR = REPORTS_DIR + "Sales & Purchase Order Reports/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String STOCK_REPORTS_DIR = REPORTS_DIR + "Stock Reports/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String DISPATCH_NOTES_DIR = REPORTS_DIR + "Dispatch Notes/" + Calendar.getInstance().get(Calendar.YEAR) + "/";
    public static final String CUSTOMER_REPORTS_DIR = REPORTS_DIR + "Customer Reports/" + Calendar.getInstance().get(Calendar.YEAR) + "/";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy EEE");
    private static final Comparator DATE_COMPARATOR = (Comparator) (Object o1, Object o2)
            -> LocalDate.parse(o1.toString(), DATE_FORMAT).compareTo(LocalDate.parse(o2.toString(), DATE_FORMAT));

    public static void addDateSorter(JTable table, int[] indexes)
    {
        for (Integer index : indexes)
        {
            MayfairConstants.addDateSorter(table, index);
        }
    }

    public static void addDateSorter(JTable table, int index)
    {
        TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
        sorter.setComparator(index, DATE_COMPARATOR);
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

    public static int outputMessage(Component component, String title, String message)
    {
        return JOptionPane.showConfirmDialog(component, message, title, YES_NO_OPTION);
    }
}
