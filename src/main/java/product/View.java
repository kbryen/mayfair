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
import javax.swing.table.DefaultTableModel;
import main.java.Database;
import static main.java.MayfairStatic.*;

/**
 *
 * @author kian_bryen
 */
public class View extends javax.swing.JInternalFrame
{
    private final JDesktopPane desktop;
    private final Database db = new Database();
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
    
    public View(JDesktopPane pane, int num)
    {
        this.desktop = pane;
        prodNum = num;
        initComponents();
        requestDB();
        updateLabels();
        table.setAutoCreateRowSorter(true);
    }

    private void requestDB()
    {
        try (Statement statement = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = statement.executeQuery("SELECT code, barcode, leather, style, colour, sales_price, purchase_price, SSAW, in_stock, in_order, comments FROM products WHERE prod_num = " + prodNum);
            rs.next();
            
            prodCode = rs.getString(PRODUCT_CODE);
            barCode = rs.getString(PRODUCT_BARCODE);
            leather = rs.getString(PRODUCT_LEATHER);
            style = rs.getString(PRODUCT_STYLE);
            colour = rs.getString(PRODUCT_COLOUR);
            season = rs.getString(PRODUCT_SEASON);
            comments = rs.getString(PRODUCT_COMMENTS);
            salesPrice = rs.getDouble(PRODUCT_SALESPRICE);
            purchasePrice = rs.getDouble(PRODUCT_PURCHASEPRICE);
            inStock = rs.getInt(PRODUCT_INSTOCK);
            onOrder = rs.getInt(PRODUCT_ONORDER);
            
            rs = statement.executeQuery("SELECT SUM(sales_order_details.fromStock) AS total FROM sales_order_details JOIN sales_order ON sales_order_details.ord_num=sales_order.ord_num WHERE sales_order_details.prod_num = " + prodNum + " AND sales_order.dispatched = false AND sales_order.delivered = false");
            rs.next();
            
            int notYetDispatched = rs.getInt("total");
            warehouseStock = inStock + notYetDispatched;

        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(View.this, e.getMessage());
        }
    }
    
    private void updateLabels()
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
        labelWarehouseStock.setText(String.valueOf(warehouseStock));
        
        // Show a breakdown of stock on purchase orders
        labelOnOrder.setText(String.valueOf(onOrder));
        if(onOrder == 0)
        {
            scrollPane.setVisible(false);
            table.setVisible(false);
        }
        else
        {
            try (Statement statement = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = statement.executeQuery("SELECT purchase_order.ord_num, purchase_order_details.avaliable, DATE(purchase_order.del_date) AS del_date FROM purchase_order JOIN purchase_order_details ON purchase_order.ord_num = purchase_order_details.ord_num WHERE purchase_order.delivered=false AND purchase_order_details.prod_num = " + prodNum);
            
                scrollPane.setVisible(true);
                table.setVisible(true);
                getContentPane().validate();
                getContentPane().repaint();
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
                
                table.setSize(table.getWidth(),table.getRowCount() * table.getRowHeight());
                scrollPane.setSize(table.getSize());
                getContentPane().setSize(scrollPane.getSize());
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(View.this, e.getMessage());
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
        labelOnOrder = new javax.swing.JLabel();
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

        labelOnOrder.setText("0");

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
                                .addComponent(labelOnOrder))
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
                        .addComponent(labelOnOrder)
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
        Sales sales = new Sales(labelProdCode.getText(), Integer.valueOf(labelProdNum.getText()));
        desktop.add(sales);
        sales.show();
    }//GEN-LAST:event_btnViewSalesActionPerformed

    private void btnInStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInStockActionPerformed
        JOptionPane.showMessageDialog(View.this, "Instantly available stock which is currently in the warehouse and not on an active sales order.", "Available Stock", INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnInStockActionPerformed

    private void btnWarehouseStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWarehouseStockActionPerformed
        JOptionPane.showMessageDialog(View.this, "Instantly available stock + stock which is on an active sales order but not dispatched from the warehouse.", "Warehouse Stock", INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnWarehouseStockActionPerformed

    private void btnOnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnOrderActionPerformed
        JOptionPane.showMessageDialog(View.this, "Stock on undelivered active purchase orders and not on an active sales order.", "On Order", INFORMATION_MESSAGE);
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
    private javax.swing.JLabel labelInStock;
    private javax.swing.JLabel labelLeather;
    private javax.swing.JLabel labelOnOrder;
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
