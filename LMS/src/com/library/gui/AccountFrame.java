package com.library.gui;

import com.library.model.Account;
import com.library.model.Book;
import com.library.model.IssuedBook;
import com.library.service.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class AccountFrame extends JFrame {
    private LibraryService libraryService = new LibraryService();

    public AccountFrame() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // tabs for operations and actions
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Operations", createOperationsPanel());
        tabbedPane.addTab("Actions", createActionsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createOperationsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        // panel for new Student
        JPanel newStudentPanel = new JPanel();
        newStudentPanel.setLayout(new FlowLayout());
        JTextField studentNameField = new JTextField(15);
        JTextField studentEmailField = new JTextField(15);
        JTextField studentPhoneField = new JTextField(15);
        JButton addStudentButton = new JButton("Add Student");
        newStudentPanel.add(new JLabel("Name:"));
        newStudentPanel.add(studentNameField);
        newStudentPanel.add(new JLabel("Email:"));
        newStudentPanel.add(studentEmailField);
        newStudentPanel.add(new JLabel("Phone:"));
        newStudentPanel.add(studentPhoneField);
        newStudentPanel.add(addStudentButton);
        panel.add(newStudentPanel);

        // panel for New book
        JPanel newBookPanel = new JPanel();
        newBookPanel.setLayout(new FlowLayout());
        JTextField bookTitleField = new JTextField(15);
        JTextField bookAuthorField = new JTextField(15);
        JTextField bookQuantityField = new JTextField(5);
        JButton addBookButton = new JButton("Add Book");
        newBookPanel.add(new JLabel("Title:"));
        newBookPanel.add(bookTitleField);
        newBookPanel.add(new JLabel("Author:"));
        newBookPanel.add(bookAuthorField);
        newBookPanel.add(new JLabel("Quantity:"));
        newBookPanel.add(bookQuantityField);
        newBookPanel.add(addBookButton);
        panel.add(newBookPanel);

        // panel for new account
        JPanel newAccountPanel = new JPanel();
        newAccountPanel.setLayout(new FlowLayout());
        JTextField accountStudentIdField = new JTextField(5);
        JButton addAccountButton = new JButton("Add Account");
        newAccountPanel.add(new JLabel("Student ID:"));
        newAccountPanel.add(accountStudentIdField);
        newAccountPanel.add(addAccountButton);
        panel.add(newAccountPanel);

        // action listeners
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = studentNameField.getText();
                String email = studentEmailField.getText();
                String phone = studentPhoneField.getText();
                // Add student to database
            }
        });

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = bookTitleField.getText();
                String author = bookAuthorField.getText();
                int quantity = Integer.parseInt(bookQuantityField.getText());
                Book book = new Book(title, author, quantity);
                // add book to database
                libraryService.addBook(book);
                JOptionPane.showMessageDialog(AccountFrame.this, "Book added successfully!");
            }
        });

        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int studentId = Integer.parseInt(accountStudentIdField.getText());
                libraryService.addAccount(studentId);
                JOptionPane.showMessageDialog(AccountFrame.this, "Account created successfully!");
            }
        });

        return panel;
    }

    private JPanel createActionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        // panel for Issue book
        JPanel issueBookPanel = new JPanel();
        issueBookPanel.setLayout(new FlowLayout());
        JTextField issueBookIdField = new JTextField(5);
        JTextField issueStudentIdField = new JTextField(5);
        JTextField issueDateField = new JTextField(10);
        JTextField returnDateField = new JTextField(10);
        JButton issueBookButton = new JButton("Issue Book");
        issueBookPanel.add(new JLabel("Book ID:"));
        issueBookPanel.add(issueBookIdField);
        issueBookPanel.add(new JLabel("Student ID:"));
        issueBookPanel.add(issueStudentIdField);
        issueBookPanel.add(new JLabel("Issue Date (yyyy-mm-dd):"));
        issueBookPanel.add(issueDateField);
        issueBookPanel.add(new JLabel("Return Date (yyyy-mm-dd):"));
        issueBookPanel.add(returnDateField);
        issueBookPanel.add(issueBookButton);
        panel.add(issueBookPanel);

        // panel for return book
        JPanel returnBookPanel = new JPanel();
        returnBookPanel.setLayout(new FlowLayout());
        JTextField returnBookIdField = new JTextField(5);
        JButton returnBookButton = new JButton("Return Book");
        returnBookPanel.add(new JLabel("Issue ID:"));
        returnBookPanel.add(returnBookIdField);
        returnBookPanel.add(returnBookButton);
        panel.add(returnBookPanel);

        // panel for search Book
        JPanel searchBookPanel = new JPanel();
        searchBookPanel.setLayout(new FlowLayout());
        JTextField searchTitleField = new JTextField(15);
        JButton searchBookButton = new JButton("Search Book");
        JTextArea searchResultArea = new JTextArea(5, 40);
        searchResultArea.setEditable(false);
        JScrollPane searchScrollPane = new JScrollPane(searchResultArea);
        searchBookPanel.add(new JLabel("Title:"));
        searchBookPanel.add(searchTitleField);
        searchBookPanel.add(searchBookButton);
        searchBookPanel.add(searchScrollPane);
        panel.add(searchBookPanel);

        issueBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bookId = Integer.parseInt(issueBookIdField.getText());
                int studentId = Integer.parseInt(issueStudentIdField.getText());
                Date issueDate = Date.valueOf(issueDateField.getText());
                Date returnDate = returnDateField.getText().isEmpty() ? null : Date.valueOf(returnDateField.getText());
                IssuedBook issuedBook = new IssuedBook(bookId, studentId, issueDate, returnDate);
                libraryService.issueBook(issuedBook);
                JOptionPane.showMessageDialog(AccountFrame.this, "Book issued successfully!");
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int issueId = Integer.parseInt(returnBookIdField.getText());
                JOptionPane.showMessageDialog(AccountFrame.this, "Book returned successfully!");
            }
        });

        searchBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = searchTitleField.getText();
                // Search book logic
                searchResultArea.setText("Search results for '" + title + "':\n");
                searchResultArea.append("1. Java Programming Basics\n");
                searchResultArea.append("2. Clean Code\n");
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountFrame().setVisible(true));
    }
}
