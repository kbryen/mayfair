/*
 * Mayfair Stock Control.
 *
 */
package main.java.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import main.java.Database;
import static main.java.MayfairConstants.*;

/**
 *
 * @author kian_bryen
 */
public class Edit extends javax.swing.JInternalFrame
{
    private final JDesktopPane desktop;
    private final Database db = new Database();
    private String sql;
    
    private final int prodNum;
    
    private String prodCode = "";
    private String barCode = "";
    private String leather = "";
    private String style = "";
    private String colour = "";
    private String season = "";
    private String comments = "";
    private double salesPrice = 0;
    private double purchasePrice = 0;
    private int inStock = 0;
    private int onOrder = 0;
    private int warehouseStock = 0;
    
    private String prodCodeCurrent = "";
    private String barCodeCurrent = "";
    private String leatherCurrent = "";
    private String styleCurrent = "";
    private String colourCurrent = "";
    private String seasonCurrent = "";
    private String commentsCurrent = "";
    private double salesPriceCurrent = 0;
    private double purchasePriceCurrent = 0;
    
    public Edit(JDesktopPane pane, int num)
    {
        this.desktop = pane;
        prodNum = num;
        initComponents();
        requestDB();
        updateLables();
    }

    private void requestDB()
    {
        try (Connection con = db.getConnection())
        {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery("SELECT code, barcode, leather, style, colour, sales_price, purchase_price, SSAW, in_stock, in_order, comments FROM products WHERE prod_num = " + prodNum);
            rs.next();
            
            prodCode = rs.getString(PRODUCTS_CODE);
            barCode = rs.getString(PRODUCTS_BARCODE);
            leather = rs.getString(PRODUCTS_LEATHER);
            style = rs.getString(PRODUCTS_STYLE);
            colour = rs.getString(PRODUCTS_COLOUR);
            season = rs.getString(PRODUCTS_SEASON);
            comments = rs.getString(PRODUCTS_COMMENTS);
            salesPrice = rs.getDouble(PRODUCTS_SALESPRICE);
            purchasePrice = rs.getDouble(PRODUCTS_PURCHASEPRICE);
            inStock = rs.getInt(PRODUCTS_INSTOCK);
            onOrder = rs.getInt(PRODUCTS_ONORDER);
            
            rs = statement.executeQuery("SELECT SUM(sales_order_details.fromStock) AS total FROM sales_order_details JOIN sales_order ON sales_order_details.ord_num=sales_order.ord_num WHERE sales_order_details.prod_num = " + prodNum + " AND sales_order.dispatched = false AND sales_order.delivered = false");
            rs.next();
            
            int notYetDispatched = rs.getInt("total");
            warehouseStock = inStock + notYetDispatched;

        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(Edit.this, e.getMessage());
        }
    }
    
    private void updateLables()
    {
        labelHeader.setText(prodCode);
        labelProdNum.setText(String.valueOf(prodNum));
        labelBarCode.setText(barCode);
        labelProdCode.setText(prodCode);
        labelLeather.setText(leather);
        labelStyle.setText(style);
        labelColour.setText(colour);
        labelSeason.setText(season);
        labelComments.setText(comments);
        labelSalesPrice.setText(String.valueOf(salesPrice));
        labelPurchasePrice.setText(String.valueOf(purchasePrice));
        labelInStock.setText(String.valueOf(inStock));
        labelOnOrder.setText(String.valueOf(onOrder));
        labelWarehouseStock.setText(String.valueOf(warehouseStock));
    }
    
    private boolean validateFields()
    {
        barCodeCurrent = labelBarCode.getText();
        prodCodeCurrent = labelProdCode.getText();
        leatherCurrent = labelLeather.getText();
        styleCurrent = labelStyle.getText();
        colourCurrent = labelColour.getText();
        seasonCurrent = labelSeason.getText();
        commentsCurrent = labelComments.getText();
        
        if(prodCodeCurrent.isEmpty() || leatherCurrent.isEmpty() || styleCurrent.isEmpty() || colourCurrent.isEmpty())
        {
            JOptionPane.showMessageDialog(Edit.this, "Please complete all compulsory fields or revert changes. (*)");
            return false;
        }
        
        try 
        {
            salesPriceCurrent = labelSalesPrice.getText().isEmpty() ? 0 : Double.valueOf(labelSalesPrice.getText());
            purchasePriceCurrent = labelPurchasePrice.getText().isEmpty() ? 0 : Double.valueOf(labelPurchasePrice.getText());
        }
        catch (NumberFormatException e)
        {
            if (e.getMessage().contains("For input string: \"" + labelSalesPrice.getText() + "\"")) 
            {
                JOptionPane.showMessageDialog(Edit.this, "Sales Price must be a number");
            } 
            else if (e.getMessage().contains("For input string: \"" + labelPurchasePrice.getText() + "\"")) 
            {
                JOptionPane.showMessageDialog(Edit.this, "Purchase Price must be a number");
            }
            return false;
        }
        
        if(salesPriceCurrent < 0)
        {
            JOptionPane.showMessageDialog(Edit.this, "Sales Price must not be negative");
            salesPriceCurrent = 0;
            return false;
        }
        if(purchasePriceCurrent < 0)
        {
            JOptionPane.showMessageDialog(Edit.this, "Purchase Price must not be negative");
            purchasePriceCurrent = 0;
            return false;
        }
        return true;
    }
    
    private boolean changesMade()
    {
        return !barCodeCurrent.equals(barCode) ||  !prodCodeCurrent.equals(prodCode) || !leatherCurrent.equals(leather) || !styleCurrent.equals(style) || !colourCurrent.equals(colour) || !seasonCurrent.equals(season) || !commentsCurrent.equals(comments) || salesPriceCurrent != salesPrice || purchasePriceCurrent != purchasePrice;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        labelBarCode6 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        btnCancel1 = new javax.swing.JButton();
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
        labelOnOrder = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        labelWarehouseStock = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        btnCancel1.setText("Cancel");
        btnCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel1ActionPerformed(evt);
            }
        });

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Edit Product");
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(400, 700));

        labelHeader.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        labelHeader.setText("Product Code");

        jLabel2.setText("*Product Code:");

        jLabel3.setText("*Leather:");

        jLabel4.setText("*Style:");

        jLabel5.setText("*Colour:");

        jLabel6.setText("Sales Price: ");

        jLabel8.setText("Comments:");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel10.setText("Purchase Price: ");

        jLabel12.setText("Season: ");

        btnRevert.setText("Revert Changes");
        btnRevert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevertActionPerformed(evt);
            }
        });

        jLabel14.setText("Product Number:");

        labelProdNum.setText("0");

        jLabel16.setText("Barcode:");

        jLabel11.setText("£");

        jLabel17.setText("£");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOverrideStock.setText("Override Stock Count");
        btnOverrideStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOverrideStockActionPerformed(evt);
            }
        });

        btnInStock.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        btnInStock.setText("?");
        btnInStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInStockActionPerformed(evt);
            }
        });

        btnWarehouseStock.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        btnWarehouseStock.setText("?");
        btnWarehouseStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWarehouseStockActionPerformed(evt);
            }
        });

        btnOnOrder.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        btnOnOrder.setText("?");
        btnOnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnOrderActionPerformed(evt);
            }
        });

        labelInStock.setText("0");

        labelOnOrder.setText("0");

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
                        .addGap(0, 127, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelOnOrder))
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
                        .addComponent(labelOnOrder)
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
        if(validateFields())
        {
        if(changesMade())
        {
            if(JOptionPane.showConfirmDialog(null, "Are you sure you want to save your changes?", "Save Changes", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                sql = "UPDATE products SET barcode = '" + barCodeCurrent + "', code = '" + prodCodeCurrent + "', leather = '" + leatherCurrent + "', style = '" + styleCurrent + "', colour = '" + colourCurrent + "', SSAW = '" + seasonCurrent + "', comments = '" + commentsCurrent + "', sales_price = " + salesPriceCurrent + ", purchase_price = " + purchasePriceCurrent + " WHERE prod_num = " + prodNum;
            
                try (Statement statement = db.getConnection().createStatement())
                {
                    statement.executeUpdate(sql);
                    db.writeToLog("EDIT PRODUCT");
                    db.writeToLog(sql);
                    db.writeToLog(LOG_SEPERATOR);
                    
                    JOptionPane.showMessageDialog(Edit.this, "Product Saved");
                    this.dispose();
                }
                catch (SQLException e)
                {
                    JOptionPane.showMessageDialog(Edit.this, e.getMessage());
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(Edit.this, "No changes made.");
            this.dispose();
        }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRevertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevertActionPerformed
        if(changesMade())
        {
            if(JOptionPane.showConfirmDialog(null, "Are you sure you want to revert your changes?", "Revert Changes", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                this.updateLables();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(Edit.this, "No changes made.");
        }
    }//GEN-LAST:event_btnRevertActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel your changes?", "Cancel Changes", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
           this.dispose();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancel1ActionPerformed

    private void btnOverrideStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOverrideStockActionPerformed
            try 
            {
                int newWarehouseStock = Integer.valueOf(JOptionPane.showInputDialog("Please input new warehouse stock number for " + prodCode + " (Current = " + warehouseStock + "):"));
                if(newWarehouseStock < 0)
                {
                    throw new NumberFormatException("Negative number " + newWarehouseStock);
                }
                
                int onSalesOrder = warehouseStock - inStock;
                if(newWarehouseStock < onSalesOrder)
                {
                    JOptionPane.showMessageDialog(Edit.this, "Warehouse stock must be at least " + onSalesOrder + " to fulfill active sales orders");
                }
                else
                {
                    int newInStock = inStock + (newWarehouseStock - warehouseStock);
                    
                    try (Statement statement = db.getConnection().createStatement())
                    {
                        db.writeToLog("OVERRIDE STOCK COUNT " + prodCode);
                        sql = "UPDATE products SET in_stock = " + newInStock + " WHERE code = '" + prodCode + "'";
                        statement.executeUpdate(sql);
                        db.writeToLog(sql);
                        db.writeToLog(LOG_SEPERATOR);
                        
                        requestDB();
                        updateLables();
                    }
                    catch (SQLException e)
                    {
                        JOptionPane.showMessageDialog(Edit.this, e.getMessage());
                    }
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(Edit.this, "Warehouse stock must be a positive whole number");
            }
    }//GEN-LAST:event_btnOverrideStockActionPerformed

    private void btnInStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInStockActionPerformed
        JOptionPane.showMessageDialog(Edit.this, "Instantly available stock which is currently in the warehouse and not on an active sales order.", "Available Stock", INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnInStockActionPerformed

    private void btnWarehouseStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWarehouseStockActionPerformed
        JOptionPane.showMessageDialog(Edit.this, "Instantly available stock + stock which is on an active sales order but not dispatched from the warehouse.", "Warehouse Stock", INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnWarehouseStockActionPerformed

    private void btnOnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnOrderActionPerformed
        JOptionPane.showMessageDialog(Edit.this, "Stock on undelivered active purchase orders and not on an active sales order.", "On Order", INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnOnOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancel1;
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
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField labelBarCode;
    private javax.swing.JTextField labelBarCode6;
    private javax.swing.JTextField labelColour;
    private javax.swing.JTextField labelComments;
    private javax.swing.JLabel labelHeader;
    private javax.swing.JLabel labelInStock;
    private javax.swing.JTextField labelLeather;
    private javax.swing.JLabel labelOnOrder;
    private javax.swing.JTextField labelProdCode;
    private javax.swing.JLabel labelProdNum;
    private javax.swing.JTextField labelPurchasePrice;
    private javax.swing.JTextField labelSalesPrice;
    private javax.swing.JTextField labelSeason;
    private javax.swing.JTextField labelStyle;
    private javax.swing.JLabel labelWarehouseStock;
    // End of variables declaration//GEN-END:variables
}
