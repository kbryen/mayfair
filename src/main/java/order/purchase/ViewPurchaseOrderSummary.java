/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static javax.swing.JOptionPane.YES_OPTION;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.POD_AVALIABLE;
import static main.java.MayfairStatic.POD_ORDNUM;
import static main.java.MayfairStatic.POD_PRICE;
import static main.java.MayfairStatic.POD_PRODNUM;
import static main.java.MayfairStatic.POD_QUANTITY;
import static main.java.MayfairStatic.PO_COMMENTS;
import static main.java.MayfairStatic.PO_DELDATE;
import static main.java.MayfairStatic.PO_ORDDATE;
import static main.java.MayfairStatic.PO_ORDNUM;
import static main.java.MayfairStatic.PO_PRICE;
import static main.java.MayfairStatic.PO_SUPPNUM;
import static main.java.MayfairStatic.PO_TOTALUNITS;
import static main.java.MayfairStatic.PRODUCTS_TABLE;
import static main.java.MayfairStatic.PRODUCT_CODE;
import static main.java.MayfairStatic.PRODUCT_PRODNUM;
import static main.java.MayfairStatic.PRODUCT_PURCHASEPRICE;
import static main.java.MayfairStatic.PURCHASE_ORDER_DETAILS_TABLE;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;
import static main.java.MayfairStatic.SUPPLIERS_TABLE;
import static main.java.MayfairStatic.SUPPLIER_NAME;
import static main.java.MayfairStatic.SUPPLIER_SUPPNUM;
import main.java.PrintUtilities;

/**
 *
 * @author kian_bryen
 */
public final class ViewPurchaseOrderSummary extends javax.swing.JInternalFrame
{

    private final String ord_num;

    public ViewPurchaseOrderSummary(String ord_num)
    {
        this.ord_num = ord_num;
        setUpGui();
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
        btnClose = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        panePrint = new javax.swing.JPanel();
        labelDetails = new javax.swing.JLabel();
        jLable1 = new javax.swing.JLabel();
        labelNumber = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        jLable2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelDelDate = new javax.swing.JLabel();
        labelOrdNum = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        labelPrice = new javax.swing.JLabel();
        labelOrdDate = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        labelComments = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        fieldComments = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        labelUnits = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Purchase Order");

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCloseActionPerformed(evt);
            }
        });

        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnPrintActionPerformed(evt);
            }
        });

        labelDetails.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        labelDetails.setText("Supplier Details");

        jLable1.setText("Supplier Number:");

        labelNumber.setText("1");

        labelName.setText("1");

        jLable2.setText("Supplier Name:");

        jLabel6.setText("Order Number: ");

        labelDelDate.setText("1");

        labelOrdNum.setText("1");

        jLabel7.setText("Delivery Date: ");

        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String []
            {
                "Product", "Quantity", "Avaliable", "Unit Price", "Total Price"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false
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
        jScrollPane1.setViewportView(table);

        jLabel8.setText("Products Ordered: ");

        labelPrice.setText("Order Total: £");

        labelOrdDate.setText("1");

        jLabel10.setText("Order Date: ");

        labelComments.setText("Comments:");

        fieldComments.setColumns(20);
        fieldComments.setRows(5);
        scrollPane.setViewportView(fieldComments);

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel11.setText("Order Details");

        labelUnits.setText("Total Units:");

        javax.swing.GroupLayout panePrintLayout = new javax.swing.GroupLayout(panePrint);
        panePrint.setLayout(panePrintLayout);
        panePrintLayout.setHorizontalGroup(
            panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panePrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panePrintLayout.createSequentialGroup()
                        .addComponent(labelComments)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(panePrintLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelUnits, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
                    .addGroup(panePrintLayout.createSequentialGroup()
                        .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panePrintLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelDelDate))
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addGroup(panePrintLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelOrdNum))
                            .addGroup(panePrintLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelOrdDate)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(panePrintLayout.createSequentialGroup()
                        .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelDetails)
                            .addGroup(panePrintLayout.createSequentialGroup()
                                .addComponent(jLable1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNumber)
                                .addGap(18, 18, 18)
                                .addComponent(jLable2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelName)))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        panePrintLayout.setVerticalGroup(
            panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panePrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panePrintLayout.createSequentialGroup()
                        .addComponent(labelDetails)
                        .addGap(18, 18, 18)
                        .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLable1)
                            .addComponent(labelNumber)))
                    .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLable2)
                        .addComponent(labelName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelOrdNum))
                .addGap(13, 13, 13)
                .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(labelOrdDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelDelDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelUnits)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPrice)
                .addGap(18, 18, 18)
                .addGroup(panePrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelComments))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panePrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnPrint)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClose))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 187, Short.MAX_VALUE))
                            .addComponent(jSeparator3))))
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
                .addComponent(panePrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnPrint))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setUpGui()
    {
        initComponents();
        table.setAutoCreateRowSorter(true);
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            ResultSet rs = statement.executeQuery("SELECT " + SUPPLIER_SUPPNUM + ", "
                    + SUPPLIER_NAME + ", "
                    + PO_ORDNUM + ", "
                    + MayfairStatic.sqlDateFormat(PO_DELDATE, "%d/%m/%Y") + ", "
                    + MayfairStatic.sqlDateFormat(PO_ORDDATE, "%d/%m/%Y") + ", "
                    + PO_PRICE + ", "
                    + PO_TOTALUNITS + ", "
                    + PO_COMMENTS + " "
                    + "FROM " + PURCHASE_ORDER_TABLE + " "
                    + "JOIN " + SUPPLIERS_TABLE + " "
                    + "ON " + PO_SUPPNUM + "=" + SUPPLIER_SUPPNUM + " "
                    + "WHERE " + PO_ORDNUM + " = '" + ord_num + "'");
            rs.next();

            labelNumber.setText(rs.getString(SUPPLIER_SUPPNUM));
            labelName.setText(rs.getString(SUPPLIER_NAME));
            labelOrdNum.setText(rs.getString(PO_ORDNUM));
            labelOrdDate.setText(rs.getString(PO_ORDDATE));
            labelDelDate.setText(rs.getString(PO_DELDATE));
            labelUnits.setText("Total Units: " + rs.getString(PO_TOTALUNITS));
            labelPrice.setText("Order Total: £" + String.format("%.02f", rs.getFloat(PO_PRICE)));

            String comments = rs.getString(PO_COMMENTS);
            if (comments.isEmpty())
            {
                labelComments.setVisible(false);
                scrollPane.setVisible(false);
            }
            else
            {
                fieldComments.setText(comments);
            }

            rs = statement.executeQuery("SELECT " + PRODUCT_CODE + ", "
                    + POD_QUANTITY + ", "
                    + POD_AVALIABLE + ", "
                    + PRODUCT_PURCHASEPRICE + ", "
                    + POD_PRICE + " "
                    + "FROM " + PURCHASE_ORDER_DETAILS_TABLE + " "
                    + "JOIN " + PRODUCTS_TABLE + " "
                    + "ON " + POD_PRODNUM + "=" + PRODUCT_PRODNUM + " "
                    + "WHERE " + POD_ORDNUM + " = '" + ord_num + "'");
            MayfairStatic.fillTable(table, rs);
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        if (MayfairStatic.outputConfirm(this, "Print Summary", "Are you sure you want to print this summary?") == YES_OPTION)
        {
            PrintUtilities.printComponent(panePrint);
        }
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPrint;
    private javax.swing.JTextArea fieldComments;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLable1;
    private javax.swing.JLabel jLable2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel labelComments;
    private javax.swing.JLabel labelDelDate;
    private javax.swing.JLabel labelDetails;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelNumber;
    private javax.swing.JLabel labelOrdDate;
    private javax.swing.JLabel labelOrdNum;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelUnits;
    private javax.swing.JPanel panePrint;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
