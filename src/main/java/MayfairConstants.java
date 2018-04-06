/*
 * Mayfair Stock Control.
 *
 */
package main.java;

import java.util.Calendar;

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
    public static final String DISPATCH_NOTES_DIR = REPORTS_DIR + "Dispatch Notes/" + Calendar.getInstance().get(Calendar.YEAR)+ "/";
    public static final String CUSTOMER_REPORTS_DIR = REPORTS_DIR + "Customer Reports/"  + Calendar.getInstance().get(Calendar.YEAR) + "/";
}
