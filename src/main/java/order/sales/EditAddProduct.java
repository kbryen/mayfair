/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mayfair.order.sales;

import Mayfair.Database;
import Mayfair.Main;
import java.awt.HeadlessException;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kian_bryen
 */
public class EditAddProduct extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;
    private final Database db = new Database();
    private String sql;
    private final int orderNum;

    public EditAddProduct(JDesktopPane desktop, int orderNum)
    {

        initComponents();
        this.setTitle("New Sales Order");

        this.desktop = desktop;
        this.orderNum = orderNum;

        FillLabels();

        scrollPane.setVisible(false);
        btnAddPO.setEnabled(false);
        btnAddStock.setEnabled(false);

    }

    private void FillLabels()
    {
        labelOrdDetails.setVisible(true);
        labelOrderTotal.setVisible(true);
        scrollPane2.setVisible(true);
        getContentPane().validate();
        getContentPane().repaint();
        Connection con = db.getConnection();

        try
        {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery("SELECT SUM(price) AS total_price FROM sales_order_details WHERE ord_num = '" + orderNum + "'");
            rs.next();
            double price = Double.parseDouble(rs.getString("total_price"));
            labelOrdTotal.setText(String.format("%.02f", price));

            Statement statement2 = con.createStatement();
            statement2.executeUpdate("UPDATE sales_order SET price = " + price + " WHERE ord_num = " + orderNum);

            rs = statement.executeQuery("SELECT products.code, sales_order_details.quantity, products.sales_price, sales_order_details.price FROM sales_order_details INNER JOIN products ON sales_order_details.prod_num=products.prod_num WHERE sales_order_details.ord_num = " + orderNum);
            while (table2.getRowCount() > 0)
            {
                ((DefaultTableModel) table2.getModel()).removeRow(0);
            }
            int columns = rs.getMetaData().getColumnCount();
            while (rs.next())
            {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++)
                {
                    row[i - 1] = rs.getObject(i);
                }
                ((DefaultTableModel) table2.getModel()).insertRow(rs.getRow() - 1, row);
            }

        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(EditAddProduct.this, e.getMessage());
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (Exception e)
            { /* ignored */ }
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
        jLabel2 = new javax.swing.JLabel();
        fieldProdCode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fieldStockQuant = new javax.swing.JTextField();
        btnAddPO = new javax.swing.JButton();
        btnFinish = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        labelProdNum = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnFind = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        scrollPane2 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        labelOrderTotal = new javax.swing.JLabel();
        labelOrdTotal = new javax.swing.JLabel();
        labelOrdDetails = new javax.swing.JLabel();
        fieldPOQuant = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnAddStock = new javax.swing.JButton();

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Add Product");

        jLabel2.setText("Product Code");

        jLabel3.setText("Quantity:");

        btnAddPO.setText("Add");
        btnAddPO.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddPOActionPerformed(evt);
            }
        });

        btnFinish.setText("Finish");
        btnFinish.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFinishActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel5.setText("Select Product");

        labelProdNum.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N

        btnFind.setText("Find");
        btnFind.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFindActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null, null, null}
            },
            new String []
            {
                "Number", "Code", "Price", "In Stock", "In Order", "Total", "Season"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false
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
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setColumnSelectionAllowed(true);
        table.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        table2.setModel(new javax.swing.table.DefaultTableModel(
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
        table2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                table2MouseClicked(evt);
            }
        });
        scrollPane2.setViewportView(table2);

        labelOrderTotal.setText("Order Total : £");

        labelOrdDetails.setText("Order Details:");

        jLabel4.setText("Take from Stock");

        jLabel6.setText("Take from PO");

        btnAddStock.setText("Add");
        btnAddStock.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelProdNum))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(90, 90, 90)
                                        .addComponent(fieldProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(btnFind))
                            .addComponent(labelOrdDetails))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(labelOrderTotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelOrdTotal)
                                .addGap(11, 11, 11))
                            .addComponent(btnFinish, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(107, 107, 107)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fieldPOQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAddPO))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fieldStockQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAddStock)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelProdNum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fieldProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldStockQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(btnAddStock))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(1, 1, 1)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPO)
                    .addComponent(fieldPOQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelOrdDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrderTotal)
                    .addComponent(labelOrdTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFinish)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPOActionPerformed
        try
        {
            Connection con = db.getConnection();
            int prod_num = (int) table.getValueAt(table.getSelectedRow(), 0);

            try
            {
                Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = statement.executeQuery("SELECT sales_price, in_order FROM products WHERE prod_num = " + prod_num);
                rs.next();
                int in_order = rs.getInt("in_order");

                int fromOrder;
                if (fieldPOQuant.getText().equals(""))
                {
                    fromOrder = 0;
                }
                else
                {
                    fromOrder = Integer.parseInt(fieldPOQuant.getText());
                }

                if (fromOrder > 0)
                {
                    if (fromOrder <= in_order)
                    {
                        // Order Num -> Avaliable + Date
                        Map<String, Pair<Integer, Date>> possiblePurchaseOrders = new HashMap();

                        rs = statement.executeQuery("SELECT purchase_order.ord_num, purchase_order.del_date, purchase_order_details.avaliable FROM purchase_order JOIN purchase_order_details ON purchase_order.ord_num = purchase_order_details.ord_num WHERE purchase_order_details.prod_num = " + prod_num + " AND purchase_order.delivered = false AND purchase_order_details.avaliable > 0");
                        while (rs.next())
                        {
                            String poNum = rs.getString("ord_num");
                            int avaliable = rs.getInt("avaliable");
                            Date date = rs.getDate("del_date");

                            possiblePurchaseOrders.put(poNum, new Pair(avaliable, date));
                        }

                        EditPickPurchaseOrders pickOrders = new EditPickPurchaseOrders(desktop, possiblePurchaseOrders, fromOrder, prod_num, orderNum);
                        desktop.add(pickOrders);
                        pickOrders.show();
                        this.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(EditAddProduct.this, "Not enough avaliable on Purchase Order.");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(EditAddProduct.this, "Quantity must be greater than 0");
                }

            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditAddProduct.this, e.getMessage());
            }
            finally
            {
                try
                {
                    con.close();
                }
                catch (Exception e)
                { /* ignored */ }
            }
        }
        catch (NumberFormatException | HeadlessException e)
        {
            JOptionPane.showMessageDialog(EditAddProduct.this, "Please enter a valid quantity");
        }
    }//GEN-LAST:event_btnAddPOActionPerformed

    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to finish adding products?", "Finish", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            Connection con = db.getConnection();
            try
            {
                Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = statement.executeQuery("SELECT SUM(price) AS total_price FROM sales_order_details WHERE ord_num = '" + orderNum + "'");
                rs.next();
                double price = rs.getDouble("total_price");

                Statement statement2 = con.createStatement();
                sql = "UPDATE sales_order SET price = " + price + " WHERE ord_num = " + orderNum;
                statement2.executeUpdate(sql);
                db.writeToLog(sql);

                EditMain editMain = new EditMain(orderNum, desktop);
                desktop.add(editMain);
                editMain.show();

                this.dispose();
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditAddProduct.this, e.getMessage());
            }
            finally
            {
                try
                {
                    con.close();
                }
                catch (Exception e)
                { /* ignored */ }
            }
        }
    }//GEN-LAST:event_btnFinishActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        try
        {
            this.setMaximum(true);

        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = db.getConnection();
        try
        {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery("SELECT prod_num, code, sales_price, in_stock, in_order, total, SSAW FROM products WHERE code LIKE '%" + fieldProdCode.getText() + "%' ORDER BY prod_num ASC");

            scrollPane.setVisible(true);
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
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(EditAddProduct.this, e.getMessage());
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (Exception e)
            { /* ignored */ }
        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        fieldProdCode.setText((String) table.getValueAt(table.getSelectedRow(), 1));
        btnAddPO.setEnabled(true);
        btnAddStock.setEnabled(true);
    }//GEN-LAST:event_tableMouseClicked

    private void table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table2MouseClicked

    }//GEN-LAST:event_table2MouseClicked

    private void btnAddStockActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddStockActionPerformed
    {//GEN-HEADEREND:event_btnAddStockActionPerformed
        try
        {
            Connection con = db.getConnection();
            int prod_num = (int) table.getValueAt(table.getSelectedRow(), 0);

            try
            {
                Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement statement2 = con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT sales_price, in_stock FROM products WHERE prod_num = " + prod_num);
                rs.next();

                double price = rs.getDouble("sales_price");
                int in_stock = rs.getInt("in_stock");

                // How many they want to take
                int fromStock;
                if (fieldStockQuant.getText().equals(""))
                {
                    fromStock = 0;
                }
                else
                {
                    fromStock = Integer.parseInt(fieldStockQuant.getText());
                }

                if (fromStock > 0)
                {
                    if (fromStock <= in_stock)
                    {
                        // UPDATE PRODUCTS
                        int newStock = in_stock - fromStock;
                        sql = "UPDATE products SET in_stock = " + newStock + " WHERE prod_num = " + prod_num;
                        statement2.executeUpdate(sql);
                        db.writeToLog(sql);

                        // UPDATE SALES ORDER DETAILS
                        rs = statement.executeQuery("SELECT code, quantity, fromStock FROM sales_order_details WHERE prod_num = " + prod_num + " AND ord_num = " + orderNum);
                        if (rs.next())
                        {
                            int oldQuant = rs.getInt("quantity");
                            int code = rs.getInt("code");
                            int oldFromStock = rs.getInt("fromStock");

                            sql = "UPDATE sales_order_details SET quantity = " + (oldQuant + fromStock) + ", price = " + ((oldQuant + fromStock) * price) + ", fromStock = " + (oldFromStock + fromStock) + " WHERE code = " + code;
                        }
                        else
                        {
                            sql = "INSERT INTO sales_order_details (ord_num, prod_num, quantity, price, fromStock, fromOrder) VALUES (" + orderNum + ", " + prod_num + ", " + fromStock + ", " + (fromStock * price) + ", " + fromStock + ", 0)";
                        }
                        statement2.executeUpdate(sql);
                        db.writeToLog(sql);

                        JOptionPane.showMessageDialog(EditAddProduct.this, "Product added");
                        EditAddProduct addProducts = new EditAddProduct(desktop, orderNum);
                        desktop.add(addProducts);
                        addProducts.show();
                        this.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(EditAddProduct.this, "Not enough avaliable in Stock.");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(EditAddProduct.this, "Quantity must be greater than 0");
                }

            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditAddProduct.this, e.getMessage());
            }
            finally
            {
                try
                {
                    con.close();
                }
                catch (Exception e)
                { /* ignored */ }
            }
        }
        catch (NumberFormatException | HeadlessException e)
        {
            JOptionPane.showMessageDialog(EditAddProduct.this, "Please enter a valid quantity");
        }
    }//GEN-LAST:event_btnAddStockActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPO;
    private javax.swing.JButton btnAddStock;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnFinish;
    private javax.swing.JTextField fieldPOQuant;
    private javax.swing.JTextField fieldProdCode;
    private javax.swing.JTextField fieldStockQuant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelOrdDetails;
    private javax.swing.JLabel labelOrdTotal;
    private javax.swing.JLabel labelOrderTotal;
    private javax.swing.JLabel labelProdNum;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPane2;
    private javax.swing.JTable table;
    private javax.swing.JTable table2;
    // End of variables declaration//GEN-END:variables
}
