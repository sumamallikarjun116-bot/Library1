package com.library.dao;

import com.library.model.IssuedBook;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class IssueDAO {
    public List<IssuedBook> findAll() throws SQLException {
        List<IssuedBook> list=new ArrayList<>();
        String sql="SELECT i.*, b.title book_title, m.name member_name FROM issued_books i " +
                   "JOIN books b ON i.book_id=b.id JOIN members m ON i.member_id=m.id ORDER BY i.id DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()) {
            while(rs.next()) {
                IssuedBook x=new IssuedBook();
                x.setId(rs.getInt("id")); x.setBookId(rs.getInt("book_id")); x.setMemberId(rs.getInt("member_id"));
                x.setBookTitle(rs.getString("book_title")); x.setMemberName(rs.getString("member_name"));
                x.setIssueDate(rs.getDate("issue_date")); x.setDueDate(rs.getDate("due_date"));
                x.setReturnDate(rs.getDate("return_date")); x.setStatus(rs.getString("status"));
                list.add(x);
            }
        }
        return list;
    }

    public void issue(int bookId, int memberId, Date dueDate) throws SQLException {
        String insert="INSERT INTO issued_books(book_id,member_id,issue_date,due_date) VALUES(?,?,CURDATE(),?)";
        Connection c=null;
        try {
            c=DBConnection.getConnection();
            c.setAutoCommit(false);
            try(PreparedStatement check=c.prepareStatement("SELECT available FROM books WHERE id=? FOR UPDATE")) {
                check.setInt(1,bookId);
                try(ResultSet rs=check.executeQuery()) {
                    if(!rs.next() || rs.getInt(1)<=0) throw new SQLException("Book is not available.");
                }
            }
            try(PreparedStatement ps=c.prepareStatement(insert)) {
                ps.setInt(1,bookId); ps.setInt(2,memberId); ps.setDate(3,dueDate); ps.executeUpdate();
            }
            try(PreparedStatement ps=c.prepareStatement("UPDATE books SET available=available-1 WHERE id=?")) {
                ps.setInt(1,bookId); ps.executeUpdate();
            }
            c.commit();
        } catch(Exception e) {
            if(c!=null) try { c.rollback(); } catch(SQLException ignored) {}
            if(e instanceof SQLException) throw (SQLException)e;
            throw new SQLException(e);
        } finally {
            if(c!=null) try { c.close(); } catch(SQLException ignored) {}
        }
    }

    public void returnBook(int issueId) throws SQLException {
        Connection c=null;
        try {
            c=DBConnection.getConnection(); c.setAutoCommit(false);
            int bookId;
            try(PreparedStatement ps=c.prepareStatement("SELECT book_id,status FROM issued_books WHERE id=? FOR UPDATE")) {
                ps.setInt(1,issueId);
                try(ResultSet rs=ps.executeQuery()) {
                    if(!rs.next()) throw new SQLException("Issue record not found.");
                    if("RETURNED".equals(rs.getString("status"))) throw new SQLException("Book already returned.");
                    bookId=rs.getInt("book_id");
                }
            }
            try(PreparedStatement ps=c.prepareStatement("UPDATE issued_books SET return_date=CURDATE(),status='RETURNED' WHERE id=?")) {
                ps.setInt(1,issueId); ps.executeUpdate();
            }
            try(PreparedStatement ps=c.prepareStatement("UPDATE books SET available=available+1 WHERE id=?")) {
                ps.setInt(1,bookId); ps.executeUpdate();
            }
            c.commit();
        } catch(Exception e) {
            if(c!=null) try { c.rollback(); } catch(SQLException ignored) {}
            if(e instanceof SQLException) throw (SQLException)e;
            throw new SQLException(e);
        } finally {
            if(c!=null) try { c.close(); } catch(SQLException ignored) {}
        }
    }
}
