package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransactionHistoryUI extends JFrame {

    public TransactionHistoryUI(int userId) {
        setTitle("Transaction History for User ID: " + userId);
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // define column names matching the database schema
        String[] columnNames = {"Transaction ID", "Type", "Amount", "Target User ID", "Timestamp"};

        // create a DefaultTableModel with column names
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // transaction history
        BankOperations bankOps = new BankOperations();
        ResultSet rs = bankOps.getTransactionHistory(userId);

        if (rs == null) {
            tableModel.addRow(new Object[]{null, "Error retrieving data", null, null, null});
        } else {
            try {
                while (rs.next()) {
                    // extracting data from the ResultSet using correct column names
                    int id = rs.getInt("id");
                    String type = rs.getString("type");
                    double amount = rs.getDouble("amount");
                    int targetUserId = rs.getInt("target_user_id");
                    java.sql.Timestamp timestamp = rs.getTimestamp("timestamp");

                    // add a row to the table model
                    tableModel.addRow(new Object[]{id, type, amount, targetUserId, timestamp});
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // adding an error row to the table model
                tableModel.addRow(new Object[]{null, "Error processing data", null, null, null});
            }
        }

        // create a JTable
        JTable table = new JTable(tableModel);

        // add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // add the JScrollPane to the frame
        add(scrollPane);
        setLocationRelativeTo(null);  // center
    }
}
