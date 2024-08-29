package com.library.dao;

import com.library.model.Account;
import com.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountDAO {
    public void addAccount(Account account) {
        String query = "INSERT INTO accounts (student_id, created_at) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, account.getStudentId());
            preparedStatement.setDate(2, new java.sql.Date(account.getCreatedAt().getTime()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM accounts";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getInt("account_id"));
                account.setStudentId(resultSet.getInt("student_id"));
                account.setCreatedAt(resultSet.getDate("created_at"));
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public Account getAccountById(int accountId) {
        Account account = null;
        String query = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = new Account();
                account.setAccountId(resultSet.getInt("account_id"));
                account.setStudentId(resultSet.getInt("student_id"));
                account.setCreatedAt(resultSet.getDate("created_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
}
