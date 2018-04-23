/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.sales;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.PO_DELDATE;
import static main.java.MayfairStatic.PO_DELIVERED;
import static main.java.MayfairStatic.PO_ORDNUM;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_INORDER;
import static main.java.MayfairStatic.PRODUCT_INSTOCK;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_SALESPRICE;
import static main.java.MayfairStatic.PRODUCT_SSAW;
import static main.java.MayfairStatic.PRODUCT_TOTAL;
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SOD_CODE;
import static main.java.MayfairStatic.SOD_FROMORDER;
import static main.java.MayfairStatic.SOD_FROMSTOCK;
import static main.java.MayfairStatic.SOD_ORDNUM;
import static main.java.MayfairStatic.SOD_PRICE;
import static main.java.MayfairStatic.SOD_PRODNUM;
import static main.java.MayfairStatic.SOD_QUANTITY;
import static main.java.MayfairStatic.SO_DELDATE;
import static main.java.MayfairStatic.SO_ORDNUM;
import static main.java.MayfairStatic.SO_PRICE;
import static main.java.MayfairStatic.SO_TOTALUNITS;

/**
 *
 * @author kian_bryen
 */
public class NewSalesOrderStep2 extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;
    private final int ord_num;
    private final int numOfProducts;

    public NewSalesOrderStep2(JDesktopPane desktop, int orderNum, int numOfProducts)
    {
        setUpGUI();
        this.desktop = desktop;
        this.ord_num = orderNum;
        this.numOfProducts = numOfProducts;
    }

    private void setUpGUI()
    {
        initComponents();
        productsTable.setAutoCreateRowSorter(true);
        productsScroll.setVisible(false);
        btnAddPO.setEnabled(false);
        btnAddStock.setEnabled(false);

        labelProdNum.setText(String.valueOf(numOfProducts));
        if (numOfProducts > 1)
        {
            btnClose.setText("Save");
            fillLables();
            orderDetailsTable.setAutoCreateRowSorter(true);
        }
        else
        {
            btnClose.setText("Cancel");
            btnNext.setEnabled(false);
            labelOrdDetails.setVisible(false);
            labelPrice.setVisible(false);
            labelUnits.setVisible(false);
            orderDetailsScroll.setVisible(false);
        }
    }

    private void fillLables()
    {
        updateSalesOrderDetails();
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_CODE + ", " + SOD_QUANTITY + ", " + PRODUCT_SALESPRICE + ", " + SOD_PRICE + " "
                    + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                    + "JOIN " + PRODUCTS_TABLE + " "
                    + "ON " + SOD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                    + "WHERE " + SOD_ORDNUM + " = '" + ord_num + "'");
            MayfairStatic.fillTable(orderDetailsTable, rs);
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }

    private void updateSalesOrderDetails()
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
        catch (SQLException ex)
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
        jLabel2 = new javax.swing.JLabel();
        fieldProdCode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fieldStockQuant = new javax.swing.JTextField();
        btnAddPO = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        labelProdNum = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnFind = new javax.swing.JButton();
        productsScroll = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        orderDetailsScroll = new javax.swing.JScrollPane();
        orderDetailsTable = new javax.swing.JTable();
        labelPrice = new javax.swing.JLabel();
        labelOrdDetails = new javax.swing.JLabel();
        fieldPOQuant = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnAddStock = new javax.swing.JButton();
        labelUnits = new javax.swing.JLabel();

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("New Sales Order");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("New Sales Order");

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

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnNextActionPerformed(evt);
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

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        productsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        productsTable.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                productsTableMouseClicked(evt);
            }
        });
        productsScroll.setViewportView(productsTable);
        productsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        orderDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        orderDetailsScroll.setViewportView(orderDetailsTable);

        labelPrice.setText("Order Total: £");

        labelOrdDetails.setText("Order Details:");

        jLabel4.setText("Take from Stock");

        jLabel6.setText("Take from PO");

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCloseActionPerformed(evt);
            }
        });

        btnAddStock.setText("Add");
        btnAddStock.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddStockActionPerformed(evt);
            }
        });

        labelUnits.setText("Total Units:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productsScroll, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addComponent(orderDetailsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
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
                                .addComponent(btnClose)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext))
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
                                        .addComponent(btnAddStock))))
                            .addComponent(labelPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelUnits, javax.swing.GroupLayout.Alignment.TRAILING))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelOrdDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderDetailsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelPrice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelUnits)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnClose))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPOActionPerformed
        try
        {
            int quantToAdd = Integer.parseInt(fieldPOQuant.getText());
            if (quantToAdd <= 0)
            {
                throw new NumberFormatException();
            }

            int prod_num = (int) productsTable.getValueAt(productsTable.getSelectedRow(), 0);
            try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_INORDER + " "
                        + "FROM " + PRODUCTS_TABLE + " "
                        + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num);
                rs.next();
                int in_order = rs.getInt(PRODUCT_INORDER);

                if (quantToAdd <= in_order)
                {
                    // Order Num -> Avaliable + Date
                    Map<String, Pair<Integer, Date>> possiblePurchaseOrders = new HashMap();

                    rs = statement.executeQuery("SELECT " + PO_ORDNUM + ", " + PO_DELDATE + ", " + POD_AVALIABLE + " "
                            + "FROM " + PURCHASE_ORDER_TABLE + " "
                            + "JOIN " + PURCHASE_ORDER_DETAILS_TABLE + " "
                            + "ON " + PO_ORDNUM + "=" + POD_ORDNUM + " "
                            + "WHERE " + POD_PRODNUM + " = " + prod_num + " "
                            + "AND " + PO_DELIVERED + " = false "
                            + "AND " + POD_AVALIABLE + " > 0");
                    while (rs.next())
                    {
                        possiblePurchaseOrders.put(rs.getString(PO_ORDNUM), new Pair(rs.getInt(POD_AVALIABLE), rs.getDate(PO_DELDATE)));
                    }

                    PickPurchaseOrders jFrame = new PickPurchaseOrders(desktop, possiblePurchaseOrders, quantToAdd, prod_num, ord_num, numOfProducts);
                    desktop.add(jFrame);
                    jFrame.show();
                    this.dispose();
                }
                else
                {
                    MayfairStatic.outputMessage(this, "Invalid quantity", "Not enough avaliable on purchase orders, please reduce quantity or take from stock.", WARNING_MESSAGE);
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
    }//GEN-LAST:event_btnAddPOActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (MayfairStatic.outputConfirm(this, "Complete Order", "Are you sure you want to complete this order?") == JOptionPane.YES_OPTION)
        {
            updateSalesOrderDetails();
            NewSalesOrderStep3 jFrame = new NewSalesOrderStep3(desktop, ord_num);
            desktop.add(jFrame);
            jFrame.show();
            this.dispose();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        MayfairStatic.setMaximum(this);
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", "
                    + PRODUCT_CODE + ", "
                    + PRODUCT_SALESPRICE + ", "
                    + PRODUCT_INSTOCK + ", "
                    + PRODUCT_INORDER + ", "
                    + PRODUCT_TOTAL + ", "
                    + PRODUCT_SSAW + " "
                    + "FROM " + PRODUCTS_TABLE + " "
                    + "WHERE " + PRODUCT_CODE + " LIKE '%" + fieldProdCode.getText() + "%' "
                    + "ORDER BY " + PRODUCT_CODE + " ASC");
            MayfairStatic.fillTable(productsTable, rs);
            productsScroll.setVisible(true);
            getContentPane().validate();
            getContentPane().repaint();
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void productsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productsTableMouseClicked
        fieldProdCode.setText(String.valueOf(productsTable.getValueAt(productsTable.getSelectedRow(), 1)));
        btnAddPO.setEnabled(true);
        btnAddStock.setEnabled(true);
    }//GEN-LAST:event_productsTableMouseClicked

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCloseActionPerformed
    {//GEN-HEADEREND:event_btnCloseActionPerformed
        if (MayfairStatic.outputConfirm(this, "Close Order", "Are you sure you want to close and " + btnClose.getText().toLowerCase() + " this order?") == JOptionPane.YES_OPTION)
        {
            try (Statement statement = MayfairStatic.getConnection().createStatement())
            {
                String sql;
                String action;
                if (numOfProducts > 1)
                {
                    sql = "UPDATE " + SALES_ORDER_TABLE + " "
                            + "SET " + SO_DELDATE + " = CURRENT_TIMESTAMP "
                            + "WHERE " + SO_ORDNUM + " = " + ord_num;
                    action = "Saved";
                }
                else
                {
                    sql = "DELETE FROM " + SALES_ORDER_TABLE + " "
                            + "WHERE " + SO_ORDNUM + " = " + ord_num;
                    action = "Cancelled";
                }
                statement.executeUpdate(sql);
                MayfairStatic.writeToLog(sql);
                MayfairStatic.writeToLog("ORDER " + action.toUpperCase());
                MayfairStatic.writeToLog(MayfairStatic.LOG_SEPERATOR);
                MayfairStatic.outputMessage(this, "Order " + action, "Order " + ord_num + " successfully " + action.toLowerCase() + ".", INFORMATION_MESSAGE);
                this.dispose();
            }
            catch (SQLException ex)
            {
                MayfairStatic.outputMessage(this, ex);
            }
        }

    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnAddStockActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddStockActionPerformed
    {//GEN-HEADEREND:event_btnAddStockActionPerformed
        try
        {
            int quantToAdd = Integer.parseInt(fieldStockQuant.getText());
            if (quantToAdd <= 0)
            {
                throw new NumberFormatException();
            }

            int prod_num = (int) productsTable.getValueAt(productsTable.getSelectedRow(), 0);
            String prodCode = (String) productsTable.getValueAt(productsTable.getSelectedRow(), 1);
            try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = selectStatement.executeQuery("SELECT " + PRODUCT_SALESPRICE + ", " + PRODUCT_INSTOCK + " "
                        + "FROM " + PRODUCTS_TABLE + " "
                        + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num);
                rs.next();
                double sales_price = rs.getDouble(PRODUCT_SALESPRICE);
                int in_stock = rs.getInt(PRODUCT_INSTOCK) - quantToAdd;

                if (in_stock >= 0)
                {
                    try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
                    {
                        // UPDATE PRODUCTS
                        String sql = "UPDATE " + PRODUCTS_TABLE + " "
                                + "SET " + PRODUCT_INSTOCK + " = " + in_stock + " "
                                + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num;
                        updateStatement.executeUpdate(sql);
                        MayfairStatic.writeToLog(sql);

                        // UPDATE SALES ORDER DETAILS
                        rs = selectStatement.executeQuery("SELECT " + SOD_CODE + ", " + SOD_QUANTITY + ", " + SOD_FROMSTOCK + " "
                                + "FROM " + SALES_ORDER_DETAILS_TABLE + " "
                                + "WHERE " + SOD_PRODNUM + " = " + prod_num + " "
                                + "AND " + SOD_ORDNUM + " = " + ord_num);
                        if (rs.next())
                        {
                            int code = rs.getInt(SOD_CODE);
                            int quantity = rs.getInt(SOD_QUANTITY) + quantToAdd;
                            int fromStock = rs.getInt(SOD_FROMSTOCK) + quantToAdd;
                            double price = quantity * sales_price;
                            sql = "UPDATE " + SALES_ORDER_DETAILS_TABLE + " "
                                    + "SET " + SOD_QUANTITY + " = " + quantity + ", "
                                    + SOD_PRICE + " = " + price + ", "
                                    + SOD_FROMSTOCK + " = " + fromStock + " "
                                    + "WHERE " + SOD_CODE + " = " + code;
                        }
                        else
                        {
                            double price = quantToAdd * sales_price;
                            sql = "INSERT INTO " + SALES_ORDER_DETAILS_TABLE + " (" + SOD_ORDNUM + ", " + SOD_PRODNUM + ", " + SOD_QUANTITY + ", " + SOD_PRICE + ", " + SOD_FROMSTOCK + ", " + SOD_FROMORDER + ") "
                                    + "VALUES (" + ord_num + ", " + prod_num + ", " + quantToAdd + ", " + price + ", " + quantToAdd + ", 0)";
                        }
                        updateStatement.executeUpdate(sql);
                        MayfairStatic.writeToLog(sql);

                        MayfairStatic.outputMessage(this, "Product Added", quantToAdd + " x " + prodCode + " added.", INFORMATION_MESSAGE);
                        NewSalesOrderStep2 jFrame = new NewSalesOrderStep2(desktop, ord_num, numOfProducts + 1);
                        desktop.add(jFrame);
                        jFrame.show();
                        this.dispose();
                    }
                }
                else
                {
                    MayfairStatic.outputMessage(this, "Invalid quantity", "Not enough avaliable in stock, please reduce quantity or take from purchase order.", WARNING_MESSAGE);
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
    }//GEN-LAST:event_btnAddStockActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPO;
    private javax.swing.JButton btnAddStock;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnNext;
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
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelProdNum;
    private javax.swing.JLabel labelUnits;
    private javax.swing.JScrollPane orderDetailsScroll;
    private javax.swing.JTable orderDetailsTable;
    private javax.swing.JScrollPane productsScroll;
    private javax.swing.JTable productsTable;
    // End of variables declaration//GEN-END:variables
}
