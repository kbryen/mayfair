/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.purchase;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.LOG_SEPERATOR;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRICE;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.POD_QUANTITY;
import static main.java.MayfairStatic.PO_COMMENTS;
import static main.java.MayfairStatic.PO_DELDATE;
import static main.java.MayfairStatic.PO_ORDNUM;
import static main.java.MayfairStatic.PO_PRICE;
import static main.java.MayfairStatic.PO_SUPPNUM;
import static main.java.MayfairStatic.PO_TOTALUNITS;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_INORDER;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_PURCHASEPRICE;
import static main.java.MayfairStatic.PS_PONUM;
import static main.java.MayfairStatic.PS_PRODNUM;
import static main.java.MayfairStatic.PS_QUANTITY;
import static main.java.MayfairStatic.PS_SONUM;
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;
import static main.java.MayfairStatic.PURCHASE_SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SUPPLIERS_TABLE;
import static main.java.MayfairStatic.SUPPLIER_NAME;
import static main.java.MayfairStatic.SUPPLIER_SUPPNUM;

/**
 *
 * @author kian_bryen
 */
public class EditPurchaseOrderStep1 extends javax.swing.JInternalFrame
{

    private final String ord_num;
    private final JDesktopPane desktop;

    public EditPurchaseOrderStep1(String ord_num, JDesktopPane desktop)
    {
        this.ord_num = ord_num;
        this.desktop = desktop;
        setUpGUI();
    }

    private void setUpGUI()
    {
        initComponents();
        calDate.setEnabled(false);
        enabledButtons(false);
        table.setAutoCreateRowSorter(true);
        fillLables();
    }

    private void enabledButtons(boolean enable)
    {
        btnQuant.setVisible(enable);
        btnDelete.setEnabled(enable);
    }

    private void fillLables()
    {
        try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            updatePurchaseOrderDetails();
            ResultSet rs = selectStatement.executeQuery("SELECT " + PO_ORDNUM + ", "
                    + PO_DELDATE + " AS del_datetime, "
                    + MayfairStatic.sqlDateFormat(PO_DELDATE) + ", "
                    + PO_PRICE + ", "
                    + PO_COMMENTS + ", "
                    + SUPPLIER_SUPPNUM + ", "
                    + SUPPLIER_NAME + " "
                    + "FROM " + PURCHASE_ORDER_TABLE + " "
                    + "JOIN " + SUPPLIERS_TABLE + " "
                    + "ON " + PO_SUPPNUM + "=" + SUPPLIER_SUPPNUM + " "
                    + "WHERE " + PO_ORDNUM + " = '" + ord_num + "'");
            rs.next();

            labelOrdNum.setText(rs.getString(PO_ORDNUM));
            calDate.setDate(rs.getDate("del_datetime"));
            labelDelDate.setText(rs.getString(PO_DELDATE));
            labelPrice.setText(String.format("%.02f", rs.getFloat(PO_PRICE)));
            fieldComments.setText(rs.getString(PO_COMMENTS));
            labelSuppNum.setText(rs.getString(SUPPLIER_SUPPNUM));
            labelSuppName.setText(rs.getString(SUPPLIER_NAME));

            rs = selectStatement.executeQuery("SELECT " + PRODUCT_CODE + ", "
                    + POD_QUANTITY + ", "
                    + PRODUCT_PURCHASEPRICE + ", "
                    + POD_PRICE + " "
                    + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                    + "JOIN " + PRODUCTS_TABLE + " "
                    + "ON " + POD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                    + "WHERE " + POD_ORDNUM + " = '" + ord_num + "'");
            MayfairStatic.fillTable(table, rs);

            MayfairStatic.writeToLog("EDIT PURCHASE ORDER " + ord_num);
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }

    private void updatePurchaseOrderDetails() throws SQLException
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
            labelUnits.setText("Total Units: " + total_units);
            labelPrice.setText("Order Total: £" + String.format("%.02f", total_price));

            try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
            {
                updateStatement.executeUpdate("UPDATE " + PURCHASE_ORDER_TABLE + " "
                        + "SET " + PO_PRICE + " = " + total_price + ", "
                        + PO_TOTALUNITS + " = " + total_units + " "
                        + "WHERE " + PO_ORDNUM + " = '" + ord_num + "'");
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

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        labelPrice = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnAdd = new javax.swing.JButton();
        btnQuant = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        labelNumber = new javax.swing.JLabel();
        labelSuppNum = new javax.swing.JLabel();
        labelSuppName = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelOrdNum = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        labelDelDate = new javax.swing.JLabel();
        fieldComments = new javax.swing.JTextField();
        labelComments = new javax.swing.JLabel();
        calDate = new com.toedter.calendar.JDateChooser();
        labelDate = new javax.swing.JLabel();
        labelUnits = new javax.swing.JLabel();

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Edit Purchase Order");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Edit Purchase Order");

        jLabel8.setText("Order Details:");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Product", "Quantity", "Unit Price", "Total Price"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false
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
        table.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        labelPrice.setText("Order Total: £");

        btnAdd.setText("Add Product");
        btnAdd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddActionPerformed(evt);
            }
        });

        btnQuant.setText("Update Quantity");
        btnQuant.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnQuantActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete Product");
        btnDelete.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDeleteActionPerformed(evt);
            }
        });

        labelNumber.setText("Supplier Number:");

        labelSuppNum.setText("1");

        labelSuppName.setText("1");

        labelName.setText("Supplier Name:");

        jLabel6.setText("Order Number: ");

        labelOrdNum.setText("1");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel7.setText("Delivery Date:");

        labelDelDate.setText("1");

        labelComments.setText("Comments:");

        calDate.setDateFormatString("yyyy-MM-dd");

        labelDate.setText("Delivery Date:");

        labelUnits.setText("Total Units:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelNumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSuppNum)
                                .addGap(18, 18, 18)
                                .addComponent(labelName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSuppName))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelOrdNum))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelDelDate))
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelComments)
                            .addComponent(labelDate))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(calDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fieldComments, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(201, 201, 201)
                        .addComponent(labelUnits))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnQuant)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd))
                            .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelPrice, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelNumber)
                        .addComponent(labelSuppNum))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelName)
                        .addComponent(labelSuppName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelOrdNum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelDelDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelComments)
                    .addComponent(fieldComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelUnits))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelPrice)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDate)
                    .addComponent(calDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(btnQuant))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        EditPurchaseOrderStep2 jFrame = new EditPurchaseOrderStep2(ord_num, desktop);
        desktop.add(jFrame);
        MayfairStatic.setMaximum(jFrame);
        jFrame.show();
        this.dispose();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuantActionPerformed
        try
        {
            int newQuantity = Integer.parseInt(MayfairStatic.outputInput(this, "Update Quantity", "Please input new quantity: "));
            if (newQuantity <= 0)
            {
                throw new NumberFormatException();
            }

            String code = (String) table.getValueAt(table.getSelectedRow(), 0);
            try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = selectStatement.executeQuery("SELECT " + POD_QUANTITY + ", "
                        + POD_AVALIABLE + ", "
                        + PRODUCT_INORDER + ", "
                        + PRODUCT_PRODNUM + ", "
                        + PRODUCT_PURCHASEPRICE + " "
                        + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + PRODUCTS_TABLE + " "
                        + "ON " + POD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                        + "WHERE " + POD_ORDNUM + " = '" + ord_num + "' "
                        + "AND " + PRODUCT_CODE + " = '" + code + "'");
                rs.next();
                int oldQuantity = rs.getInt(POD_QUANTITY);
                int avaliable = rs.getInt(POD_AVALIABLE);
                int in_order = rs.getInt(PRODUCT_INORDER);
                int prod_num = rs.getInt(PRODUCT_PRODNUM);
                double purchase_price = rs.getDouble(PRODUCT_PURCHASEPRICE);

                int onSalesOrder = oldQuantity - avaliable;
                if (newQuantity >= onSalesOrder)
                {
                    double price = newQuantity * purchase_price;
                    int delta = newQuantity - oldQuantity;
                    avaliable += delta;
                    in_order += delta;
                    try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
                    {
                        MayfairStatic.writeToLog("UPDATE QUANTITY " + code + " ON  PO " + ord_num);

                        // UPDATE PURCHASE ORDER DETAILS
                        String sql = ("UPDATE " + PURCHASE_ORDER_DETAILS_TABLE + " "
                                + "SET " + POD_QUANTITY + " = " + newQuantity + ", "
                                + POD_AVALIABLE + " = " + avaliable + ", "
                                + POD_PRICE + " = " + price + " "
                                + "WHERE " + POD_ORDNUM + " = '" + ord_num + "'  "
                                + "AND " + POD_PRODNUM + " = " + prod_num);
                        updateStatement.executeUpdate(sql);
                        MayfairStatic.writeToLog(sql);

                        // UPDATE PRODUCTS
                        sql = ("UPDATE " + PRODUCTS_TABLE + " "
                                + "SET " + PRODUCT_INORDER + " = " + in_order + " "
                                + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num);
                        updateStatement.executeUpdate(sql);
                        MayfairStatic.writeToLog(sql);

                        fillLables();
                        JOptionPane.showMessageDialog(EditPurchaseOrderStep1.this, "Quantity Updated");
                        MayfairStatic.outputMessage(this, "Updated", "Quantity updated from " + oldQuantity + " to " + newQuantity + ".", INFORMATION_MESSAGE);
                    }
                }
                else
                {
                    MayfairStatic.outputMessage(this, "Invalid quantity", "New Quantity must be at least " + onSalesOrder + " as there are active sales orders for this product.", WARNING_MESSAGE);
                }
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(this, ex);
            }
        }
        catch (NumberFormatException | HeadlessException ex)
        {
            MayfairStatic.outputMessage(this, "Invalid quantity", "Please enter a valid quantity.", WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnQuantActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String prodCode = (String) table.getValueAt(table.getSelectedRow(), 0);
        if (MayfairStatic.outputConfirm(this, "Delete Product", "Are you sure you want to remove " + prodCode + " from order " + ord_num + "?") == JOptionPane.YES_OPTION)
        {
            try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = selectStatement.executeQuery("SELECT " + POD_QUANTITY + ", " + PRODUCT_PRODNUM + ", " + PRODUCT_INORDER + " "
                        + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + PRODUCTS_TABLE + " "
                        + "ON " + POD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                        + "WHERE " + POD_ORDNUM + " = '" + ord_num + "'"
                        + "AND " + PRODUCT_CODE + " = '" + prodCode + "' ");
                rs.next();
                int prod_num = rs.getInt(PRODUCT_PRODNUM);
                int quantity = rs.getInt(POD_QUANTITY);
                int in_order = rs.getInt(PRODUCT_INORDER) - quantity;

                // CHECK PURCHASE_SALES_ORDER 
                HashMap<Integer, Integer> prodsOnSales = new HashMap();
                rs = selectStatement.executeQuery("SELECT " + PS_SONUM + ", " + PS_QUANTITY + " "
                        + "FROM " + PURCHASE_SALES_ORDER_TABLE + " "
                        + "WHERE " + PS_PONUM + " = '" + ord_num + "' "
                        + "AND " + PS_PRODNUM + " = " + prod_num);
                while (rs.next())
                {
                    prodsOnSales.put(rs.getInt(PS_SONUM), rs.getInt(PS_QUANTITY));
                }

                if (prodsOnSales.isEmpty())
                {
                    try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
                    {
                        MayfairStatic.writeToLog("DELETE PRODUCT " + prodCode + " FROM PO " + ord_num);

                        // UPDATE PURCHASE_ORDER_DETAILS
                        String sql = ("DELETE FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                                + "WHERE " + POD_ORDNUM + " = '" + ord_num + "'  "
                                + "AND " + POD_PRODNUM + " = " + prod_num);
                        updateStatement.executeUpdate(sql);
                        MayfairStatic.writeToLog(sql);

                        // UPDATE PRODUCTS
                        sql = ("UPDATE " + PRODUCTS_TABLE + " "
                                + "SET " + PRODUCT_INORDER + " = " + in_order + " "
                                + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num);
                        updateStatement.executeUpdate(sql);
                        MayfairStatic.writeToLog(sql);
                        fillLables();
                        MayfairStatic.outputMessage(this, "Product Deleted", prodCode + " removed from " + ord_num + ".", INFORMATION_MESSAGE);
                    }
                }
                else
                {
                    StringBuilder message = new StringBuilder();
                    message.append("Can not delete product due to it being on the following Sales Orders:");
                    prodsOnSales.entrySet().stream().forEach((products) ->
                    {
                        message.append("\n").append(products.getValue()).append(" x ").append(prodCode).append(" on Sales Order ").append(products.getKey());
                    });
                    MayfairStatic.outputMessage(this, "Warning", message.toString(), WARNING_MESSAGE);
                }
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditPurchaseOrderStep1.this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (MayfairStatic.outputConfirm(this, "Save Changes", "Are you sure you have finished editting the order?") == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement())
            {
                String sql = "UPDATE " + PURCHASE_ORDER_TABLE + " "
                        + "SET " + PO_COMMENTS + " = '" + fieldComments.getText() + "' "
                        + "WHERE " + PO_ORDNUM + " = '" + ord_num + "'";
                statement.executeUpdate(sql);
                MayfairStatic.writeToLog(sql);
                MayfairStatic.writeToLog(LOG_SEPERATOR);
                JOptionPane.showMessageDialog(EditPurchaseOrderStep1.this, "Order Saved.");
                this.dispose();
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditPurchaseOrderStep1.this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        enabledButtons(true);
    }//GEN-LAST:event_tableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnQuant;
    private javax.swing.JButton btnSave;
    private com.toedter.calendar.JDateChooser calDate;
    private javax.swing.JTextField fieldComments;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelComments;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelDelDate;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelNumber;
    private javax.swing.JLabel labelOrdNum;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelSuppName;
    private javax.swing.JLabel labelSuppNum;
    private javax.swing.JLabel labelUnits;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
