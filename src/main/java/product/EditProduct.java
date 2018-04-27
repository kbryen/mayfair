/*
 * Mayfair Stock Control.
 *
 */
package main.java.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.LOG_SEPERATOR;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_BARCODE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_COLOUR;
import static main.java.MayfairStatic.PRODUCT_COMMENTS;
import static main.java.MayfairStatic.PRODUCT_INORDER;
import static main.java.MayfairStatic.PRODUCT_INSTOCK;
import static main.java.MayfairStatic.PRODUCT_LEATHER;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_PURCHASEPRICE;
import static main.java.MayfairStatic.PRODUCT_SALESPRICE;
import static main.java.MayfairStatic.PRODUCT_SEASON;
import static main.java.MayfairStatic.PRODUCT_STYLE;
import static main.java.MayfairStatic.SALES_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SOD_FROMSTOCK;
import static main.java.MayfairStatic.SOD_ORDNUM;
import static main.java.MayfairStatic.SOD_PRODNUM;
import static main.java.MayfairStatic.SO_DELIVERED;
import static main.java.MayfairStatic.SO_DISPATCHED;
import static main.java.MayfairStatic.SO_ORDNUM;

/**
 *
 * @author kian_bryen
 */
public class EditProduct extends javax.swing.JInternalFrame
{

    private final int prod_num;

    private String code = "";
    private String barcode = "";
    private String leather = "";
    private String style = "";
    private String colour = "";
    private String season = "";
    private String comments = "";
    private double salesPrice = 0;
    private double purchasePrice = 0;
    private int inOrder = 0;
    private int inStock = 0;
    private int warehouseStock = 0;
    private int onSalesOrderStock = 0;

    private String codeCurrent;
    private String barcodeCurrent;
    private String leatherCurrent;
    private String styleCurrent;
    private String colourCurrent;
    private String seasonCurrent;
    private String commentsCurrent;
    private double salesPriceCurrent;
    private double purchasePriceCurrent;
    private double inStockCurrent;
    private double warehouseStockCurrent;

    public EditProduct(int prod_num)
    {
        this.prod_num = prod_num;
        setUpGUI();
    }

    private void setUpGUI()
    {
        initComponents();
        updateVariables();
        fillLabels();
    }

    private void updateVariables()
    {
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_CODE + ", "
                    + PRODUCT_BARCODE + ", "
                    + PRODUCT_LEATHER + ", "
                    + PRODUCT_STYLE + ", "
                    + PRODUCT_COLOUR + ", "
                    + PRODUCT_SEASON + ", "
                    + PRODUCT_COMMENTS + ", "
                    + PRODUCT_SALESPRICE + ", "
                    + PRODUCT_PURCHASEPRICE + ", "
                    + PRODUCT_INSTOCK + ", "
                    + PRODUCT_INORDER + " "
                    + "FROM " + PRODUCTS_TABLE + " "
                    + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num);
            rs.next();
            code = rs.getString(PRODUCT_CODE);
            barcode = rs.getString(PRODUCT_BARCODE);
            leather = rs.getString(PRODUCT_LEATHER);
            style = rs.getString(PRODUCT_STYLE);
            colour = rs.getString(PRODUCT_COLOUR);
            season = rs.getString(PRODUCT_SEASON);
            comments = rs.getString(PRODUCT_COMMENTS);
            salesPrice = rs.getDouble(PRODUCT_SALESPRICE);
            purchasePrice = rs.getDouble(PRODUCT_PURCHASEPRICE);
            inStock = rs.getInt(PRODUCT_INSTOCK);
            inOrder = rs.getInt(PRODUCT_INORDER);

            rs = statement.executeQuery("SELECT SUM(" + SOD_FROMSTOCK + ") AS total "
                    + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                    + "JOIN " + SALES_ORDER_TABLE + " "
                    + "ON " + SOD_ORDNUM + "=" + SO_ORDNUM + " "
                    + "WHERE " + SOD_PRODNUM + " = " + prod_num + " "
                    + "AND " + SO_DISPATCHED + " = false "
                    + "AND " + SO_DELIVERED + " = false");
            rs.next();
            onSalesOrderStock = rs.getInt("total");
            warehouseStock = inStock + onSalesOrderStock;
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }

    private void fillLabels()
    {
        labelHeader.setText(code);
        labelProdNum.setText(String.valueOf(prod_num));
        labelBarCode.setText(barcode);
        labelProdCode.setText(code);
        labelLeather.setText(leather);
        labelStyle.setText(style);
        labelColour.setText(colour);
        labelSeason.setText(season);
        labelComments.setText(comments);
        labelSalesPrice.setText(String.valueOf(salesPrice));
        labelPurchasePrice.setText(String.valueOf(purchasePrice));
        labelInStock.setText(String.valueOf(inStock));
        labelInOrder.setText(String.valueOf(inOrder));
        labelWarehouseStock.setText(String.valueOf(warehouseStock));

        codeCurrent = "";
        barcodeCurrent = "";
        leatherCurrent = "";
        styleCurrent = "";
        colourCurrent = "";
        seasonCurrent = "";
        commentsCurrent = "";
        salesPriceCurrent = 0;
        purchasePriceCurrent = 0;
        inStockCurrent = 0;
        warehouseStockCurrent = 0;
    }

    private boolean validateFields()
    {
        barcodeCurrent = labelBarCode.getText();
        codeCurrent = labelProdCode.getText();
        leatherCurrent = labelLeather.getText();
        styleCurrent = labelStyle.getText();
        colourCurrent = labelColour.getText();
        seasonCurrent = labelSeason.getText();
        commentsCurrent = labelComments.getText();

        if (codeCurrent.isEmpty() || leatherCurrent.isEmpty() || styleCurrent.isEmpty() || colourCurrent.isEmpty())
        {
            MayfairStatic.outputMessage(this, "Incomplete Product", "Please complete all compulsory fields. (*)", WARNING_MESSAGE);
            return false;
        }

        try
        {
            salesPriceCurrent = labelSalesPrice.getText().isEmpty() ? 0 : Double.valueOf(labelSalesPrice.getText());
            purchasePriceCurrent = labelPurchasePrice.getText().isEmpty() ? 0 : Double.valueOf(labelPurchasePrice.getText());
        }
        catch (NumberFormatException e)
        {
            if (e.getMessage().contains(labelSalesPrice.getText()))
            {
                MayfairStatic.outputMessage(this, "Invalid number", "Sales Price is not a valid number.", WARNING_MESSAGE);
            }
            else if (e.getMessage().contains(labelPurchasePrice.getText()))
            {
                MayfairStatic.outputMessage(this, "Invalid number", "Purchase Price is not a valid number.", WARNING_MESSAGE);
            }
            return false;
        }

        if (salesPriceCurrent < 0)
        {
            MayfairStatic.outputMessage(this, "Invalid number", "Sales Price must not be negative.", WARNING_MESSAGE);
            salesPriceCurrent = 0;
            return false;
        }
        if (purchasePriceCurrent < 0)
        {
            MayfairStatic.outputMessage(this, "Invalid number", "Purchase Price must not be negative.", WARNING_MESSAGE);
            purchasePriceCurrent = 0;
            return false;
        }

        return true;
    }

    private boolean changesMade()
    {
        return !barcodeCurrent.equals(barcode)
                || !codeCurrent.equals(code)
                || !leatherCurrent.equals(leather)
                || !styleCurrent.equals(style)
                || !colourCurrent.equals(colour)
                || !seasonCurrent.equals(season)
                || !commentsCurrent.equals(comments)
                || salesPriceCurrent != salesPrice
                || purchasePriceCurrent != purchasePrice
                || inStockCurrent != inStock
                || warehouseStockCurrent != warehouseStock;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        labelHeader = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnRevert = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        labelProdNum = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        labelBarCode = new javax.swing.JTextField();
        labelProdCode = new javax.swing.JTextField();
        labelLeather = new javax.swing.JTextField();
        labelStyle = new javax.swing.JTextField();
        labelColour = new javax.swing.JTextField();
        labelSeason = new javax.swing.JTextField();
        labelSalesPrice = new javax.swing.JTextField();
        labelPurchasePrice = new javax.swing.JTextField();
        labelComments = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        btnOverrideStock = new javax.swing.JButton();
        btnInStock = new javax.swing.JButton();
        btnWarehouseStock = new javax.swing.JButton();
        btnOnOrder = new javax.swing.JButton();
        labelInStock = new javax.swing.JLabel();
        labelInOrder = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        labelWarehouseStock = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Edit Product");
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(500, 700));

        labelHeader.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        labelHeader.setText("Product Code");

        jLabel2.setText("*Product Code:");

        jLabel3.setText("*Leather:");

        jLabel4.setText("*Style:");

        jLabel5.setText("*Colour:");

        jLabel6.setText("Sales Price: ");

        jLabel8.setText("Comments:");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel10.setText("Purchase Price: ");

        jLabel12.setText("Season: ");

        btnRevert.setText("Revert Changes");
        btnRevert.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnRevertActionPerformed(evt);
            }
        });

        jLabel14.setText("Product Number:");

        labelProdNum.setText("0");

        jLabel16.setText("Barcode:");

        jLabel11.setText("£");

        jLabel17.setText("£");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCancelActionPerformed(evt);
            }
        });

        btnOverrideStock.setText("Override Stock Count");
        btnOverrideStock.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOverrideStockActionPerformed(evt);
            }
        });

        btnInStock.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        btnInStock.setText("?");
        btnInStock.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnInStockActionPerformed(evt);
            }
        });

        btnWarehouseStock.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        btnWarehouseStock.setText("?");
        btnWarehouseStock.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnWarehouseStockActionPerformed(evt);
            }
        });

        btnOnOrder.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        btnOnOrder.setText("?");
        btnOnOrder.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOnOrderActionPerformed(evt);
            }
        });

        labelInStock.setText("0");

        labelInOrder.setText("0");

        jLabel13.setText("On Order:");

        jLabel15.setText("Warehouse Stock:");

        labelWarehouseStock.setText("0");

        jLabel9.setText("Available Stock:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelBarCode, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSeason, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelColour, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelStyle, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelLeather, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSalesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelComments, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(labelProdNum)
                                .addGap(4, 4, 4))
                            .addComponent(btnRevert, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(labelHeader)
                            .addComponent(btnOverrideStock, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 307, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelInOrder))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnWarehouseStock, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelInStock))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelWarehouseStock)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(labelProdNum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(labelBarCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelLeather, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelStyle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(labelSeason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelSalesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(labelPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelInStock)
                        .addComponent(jLabel9))
                    .addComponent(btnInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelWarehouseStock)
                        .addComponent(jLabel15))
                    .addComponent(btnWarehouseStock, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelInOrder)
                        .addComponent(btnOnOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnRevert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel)
                    .addComponent(btnOverrideStock))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (validateFields())
        {
            if (changesMade())
            {
                if (MayfairStatic.outputConfirm(this, "Save Changes", "Are you sure you want to save your changes?") == JOptionPane.YES_OPTION)
                {
                    try (Statement statement = MayfairStatic.getConnection().createStatement())
                    {
                        String sql = "UPDATE " + PRODUCTS_TABLE + " "
                                + "SET " + PRODUCT_BARCODE + " = '" + barcodeCurrent + "', "
                                + PRODUCT_CODE + " = '" + codeCurrent + "', "
                                + PRODUCT_LEATHER + " = '" + leatherCurrent + "', "
                                + PRODUCT_STYLE + " = '" + styleCurrent + "', "
                                + PRODUCT_COLOUR + " = '" + colourCurrent + "', "
                                + PRODUCT_SEASON + " = '" + seasonCurrent + "', "
                                + PRODUCT_COMMENTS + " = '" + commentsCurrent + "', "
                                + PRODUCT_SALESPRICE + " = " + salesPriceCurrent + ", "
                                + PRODUCT_PURCHASEPRICE + " = " + purchasePriceCurrent + ", "
                                + PRODUCT_INSTOCK + " = " + inStockCurrent + " "
                                + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num;
                        statement.executeUpdate(sql);
                        MayfairStatic.writeToLog("EDIT PRODUCT");
                        MayfairStatic.writeToLog(sql);
                        MayfairStatic.writeToLog(LOG_SEPERATOR);

                        MayfairStatic.outputMessage(this, "Product Saved", "Changes to product saved.", INFORMATION_MESSAGE);
                        this.dispose();
                    }
                    catch (SQLException ex)
                    {
                        MayfairStatic.outputMessage(this, ex);
                    }
                }
            }
            else
            {
                MayfairStatic.outputMessage(this, "Product Saved", "No changes made to product.", INFORMATION_MESSAGE);
                this.dispose();
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRevertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevertActionPerformed
        if (changesMade())
        {
            String message = "Changes have been made, are you sure you want to revert these?";
            if (inStock != inStockCurrent)
            {
                message += "\nThis includes stock count changes.";
            }
            
            if (MayfairStatic.outputConfirm(this, "Revert", message) == JOptionPane.YES_OPTION)
            {
                fillLabels();
            }
        }
        else
        {
            MayfairStatic.outputMessage(this, "Revert", "No changes made to product.", INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnRevertActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if (changesMade())
        {
            String message = "Changes have been made, are you sure you want to cancel?";
            if (inStock != inStockCurrent)
            {
                message += "\nThis includes stock count changes.";
            }

            if (MayfairStatic.outputConfirm(this, "Cancel", message) == JOptionPane.YES_OPTION)
            {
                this.dispose();
            }
        }
        else
        {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOverrideStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOverrideStockActionPerformed
        try
        {
            warehouseStockCurrent = Integer.valueOf(MayfairStatic.outputInput(this, "Override Stock Count", "Please input new warehouse stock number for " + code + " (Current = " + warehouseStock + ")"));
            if (warehouseStockCurrent < 0)
            {
                throw new NumberFormatException();
            }

            if (warehouseStockCurrent < onSalesOrderStock)
            {
                MayfairStatic.outputMessage(this, "Invalid quantity", "Warehouse stock must be at least " + onSalesOrderStock + " to fulfill active sales orders.", WARNING_MESSAGE);
            }
            else
            {
                inStockCurrent = inStock + (warehouseStockCurrent - warehouseStock);
                labelInStock.setText(String.valueOf(inStockCurrent));
                labelWarehouseStock.setText(String.valueOf(warehouseStockCurrent));
                MayfairStatic.outputMessage(this, "Stock Count Updated", "Make sure to save changes before closing.", INFORMATION_MESSAGE);
            }
        }
        catch (NumberFormatException ex)
        {
            MayfairStatic.outputMessage(this, "Invalid quantity", "Please enter a valid quantity.", WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnOverrideStockActionPerformed

    private void btnInStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInStockActionPerformed
        MayfairStatic.outputMessage(this, "Available Stock", "Instantly available stock which is currently in the warehouse and not on an active sales order.", INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnInStockActionPerformed

    private void btnWarehouseStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWarehouseStockActionPerformed
        MayfairStatic.outputMessage(this, "Warehouse Stock", "Instantly available stock + stock which is on an active sales order but not dispatched from the warehouse.", INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnWarehouseStockActionPerformed

    private void btnOnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnOrderActionPerformed
        MayfairStatic.outputMessage(this, "On Order", "Stock on undelivered active purchase orders and not on an active sales order.", INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnOnOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnInStock;
    private javax.swing.JButton btnOnOrder;
    private javax.swing.JButton btnOverrideStock;
    private javax.swing.JButton btnRevert;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnWarehouseStock;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField labelBarCode;
    private javax.swing.JTextField labelColour;
    private javax.swing.JTextField labelComments;
    private javax.swing.JLabel labelHeader;
    private javax.swing.JLabel labelInOrder;
    private javax.swing.JLabel labelInStock;
    private javax.swing.JTextField labelLeather;
    private javax.swing.JTextField labelProdCode;
    private javax.swing.JLabel labelProdNum;
    private javax.swing.JTextField labelPurchasePrice;
    private javax.swing.JTextField labelSalesPrice;
    private javax.swing.JTextField labelSeason;
    private javax.swing.JTextField labelStyle;
    private javax.swing.JLabel labelWarehouseStock;
    // End of variables declaration//GEN-END:variables
}
