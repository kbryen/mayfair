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
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_CODE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRICE;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.POD_QUANTITY;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_INORDER;
import static main.java.MayfairStatic.PRODUCT_INSTOCK;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_PURCHASEPRICE;
import static main.java.MayfairStatic.PRODUCT_TOTAL;
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;

/**
 *
 * @author kian_bryen
 */
public class EditPurchaseOrderStep2 extends javax.swing.JInternalFrame
{

    private final String ord_num;
    private final JDesktopPane desktop;

    public EditPurchaseOrderStep2(String ord_num, JDesktopPane pane)
    {
        setUpGUI();
        this.ord_num = ord_num;
        this.desktop = pane;
        btnFindActionPerformed(null);
    }

    private void setUpGUI()
    {
        initComponents();
        btnAdd.setEnabled(false);
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
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fieldProdCode = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        fieldQuant = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Edit Purchase Order");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Add Product");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel5.setText("Select Product");

        jLabel2.setText("Product Code");

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
                {null, null, null, null, null, null}
            },
            new String []
            {
                "Number", "Code", "Price", "In Stock", "In Order", "Total"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Object.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false
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
        table.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(90, 90, 90)
                                        .addComponent(fieldProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(btnFind)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fieldQuant, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fieldProdCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAdd)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = statement.executeQuery("SELECT " + PRODUCT_PRODNUM + ", "
                    + PRODUCT_CODE + ", "
                    + PRODUCT_PURCHASEPRICE + ", "
                    + PRODUCT_INSTOCK + ", "
                    + PRODUCT_INORDER + ", "
                    + PRODUCT_TOTAL + " "
                    + "FROM " + PRODUCTS_TABLE + " "
                    + "WHERE " + PRODUCT_CODE + " LIKE '%" + fieldProdCode.getText() + "%' "
                    + "ORDER BY " + PRODUCT_PRODNUM + " ASC");
            MayfairStatic.fillTable(table, rs);
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        fieldProdCode.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
        btnAdd.setEnabled(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try
        {
            int newQuantity = Integer.parseInt(fieldQuant.getText());
            if (newQuantity <= 0)
            {
                throw new NumberFormatException();
            }

            try (Statement selectStatement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
            {
                int prod_num = (int) table.getValueAt(table.getSelectedRow(), 0);

                ResultSet rs = selectStatement.executeQuery("SELECT " + PRODUCT_PURCHASEPRICE + ", " + PRODUCT_INORDER + " "
                        + "FROM " + PRODUCTS_TABLE + " "
                        + "WHERE " + PRODUCT_PRODNUM + " = " + prod_num);
                rs.next();
                double purchase_price = rs.getDouble(PRODUCT_PURCHASEPRICE);
                int in_order = rs.getInt(PRODUCT_INORDER) + newQuantity;

                MayfairStatic.writeToLog("ADD PRODUCT " + prod_num);
                String sql;
                rs = selectStatement.executeQuery("SELECT " + POD_CODE + ", " + POD_QUANTITY + ", " + POD_AVALIABLE + " "
                        + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                        + "WHERE " + POD_PRODNUM + " = " + prod_num + " "
                        + "AND " + POD_ORDNUM + " = '" + ord_num + "'");
                if (rs.next())
                {
                    int code = rs.getInt(POD_CODE);
                    int quantity = rs.getInt(POD_QUANTITY) + newQuantity;
                    int avaliable = rs.getInt(POD_AVALIABLE) + newQuantity;
                    double price = quantity * purchase_price;
                    sql = "UPDATE " + PURCHASE_ORDER_DETAILS_TABLE + " "
                            + "SET " + POD_QUANTITY + " = " + quantity + ", "
                            + POD_AVALIABLE + " = " + avaliable + ", "
                            + POD_PRICE + " = " + price + " "
                            + "WHERE " + POD_CODE + " = " + code;
                }
                else
                {
                    double price = newQuantity * purchase_price;
                    sql = "INSERT INTO " + PURCHASE_ORDER_DETAILS_TABLE + " (" + POD_ORDNUM + ", " + POD_PRODNUM + ", " + POD_QUANTITY + ", " + POD_AVALIABLE + ", " + POD_PRICE + ") "
                            + "VALUES ('" + ord_num + "', " + prod_num + ", " + newQuantity + ", " + newQuantity + ", " + price + ")";
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

                    MayfairStatic.outputMessage(this, "Product Added", "Product added to order " + ord_num + ".", INFORMATION_MESSAGE);
                    EditPurchaseOrderStep1 jFrame = new EditPurchaseOrderStep1(ord_num, desktop);
                    desktop.add(jFrame);
                    jFrame.show();
                    this.dispose();
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
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnFind;
    private javax.swing.JTextField fieldProdCode;
    private javax.swing.JTextField fieldQuant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
