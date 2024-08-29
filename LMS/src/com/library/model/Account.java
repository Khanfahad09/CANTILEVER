package com.library.model;

import java.util.Date;

public class Account {
    private int accountId;
    private int studentId;
    private Date createdAt;

    public Account() {}

    public Account(int studentId, Date createdAt) {
        this.studentId = studentId;
        this.createdAt = createdAt;
    }


    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
