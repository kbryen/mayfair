/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.purchase;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_CODE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRICE;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.POD_QUANTITY;
import static main.java.MayfairStatic.PO_ORDNUM;
import static main.java.MayfairStatic.PO_PRICE;
import static main.java.MayfairStatic.PO_TOTALUNITS;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_INORDER;
import static main.java.MayfairStatic.PRODUCT_INSTOCK;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_PURCHASEPRICE;
import static main.java.MayfairStatic.PRODUCT_SSAW;
import static main.java.MayfairStatic.PRODUCT_TOTAL;
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;

/**
 *
 * @author kian_bryen
 */
public class NewPurchaseOrderStep2 extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;
    private final String ord_num;
    private final int numOfProducts;

    public NewPurchaseOrderStep2(JDesktopPane desktop, String ord_num, int numOfProducts)
    {
        setUpGUI();
        this.desktop = desktop;
        this.ord_num = ord_num;
        this.numOfProducts = numOfProducts;
    }

    private void setUpGUI()
    {
        initComponents();
        productsTable.setAutoCreateRowSorter(true);
        productsScroll.setVisible(false);
        btnAdd.setEnabled(false);

        labelProdNum.setText(String.valueOf(numOfProducts));
        if (numOfProducts > 1)
        {
            fillLables();
            orderDetailsTable.setAutoCreateRowSorter(true);
        }
        else
        {
            orderDetailsScroll.setVisible(false);
            btnNext.setEnabled(false);
            labelOrdDetails.setVisible(false);
            labelPrice.setVisible(false);
            labelUnits.setVisible(false);
        }
    }

    private void fillLables()
    {
        updatePurchaseOrderDetails();
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_CODE + ", " + POD_QUANTITY + ", " + PRODUCT_PURCHASEPRICE + ", " + POD_PRICE + " "
                    + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                    + "JOIN " + PRODUCTS_TABLE + " "
                    + "ON " + POD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                    + "WHERE " + POD_ORDNUM + " = '" + ord_num + "'");
            MayfairStatic.fillTable(orderDetailsTable, rs);
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }

    private void updatePurchaseOrderDetails()
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
        btnAdd = new javax.swing.JButton();
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
        fieldQuant = new javax.swing.JTextField();
        labelUnits = new javax.swing.JLabel();

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("New Purchase Order");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("New Purchase Order");

        jLabel2.setText("Product Code");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddActionPerformed(evt);
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
                    .addComponent(orderDetailsScroll)
                    .addComponent(jSeparator2)
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
                                .addComponent(fieldProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFind))
                            .addComponent(labelOrdDetails))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(fieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelUnits)
                                    .addComponent(labelPrice))
                                .addGap(17, 17, 17))
                            .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING))))
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
                .addComponent(productsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(fieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelOrdDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderDetailsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelUnits)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(labelPrice)
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try
        {
            int quantToAdd = Integer.parseInt(fieldQuant.getText());
            if (quantToAdd <= 0)
            {
                throw new NumberFormatException();
            }

            int prod_num = (int) productsTable.getValueAt(productsTable.getSelectedRow(), 0);
            String prodCode = (String) productsTable.getValueAt(productsTable.getSelectedRow(), 1);
            try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet rs = selectStatement.executeQuery("SELECT " + PRODUCT_PURCHASEPRICE + ", " + PRODUCT_INORDER + " "
                        + "FROM " + PRODUCTS_TABLE + " "
                        + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num);
                rs.next();
                double purchase_price = rs.getDouble(PRODUCT_PURCHASEPRICE);
                int in_order = rs.getInt(PRODUCT_INORDER) + quantToAdd;

                String sql;
                rs = selectStatement.executeQuery("SELECT " + POD_CODE + ", " + POD_QUANTITY + ", " + POD_AVALIABLE + ", "
                        + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "WHERE " + POD_PRODNUM + " = " + prod_num + " "
                        + "AND " + POD_ORDNUM + " = '" + ord_num + "'");
                if (rs.next())
                {
                    int quantity = rs.getInt(POD_QUANTITY) + quantToAdd;
                    int avaliable = rs.getInt(POD_AVALIABLE) + quantToAdd;
                    double price = quantity * purchase_price;
                    int code = rs.getInt(POD_CODE);
                    sql = "UPDATE " + PURCHASE_ORDER_DETAILS_TABLE + " "
                            + "SET " + POD_QUANTITY + " = " + quantity + ", "
                            + POD_AVALIABLE + " = " + avaliable + ", "
                            + POD_PRICE + " = " + price + " "
                            + "WHERE " + POD_CODE + " = " + code;
                }
                else
                {
                    double price = quantToAdd * purchase_price;
                    sql = "INSERT INTO " + PURCHASE_ORDER_DETAILS_TABLE + " (" + POD_ORDNUM + ", " + POD_PRODNUM + ", " + POD_QUANTITY + ", " + POD_AVALIABLE + ", " + POD_PRICE + ") "
                            + "VALUES ('" + ord_num + "', '" + prod_num + "', '" + quantToAdd + "', '" + quantToAdd + "', '" + price + "')";
                }

                try (Statement updateStatement = MayfairStatic.getConnection().createStatement())
                {
                    updateStatement.executeUpdate(sql);
                    MayfairStatic.writeToLog(sql);

                    sql = "UPDATE " + PRODUCTS_TABLE + " "
                            + "SET " + PRODUCT_INORDER + " = " + in_order + " "
                            + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num;
                    updateStatement.executeUpdate(sql);
                    MayfairStatic.writeToLog(sql);
                }

                MayfairStatic.outputMessage(this, "Product Added", quantToAdd + " x " + prodCode + " added.", INFORMATION_MESSAGE);
                NewPurchaseOrderStep2 jFrame = new NewPurchaseOrderStep2(desktop, ord_num, numOfProducts + 1);
                desktop.add(jFrame);
                jFrame.show();
                this.dispose();
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
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (MayfairStatic.outputConfirm(this, "Complete Order", "Are you sure you want to complete this order?") == JOptionPane.YES_OPTION)
        {
            updatePurchaseOrderDetails();
            NewPurchaseOrderStep3 jFrame = new NewPurchaseOrderStep3(desktop, ord_num);
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
                    + PRODUCT_PURCHASEPRICE + ", "
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
        btnAdd.setEnabled(true);
    }//GEN-LAST:event_productsTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnNext;
    private javax.swing.JTextField fieldProdCode;
    private javax.swing.JTextField fieldQuant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
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