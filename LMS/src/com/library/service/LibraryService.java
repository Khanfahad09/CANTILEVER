package com.library.service;

import com.library.dao.AccountDAO;
import com.library.dao.BookDAO;
import com.library.dao.IssuedBookDAO;
import com.library.model.Account;
import com.library.model.Book;
import com.library.model.IssuedBook;
import java.util.Date;

public class LibraryService {
    private AccountDAO accountDAO = new AccountDAO();
    private BookDAO bookDAO = new BookDAO();
    private IssuedBookDAO issuedBookDAO = new IssuedBookDAO();

    // method to add a new account
    public void addAccount(int studentId) {
        Account account = new Account(studentId, new Date());
        accountDAO.addAccount(account);
    }

    // method to add a new book
    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    // method to issue a book
    public void issueBook(IssuedBook issuedBook) {
        issuedBookDAO.issueBook(issuedBook);
    }
}
