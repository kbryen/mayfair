/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.sales;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import main.java.Database;
import main.java.MayfairConstants;

/**
 *
 * @author kian_bryen
 */
public class EditMain extends javax.swing.JInternalFrame
{

    private final Database db = new Database();
    private String sql;
    private final int orderNum;
    private final JDesktopPane desktop;
    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public EditMain(int orderNum, JDesktopPane desktop)
    {
        initComponents();
        this.orderNum = orderNum;
        this.setTitle("Edit Sales Order");
        this.desktop = desktop;

        FillLabels();
        btnQuant.setVisible(false);
        btnDelete.setEnabled(false);
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
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        labelOrdTotal = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnAdd = new javax.swing.JButton();
        btnQuant = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        labelNumber = new javax.swing.JLabel();
        labelNumber2 = new javax.swing.JLabel();
        labelName2 = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelOrdNum = new javax.swing.JLabel();
        btnFinish = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        labelDelDate = new javax.swing.JLabel();
        fieldComments = new javax.swing.JTextField();
        labelComments = new javax.swing.JLabel();
        calDelDate = new com.toedter.calendar.JDateChooser();
        labelDelDate2 = new javax.swing.JLabel();

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Edit Order");

        jLabel8.setText("Update Order Details :");

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

        jLabel9.setText("Order Total : £");

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

        jLabel6.setText("Order Number : ");

        btnFinish.setText("Finish");
        btnFinish.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFinishActionPerformed(evt);
            }
        });

        jLabel7.setText("Delivery Date :");

        labelComments.setText("Update Comments :");

        calDelDate.setDateFormatString("yyyy-MM-dd");

        labelDelDate2.setText("Set Delivery Date :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelNumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNumber2)
                                .addGap(18, 18, 18)
                                .addComponent(labelName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelName2))
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelComments)
                            .addComponent(labelDelDate2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(calDelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fieldComments, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelOrdTotal)
                                .addGap(10, 10, 10)))))
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
                        .addComponent(labelNumber2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelName)
                        .addComponent(labelName2)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(labelOrdTotal))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelComments)
                        .addComponent(fieldComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDelDate2)
                    .addComponent(calDelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

    /**
     * Fill Labels
     */
    private void FillLabels()
    {
        try (Connection con = db.getConnection())
        {
            Statement queryStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = queryStatement.executeQuery("SELECT SUM(price) AS total_price FROM sales_order_details WHERE ord_num = '" + orderNum + "'");
            rs.next();
            double price = Double.parseDouble(rs.getString("total_price"));

            Statement updateStatement = con.createStatement();
            updateStatement.executeUpdate("UPDATE sales_order SET price = " + price + " WHERE ord_num = " + orderNum);

            rs = queryStatement.executeQuery("SELECT customers.cust_num, customers.name, sales_order.ord_num, sales_order.del_date, sales_order.price, sales_order.comments FROM sales_order INNER JOIN customers ON sales_order.cust_num=customers.cust_num WHERE sales_order.ord_num = " + orderNum);
            rs.next();

            labelNumber.setText("Customer Number : ");
            labelNumber2.setText(rs.getString("cust_num"));
            labelName.setText("Customer Name : ");
            labelName2.setText(rs.getString("name"));
            db.writeToLog("EDIT SALES ORDER " + orderNum);

            labelOrdNum.setText(rs.getString("ord_num"));
            Date delDate = rs.getDate("del_date");
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
            labelOrdTotal.setText(String.format("%.02f", rs.getFloat("price")));
            fieldComments.setText(rs.getString("comments"));

            rs = queryStatement.executeQuery("SELECT products.code, sales_order_details.quantity, products.sales_price, sales_order_details.price FROM sales_order_details INNER JOIN products ON sales_order_details.prod_num=products.prod_num WHERE sales_order_details.ord_num = " + orderNum);
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
            JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
        }
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        EditAddProduct addProd = new EditAddProduct(desktop, orderNum);
        desktop.add(addProd);
        addProd.show();
        this.dispose();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuantActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit the quantity?", "Edit Quantity", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            String prodCode = (String) table.getValueAt(table.getSelectedRow(), 0);
            try (Connection con = db.getConnection())
            {
                Statement queryStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement updateStatement = con.createStatement();
                ResultSet rs;
                db.writeToLog("DELETE PRODUCT " + prodCode);

                rs = queryStatement.executeQuery("SELECT prod_num, in_stock, in_order FROM products WHERE code = '" + prodCode + "'");
                rs.next();
                int prod_num = rs.getInt("prod_num");
                int in_stock = rs.getInt("in_stock");
                int in_order = rs.getInt("in_order");

                rs = queryStatement.executeQuery("SELECT fromStock, fromOrder FROM sales_order_details WHERE prod_num = " + prod_num + " AND ord_num = " + orderNum);
                rs.next();
                int fromStock = rs.getInt("fromStock");
                int fromOrder = rs.getInt("fromOrder");

                int newStock = in_stock + fromStock;
                int newOrder = in_order + fromOrder;

                // UPDATE PRODUCTS 
                sql = "UPDATE products SET in_stock = " + newStock + ", in_order = " + newOrder + " WHERE prod_num = " + prod_num;
                updateStatement.executeUpdate(sql);
                db.writeToLog(sql);

                // UPDATE PURCHASE_SALES_ORDER
                ArrayList<Pair<String, Integer>> purchaseOrders = new ArrayList();
                ArrayList<String> codes = new ArrayList();
                rs = queryStatement.executeQuery("SELECT code, po_num, quantity FROM purchase_sales_order WHERE prod_num = " + prod_num + " AND so_num = " + orderNum);
                while (rs.next())
                {
                    int quantity = rs.getInt("quantity");
                    String poNum = rs.getString("po_num");
                    purchaseOrders.add(new Pair(poNum, quantity));
                    codes.add(rs.getString("code"));
                }
                for (String code : codes)
                {
                    sql = "DELETE FROM purchase_sales_order WHERE code = " + code;
                    updateStatement.executeUpdate(sql);
                    db.writeToLog(sql);
                }

                // UPDATE PURCHASE ORDER DETAILS
                for (Pair<String, Integer> purchaseOrder : purchaseOrders)
                {
                    rs = queryStatement.executeQuery("SELECT avaliable FROM purchase_order_details WHERE prod_num = " + prod_num + " AND ord_num = '" + purchaseOrder.getKey() + "'");
                    rs.next();
                    int avaliable = rs.getInt("avaliable");

                    sql = "UPDATE purchase_order_details SET avaliable = " + (avaliable + purchaseOrder.getValue()) + " WHERE prod_num = " + prod_num + " AND ord_num = '" + purchaseOrder.getKey() + "'";
                    updateStatement.executeUpdate(sql);
                    db.writeToLog(sql);
                }

                // DELETE FROM SALES ORDER DETAILS
                sql = "DELETE FROM sales_order_details WHERE prod_num = " + prod_num + " AND ord_num = " + orderNum;
                updateStatement.executeUpdate(sql);
                db.writeToLog(sql);

//                EditAddExisting addProd = new EditAddExisting(orderNum, desktop, prod_num);
//                desktop.add(addProd);
//                addProd.show();
//                this.dispose();
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnQuantActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?", "Delete Product", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            String prodCode = (String) table.getValueAt(table.getSelectedRow(), 0);
            try (Connection con = db.getConnection())
            {
                Statement queryStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement updateStatement = con.createStatement();
                ResultSet rs;
                db.writeToLog("DELETE PRODUCT " + prodCode);

                rs = queryStatement.executeQuery("SELECT prod_num, in_stock, in_order FROM products WHERE code = '" + prodCode + "'");
                rs.next();
                int prod_num = rs.getInt("prod_num");
                int in_stock = rs.getInt("in_stock");
                int in_order = rs.getInt("in_order");

                rs = queryStatement.executeQuery("SELECT fromStock, fromOrder FROM sales_order_details WHERE prod_num = " + prod_num + " AND ord_num = " + orderNum);
                rs.next();
                int fromStock = rs.getInt("fromStock");
                int fromOrder = rs.getInt("fromOrder");

                int newStock = in_stock + fromStock;
                int newOrder = in_order + fromOrder;

                // UPDATE PRODUCTS 
                sql = "UPDATE products SET in_stock = " + newStock + ", in_order = " + newOrder + " WHERE prod_num = " + prod_num;
                updateStatement.executeUpdate(sql);
                db.writeToLog(sql);

                // UPDATE PURCHASE_SALES_ORDER
                ArrayList<Pair<String, Integer>> purchaseOrders = new ArrayList();
                ArrayList<String> codes = new ArrayList();
                rs = queryStatement.executeQuery("SELECT code, po_num, quantity FROM purchase_sales_order WHERE prod_num = " + prod_num + " AND so_num = " + orderNum);
                while (rs.next())
                {
                    int quantity = rs.getInt("quantity");
                    String poNum = rs.getString("po_num");
                    purchaseOrders.add(new Pair(poNum, quantity));
                    codes.add(rs.getString("code"));
                }
                for (String code : codes)
                {
                    sql = "DELETE FROM purchase_sales_order WHERE code = " + code;
                    updateStatement.executeUpdate(sql);
                    db.writeToLog(sql);
                }

                // UPDATE PURCHASE ORDER DETAILS
                for (Pair<String, Integer> purchaseOrder : purchaseOrders)
                {
                    rs = queryStatement.executeQuery("SELECT avaliable FROM purchase_order_details WHERE prod_num = " + prod_num + " AND ord_num = '" + purchaseOrder.getKey() + "'");
                    rs.next();
                    int avaliable = rs.getInt("avaliable");

                    sql = "UPDATE purchase_order_details SET avaliable = " + (avaliable + purchaseOrder.getValue()) + " WHERE prod_num = " + prod_num + " AND ord_num = '" + purchaseOrder.getKey() + "'";
                    updateStatement.executeUpdate(sql);
                    db.writeToLog(sql);
                }

                // DELETE FROM SALES ORDER DETAILS
                sql = "DELETE FROM sales_order_details WHERE prod_num = " + prod_num + " AND ord_num = " + orderNum;
                updateStatement.executeUpdate(sql);
                db.writeToLog(sql);
                JOptionPane.showMessageDialog(EditMain.this, "Product Deleted");

                FillLabels();
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
        String date = ((JTextField) calDelDate.getDateEditor().getUiComponent()).getText();
        if (!date.equals(""))
        {
            try
            {
                Date minDate = getMinDelDate(orderNum);
                Date orderDate = formatter.parse(date);
                if (orderDate.after(minDate) || orderDate.equals(minDate))
                {
                    try (Statement statement = db.getConnection().createStatement())
                    {
                        sql = "UPDATE sales_order SET del_date = '" + date + "', comments = '" + fieldComments.getText() + "' WHERE ord_num = " + orderNum;
                        statement.executeUpdate(sql);
                        db.writeToLog(sql);
                        db.writeToLog(MayfairConstants.LOG_SEPERATOR);

                        ViewSalesSummary salesOrder = new ViewSalesSummary(orderNum);
                        desktop.add(salesOrder);
                        salesOrder.show();

                        this.dispose();
                    }
                    catch (SQLException e)
                    {
                        JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(EditMain.this, "Delivery Date needs to be after " + minDate);
                }
            }
            catch (ParseException e)
            {
                JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(EditMain.this, "Please select a Delivery Date");
        }
    }//GEN-LAST:event_btnFinishActionPerformed

    private Date getMinDelDate(int salesOrderNum)
    {
        try (Statement statement = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            Date datetime = new Date();
            datetime = formatter.parse(formatter.format(datetime));
            
            ResultSet rs = statement.executeQuery("SELECT purchase_order.del_date FROM purchase_order JOIN purchase_sales_order ON purchase_order.ord_num = purchase_sales_order.po_num WHERE purchase_sales_order.so_num = " + salesOrderNum);
            while (rs.next())
            {
                Date delivery_date = rs.getDate("del_date");
                delivery_date = formatter.parse(formatter.format(delivery_date));
                if (delivery_date.after(datetime))
                {
                    datetime = delivery_date;
                }
            }
            
            return datetime;
        }
        catch (ParseException | SQLException e)
        {
            JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
            return new Date();
        }
    }

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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelComments;
    private javax.swing.JLabel labelDelDate;
    private javax.swing.JLabel labelDelDate2;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelName2;
    private javax.swing.JLabel labelNumber;
    private javax.swing.JLabel labelNumber2;
    private javax.swing.JLabel labelOrdNum;
    private javax.swing.JLabel labelOrdTotal;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
