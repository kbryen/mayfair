/*
 * Mayfair Stock Control.
 *
 */
package main.java;

import main.java.product.Products;
import java.beans.PropertyVetoException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.customer.Customers;
import main.java.report.Reports;

/**
 * 
 * @author kian_bryen
 */
public class Main extends javax.swing.JFrame
{
    public static String formatDate(String datetime)
    {
        LocalDateTime localDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        return localDateTime.format(DateTimeFormatter.ofPattern("E dd MMM yyyy"));
    }

    public Main()
    {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
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

        panelSelection = new javax.swing.JPanel();
        btnNewSO = new javax.swing.JButton();
        btnCurrentSO = new javax.swing.JButton();
        btnNewPO = new javax.swing.JButton();
        btnCurrentPO = new javax.swing.JButton();
        btnProducts = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        btnCustomers = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        btnPastSO = new javax.swing.JButton();
        btnPastPO = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        desktop = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mayfair Trunk Company Ltd.");

        panelSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnNewSO.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        btnNewSO.setText("New Order");
        btnNewSO.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnNewSOActionPerformed(evt);
            }
        });

        btnCurrentSO.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        btnCurrentSO.setText("Current");
        btnCurrentSO.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCurrentSOActionPerformed(evt);
            }
        });

        btnNewPO.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        btnNewPO.setText("New Order");
        btnNewPO.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnNewPOActionPerformed(evt);
            }
        });

        btnCurrentPO.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        btnCurrentPO.setText("Current");
        btnCurrentPO.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCurrentPOActionPerformed(evt);
            }
        });

        btnProducts.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnProducts.setText("Products");
        btnProducts.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnProductsActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Sales Orders");

        btnCustomers.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnCustomers.setText("Customers");
        btnCustomers.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCustomersActionPerformed(evt);
            }
        });

        btnPastSO.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        btnPastSO.setText("Past");
        btnPastSO.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnPastSOActionPerformed(evt);
            }
        });

        btnPastPO.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        btnPastPO.setText("Past");
        btnPastPO.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnPastPOActionPerformed(evt);
            }
        });

        btnReports.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnReports.setText("Reports");
        btnReports.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnReportsActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Purchase Orders");

        javax.swing.GroupLayout panelSelectionLayout = new javax.swing.GroupLayout(panelSelection);
        panelSelection.setLayout(panelSelectionLayout);
        panelSelectionLayout.setHorizontalGroup(
            panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNewSO, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCurrentSO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPastSO, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addComponent(btnProducts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator8)
                    .addComponent(jSeparator9)
                    .addComponent(btnCustomers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator10)
                    .addComponent(btnPastPO, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnNewPO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCurrentPO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator11))
                .addContainerGap())
        );
        panelSelectionLayout.setVerticalGroup(
            panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectionLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNewSO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCurrentSO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPastSO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNewPO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCurrentPO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPastPO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProducts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCustomers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReports)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        getContentPane().add(panelSelection, java.awt.BorderLayout.LINE_START);

        desktop.setBackground(new java.awt.Color(0, 153, 255));
        desktop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1030, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 656, Short.MAX_VALUE)
        );

        getContentPane().add(desktop, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewSOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSOActionPerformed
        main.java.order.sales.CustomerNumber salesOrder = new main.java.order.sales.CustomerNumber(desktop);
        desktop.add(salesOrder);
        try
        {
            salesOrder.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        salesOrder.show();
    }//GEN-LAST:event_btnNewSOActionPerformed

    private void btnCurrentSOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCurrentSOActionPerformed
        main.java.order.sales.Current salesOrder = new main.java.order.sales.Current(desktop);
        desktop.add(salesOrder);
        try
        {
            salesOrder.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        salesOrder.show();
    }//GEN-LAST:event_btnCurrentSOActionPerformed

    private void btnNewPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPOActionPerformed
        main.java.order.purchase.OrderNumber newPO = new main.java.order.purchase.OrderNumber(desktop);
        desktop.add(newPO);
        newPO.show();
    }//GEN-LAST:event_btnNewPOActionPerformed

    private void btnCurrentPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCurrentPOActionPerformed
        main.java.order.purchase.Current purchaseOrder = new main.java.order.purchase.Current(desktop);
        desktop.add(purchaseOrder);
        try
        {
            purchaseOrder.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        purchaseOrder.show();
    }//GEN-LAST:event_btnCurrentPOActionPerformed

    private void btnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
        Products products = new Products(desktop);
        desktop.add(products);
        try
        {
            products.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        products.show();
    }//GEN-LAST:event_btnProductsActionPerformed

    private void btnCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomersActionPerformed
        Customers customers = new Customers(desktop);
        desktop.add(customers);
        try
        {
            customers.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        customers.show();
    }//GEN-LAST:event_btnCustomersActionPerformed

    private void btnPastSOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastSOActionPerformed
        main.java.order.sales.Past salesOrder = new main.java.order.sales.Past(desktop);
        desktop.add(salesOrder);
        try
        {
            salesOrder.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        salesOrder.show();
    }//GEN-LAST:event_btnPastSOActionPerformed

    private void btnPastPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastPOActionPerformed
        main.java.order.purchase.Past purchaseOrder = new main.java.order.purchase.Past(desktop);
        desktop.add(purchaseOrder);
        try
        {
            purchaseOrder.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        purchaseOrder.show();
    }//GEN-LAST:event_btnPastPOActionPerformed

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed
        Reports reports = new Reports(desktop);
        desktop.add(reports);
        reports.show();
    }//GEN-LAST:event_btnReportsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() ->
        {
            new Main().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCurrentPO;
    private javax.swing.JButton btnCurrentSO;
    private javax.swing.JButton btnCustomers;
    private javax.swing.JButton btnNewPO;
    private javax.swing.JButton btnNewSO;
    private javax.swing.JButton btnPastPO;
    private javax.swing.JButton btnPastSO;
    private javax.swing.JButton btnProducts;
    private javax.swing.JButton btnReports;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel panelSelection;
    // End of variables declaration//GEN-END:variables

}
