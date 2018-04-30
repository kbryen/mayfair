/*
 * Mayfair Stock Control.
 *
 */
package main.java.reminders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JDesktopPane;
import main.java.MayfairStatic;
import static main.java.MayfairStatic.CAL_DATE_FORMAT;
import static main.java.MayfairStatic.CUSTOMERS_TABLE;
import static main.java.MayfairStatic.CUSTOMER_CUSTNUM;
import static main.java.MayfairStatic.CUSTOMER_NAME;
import static main.java.MayfairStatic.PO_DELDATE;
import static main.java.MayfairStatic.PO_DELIVERED;
import static main.java.MayfairStatic.PO_DISPATCHDATE;
import static main.java.MayfairStatic.PO_DISPATCHED;
import static main.java.MayfairStatic.PO_ORDDATE;
import static main.java.MayfairStatic.PO_ORDNUM;
import static main.java.MayfairStatic.PO_SUPPNUM;
import static main.java.MayfairStatic.PURCHASE_ORDER_TABLE;
import static main.java.MayfairStatic.REMINDERS_TABLE;
import static main.java.MayfairStatic.REMINDER_COMPLETED;
import static main.java.MayfairStatic.REMINDER_DATE;
import static main.java.MayfairStatic.REMINDER_DESCRIPTION;
import static main.java.MayfairStatic.SALES_ORDER_TABLE;
import static main.java.MayfairStatic.SO_CUSTNUM;
import static main.java.MayfairStatic.SO_DELDATE;
import static main.java.MayfairStatic.SO_DELIVERED;
import static main.java.MayfairStatic.SO_DISPATCHDATE;
import static main.java.MayfairStatic.SO_DISPATCHED;
import static main.java.MayfairStatic.SO_ORDDATE;
import static main.java.MayfairStatic.SO_ORDNUM;
import static main.java.MayfairStatic.SO_TOTALUNITS;
import static main.java.MayfairStatic.SUPPLIERS_TABLE;
import static main.java.MayfairStatic.SUPPLIER_NAME;
import static main.java.MayfairStatic.SUPPLIER_SUPPNUM;

/**
 *
 * @author kian_bryen
 */
public class Reminders extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;

    public Reminders(JDesktopPane desktop)
    {
        this.desktop = desktop;
        setUpGUI();
    }

    private void setUpGUI()
    {
        initComponents();
        fillReminders();
        fillSalesOrders();
        fillPurchaseOrders();
    }

    private void fillReminders()
    {
        tableReminders.setAutoCreateRowSorter(true);
        MayfairStatic.addDateSorter(tableReminders, 1);
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            LocalDate date = LocalDate.now();
            String startDate = CAL_DATE_FORMAT.format(date);
            date = date.plusWeeks(1);
            String endDate = CAL_DATE_FORMAT.format(date);

            ResultSet rs = statement.executeQuery("SELECT " + REMINDER_DESCRIPTION + ", "
                    + MayfairStatic.sqlDateFormat(REMINDER_DATE) + " "
                    + "FROM " + REMINDERS_TABLE + " "
                    + "WHERE DATE(" + REMINDER_DATE + ") BETWEEN '" + startDate + "' AND '" + endDate + "' "
                    + "AND " + REMINDER_COMPLETED + " = false "
                    + "ORDER BY " + REMINDER_DATE + " ASC");

            MayfairStatic.fillTable(tableReminders, rs);
        }
        catch (Exception ex)
        {
            MayfairStatic.outputMessage(desktop, ex);
        }
    }

    private void fillSalesOrders()
    {
        tableSales.setAutoCreateRowSorter(true);
        MayfairStatic.addDateSorter(tableReminders, new int[]
        {
            2, 3, 6
        });
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            String toDate = CAL_DATE_FORMAT.format(LocalDate.now().plusWeeks(1));

            ResultSet rs = statement.executeQuery("SELECT " + SO_ORDNUM + ", "
                    + CUSTOMER_NAME + ", "
                    + MayfairStatic.sqlDateFormat(SO_ORDDATE) + ", "
                    + MayfairStatic.sqlDateFormat(SO_DELDATE) + ", "
                    + SO_TOTALUNITS + ", "
                    + SO_DISPATCHED + ", "
                    + MayfairStatic.sqlDateFormat(SO_DISPATCHDATE) + " "
                    + "FROM " + SALES_ORDER_TABLE + " "
                    + "JOIN " + CUSTOMERS_TABLE + " "
                    + "ON " + SO_CUSTNUM + "=" + CUSTOMER_CUSTNUM + " "
                    + "WHERE " + SO_DELDATE + " <= '" + toDate + "' "
                    + "AND  " + SO_DELIVERED + " = false "
                    + "ORDER BY " + SO_DELDATE + ", " + SO_ORDNUM + " DESC");
            MayfairStatic.fillTable(tableSales, rs);

            rs = statement.executeQuery("SELECT SUM(" + SO_TOTALUNITS + ") AS weekly_total_units "
                    + "FROM " + SALES_ORDER_TABLE + " "
                    + "WHERE " + SO_DELDATE + " <= '" + toDate + "' "
                    + "AND " + SO_DELIVERED + " = false ");
            if (rs.next())
            {
                labelTotalUnits.setText(rs.getString("weekly_total_units"));
            }

        }
        catch (Exception ex)
        {
            MayfairStatic.outputMessage(desktop, ex);
        }
    }

    private void fillPurchaseOrders()
    {
        tablePurchase.setAutoCreateRowSorter(true);
        MayfairStatic.addDateSorter(tableReminders, new int[]
        {
            2, 3, 5
        });
        try (Statement statement = MayfairStatic.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            LocalDate date = LocalDate.now().plusMonths(1);
            String toDate = CAL_DATE_FORMAT.format(date);

            ResultSet rs = statement.executeQuery("SELECT " + PO_ORDNUM + ", "
                    + SUPPLIER_NAME + ", "
                    + MayfairStatic.sqlDateFormat(PO_ORDDATE) + ", "
                    + MayfairStatic.sqlDateFormat(PO_DELDATE) + ", "
                    + PO_DISPATCHED + ", "
                    + MayfairStatic.sqlDateFormat(PO_DISPATCHDATE) + " "
                    + "FROM " + PURCHASE_ORDER_TABLE + " "
                    + "JOIN " + SUPPLIERS_TABLE + " "
                    + "ON " + PO_SUPPNUM + "=" + SUPPLIER_SUPPNUM + " "
                    + "WHERE " + PO_DELDATE + " <= '" + toDate + "' "
                    + "AND " + PO_DELIVERED + " = false "
                    + "ORDER BY " + PO_DELDATE + ", " + PO_ORDNUM + " DESC");

            MayfairStatic.fillTable(tablePurchase, rs);
        }
        catch (Exception ex)
        {
            MayfairStatic.outputMessage(desktop, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        tableReminders = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        scrollPane1 = new javax.swing.JScrollPane();
        tableSales = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        scrollPane2 = new javax.swing.JScrollPane();
        tablePurchase = new javax.swing.JTable();
        jSeparator4 = new javax.swing.JSeparator();
        btnNewReminder = new javax.swing.JButton();
        labelOrderTotal1 = new javax.swing.JLabel();
        labelTotalUnits = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reminders");
        setPreferredSize(new java.awt.Dimension(630, 720));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Reminders");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel4.setText("Active Reminders");

        scrollPane.setBorder(null);

        tableReminders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String []
            {
                "Description", "Date"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false
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
        scrollPane.setViewportView(tableReminders);
        tableReminders.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel5.setText("Upcoming Sales Orders");

        scrollPane1.setBorder(null);

        tableSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String []
            {
                "Number", "Customer", "Ordered", "Expected Delivery", "Total Units", "Dispatched", "Dispatched Date"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.String.class
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
        tableSales.setRowSelectionAllowed(true);
        scrollPane1.setViewportView(tableSales);
        tableSales.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel6.setText("Incoming Purchase Orders");

        scrollPane2.setBorder(null);

        tablePurchase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String []
            {
                "Number", "Supplier", "Ordered", "Expected Delivery", "Dispatched", "Dispatched Date"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class
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
        scrollPane2.setViewportView(tablePurchase);
        tablePurchase.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tablePurchase.getColumnModel().getColumnCount() > 0)
        {
            tablePurchase.getColumnModel().getColumn(0).setHeaderValue("Number");
            tablePurchase.getColumnModel().getColumn(3).setHeaderValue("Expected Delivery");
            tablePurchase.getColumnModel().getColumn(4).setHeaderValue("Dispatched");
            tablePurchase.getColumnModel().getColumn(5).setHeaderValue("Dispatched Date");
        }

        btnNewReminder.setText("New Reminder");
        btnNewReminder.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnNewReminderActionPerformed(evt);
            }
        });

        labelOrderTotal1.setText("Weekly Total Units:");

        labelTotalUnits.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(scrollPane1)
                    .addComponent(jSeparator3)
                    .addComponent(scrollPane2)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(labelOrderTotal1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTotalUnits))
                            .addComponent(btnNewReminder, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTotalUnits)
                    .addComponent(labelOrderTotal1))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(btnNewReminder)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewReminderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNewReminderActionPerformed
    {//GEN-HEADEREND:event_btnNewReminderActionPerformed
        NewReminder jFrame = new NewReminder(desktop);
        desktop.add(jFrame);
        jFrame.show();
    }//GEN-LAST:event_btnNewReminderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewReminder;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel labelOrderTotal1;
    private javax.swing.JLabel labelTotalUnits;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPane1;
    private javax.swing.JScrollPane scrollPane2;
    private javax.swing.JTable tablePurchase;
    private javax.swing.JTable tableReminders;
    private javax.swing.JTable tableSales;
    // End of variables declaration//GEN-END:variables
}
