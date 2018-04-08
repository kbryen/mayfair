/*
 * Mayfair Stock Control.
 *
 */
package main.java.order.sales;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.java.Database;
import main.java.MayfairConstants;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 *
 * @author kian_bryen
 */
public class Past extends javax.swing.JInternalFrame
{

    private final JDesktopPane desktop;
    private final Database db = new Database();
    private String sql;

    public Past(JDesktopPane pane)
    {
        initComponents();
        this.setTitle("Past Sales Orders");

        table.getColumnModel().getColumn(1).setHeaderValue("Customer");
        labelName.setText("Customer Name");
        btnMarkUndelivered.setEnabled(false);

        desktop = pane;
        scrollPane.setVisible(false);
        btnViewSummary.setEnabled(false);
        btnExcelSummary.setVisible(false);
        btnFindActionPerformed(null);
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
        labelName = new javax.swing.JLabel();
        labelOR = new javax.swing.JLabel();
        fieldName = new javax.swing.JTextField();
        btnViewSummary = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        fieldOrderNumber = new javax.swing.JTextField();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnMarkUndelivered = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnClear = new javax.swing.JButton();
        btnExcelSummary = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Past Sales Orders");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setText("Past Sales Orders");

        labelName.setText("Customer Name");

        labelOR.setText("OR");

        btnViewSummary.setBackground(new java.awt.Color(153, 204, 255));
        btnViewSummary.setText("View Summary");
        btnViewSummary.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnViewSummaryActionPerformed(evt);
            }
        });

        btnFind.setText("Find");
        btnFind.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFindActionPerformed(evt);
            }
        });

        fieldOrderNumber.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                fieldOrderNumberActionPerformed(evt);
            }
        });

        scrollPane.setBorder(null);

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
                "Number", "Customer", "Order Date", "Delivered", "Total (£)"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
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
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        table.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        btnMarkUndelivered.setText("Mark Undelivered");
        btnMarkUndelivered.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnMarkUndeliveredActionPerformed(evt);
            }
        });

        jLabel3.setText("Order Number");

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnClearActionPerformed(evt);
            }
        });

        btnExcelSummary.setText("Create Summary File");
        btnExcelSummary.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnExcelSummaryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnMarkUndelivered)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnViewSummary))
                                    .addComponent(btnExcelSummary, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(fieldOrderNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelOR)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelName)
                                        .addGap(18, 18, 18)
                                        .addComponent(fieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnFind)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnClear)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fieldOrderNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelOR)
                    .addComponent(labelName)
                    .addComponent(btnClear)
                    .addComponent(btnFind))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcelSummary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewSummary)
                    .addComponent(btnMarkUndelivered))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSummaryActionPerformed
        ViewSalesSummary salesOrder = new ViewSalesSummary(Integer.parseInt(fieldOrderNumber.getText()));
        desktop.add(salesOrder);
        salesOrder.show();
    }//GEN-LAST:event_btnViewSummaryActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        Connection con = db.getConnection();
        try
        {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            if (!fieldOrderNumber.getText().equals(""))
            {
                rs = statement.executeQuery("SELECT sales_order.ord_num, customers.name, DATE_FORMAT(sales_order.ord_date,'%d/%m/%Y %a'), DATE_FORMAT(sales_order.del_date,'%d/%m/%Y %a'), sales_order.price FROM sales_order INNER JOIN customers ON sales_order.cust_num=customers.cust_num WHERE sales_order.ord_num LIKE '%" + fieldOrderNumber.getText() + "%' and delivered = true ORDER BY sales_order.del_date DESC");
            }
            else
            {
                rs = statement.executeQuery("SELECT sales_order.ord_num, customers.name, DATE_FORMAT(sales_order.ord_date,'%d/%m/%Y %a'), DATE_FORMAT(sales_order.del_date,'%d/%m/%Y %a'), sales_order.price FROM sales_order INNER JOIN customers ON sales_order.cust_num=customers.cust_num WHERE customers.name LIKE '%" + fieldName.getText() + "%' and delivered = true ORDER BY sales_order.del_date DESC");
            }

            scrollPane.setVisible(true);
            getContentPane().validate();
            getContentPane().repaint();
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

            btnExcelSummary.setVisible(false);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(Past.this, e.getMessage());
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (Exception e)
            { /* ignored */ }
        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void fieldOrderNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldOrderNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldOrderNumberActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        fieldOrderNumber.setText(String.valueOf((int) table.getValueAt(table.getSelectedRow(), 0)));
        fieldName.setText((String) table.getValueAt(table.getSelectedRow(), 1));
        btnViewSummary.setEnabled(true);
        btnMarkUndelivered.setEnabled(true);
        btnExcelSummary.setVisible(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btnMarkUndeliveredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkUndeliveredActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to Mark as Undelivered?", "Cancel order", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            Connection con = db.getConnection();
            try
            {
                db.writeToLog("MARK SALES ORDER UNDELIVERED " + fieldOrderNumber.getText());
                Statement statement = con.createStatement();
                sql = "UPDATE sales_order SET delivered = false WHERE ord_num = " + Integer.parseInt(fieldOrderNumber.getText());
                statement.executeUpdate(sql);
                db.writeToLog(sql);
                db.writeToLog(MayfairConstants.LOG_SEPERATOR);

                fieldOrderNumber.setText("");
                fieldName.setText("");
                btnFindActionPerformed(null);
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(Past.this, e.getMessage());
            }
            finally
            {
                try
                {
                    con.close();
                }
                catch (Exception e)
                { /* ignored */ }
            }
        }
    }//GEN-LAST:event_btnMarkUndeliveredActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        fieldName.setText("");
        fieldOrderNumber.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnExcelSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelSummaryActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to Create an Excel Version of the Order?", "Excel Summary", JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION)
        {
            int ord_num = (int) table.getValueAt(table.getSelectedRow(), 0);
            int cust_num;
            String name;
            String del_date;
            double price;

            // SQL
            Connection con = db.getConnection();
            Statement statement;
            try
            {
                statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = statement.executeQuery("SELECT cust_num, del_date, price FROM sales_order WHERE ord_num = " + ord_num);
                rs.next();
                cust_num = rs.getInt("cust_num");
                String[] date = rs.getString("del_date").split(" ");
                del_date = date[0];

                price = rs.getDouble("price");

                rs = statement.executeQuery("SELECT name, del_address FROM customers WHERE cust_num = " + cust_num);
                rs.next();
                name = rs.getString("name");

                FileOutputStream fileOut;
                fileOut = new FileOutputStream("S:/SALES ORDERS/SALES ORDER - " + ord_num + ".xls");

                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet worksheet = wb.createSheet(String.valueOf(ord_num));
                CellStyle editableStyle = wb.createCellStyle();
                editableStyle.setLocked(false);

                HSSFRow row = worksheet.createRow((short) 0);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("Order Number");
                cell = row.createCell(1);
                cell.setCellValue(ord_num);

                row = worksheet.createRow((short) 1);
                cell = row.createCell(0);
                cell.setCellValue("Delivery Date");
                cell = row.createCell(1);
                cell.setCellValue(del_date);

                row = worksheet.createRow((short) 3);
                cell = row.createCell(0);
                cell.setCellValue("Customer Number");
                cell = row.createCell(1);
                cell.setCellValue(cust_num);

                row = worksheet.createRow((short) 4);
                cell = row.createCell(0);
                cell.setCellValue("Customer Name");
                cell = row.createCell(1);
                cell.setCellValue(name);

                row = worksheet.createRow((short) 6);
                cell = row.createCell(0);
                cell.setCellValue("Product Code");
                cell = row.createCell(1);
                cell.setCellValue("Quantity");

                int i = 7;
                rs = statement.executeQuery("SELECT products.code, sales_order_details.quantity FROM sales_order_details JOIN products ON sales_order_details.prod_num=products.prod_num WHERE sales_order_details.ord_num = " + ord_num);
                while (rs.next())
                {
                    String code = rs.getString("code");
                    int quantity = rs.getInt("quantity");

                    row = worksheet.createRow((short) i);
                    cell = row.createCell(0);
                    cell.setCellValue(code);
                    cell = row.createCell(1);
                    cell.setCellValue(quantity);

                    i++;
                }

                row = worksheet.createRow((short) i + 1);
                cell = row.createCell(0);
                cell.setCellValue("Order Price");
                cell = row.createCell(1);
                cell.setCellValue(price);

                rs = statement.executeQuery("SELECT SUM(quantity) FROM sales_order_details WHERE ord_num = " + ord_num);
                rs.next();

                row = worksheet.createRow((short) i + 2);
                cell = row.createCell(0);
                cell.setCellValue("Total Units");
                cell = row.createCell(1);
                cell.setCellValue(rs.getInt("quantity"));

                // Auto Size Columns
                for (int k = 0; k < 2; k++)
                {
                    worksheet.autoSizeColumn(k);
                    worksheet.setDefaultColumnStyle(i + 2, editableStyle);
                }

                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();

                JOptionPane.showMessageDialog(Past.this, "Excel Order Summary Created.\n'SALES ORDER - " + ord_num + ".xls'");
            }

            catch (SQLException | IOException ex)
            {
                Logger.getLogger(Current.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                try
                {
                    con.close();
                }
                catch (Exception e)
                { /* ignored */ }
            }
        }
    }//GEN-LAST:event_btnExcelSummaryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnExcelSummary;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnMarkUndelivered;
    private javax.swing.JButton btnViewSummary;
    private javax.swing.JTextField fieldName;
    private javax.swing.JTextField fieldOrderNumber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelOR;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
