/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.sales;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import javafx.util.Pair;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.java.Database;
import main.java.MayfairConstants;

/**
 *
 * @author kian_bryen
 */
public class PickPurchaseOrders extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;
    private final Database db = new Database();
    private String sql;
    private final int leftToFulfill;
    private final int prodNum;
    private String purchaseOrderNum;
    private final int salesOrderNum;
    private final int numOfProducts;

    private final Map<String, Pair<Integer, Date>> possiblePurchaseOrders;

    public PickPurchaseOrders(JDesktopPane desktop, Map<String, Pair<Integer, Date>> possiblePurchaseOrders, int leftToFulfill, int prodNum, int salesOrderNum, int numOfProducts)
    {
        initComponents();
        this.desktop = desktop;
        this.possiblePurchaseOrders = possiblePurchaseOrders;
        this.leftToFulfill = leftToFulfill;
        this.prodNum = prodNum;
        this.salesOrderNum = salesOrderNum;
        this.numOfProducts = numOfProducts;

        comboPOS.setVisible(true);
        comboPOS.setModel(new javax.swing.DefaultComboBoxModel(this.possiblePurchaseOrders.keySet().toArray()));
        showLabels(false);
        FillTable();
        btnBack.setVisible(true);
        table.setAutoCreateRowSorter(true);
    }

    private void showLabels(boolean show)
    {
        label2.setVisible(show);
        label3.setVisible(show);
        label4.setVisible(show);
        label5.setVisible(show);
        label6.setVisible(show);
        labelOrdNum.setVisible(show);
        labelDate.setVisible(show);
        labelAvaliable.setVisible(show);
        labelQuant.setVisible(show);
        fieldQuant.setVisible(show);

        btnAdd.setVisible(show);
    }

    private void showLabels(String date, int avaliable)
    {
        showLabels(true);
        labelOrdNum.setText(purchaseOrderNum);
        labelDate.setText(date);
        labelAvaliable.setText(String.valueOf(avaliable));
        labelQuant.setText(String.valueOf(leftToFulfill));
    }

    private void FillTable()
    {
        Connection con = db.getConnection();
        try
        {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery("SELECT purchase_sales_order.po_num, purchase_order.del_date, purchase_sales_order.quantity FROM purchase_sales_order JOIN purchase_order ON purchase_sales_order.po_num = purchase_order.ord_num WHERE purchase_sales_order.so_num = " + salesOrderNum + " AND purchase_sales_order.prod_num = " + prodNum);

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
            JOptionPane.showMessageDialog(PickPurchaseOrders.this, e.getMessage());
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
        jLabel5 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        comboPOS = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        label = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        labelDate = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        labelAvaliable = new javax.swing.JLabel();
        fieldQuant = new javax.swing.JTextField();
        label6 = new javax.swing.JLabel();
        label5 = new javax.swing.JLabel();
        labelQuant = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        labelOrders = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        labelOrdNum = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();

        setIconifiable(true);
        setResizable(true);
        setTitle("New Sales Order");
        setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Pick Purchase Orders");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel5.setText("Product Code");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddActionPerformed(evt);
            }
        });

        comboPOS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboPOS.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                comboPOSActionPerformed(evt);
            }
        });

        label.setText("Pick a purchase order from the drop down list: ");

        label3.setText("Delivery Date:");

        labelDate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelDate.setText("DATE");

        label4.setText("Avaliable on Order:");

        labelAvaliable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAvaliable.setText("AVALIABLE");

        label6.setText("Take from Order:");

        label5.setText("Quantity left to fulfill:");

        labelQuant.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelQuant.setText("QUANTITY");

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
                "Order Number", "Delivery Date", "Quantity"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false
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
        scrollPane.setViewportView(table);

        labelOrders.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        labelOrders.setText("Orders Taken From:");

        label2.setText("Order Number:");

        labelOrdNum.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelOrdNum.setText("ORDER NUM");

        btnBack.setText("Back to Order");
        btnBack.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label)
                                .addGap(18, 18, 18)
                                .addComponent(comboPOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label6)
                                .addGap(38, 38, 38)
                                .addComponent(fieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd))
                            .addComponent(labelOrders)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label3)
                                    .addComponent(label4)
                                    .addComponent(label2))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelOrdNum)
                                    .addComponent(labelDate)
                                    .addComponent(labelAvaliable)
                                    .addComponent(labelQuant))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBack)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label2)
                    .addComponent(labelOrdNum))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label3)
                    .addComponent(labelDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label4)
                    .addComponent(labelAvaliable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label5)
                    .addComponent(labelQuant))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label6)
                    .addComponent(btnAdd))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try
        {
            Connection con = db.getConnection();
            try
            {

                Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement statement2 = con.createStatement();
                ResultSet rs;

                int quantity;
                if (fieldQuant.getText().equals(""))
                {
                    quantity = 0;
                }
                else
                {
                    quantity = Integer.parseInt(fieldQuant.getText());
                }

                Pair<Integer, Date> values = possiblePurchaseOrders.get(purchaseOrderNum);
                int avaliable = values.getKey();
                Date deliveryDate = values.getValue();

                if (quantity > 0)
                {
                    if (quantity <= avaliable)
                    {
                        if (quantity <= leftToFulfill)
                        {
                            // UPDATE PURCHASE ORDER DETAILS
                            avaliable = avaliable - quantity;
                            sql = "UPDATE purchase_order_details SET avaliable = " + avaliable + " WHERE prod_num = " + prodNum + " AND ord_num = '" + purchaseOrderNum + "'";
                            statement2.executeUpdate(sql);
                            db.writeToLog(sql);

                            if (avaliable > 0)
                            {
                                possiblePurchaseOrders.replace(purchaseOrderNum, new Pair(avaliable, deliveryDate));
                            }
                            else
                            {
                                possiblePurchaseOrders.remove(purchaseOrderNum);
                            }

                            // UPDATE PURCHASE_SALES_ORDER
                            rs = statement.executeQuery("SELECT code, quantity FROM purchase_sales_order WHERE po_num = '" + purchaseOrderNum + "' AND so_num = " + salesOrderNum + " AND prod_num = " + prodNum);
                            if (rs.next())
                            {
                                int oldQuant = rs.getInt("quantity");
                                int code = rs.getInt("code");

                                sql = "UPDATE purchase_sales_order SET quantity = " + (oldQuant + quantity) + " WHERE code = " + code;
                            }
                            else
                            {
                                sql = "INSERT INTO purchase_sales_order (po_num, so_num, prod_num, quantity) VALUES ('" + purchaseOrderNum + "', " + salesOrderNum + ", " + prodNum + ", " + quantity + ")";
                            }
                            statement2.executeUpdate(sql);
                            db.writeToLog(sql);

                            // UPDATE PRODUCTS
                            rs = statement.executeQuery("SELECT in_order, sales_price FROM products WHERE prod_num = " + prodNum);
                            rs.next();
                            double price = Double.parseDouble(rs.getString("sales_price"));
                            int in_order = rs.getInt("in_order");
                            int newOrder = in_order - quantity;
                            sql = "UPDATE products SET in_order = " + newOrder + " WHERE prod_num = " + prodNum;
                            statement2.executeUpdate(sql);
                            db.writeToLog(sql);

                            // UPDATE SALES ORDER DETAILS
                            rs = statement.executeQuery("SELECT code, quantity, fromOrder FROM sales_order_details WHERE prod_num = " + prodNum + " AND ord_num = " + salesOrderNum);
                            if (rs.next())
                            {
                                int oldQuant = rs.getInt("quantity");
                                int code = rs.getInt("code");
                                int oldFromOrder = rs.getInt("fromOrder");

                                sql = "UPDATE sales_order_details SET quantity = " + (oldQuant + quantity) + ", price = " + ((oldQuant + quantity) * price) + ", fromOrder = " + (oldFromOrder + quantity) + " WHERE code = " + code;
                            }
                            else
                            {
                                sql = "INSERT INTO sales_order_details (ord_num, prod_num, quantity, price, fromStock, fromOrder) VALUES (" + salesOrderNum + ", " + prodNum + ", " + quantity + ", " + (quantity * price) + ", 0, " + quantity + ")";
                            }
                            statement2.executeUpdate(sql);
                            db.writeToLog(sql);

                            if ((leftToFulfill - quantity) != 0)
                            {
                                JOptionPane.showMessageDialog(PickPurchaseOrders.this, quantity + " Added");
                                PickPurchaseOrders pickOrders = new PickPurchaseOrders(desktop, possiblePurchaseOrders, leftToFulfill - quantity, prodNum, salesOrderNum, numOfProducts);
                                desktop.add(pickOrders);
                                pickOrders.show();
                                this.dispose();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(PickPurchaseOrders.this, "Product added");
                                AddProduct addProducts = new AddProduct(desktop, salesOrderNum, numOfProducts + 1);
                                desktop.add(addProducts);
                                addProducts.show();
                                this.dispose();
                            }

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(PickPurchaseOrders.this, "Quantity is greater than Number left to fulfill");
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(PickPurchaseOrders.this, "Quantity is greater than Number avaliable");
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(PickPurchaseOrders.this, "Quantity must be greater than 0");
                }

            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(PickPurchaseOrders.this, e.getMessage());
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
            JOptionPane.showMessageDialog(PickPurchaseOrders.this, "Please enter a valid quantity");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void comboPOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPOSActionPerformed
        this.purchaseOrderNum = (String) comboPOS.getSelectedItem();
        Pair<Integer, Date> values = possiblePurchaseOrders.get(purchaseOrderNum);
        int avaliable = values.getKey();
        String date = values.getValue().toString();

        this.showLabels(date, avaliable);
    }//GEN-LAST:event_comboPOSActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tableMouseClicked
    {//GEN-HEADEREND:event_tableMouseClicked

    }//GEN-LAST:event_tableMouseClicked

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBackActionPerformed
    {//GEN-HEADEREND:event_btnBackActionPerformed
        Connection con = db.getConnection();
        try
        {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery("SELECT count(*) AS total FROM sales_order_details WHERE ord_num = " + salesOrderNum);
            rs.next();
            int total = rs.getInt("total");
            
            AddProduct addProducts = new AddProduct(desktop, salesOrderNum, total + 1);
            desktop.add(addProducts);
            MayfairConstants.setMaximum(addProducts);
            addProducts.show();
            this.dispose();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(PickPurchaseOrders.this, e.getMessage());
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
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox comboPOS;
    private javax.swing.JTextField fieldQuant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    private javax.swing.JLabel label6;
    private javax.swing.JLabel labelAvaliable;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelOrdNum;
    private javax.swing.JLabel labelOrders;
    private javax.swing.JLabel labelQuant;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
