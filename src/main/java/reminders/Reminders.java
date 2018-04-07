/*
 * Mayfair Stock Control.
 *
 */
package main.java.reminders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import main.java.Database;
import main.java.Main;

/**
 *
 * @author kian_bryen
 */
public class Reminders extends javax.swing.JInternalFrame
{

    private final Database db = new Database();
    private final DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final JDesktopPane desktop;

    public Reminders(JDesktopPane desktop)
    {
        this.desktop = desktop;
        initComponents();
        fillReminders();
        fillSalesOrders();
        fillPurchaseOrders();
    }

    private void fillReminders()
    {
        tableReminders.setAutoCreateRowSorter(true);
        try (Statement statement = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            LocalDate date = LocalDate.now();
            String fromDate = date.format(formatters);
            date = date.plusWeeks(1);
            String toDate = date.format(formatters);

            ResultSet rs = statement.executeQuery("SELECT description, DATE_FORMAT(date,'%d/%m/%Y %a') "
                    + "FROM reminders "
                    + "WHERE date >= '" + fromDate + "' AND date <= '" + toDate + "' AND completed = false "
                    + "ORDER BY date ASC");

            Main.fillTable(tableReminders, rs);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(Reminders.this, e.getMessage());
        }
    }

    private void fillSalesOrders()
    {
        tableSales.setAutoCreateRowSorter(true);
        try (Statement statement = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            LocalDate date = LocalDate.now();
            String fromDate = date.format(formatters);
            date = date.plusWeeks(1);
            String toDate = date.format(formatters);

            ResultSet rs = statement.executeQuery("SELECT sales_order.ord_num, customers.name, DATE_FORMAT(sales_order.ord_date,'%d/%m/%Y %a'), DATE_FORMAT(sales_order.del_date,'%d/%m/%Y %a'), sales_order.dispatched, DATE_FORMAT(sales_order.dispatched_date,'%d/%m/%Y %a') "
                    + "FROM sales_order "
                    + "JOIN customers ON sales_order.cust_num=customers.cust_num "
                    + "WHERE sales_order.del_date >= '" + fromDate + "' AND sales_order.del_date <= '" + toDate + "' AND sales_order.del_date and delivered = false "
                    + "ORDER BY sales_order.del_date, sales_order.ord_num DESC");

            Main.fillTable(tableSales, rs);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(Reminders.this, e.getMessage());
        }
    }

    private void fillPurchaseOrders()
    {
        tablePurchase.setAutoCreateRowSorter(true);
        try (Statement statement = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            LocalDate date = LocalDate.now();
            String fromDate = date.format(formatters);
            date = date.plusMonths(1);
            String toDate = date.format(formatters);

            ResultSet rs = statement.executeQuery("SELECT purchase_order.ord_num, suppliers.name, DATE_FORMAT(purchase_order.ord_date,'%d/%m/%Y %a'), DATE_FORMAT(purchase_order.del_date,'%d/%m/%Y %a'), purchase_order.dispatched, DATE_FORMAT(purchase_order.dispatched_date,'%d/%m/%Y %a') "
                    + "FROM purchase_order "
                    + "JOIN suppliers ON purchase_order.supp_num=suppliers.supp_num "
                    + "WHERE purchase_order.del_date >= '" + fromDate + "' AND purchase_order.del_date <= '" + toDate + "' AND purchase_order.del_date and delivered = false "
                    + "ORDER BY purchase_order.del_date, purchase_order.ord_num DESC");

            Main.fillTable(tablePurchase, rs);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(Reminders.this, e.getMessage());
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
        tableReminders.setColumnSelectionAllowed(true);
        scrollPane.setViewportView(tableReminders);
        tableReminders.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel5.setText("Upcoming Sales Orders");

        scrollPane1.setBorder(null);

        tableSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String []
            {
                "Number", "Customer", "Ordered", "Expected Delivery", "Dispatched", "Dispatched Date"
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
        scrollPane1.setViewportView(tableSales);
        tableSales.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tableSales.getColumnModel().getColumnCount() > 0)
        {
            tableSales.getColumnModel().getColumn(0).setHeaderValue("Number");
            tableSales.getColumnModel().getColumn(3).setHeaderValue("Expected Delivery");
            tableSales.getColumnModel().getColumn(4).setHeaderValue("Dispatched");
            tableSales.getColumnModel().getColumn(5).setHeaderValue("Dispatched Date");
        }

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(scrollPane1)
                    .addComponent(jSeparator3)
                    .addComponent(scrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNewReminder)))
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
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                .addComponent(btnNewReminder)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewReminderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNewReminderActionPerformed
    {//GEN-HEADEREND:event_btnNewReminderActionPerformed
        NewReminder reminder = new NewReminder(this, desktop);
        desktop.add(reminder);
        reminder.show();
    }//GEN-LAST:event_btnNewReminderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
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
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPane1;
    private javax.swing.JScrollPane scrollPane2;
    private javax.swing.JTable tablePurchase;
    private javax.swing.JTable tableReminders;
    private javax.swing.JTable tableSales;
    // End of variables declaration//GEN-END:variables
}
