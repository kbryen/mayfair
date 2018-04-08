/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.java.Database;
import main.java.MayfairConstants;

/**
 *
 * @author kian_bryen
 */
public class Products extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;
    private final Database db = new Database();
    private String sql;

    /**
     * Creates new form ViewEditProducts
     *
     * @param pane Desktop Pane
     */
    public Products(JDesktopPane pane)
    {
        desktop = pane;
        initComponents();
        btnFindActionPerformed(null);
        btnDiscontinue.setVisible(false);
        btnView.setEnabled(false);
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
        table.setAutoCreateRowSorter(true);
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
        jLabel2 = new javax.swing.JLabel();
        fieldProdCode = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        comboSeason = new javax.swing.JComboBox();
        btnDiscontinue = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        Add = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Products");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Products");

        jLabel2.setText("Product Code : ");

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
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String []
            {
                "Product Number", "Code", "Purchase Price", "Sales Price", "Avaliable Stock", "PO Stock", "Potential Stock", "Warehouse Stock", "Season", "Discontinued"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false, false, false
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
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        table.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (table.getColumnModel().getColumnCount() > 0)
        {
            table.getColumnModel().getColumn(9).setResizable(false);
        }

        jLabel3.setText("Season : ");

        comboSeason.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "SS", "AW", "Discontinued" }));
        comboSeason.setToolTipText("");

        btnDiscontinue.setText("Discontinue");
        btnDiscontinue.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDiscontinueActionPerformed(evt);
            }
        });

        btnView.setText("View Product");
        btnView.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnViewActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnClearActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEditActionPerformed(evt);
            }
        });

        Add.setText("Add New Product");
        Add.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                AddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(fieldProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboSeason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                        .addComponent(btnClear)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind)
                        .addGap(44, 44, 44))
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnDelete)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnDiscontinue)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnEdit))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnView)))
                            .addComponent(Add, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(scrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fieldProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind)
                    .addComponent(jLabel3)
                    .addComponent(comboSeason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Add)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnDiscontinue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnView)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        Connection con = db.getConnection();
        try
        {
            List<String> prodNums = new ArrayList();
            
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            if (!comboSeason.getSelectedItem().equals("Discontinued"))
            {
                rs = statement.executeQuery("SELECT prod_num AS prodNum FROM products WHERE code LIKE '%" + fieldProdCode.getText() + "%' AND SSAW LIKE '%" + comboSeason.getSelectedItem() + "%' ORDER BY discon, total DESC, code");
            }
            else
            {
                rs = statement.executeQuery("SELECT prod_num AS prodNum FROM products WHERE code LIKE '%" + fieldProdCode.getText() + "%' AND discon = true ORDER BY discon, total DESC, code");
            }
            
            while(rs.next())
            {
                prodNums.add(rs.getString("prodNum"));
            }
            
            // Clear table
            scrollPane.setVisible(true);
            getContentPane().validate();
            getContentPane().repaint();
            while (table.getRowCount() > 0)
            {
                ((DefaultTableModel) table.getModel()).removeRow(0);
            }
            int rowNum = 0;
            
            for(String prodNum : prodNums)
            {
                rs = statement.executeQuery("SELECT products.prod_num AS prodNum, products.code AS code, products.purchase_price AS purchasePrice, products.sales_price AS salesPrice, products.in_stock AS availableStock, products.in_order AS purchaseOrderStock, products.total AS potentialStock, (IFNULL(SUM(sales_order_details.fromStock), 0)  + products.in_stock) AS warehouseStock, products.SSAW AS season, products.discon AS discontinued FROM sales_order_details LEFT JOIN sales_order ON sales_order_details.ord_num=sales_order.ord_num RIGHT JOIN products ON sales_order_details.prod_num=products.prod_num WHERE sales_order_details.prod_num = " + prodNum + " AND sales_order.dispatched = false AND sales_order.delivered = false AND products.prod_num = " + prodNum);
            
                int columns = rs.getMetaData().getColumnCount();
                while (rs.next())
                {
                    Object[] row = new Object[columns];
                    for (int i = 1; i <= columns; i++)
                    {
                        row[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) table.getModel()).insertRow(rowNum++, row);
                }
            }
            btnDiscontinue.setVisible(false);
            btnView.setEnabled(false);
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
            
        }   
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(Products.this, e.getMessage());
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

        btnDiscontinue.setVisible(true);
        if ((boolean) table.getValueAt(table.getSelectedRow(), 9))
        {
            btnDiscontinue.setText("Undiscontinue");
        }
        else
        {
            btnDiscontinue.setText("Discontinue");
        }
        btnView.setEnabled(true);
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btnDiscontinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscontinueActionPerformed
        int selectedOption;
        if (btnDiscontinue.getText().equals("Discontinue"))
        {
            selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to discontinue this product?", "Discontinue", JOptionPane.YES_NO_OPTION);
        }
        else
        {
            selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to undiscontinue this product?", "Undiscontinue", JOptionPane.YES_NO_OPTION);
        }
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            Connection con = db.getConnection();
            try
            {
                Statement statement = con.createStatement();

                if (btnDiscontinue.getText().equals("Discontinue"))
                {
                    int number = (int) table.getValueAt(table.getSelectedRow(), 5);

                    if (number > 0)
                    {
                        JOptionPane.showMessageDialog(Products.this, "Product cannot be discontinued as a Purchase Order has been placed.");
                    }
                    else
                    {
                        db.writeToLog("DISCONTINUE PRODUCT " + fieldProdCode.getText());
                        sql = "UPDATE products SET discon = true WHERE code = '" + fieldProdCode.getText() + "'";
                        statement.executeUpdate(sql);
                        db.writeToLog(sql);
                        db.writeToLog(MayfairConstants.LOG_SEPERATOR);
                        JOptionPane.showMessageDialog(Products.this, "State of product has been updated.");
                    }
                }
                else
                {
                    db.writeToLog("UNDISCONTINUE PRODUCT " + fieldProdCode.getText());
                    sql = "UPDATE products SET discon = false WHERE code = '" + fieldProdCode.getText() + "'";
                    statement.executeUpdate(sql);
                    db.writeToLog(sql);
                    db.writeToLog(MayfairConstants.LOG_SEPERATOR);
                    JOptionPane.showMessageDialog(Products.this, "Product has been updated.");
                }

                fieldProdCode.setText("");
                btnFindActionPerformed(null);
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(Products.this, e.getMessage());
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
    }//GEN-LAST:event_btnDiscontinueActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        View prod = new View(desktop, (int) table.getValueAt(table.getSelectedRow(), 0));
        desktop.add(prod);
        prod.show();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?", "Delete", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            Connection con = db.getConnection();
            try
            {
                Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = statement.executeQuery("SELECT prod_num FROM products WHERE code = '" + fieldProdCode.getText() + "'");
                rs.next();
                int prod_num = rs.getInt("prod_num");

                rs = statement.executeQuery("SELECT SUM(sales_order_details.quantity) AS total FROM sales_order_details JOIN sales_order ON sales_order_details.ord_num=sales_order.ord_num WHERE sales_order_details.prod_num = " + prod_num + " AND sales_order.dispatched = false AND sales_order.delivered = false");
                rs.next();
                int on_sales_order = rs.getInt("total");

                rs = statement.executeQuery("SELECT SUM(purchase_order_details.quantity) AS total FROM purchase_order_details JOIN purchase_order ON purchase_order_details.ord_num=purchase_order.ord_num WHERE purchase_order_details.prod_num = " + prod_num + " AND purchase_order.dispatched = false AND purchase_order.delivered = false");
                rs.next();
                int on_purchase_order = rs.getInt("total");

                if ((on_sales_order + on_purchase_order) == 0)
                {
                    Statement statement2 = con.createStatement();
                    db.writeToLog("DELETE PRODUCT " + fieldProdCode.getText());
                    sql = "DELETE FROM products WHERE code = '" + fieldProdCode.getText() + "'";
                    statement2.executeUpdate(sql);
                    db.writeToLog(sql);
                    db.writeToLog(MayfairConstants.LOG_SEPERATOR);

                    JOptionPane.showMessageDialog(Products.this, "Product Deleted.");

                    fieldProdCode.setText("");
                    btnFindActionPerformed(null);
                }
                else
                {
                    JOptionPane.showMessageDialog(Products.this, "Cannot delete product:\n" + on_sales_order + " - on Sales Orders\n" + on_purchase_order + " - on Purchase Orders");
                }

            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(Products.this, e.getMessage());
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
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        fieldProdCode.setText("");
        btnDiscontinue.setVisible(false);
        btnView.setEnabled(false);
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Edit prod = new Edit(desktop, (int) table.getValueAt(table.getSelectedRow(), 0));
        desktop.add(prod);
        prod.show();
    }//GEN-LAST:event_btnEditActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        Add prod = new Add();
        desktop.add(prod);
        prod.show();
    }//GEN-LAST:event_AddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDiscontinue;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox comboSeason;
    private javax.swing.JTextField fieldProdCode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
