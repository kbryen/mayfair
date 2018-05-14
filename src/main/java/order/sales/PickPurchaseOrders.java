/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.sales;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import javafx.util.Pair;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.PO_DELDATE;
import static main.java.MayfairStatic.PO_ORDNUM;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_INORDER;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_SALESPRICE;
import static main.java.MayfairStatic.PS_CODE;
import static main.java.MayfairStatic.PS_PONUM;
import static main.java.MayfairStatic.PS_PRODNUM;
import static main.java.MayfairStatic.PS_QUANTITY;
import static main.java.MayfairStatic.PS_SONUM;
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;
import static main.java.MayfairStatic.PURCHASE_SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.SOD_CODE;
import static main.java.MayfairStatic.SOD_FROMORDER;
import static main.java.MayfairStatic.SOD_FROMSTOCK;
import static main.java.MayfairStatic.SOD_ORDNUM;
import static main.java.MayfairStatic.SOD_PRICE;
import static main.java.MayfairStatic.SOD_PRODNUM;
import static main.java.MayfairStatic.SOD_QUANTITY;
import static main.java.MayfairStatic.SO_ORDNUM;

/**
 *
 * @author kian_bryen
 */
public class PickPurchaseOrders extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;
    private int leftToFulfill;
    private int numOfProducts = -1;
    private final int prod_num;
    private String po_ordnum;
    private final int so_ordnum;
    private final Map<String, Pair<Integer, Date>> possiblePurchaseOrders;

    public PickPurchaseOrders(JDesktopPane desktop, Map<String, Pair<Integer, Date>> possiblePurchaseOrders, int leftToFulfill, int prod_num, int so_ordnum, int numOfProducts)
    {
        this(desktop, possiblePurchaseOrders, leftToFulfill, prod_num, so_ordnum);
        this.numOfProducts = numOfProducts;
    }

    public PickPurchaseOrders(JDesktopPane desktop, Map<String, Pair<Integer, Date>> possiblePurchaseOrders, int leftToFulfill, int prod_num, int so_ordnum)
    {
        setUpGUI();
        this.desktop = desktop;
        this.possiblePurchaseOrders = possiblePurchaseOrders;
        this.leftToFulfill = leftToFulfill;
        this.prod_num = prod_num;
        this.so_ordnum = so_ordnum;
    }

    private void setUpGUI()
    {
        initComponents();
        enableButtons(false);
        comboPOS.setModel(new javax.swing.DefaultComboBoxModel(possiblePurchaseOrders.keySet().toArray()));
        table.setAutoCreateRowSorter(true);
        MayfairStatic.addDateSorter(table, 1);
        fillTable();
    }

    private void enableButtons(boolean enable)
    {
        label1.setVisible(enable);
        label2.setVisible(enable);
        label3.setVisible(enable);
        label4.setVisible(enable);
        label5.setVisible(enable);
        labelOrdNum.setVisible(enable);
        labelDelDate.setVisible(enable);
        labelAvaliable.setVisible(enable);
        labelQuantity.setVisible(enable);
        fieldQuantity.setVisible(enable);
        btnAdd.setVisible(enable);
    }

    private void fillTable()
    {
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {

            ResultSet rs = statement.executeQuery("SELECT " + PS_PONUM + ", " + PO_DELDATE + ", " + PS_QUANTITY + " "
                    + "FROM " + PURCHASE_SALES_ORDER_TABLE + " "
                    + "JOIN " + PURCHASE_ORDER_TABLE + " "
                    + "ON " + PS_PONUM + "=" + PO_ORDNUM + " "
                    + "WHERE " + PS_SONUM + " = " + so_ordnum + " "
                    + "AND " + PS_PRODNUM + " = " + prod_num);
            MayfairStatic.fillTable(table, rs);
        }
        catch (Exception ex)
        {
            MayfairStatic.outputMessage(this, ex);
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
        label2 = new javax.swing.JLabel();
        labelDelDate = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        labelAvaliable = new javax.swing.JLabel();
        fieldQuantity = new javax.swing.JTextField();
        label5 = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        labelQuantity = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        labelOrders = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
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

        label2.setText("Delivery Date:");

        labelDelDate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelDelDate.setText("DATE");

        label3.setText("Avaliable on Order:");

        labelAvaliable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAvaliable.setText("AVALIABLE");

        label5.setText("Take from Order:");

        label4.setText("Quantity left to fulfill:");

        labelQuantity.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelQuantity.setText("QUANTITY");

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
        scrollPane.setViewportView(table);

        labelOrders.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        labelOrders.setText("Orders Taken From:");

        label1.setText("Order Number:");

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
                            .addComponent(label4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label5)
                                .addGap(38, 38, 38)
                                .addComponent(fieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd))
                            .addComponent(labelOrders)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label2)
                                    .addComponent(label3)
                                    .addComponent(label1))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelOrdNum)
                                    .addComponent(labelDelDate)
                                    .addComponent(labelAvaliable)
                                    .addComponent(labelQuantity))))
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
                    .addComponent(label1)
                    .addComponent(labelOrdNum))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label2)
                    .addComponent(labelDelDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label3)
                    .addComponent(labelAvaliable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label4)
                    .addComponent(labelQuantity))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label5)
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
            int quantToAdd = Integer.parseInt(fieldQuantity.getText());
            if (quantToAdd <= 0)
            {
                throw new NumberFormatException();
            }
            if (quantToAdd <= leftToFulfill)
            {
                Pair<Integer, Date> values = possiblePurchaseOrders.get(po_ordnum);
                int avaliable = values.getKey() - quantToAdd;
                Date deliveryDate = values.getValue();
                if (avaliable >= 0)
                {
                    try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
                    {
                        try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
                        {
                            // UPDATE PURCHASE ORDER DETAILS
                            String sql = "UPDATE " + PURCHASE_ORDER_DETAILS_TABLE + " "
                                    + "SET " + POD_AVALIABLE + " = " + avaliable + " "
                                    + "WHERE " + POD_PRODNUM + " = " + prod_num + " "
                                    + "AND " + POD_ORDNUM + " = '" + po_ordnum + "'";
                            updateStatement.executeUpdate(sql);
                            MayfairStatic.writeToLog(sql);
                            if (avaliable > 0)
                            {
                                possiblePurchaseOrders.replace(po_ordnum, new Pair(avaliable, deliveryDate));
                            }
                            else
                            {
                                possiblePurchaseOrders.remove(po_ordnum);
                            }

                            // UPDATE PURCHASE_SALES_ORDER
                            ResultSet rs = selectStatement.executeQuery("SELECT " + PS_CODE + ", " + PS_QUANTITY + " "
                                    + "FROM " + PURCHASE_SALES_ORDER_TABLE + " "
                                    + "WHERE " + PS_PONUM + " = '" + po_ordnum + "' "
                                    + "AND " + PS_SONUM + " = " + so_ordnum + " "
                                    + "AND " + PS_PRODNUM + " = " + prod_num);
                            if (rs.next())
                            {
                                int quantity = rs.getInt(PS_QUANTITY) + quantToAdd;
                                int code = rs.getInt(PS_CODE);
                                sql = "UPDATE " + PURCHASE_SALES_ORDER_TABLE + " "
                                        + "SET " + PS_QUANTITY + " = " + quantity + " "
                                        + "WHERE " + PS_CODE + " = " + code;
                            }
                            else
                            {
                                sql = "INSERT INTO " + PURCHASE_SALES_ORDER_TABLE + " (" 
                                        + PS_PONUM + ", " 
                                        + PS_SONUM + ", " 
                                        + PS_PRODNUM + ", " 
                                        + PS_QUANTITY + ") "
                                        + "VALUES ('" 
                                        + po_ordnum + "', " 
                                        + so_ordnum + ", " 
                                        + prod_num + ", " 
                                        + quantToAdd + ")";
                            }
                            updateStatement.executeUpdate(sql);
                            MayfairStatic.writeToLog(sql);

                            // UPDATE PRODUCTS
                            rs = selectStatement.executeQuery("SELECT " + PRODUCT_INORDER + ", " + PRODUCT_SALESPRICE + " "
                                    + "FROM " + PRODUCTS_TABLE + " "
                                    + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num);
                            rs.next();
                            double sales_price = rs.getDouble(PRODUCT_SALESPRICE);
                            int in_order = rs.getInt(PRODUCT_INORDER) - quantToAdd;
                            sql = "UPDATE " + PRODUCTS_TABLE + " "
                                    + "SET " + PRODUCT_INORDER + " = " + in_order + " "
                                    + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num;
                            updateStatement.executeUpdate(sql);
                            MayfairStatic.writeToLog(sql);

                            // UPDATE SALES ORDER DETAILS
                            rs = selectStatement.executeQuery("SELECT " + SOD_CODE + ", " + SOD_QUANTITY + ", " + SOD_FROMORDER + " "
                                    + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                                    + "WHERE " + SOD_PRODNUM + " = " + prod_num + " "
                                    + "AND " + SOD_ORDNUM + " = " + so_ordnum);
                            if (rs.next())
                            {
                                int quantity = rs.getInt(SOD_QUANTITY) + quantToAdd;
                                int code = rs.getInt(SOD_CODE);
                                int fromOrder = rs.getInt(SOD_FROMORDER) + quantToAdd;
                                double price = quantity * sales_price;
                                sql = "UPDATE " + SALES_ORDER_DETAILS_TABLE + " "
                                        + "SET " + SOD_QUANTITY + " = " + quantity + ", "
                                        + SOD_PRICE + " = " + price + ", "
                                        + SOD_FROMORDER + " = " + fromOrder + " "
                                        + "WHERE " + SOD_CODE + " = " + code;
                            }
                            else
                            {
                                double price = quantToAdd * sales_price;
                                sql = "INSERT INTO " + SALES_ORDER_DETAILS_TABLE + " ("
                                        + SOD_ORDNUM + ", "
                                        + SOD_PRODNUM + ", "
                                        + SOD_QUANTITY + ", "
                                        + SOD_PRICE + ", "
                                        + SOD_FROMSTOCK + ", "
                                        + SOD_FROMORDER + ") "
                                        + "VALUES (" 
                                        + so_ordnum + ", " 
                                        + prod_num + ", " 
                                        + quantToAdd + ", " 
                                        + price 
                                        + ", 0, " 
                                        + quantToAdd + ")";
                            }
                            updateStatement.executeUpdate(sql);
                            MayfairStatic.writeToLog(sql);

                            MayfairStatic.outputMessage(this, "Product added", quantToAdd + " added to order.", INFORMATION_MESSAGE);
                            JInternalFrame jFrame;

                            leftToFulfill -= quantToAdd;
                            if (leftToFulfill != 0)
                            {
                                jFrame = new PickPurchaseOrders(desktop, possiblePurchaseOrders, leftToFulfill, prod_num, so_ordnum, numOfProducts);
                            }
                            else if (numOfProducts != -1)
                            {
                                jFrame = new NewSalesOrderStep2(desktop, so_ordnum, numOfProducts + 1);
                                MayfairStatic.setMaximum(jFrame);
                            }
                            else
                            {
                                jFrame = new EditSalesOrderStep2(desktop, so_ordnum);
                                MayfairStatic.setMaximum(jFrame);
                            }
                            desktop.add(jFrame);
                            jFrame.show();
                            this.dispose();
                        }
                    }
                    catch (Exception ex)
                    {
                        MayfairStatic.outputMessage(this, ex);
                    }
                }
                else
                {
                    MayfairStatic.outputMessage(this, "Invalid quantity", "Quantity is greater than amount avaliable on order.", WARNING_MESSAGE);
                }
            }
            else
            {
                MayfairStatic.outputMessage(this, "Invalid quantity", "Quantity is greater than number left to fulfill.", WARNING_MESSAGE);
            }
        }
        catch (NumberFormatException ex)
        {
            MayfairStatic.outputMessage(this, "Invalid quantity", "Please enter a valid quantity.", WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void comboPOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPOSActionPerformed
        this.po_ordnum = (String) comboPOS.getSelectedItem();
        enableButtons(true);
        Pair<Integer, Date> values = possiblePurchaseOrders.get(po_ordnum);
        labelDelDate.setText(values.getValue().toString());
        labelAvaliable.setText(String.valueOf(values.getKey()));
        labelQuantity.setText(String.valueOf(leftToFulfill));
        labelOrdNum.setText(po_ordnum);
    }//GEN-LAST:event_comboPOSActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBackActionPerformed
    {//GEN-HEADEREND:event_btnBackActionPerformed
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = statement.executeQuery("SELECT count(*) AS total "
                    + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                    + "WHERE " + SO_ORDNUM + " = " + so_ordnum);
            rs.next();
            int total = rs.getInt("total") + 1;

            NewSalesOrderStep2 jFrame = new NewSalesOrderStep2(desktop, so_ordnum, total);
            desktop.add(jFrame);
            MayfairStatic.setMaximum(jFrame);
            jFrame.show();
            this.dispose();
        }
        catch (Exception ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox comboPOS;
    private javax.swing.JTextField fieldQuantity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    private javax.swing.JLabel labelAvaliable;
    private javax.swing.JLabel labelDelDate;
    private javax.swing.JLabel labelOrdNum;
    private javax.swing.JLabel labelOrders;
    private javax.swing.JLabel labelQuantity;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
