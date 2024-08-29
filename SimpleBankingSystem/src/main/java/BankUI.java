package main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankUI extends JFrame {
    private JTextField userIdField;
    private JTextField amountField;
    private JTextField targetUserIdField;
    private JLabel balanceLabel;
    private BankOperations bankOps;

    public BankUI() {
        bankOps = new BankOperations();

        setTitle("Simple Banking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(20, 20, 80, 25);
        add(userIdLabel);

        userIdField = new JTextField();
        userIdField.setBounds(100, 20, 160, 25);
        add(userIdField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(20, 60, 80, 25);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(100, 60, 160, 25);
        add(amountField);

        JLabel targetUserIdLabel = new JLabel("Target User ID:");
        targetUserIdLabel.setBounds(20, 100, 120, 25);
        add(targetUserIdLabel);

        targetUserIdField = new JTextField();
        targetUserIdField.setBounds(140, 100, 120, 25);
        add(targetUserIdField);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(20, 140, 150, 25);
        add(checkBalanceButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(20, 180, 150, 25);
        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(20, 220, 150, 25);
        add(withdrawButton);

        JButton transferButton = new JButton("Transfer Funds");
        transferButton.setBounds(20, 260, 150, 25);
        add(transferButton);

        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.setBounds(20, 300, 150, 25);
        add(transactionHistoryButton);

        balanceLabel = new JLabel("Balance: ");
        balanceLabel.setBounds(20, 340, 200, 25);
        add(balanceLabel);

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.parseInt(userIdField.getText());
                double balance = bankOps.checkBalance(userId);
                balanceLabel.setText("Balance: " + balance);
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.parseInt(userIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
                if (bankOps.deposit(userId, amount)) {
                    JOptionPane.showMessageDialog(null, "Deposit successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Deposit failed!");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.parseInt(userIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
                if (bankOps.withdraw(userId, amount)) {
                    JOptionPane.showMessageDialog(null, "Withdrawal successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient balance!");
                }
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fromUserId = Integer.parseInt(userIdField.getText());
                int toUserId = Integer.parseInt(targetUserIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
                if (bankOps.transferFunds(fromUserId, toUserId, amount)) {
                    JOptionPane.showMessageDialog(null, "Transfer successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Transfer failed!");
                }
            }
        });

        transactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.parseInt(userIdField.getText());
                new TransactionHistoryUI(userId).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BankUI().setVisible(true);
            }
        });
    }
}
