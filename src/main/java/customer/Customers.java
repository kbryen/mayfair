/*
 * Mayfair Stock Control.
 *
 */
package main.java.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDesktopPane;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.CUSTOMERS_TABLE;
import static main.java.MayfairStatic.CUSTOMER_BILLINGADDRESS;
import static main.java.MayfairStatic.CUSTOMER_COMMENTS;
import static main.java.MayfairStatic.CUSTOMER_CONTACT;
import static main.java.MayfairStatic.CUSTOMER_DELADDRESS;
import static main.java.MayfairStatic.CUSTOMER_DELIVERY;
import static main.java.MayfairStatic.CUSTOMER_EMAIL;
import static main.java.MayfairStatic.CUSTOMER_FAX;
import static main.java.MayfairStatic.CUSTOMER_NAME;
import static main.java.MayfairStatic.CUSTOMER_NUM;
import static main.java.MayfairStatic.CUSTOMER_PROFORMA;
import static main.java.MayfairStatic.CUSTOMER_REFERENCE;
import static main.java.MayfairStatic.CUSTOMER_TEL;

/**
 *
 * @author kian_bryen
 */
public class Customers extends javax.swing.JInternalFrame
{
    private final JDesktopPane desktop;

    public Customers(JDesktopPane desktop)
    {
        setUpGUI();
        this.desktop = desktop;
        btnFindActionPerformed(null);
    }

    private void setUpGUI()
    {
        initComponents();
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
        fieldReference = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fieldName = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        fieldNumber = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Customers");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Customers");

        jLabel2.setText("Reference :");

        jLabel3.setText("Company Name : ");

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
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String []
            {
                "Number", "Reference", "Company", "Del Address", "Delivery Instructions", "Billing Address", "Contact", "Telephone", "Fax", "Email", "Comments", "Proforma"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false, false, false, false, false
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

        jLabel4.setText("Company Number : ");

        btnEdit.setText("Edit Customer");
        btnEdit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEditActionPerformed(evt);
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

        btnAdd.setText("Add Customer");
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
                    .addComponent(jSeparator1)
                    .addComponent(scrollPane)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldReference, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClear)
                                .addGap(18, 18, 18)
                                .addComponent(btnFind))
                            .addComponent(jLabel1))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(fieldReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(fieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear)
                    .addComponent(btnFind))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnAdd))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            String sql = "SELECT " + CUSTOMER_NUM + ", "
                    + CUSTOMER_REFERENCE + ", "
                    + CUSTOMER_NAME + ", "
                    + CUSTOMER_DELADDRESS + ", "
                    + CUSTOMER_DELIVERY + ", "
                    + CUSTOMER_BILLINGADDRESS + ", "
                    + CUSTOMER_CONTACT + ", "
                    + CUSTOMER_TEL + ", "
                    + CUSTOMER_FAX + ", "
                    + CUSTOMER_EMAIL + ", "
                    + CUSTOMER_COMMENTS + ", "
                    + CUSTOMER_PROFORMA + " "
                    + "FROM " + CUSTOMERS_TABLE + " ";
            if (!fieldNumber.getText().isEmpty())
            {
                sql += "WHERE " + CUSTOMER_NUM + " = " + fieldNumber.getText() + " ";
            }
            else if (!fieldReference.getText().isEmpty())
            {
                sql += "WHERE " + CUSTOMER_NAME + " LIKE '%" + fieldReference.getText() + "%' ";
            }
            else
            {
                sql += "WHERE " + CUSTOMER_CONTACT + " LIKE '%" + fieldName.getText() + "%' ";
            }
            sql += "ORDER BY " + CUSTOMER_REFERENCE + " ASC";

            MayfairStatic.fillTable(table, statement.executeQuery(sql));
            scrollPane.setVisible(true);
            getContentPane().validate();
            getContentPane().repaint();
            btnEdit.setEnabled(false);
        }
        catch (SQLException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        fieldNumber.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
        fieldReference.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
        fieldName.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
        btnEdit.setEnabled(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try
        {
            CustomerInfo jFrame = new CustomerInfo(Integer.valueOf(fieldNumber.getText()));
            desktop.add(jFrame);
            jFrame.show();
        }
        catch (NumberFormatException ex)
        {
            MayfairStatic.outputMessage(this, ex);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        fieldName.setText("");
        fieldNumber.setText("");
        fieldReference.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        CustomerInfo jFrame = new CustomerInfo();
        desktop.add(jFrame);
        jFrame.show();
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFind;
    private javax.swing.JTextField fieldName;
    private javax.swing.JTextField fieldNumber;
    private javax.swing.JTextField fieldReference;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
