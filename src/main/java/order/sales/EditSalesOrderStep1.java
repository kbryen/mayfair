/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.sales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.CUSTOMERS_TABLE;
import static main.java.MayfairStatic.CUSTOMER_CUSTNUM;
import static main.java.MayfairStatic.CUSTOMER_NAME;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_INORDER;
import static main.java.MayfairStatic.PRODUCT_INSTOCK;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_SALESPRICE;
import static main.java.MayfairStatic.PS_CODE;
import static main.java.MayfairStatic.PS_PONUM;
import static main.java.MayfairStatic.PS_PRODNUM;
import static main.java.MayfairStatic.PS_QUANTITY;
import static main.java.MayfairStatic.PS_SONUM;
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SOD_FROMORDER;
import static main.java.MayfairStatic.SOD_FROMSTOCK;
import static main.java.MayfairStatic.SOD_ORDNUM;
import static main.java.MayfairStatic.SOD_PRICE;
import static main.java.MayfairStatic.SOD_PRODNUM;
import static main.java.MayfairStatic.SOD_QUANTITY;
import static main.java.MayfairStatic.SO_COMMENTS;
import static main.java.MayfairStatic.SO_CUSTNUM;
import static main.java.MayfairStatic.SO_DELDATE;
import static main.java.MayfairStatic.SO_ORDNUM;
import static main.java.MayfairStatic.SO_PRICE;
import static main.java.MayfairStatic.SO_TOTALUNITS;

/**
 *
 * @author kian_bryen
 */
public class EditSalesOrderStep1 extends javax.swing.JInternalFrame
{

    private final int ord_num;
    private final JDesktopPane desktop;
    
    public EditSalesOrderStep1(int ord_num, JDesktopPane pane)
    {
        setUpGUI();
        this.ord_num = ord_num;
        this.desktop = pane;
    }

    private void setUpGUI()
    {
        initComponents();
        btnQuant.setVisible(false);
        btnDelete.setEnabled(false);
        table.setAutoCreateRowSorter(true);
        fillLables();
    }

    private void fillLables()
    {
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            updateSalesOrderDetails();
            ResultSet rs = statement.executeQuery("SELECT " + CUSTOMER_CUSTNUM + ", " 
                    + CUSTOMER_NAME + ", " 
                    + SO_ORDNUM + ", " 
                    + SO_DELDATE + ", " 
                    + SO_COMMENTS + " "
                    + "FROM " + SALES_ORDER_TABLE + " "
                    + "JOIN " + CUSTOMERS_TABLE + " "
                    + "ON " + SO_CUSTNUM + "=" + CUSTOMER_CUSTNUM + " "
                    + "WHERE " + SO_ORDNUM + " = " + ord_num);
            rs.next();

            labelNumber.setText(rs.getString(CUSTOMER_CUSTNUM));
            labelName.setText(rs.getString(CUSTOMER_NAME));
            labelOrdNum.setText(rs.getString(SO_ORDNUM));
            fieldComments.setText(rs.getString(SO_COMMENTS));

            Date delDate = rs.getDate(SO_DELDATE);
            String date;
            if (delDate == null)
            {
                delDate = new Date();
                date = "";
            }
            else
            {
                date = delDate.toString();
            }
            labelDelDate.setText(date);
            calDelDate.setDate(delDate);

            rs = statement.executeQuery("SELECT " + PRODUCT_CODE + ", " 
                    + SOD_QUANTITY + ", " 
                    + PRODUCT_SALESPRICE + ", " 
                    + SOD_PRICE + " "
                    + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                    + "JOIN " + PRODUCTS_TABLE + " "
                    + "ON " + SOD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                    + "WHERE " + SOD_ORDNUM + " = " + ord_num);
            MayfairStatic.fillTable(table, rs);
            MayfairStatic.writeToLog("EDIT SALES ORDER " + ord_num);
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }

    private void updateSalesOrderDetails() throws SQLException
    {
        try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = selectStatement.executeQuery("SELECT SUM(" + SOD_PRICE + ") AS total_price, "
                    + "SUM(" + SOD_QUANTITY + ") as total_quantity "
                    + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                    + "WHERE " + SOD_ORDNUM + " = '" + ord_num + "'");
            rs.next();

            int total_units = rs.getInt("total_units");
            double total_price = rs.getDouble("total_price");
            labelUnits.setText("Total Units: " + total_units);
            labelPrice.setText("Order Total: £" + String.format("%.02f", total_price));

            try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
            {
                updateStatement.executeUpdate("UPDATE " + SALES_ORDER_TABLE + " "
                        + "SET " + SO_PRICE + " = " + total_price + ", "
                        + SO_TOTALUNITS + " = " + total_units + " "
                        + "WHERE " + SO_ORDNUM + " = '" + ord_num + "'");
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
        label5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        labelPrice = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnAdd = new javax.swing.JButton();
        btnQuant = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        label1 = new javax.swing.JLabel();
        labelNumber = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        labelOrdNum = new javax.swing.JLabel();
        btnFinish = new javax.swing.JButton();
        label4 = new javax.swing.JLabel();
        labelDelDate = new javax.swing.JLabel();
        fieldComments = new javax.swing.JTextField();
        labelComments = new javax.swing.JLabel();
        calDelDate = new com.toedter.calendar.JDateChooser();
        labelDelDate2 = new javax.swing.JLabel();
        labelUnits = new javax.swing.JLabel();

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Edit Sales Order");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Edit Sales Order");

        label5.setText("Update Order Details:");

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

        label1.setText("Customer Number:");

        labelNumber.setText("1");

        labelName.setText("1");

        label2.setText("Customer Name:");

        label3.setText("Order Number: ");

        labelOrdNum.setText("1");

        btnFinish.setText("Finish");
        btnFinish.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFinishActionPerformed(evt);
            }
        });

        label4.setText("Delivery Date:");

        labelDelDate.setText("1");

        labelComments.setText("Update Comments :");

        calDelDate.setDateFormatString("yyyy-MM-dd");

        labelDelDate2.setText("Set Delivery Date :");

        labelUnits.setText("Total Units:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNumber)
                                .addGap(18, 18, 18)
                                .addComponent(label2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelName))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelOrdNum))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelDelDate))
                            .addComponent(label5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelComments)
                        .addGap(18, 18, 18)
                        .addComponent(fieldComments, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelPrice))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnQuant)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd))
                            .addComponent(btnFinish, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelDelDate2)
                        .addGap(18, 18, 18)
                        .addComponent(calDelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelUnits)))
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
                        .addComponent(label1)
                        .addComponent(labelNumber))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(labelName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label3)
                    .addComponent(labelOrdNum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label4)
                    .addComponent(labelDelDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label5)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelComments)
                    .addComponent(fieldComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPrice))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUnits)
                    .addComponent(labelDelDate2)
                    .addComponent(calDelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(btnQuant))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFinish)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        EditSalesOrderStep2 jFrame = new EditSalesOrderStep2(desktop, ord_num);
        desktop.add(jFrame);
        MayfairStatic.setMaximum(jFrame);
        jFrame.show();
        this.dispose();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuantActionPerformed
//        if (MayfairStatic.outputConfirm(this, "Update Quantity", "Are you sure you want to update the quantity?") == JOptionPane.YES_OPTION)
//        {
//            String prodCode = (String) table.getValueAt(table.getSelectedRow(), 0);
//            try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
//            {
//                ResultSet rs;
//                MayfairStatic.writeToLog("DELETE PRODUCT " + prodCode);
//
//                rs = selectStatement.executeQuery("SELECT prod_num, in_stock, in_order FROM products WHERE code = '" + prodCode + "'");
//                rs.next();
//                int prod_num = rs.getInt("prod_num");
//                int in_stock = rs.getInt("in_stock");
//                int in_order = rs.getInt("in_order");
//
//                rs = selectStatement.executeQuery("SELECT fromStock, fromOrder FROM sales_order_details WHERE prod_num = " + prod_num + " AND ord_num = " + ord_num);
//                rs.next();
//                int fromStock = rs.getInt("fromStock");
//                int fromOrder = rs.getInt("fromOrder");
//
//                int newStock = in_stock + fromStock;
//                int newOrder = in_order + fromOrder;
//
//                // UPDATE PRODUCTS 
//                sql = "UPDATE products SET in_stock = " + newStock + ", in_order = " + newOrder + " WHERE prod_num = " + prod_num;
//                updateStatement.executeUpdate(sql);
//                db.writeToLog(sql);
//
//                // UPDATE PURCHASE_SALES_ORDER
//                ArrayList<Pair<String, Integer>> purchaseOrders = new ArrayList();
//                ArrayList<String> codes = new ArrayList();
//                rs = selectStatement.executeQuery("SELECT code, po_num, quantity FROM purchase_sales_order WHERE prod_num = " + prod_num + " AND so_num = " + ord_num);
//                while (rs.next())
//                {
//                    int quantity = rs.getInt("quantity");
//                    String poNum = rs.getString("po_num");
//                    purchaseOrders.add(new Pair(poNum, quantity));
//                    codes.add(rs.getString("code"));
//                }
//                for (String code : codes)
//                {
//                    sql = "DELETE FROM purchase_sales_order WHERE code = " + code;
//                    updateStatement.executeUpdate(sql);
//                    db.writeToLog(sql);
//                }
//
//                // UPDATE PURCHASE ORDER DETAILS
//                for (Pair<String, Integer> purchaseOrder : purchaseOrders)
//                {
//                    rs = selectStatement.executeQuery("SELECT avaliable FROM purchase_order_details WHERE prod_num = " + prod_num + " AND ord_num = '" + purchaseOrder.getKey() + "'");
//                    rs.next();
//                    int avaliable = rs.getInt("avaliable");
//
//                    sql = "UPDATE purchase_order_details SET avaliable = " + (avaliable + purchaseOrder.getValue()) + " WHERE prod_num = " + prod_num + " AND ord_num = '" + purchaseOrder.getKey() + "'";
//                    updateStatement.executeUpdate(sql);
//                    db.writeToLog(sql);
//                }
//
//                // DELETE FROM SALES ORDER DETAILS
//                sql = "DELETE FROM sales_order_details WHERE prod_num = " + prod_num + " AND ord_num = " + ord_num;
//                updateStatement.executeUpdate(sql);
//                db.writeToLog(sql);
//            }
//            catch (SQLException e)
//            {
//                JOptionPane.showMessageDialog(EditSalesOrderStep1.this, e.getMessage());
//            }
//        }
    }//GEN-LAST:event_btnQuantActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String prodCode = (String) table.getValueAt(table.getSelectedRow(), 0);
        if (MayfairStatic.outputConfirm(this, "Delete Product", "Are you sure you want to remove " + prodCode + " from order number " + ord_num + "?") == JOptionPane.YES_OPTION)
        {
            try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = selectStatement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", " 
                        + PRODUCT_INSTOCK + ", " 
                        + PRODUCT_INORDER + ", " 
                        + SOD_FROMSTOCK + ", " 
                        + SOD_FROMORDER + " "
                        + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                        + "JOIN " + PRODUCTS_TABLE + " "
                        + "ON " + SOD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                        + "WHERE " + SOD_ORDNUM + " = " + ord_num + " "
                        + "AND " + PRODUCT_CODE + " = '" + prodCode + "'");
                rs.next();
                int prod_num = rs.getInt(PRODUCT_PRODNUM);
                int in_stock = rs.getInt(PRODUCT_INSTOCK) + rs.getInt(SOD_FROMSTOCK);
                int in_order = rs.getInt(PRODUCT_INORDER) + rs.getInt(SOD_FROMORDER);

                // UPDATE PURCHASE_SALES_ORDER
                Map<String, Integer> purchaseOrders = new HashMap();
                ArrayList<String> psCodes = new ArrayList();
                rs = selectStatement.executeQuery("SELECT " + PS_CODE + ", " 
                        + PS_PONUM + ", " 
                        + PS_QUANTITY + ", " 
                        + POD_AVALIABLE + " "
                        + "FROM " + PURCHASE_SALES_ORDER_TABLE + " "
                        + "JOIN " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "ON " + PS_PONUM + "=" + POD_ORDNUM + " "
                        + "AND " + PS_PRODNUM + "=" + POD_PRODNUM + " "
                        + "WHERE " + PS_PRODNUM + " = " + prod_num + " "
                        + "AND " + PS_SONUM + " = " + ord_num);
                while (rs.next())
                {
                    purchaseOrders.put(rs.getString(PS_PONUM), rs.getInt(POD_AVALIABLE) + rs.getInt(PS_QUANTITY));
                    psCodes.add(rs.getString(PS_CODE));
                }

                try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
                {
                    MayfairStatic.writeToLog("DELETE PRODUCT " + prodCode);

                    // UPDATE PRODUCTS 
                    String sql = "UPDATE " + PRODUCTS_TABLE + " "
                            + "SET " + PRODUCT_INSTOCK + " = " + in_stock + ", " 
                            + PRODUCT_INORDER + " = " + in_order + " "
                            + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num;
                    updateStatement.executeUpdate(sql);
                    MayfairStatic.writeToLog(sql);

                    for (String code : psCodes)
                    {
                        sql = "DELETE FROM " + PURCHASE_SALES_ORDER_TABLE + " "
                                + "WHERE " + PS_CODE + " = " + code;
                        updateStatement.executeUpdate(sql);
                        MayfairStatic.writeToLog(sql);
                    }

                    // UPDATE PURCHASE ORDER DETAILS
                    for (Map.Entry<String, Integer> purchaseOrder : purchaseOrders.entrySet())
                    {
                        sql = "UPDATE " + PURCHASE_ORDER_DETAILS_TABLE + " "
                                + "SET " + POD_AVALIABLE + " = " + purchaseOrder.getValue() + " "
                                + "WHERE " + POD_PRODNUM + " = " + prod_num + " "
                                + "AND " + POD_ORDNUM + " = '" + purchaseOrder.getKey() + "'";
                        updateStatement.executeUpdate(sql);
                        MayfairStatic.writeToLog(sql);
                    }

                    // DELETE FROM SALES ORDER DETAILS
                    sql = "DELETE FROM " + SALES_ORDER_DETAILS_TABLE + " "
                            + "WHERE " + SOD_PRODNUM + " = " + prod_num + " "
                            + "AND " + SOD_ORDNUM + " = " + ord_num;
                    updateStatement.executeUpdate(sql);
                    MayfairStatic.writeToLog(sql);

                    fillLables();
                }
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(this, ex);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
        try (Statement statement = MayfairStatic.getConnection().createStatement())
        {
            String date = ((JTextField) calDelDate.getDateEditor().getUiComponent()).getText();
            if (MayfairStatic.validateSalesOrderDelDate(this, date, ord_num))
            {
                String sql = "UPDATE " + SALES_ORDER_TABLE + " "
                        + "SET " + SO_DELDATE + " = '" + date + "', "
                        + SO_COMMENTS + " = '" + fieldComments.getText() + "' "
                        + "WHERE " + SO_ORDNUM + " = " + ord_num;
                statement.executeUpdate(sql);
                MayfairStatic.writeToLog(sql);
                MayfairStatic.writeToLog(MayfairStatic.LOG_SEPERATOR);

                ViewSalesOrderSummary jFrame = new ViewSalesOrderSummary(ord_num);
                desktop.add(jFrame);
                jFrame.show();
                this.dispose();
            }
        }
        catch (SQLException | ParseException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }//GEN-LAST:event_btnFinishActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        btnDelete.setEnabled(true);
    }//GEN-LAST:event_tableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFinish;
    private javax.swing.JButton btnQuant;
    private com.toedter.calendar.JDateChooser calDelDate;
    private javax.swing.JTextField fieldComments;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    private javax.swing.JLabel labelComments;
    private javax.swing.JLabel labelDelDate;
    private javax.swing.JLabel labelDelDate2;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelNumber;
    private javax.swing.JLabel labelOrdNum;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelUnits;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
