package com.library.dao;

import com.library.model.IssuedBook;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IssuedBookDAO {
    private Connection getConnection() throws SQLException {
        // Return a connection to your database
    }

    public void issueBook(IssuedBook issuedBook) {
        String query = "INSERT INTO issued_books (book_id, student_id, issue_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, issuedBook.getBookId());
            stmt.setInt(2, issuedBook.getStudentId());
            stmt.setDate(3, new Date(issuedBook.getIssueDate().getTime()));
            stmt.setDate(4, issuedBook.getReturnDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int issueId, Date returnDate) {
        String query = "UPDATE issued_books SET return_date = ? WHERE issue_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, returnDate);
            stmt.setInt(2, issueId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
