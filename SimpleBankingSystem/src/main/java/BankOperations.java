package main.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankOperations {

    public double checkBalance(int userId) {
        String query = "SELECT balance FROM users WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean deposit(int userId, double amount) {
        String query = "UPDATE users SET balance = balance + ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean withdraw(int userId, double amount) {
        String query = "UPDATE users SET balance = balance - ? WHERE id = ? AND balance >= ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            ps.setDouble(3, amount);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean transferFunds(int fromUserId, int toUserId, double amount) {
        Connection con = null;
        PreparedStatement withdrawPs = null;
        PreparedStatement depositPs = null;
        PreparedStatement logTransactionPs = null;

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);  // Start transaction

            // Withdraw from source account
            String withdrawQuery = "UPDATE users SET balance = balance - ? WHERE id = ? AND balance >= ?";
            withdrawPs = con.prepareStatement(withdrawQuery);
            withdrawPs.setDouble(1, amount);
            withdrawPs.setInt(2, fromUserId);
            withdrawPs.setDouble(3, amount);
            int withdrawResult = withdrawPs.executeUpdate();

            if (withdrawResult <= 0) {
                con.rollback();
                return false;  // Insufficient balance or other error
            }

            // Deposit to destination account
            String depositQuery = "UPDATE users SET balance = balance + ? WHERE id = ?";
            depositPs = con.prepareStatement(depositQuery);
            depositPs.setDouble(1, amount);
            depositPs.setInt(2, toUserId);
            int depositResult = depositPs.executeUpdate();

            if (depositResult <= 0) {
                con.rollback();
                return false;  // Transfer failed
            }

            // Log the transaction
            String logTransactionQuery = "INSERT INTO transactions (user_id, type, amount, target_user_id) VALUES (?, ?, ?, ?)";
            logTransactionPs = con.prepareStatement(logTransactionQuery);
            logTransactionPs.setInt(1, fromUserId);
            logTransactionPs.setString(2, "transfer");
            logTransactionPs.setDouble(3, amount);
            logTransactionPs.setInt(4, toUserId);
            logTransactionPs.executeUpdate();

            con.commit();  // Commit transaction
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (withdrawPs != null) withdrawPs.close();
                if (depositPs != null) depositPs.close();
                if (logTransactionPs != null) logTransactionPs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet getTransactionHistory(int userId) {
        String query = "SELECT * FROM transactions WHERE user_id = ? OR target_user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
