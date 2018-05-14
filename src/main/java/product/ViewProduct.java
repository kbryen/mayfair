/*
 * Mayfair Stock Control.
 *
 */
package main.java.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.PO_DELDATE;
import static main.java.MayfairStatic.PO_DELIVERED;
import static main.java.MayfairStatic.PO_ORDNUM;
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
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;
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
public class ViewProduct extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;
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

    public ViewProduct(JDesktopPane pane, int prod_num)
    {
        setUpGUI();
        this.desktop = pane;
        this.prod_num = prod_num;
    }

    private void setUpGUI()
    {
        initComponents();
        updateVariables();
        fillLabels();
        table.setAutoCreateRowSorter(true);
        MayfairStatic.addDateSorter(table, 2);
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
        catch (Exception ex)
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
        labelWarehouseStock.setText(String.valueOf(warehouseStock));

        // Show a breakdown of stock on purchase orders
        labelInOrder.setText(String.valueOf(inOrder));
        if (inOrder == 0)
        {
            scrollPane.setVisible(false);
            table.setVisible(false);
        }
        else
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = statement.executeQuery("SELECT " + PO_ORDNUM + ", "
                        + POD_AVALIABLE + ", "
                        + MayfairStatic.sqlDateFormat(PO_DELDATE) + " "
                        + "FROM " + PURCHASE_ORDER_TABLE + " "
                        + "JOIN " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "ON " + PO_ORDNUM + "=" + POD_ORDNUM + " "
                        + "WHERE " + PO_DELIVERED + " = false "
                        + "AND " + POD_PRODNUM + " = " + prod_num);

                MayfairStatic.fillTable(table, rs);
                table.setSize(table.getWidth(), table.getRowCount() * table.getRowHeight());
                scrollPane.setSize(table.getSize());
                getContentPane().setSize(scrollPane.getSize());
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(ViewProduct.this, e.getMessage());
            }
        }
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        labelProdCode = new javax.swing.JLabel();
        labelLeather = new javax.swing.JLabel();
        labelStyle = new javax.swing.JLabel();
        labelColour = new javax.swing.JLabel();
        labelSalesPrice = new javax.swing.JLabel();
        labelPurchasePrice = new javax.swing.JLabel();
        labelSeason = new javax.swing.JLabel();
        labelInStock = new javax.swing.JLabel();
        labelComments = new javax.swing.JLabel();
        labelInOrder = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnViewSales = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        labelProdNum = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        labelWarehouseStock = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        labelBarCode = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        btnInStock = new javax.swing.JButton();
        btnWarehouseStock = new javax.swing.JButton();
        btnOnOrder = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("View Product");
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(500, 650));

        labelHeader.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        labelHeader.setText("Product Code");

        jLabel2.setText("Product Code:");

        jLabel3.setText("Leather:");

        jLabel4.setText("Style:");

        jLabel5.setText("Colour:");

        jLabel6.setText("Sales Price: ");

        jLabel7.setText("£");

        jLabel8.setText("Comments:");

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel9.setText("Available Stock:");

        jLabel10.setText("Purchase Price: ");

        jLabel11.setText("£");

        jLabel12.setText("Season: ");

        labelProdCode.setText("Product Code");

        labelLeather.setText("Leather");

        labelStyle.setText("Style");

        labelColour.setText("Colour");

        labelSalesPrice.setText("0");

        labelPurchasePrice.setText("0");

        labelSeason.setText("Season");

        labelInStock.setText("0");

        labelComments.setText("Comments");

        labelInOrder.setText("0");

        jLabel13.setText("On Order:");

        btnViewSales.setText("View Sales");
        btnViewSales.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnViewSalesActionPerformed(evt);
            }
        });

        jLabel14.setText("Product Number:");

        labelProdNum.setText("0");

        jLabel15.setText("Warehouse Stock:");

        labelWarehouseStock.setText("0");

        jLabel16.setText("Barcode:");

        labelBarCode.setText("Barcode");

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

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String []
            {
                "Order Number", "Quantity", "Delivery Date"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean []
            {
                true, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelComments)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4)
                            .addComponent(jSeparator5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelHeader)
                                .addGap(0, 135, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelProdNum))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelBarCode))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelPurchasePrice))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelSalesPrice)))
                                .addGap(4, 4, 4))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnViewSales)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClose)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnOnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelInOrder))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
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
                                        .addComponent(labelWarehouseStock))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelColour)
                                    .addComponent(labelStyle)
                                    .addComponent(labelLeather)
                                    .addComponent(labelSeason)
                                    .addComponent(labelProdCode))))
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator6)
                        .addContainerGap())))
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
                    .addComponent(labelBarCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelProdCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelLeather))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelStyle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelColour))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(labelSeason))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(labelSalesPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(labelPurchasePrice))
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
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelComments))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnViewSales))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnViewSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSalesActionPerformed
        ProductSales jFrame = new ProductSales(labelProdCode.getText(), Integer.valueOf(labelProdNum.getText()));
        desktop.add(jFrame);
        jFrame.show();
    }//GEN-LAST:event_btnViewSalesActionPerformed

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
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnInStock;
    private javax.swing.JButton btnOnOrder;
    private javax.swing.JButton btnViewSales;
    private javax.swing.JButton btnWarehouseStock;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel labelBarCode;
    private javax.swing.JLabel labelColour;
    private javax.swing.JLabel labelComments;
    private javax.swing.JLabel labelHeader;
    private javax.swing.JLabel labelInOrder;
    private javax.swing.JLabel labelInStock;
    private javax.swing.JLabel labelLeather;
    private javax.swing.JLabel labelProdCode;
    private javax.swing.JLabel labelProdNum;
    private javax.swing.JLabel labelPurchasePrice;
    private javax.swing.JLabel labelSalesPrice;
    private javax.swing.JLabel labelSeason;
    private javax.swing.JLabel labelStyle;
    private javax.swing.JLabel labelWarehouseStock;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
