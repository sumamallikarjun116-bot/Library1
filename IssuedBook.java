package com.library.model;

import java.sql.Date;

public class IssuedBook {
    private int id, bookId, memberId;
    private String bookTitle, memberName, status;
    private Date issueDate, dueDate, returnDate;

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getBookId(){return bookId;}
    public void setBookId(int bookId){this.bookId=bookId;}
    public int getMemberId(){return memberId;}
    public void setMemberId(int memberId){this.memberId=memberId;}
    public String getBookTitle(){return bookTitle;}
    public void setBookTitle(String bookTitle){this.bookTitle=bookTitle;}
    public String getMemberName(){return memberName;}
    public void setMemberName(String memberName){this.memberName=memberName;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
    public Date getIssueDate(){return issueDate;}
    public void setIssueDate(Date issueDate){this.issueDate=issueDate;}
    public Date getDueDate(){return dueDate;}
    public void setDueDate(Date dueDate){this.dueDate=dueDate;}
    public Date getReturnDate(){return returnDate;}
    public void setReturnDate(Date returnDate){this.returnDate=returnDate;}
}
