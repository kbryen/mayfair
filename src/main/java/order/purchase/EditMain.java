/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.purchase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
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
    private final String orderNum;
    private final JDesktopPane desktop;

    public EditMain(String orderNum, JDesktopPane desktop)
    {
        initComponents();
        this.orderNum = orderNum;
        this.desktop = desktop;
        this.setTitle("Edit Purchase Order");

        labelDelDate2.setVisible(false);
        calDelDate.setVisible(false);
        btnQuant.setVisible(false);
        btnDelete.setEnabled(false);

        FillLabels();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product", "Quantity", "Unit Price", "Total Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        jLabel9.setText("Order Total : £");

        btnAdd.setText("Add Product");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnQuant.setText("Update Quantity");
        btnQuant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuantActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete Product");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel6.setText("Order Number : ");

        btnFinish.setText("Finish");
        btnFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
            ResultSet rs = queryStatement.executeQuery("SELECT SUM(price) AS total_price FROM purchase_order_details WHERE ord_num = '" + orderNum + "'");
            rs.next();
            double price = Double.parseDouble(rs.getString("total_price"));

            Statement updateStatement = con.createStatement();
            updateStatement.executeUpdate("UPDATE purchase_order SET price = " + price + " WHERE ord_num = '" + orderNum + "'");

            rs = queryStatement.executeQuery("SELECT suppliers.supp_num, suppliers.name, purchase_order.ord_num, purchase_order.del_date, purchase_order.price, purchase_order.comments FROM purchase_order INNER JOIN suppliers ON purchase_order.supp_num=suppliers.supp_num WHERE purchase_order.ord_num = '" + orderNum + "'");
            rs.next();

            labelNumber.setText("Supplier Number : ");
            labelNumber2.setText(rs.getString("supp_num"));
            labelName.setText("Supplier Name : ");
            labelName2.setText(rs.getString("name"));
            db.writeToLog("EDIT PURCHASE ORDER " + orderNum);

            labelOrdNum.setText(rs.getString("ord_num"));
            calDelDate.setDate(rs.getDate("del_date"));
            String[] date = rs.getString("del_date").split(" ");
            labelDelDate.setText(date[0]);
            labelOrdTotal.setText(String.format("%.02f", rs.getFloat("price")));
            fieldComments.setText(rs.getString("comments"));

            rs = queryStatement.executeQuery("SELECT products.code, purchase_order_details.quantity, products.purchase_price, purchase_order_details.price FROM purchase_order_details INNER JOIN products ON purchase_order_details.prod_num=products.prod_num WHERE purchase_order_details.ord_num = '" + orderNum + "'");
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
        EditAddProduct purchaseOrder = new EditAddProduct(orderNum, desktop);
        desktop.add(purchaseOrder);
        purchaseOrder.show();

        this.dispose();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuantActionPerformed
        String input = JOptionPane.showInputDialog("Please input new Quantity: ");
        int newQuantity = Integer.parseInt(input);

        if(newQuantity > 0)
        {
        String prodCode = (String) table.getValueAt(table.getSelectedRow(), 0);
        try (Connection con = db.getConnection())
        {
            Statement updateStatement = con.createStatement();
            Statement queryStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            db.writeToLog("UPDATE QUANTITY " + prodCode + " ON  PO " + orderNum);

            // FIND PROD_NUM
            ResultSet rs = queryStatement.executeQuery("SELECT prod_num, purchase_price, in_order FROM products WHERE code = '" + prodCode + "'");
            rs.next();
            int prod_num = rs.getInt("prod_num");
            double price = rs.getDouble("purchase_price");
            int in_order = rs.getInt("in_order");

            // CHECK PURCHASE_ORDER_DETAILS 
            rs = queryStatement.executeQuery("SELECT quantity, avaliable FROM purchase_order_details WHERE ord_num = '" + orderNum + "' AND prod_num = " + prod_num);
            rs.next();
            int quantity = rs.getInt("quantity");
            int avaliable = rs.getInt("avaliable");
            int onOrder = quantity - avaliable;

            if (newQuantity >= onOrder)
            {
                sql = ("UPDATE purchase_order_details SET quantity = " + newQuantity + ", avaliable = " + (newQuantity - onOrder) + ", price = " + (price * newQuantity) + " WHERE ord_num = '" + orderNum + "'  AND prod_num = " + prod_num);
                updateStatement.executeUpdate(sql);
                db.writeToLog(sql);

                // UPDATE PRODUCTS
                int difference = newQuantity - quantity;
                sql = ("UPDATE products SET in_order = " + (in_order + difference) + " WHERE prod_num = " + prod_num);
                updateStatement.executeUpdate(sql);
                db.writeToLog(sql);

                db.writeToLog(MayfairConstants.LOG_SEPERATOR);
                FillLabels();
                JOptionPane.showMessageDialog(EditMain.this, "Quantity Updated");
            }
            else
            {
                db.writeToLog("UNABLE TO UPDATE QUANTITY");
                db.writeToLog(MayfairConstants.LOG_SEPERATOR);
                JOptionPane.showMessageDialog(EditMain.this, "New Quantity must be at least " + onOrder + " as there are Sales Orders for this product.");
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
        }
        }
        else
        {
            JOptionPane.showMessageDialog(EditMain.this, "New Quantity must be greater than 0.");
        }
    }//GEN-LAST:event_btnQuantActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?", "Delete Product", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            String prodCode = (String) table.getValueAt(table.getSelectedRow(), 0);
            try (Connection con = db.getConnection())
            {
                Statement updateStatment = con.createStatement();
                Statement queryStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                db.writeToLog("DELETE PRODUCT " + prodCode + " FROM PO " + orderNum);

                // FIND PROD_NUM
                ResultSet rs = queryStatement.executeQuery("SELECT prod_num FROM products WHERE code = '" + prodCode + "'");
                rs.next();
                int prod_num = rs.getInt("prod_num");

                // CHECK PURCHASE_SALES_ORDER 
                HashMap<Integer, Integer> prodsOnSales = new HashMap();
                rs = queryStatement.executeQuery("SELECT so_num, quantity FROM purchase_sales_order WHERE po_num = '" + orderNum + "' AND prod_num = " + prod_num);
                while (rs.next())
                {
                    int so_num = rs.getInt("so_num");
                    int quantity = rs.getInt("quantity");

                    prodsOnSales.put(so_num, quantity);
                }

                if (prodsOnSales.isEmpty())
                {
                    // UPDATE PURCHASE_ORDER_DETAILS
                    rs = queryStatement.executeQuery("SELECT quantity FROM purchase_order_details WHERE ord_num = '" + orderNum + "' AND prod_num = " + prod_num);
                    rs.next();
                    int quantity = rs.getInt("quantity");

                    sql = ("DELETE FROM purchase_order_details WHERE ord_num = '" + orderNum + "'  AND prod_num = " + prod_num);
                    updateStatment.executeUpdate(sql);
                    db.writeToLog(sql);

                    // UPDATE PRODUCTS
                    rs = queryStatement.executeQuery("SELECT in_order FROM products WHERE prod_num = " + prod_num);
                    rs.next();
                    int in_order = rs.getInt("in_order");

                    sql = ("UPDATE products SET in_order = " + (in_order - quantity) + " WHERE prod_num = " + prod_num);
                    updateStatment.executeUpdate(sql);
                    db.writeToLog(sql);

                    db.writeToLog(MayfairConstants.LOG_SEPERATOR);
                    FillLabels();
                    JOptionPane.showMessageDialog(EditMain.this, "Product Deleted");
                }
                else
                {
                    StringBuilder message = new StringBuilder();
                    prodsOnSales.entrySet().stream().forEach((products) ->
                    {
                        int so_num = products.getKey();
                        int quantity = products.getValue();

                        message.append("\n").append(quantity).append(" on Sales Order ").append(so_num);
                    });

                    db.writeToLog("UNABLE TO CANCEL ORDER" + message);
                    db.writeToLog(MayfairConstants.LOG_SEPERATOR);
                    JOptionPane.showMessageDialog(EditMain.this, "Can not delete product " + prodCode + " due to being on the following Sales Orders:" + message);
                }
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you have finished editting?", "Finish", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            try (Connection con = db.getConnection())
            {
                Statement statement = con.createStatement();
                sql = "UPDATE purchase_order SET comments = '" + fieldComments.getText() + "' WHERE ord_num = '" + orderNum + "'";
                statement.executeUpdate(sql);
                db.writeToLog(sql);

                JOptionPane.showMessageDialog(EditMain.this, "Order Saved.");
                this.dispose();
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(EditMain.this, e.getMessage());
            }
            finally
            {
                db.writeToLog(MayfairConstants.LOG_SEPERATOR);
            }
        }
    }//GEN-LAST:event_btnFinishActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        btnQuant.setVisible(true);
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
