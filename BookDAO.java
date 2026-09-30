package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BookDAO {
    public List<Book> findAll(String search) throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? ORDER BY id DESC";
        try (Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            String q="%" + (search==null ? "" : search.trim()) + "%";
            ps.setString(1,q); ps.setString(2,q); ps.setString(3,q);
            try(ResultSet rs=ps.executeQuery()) {
                while(rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public void add(Book b) throws SQLException {
        String sql="INSERT INTO books(title,author,category,isbn,quantity,available) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,b.getTitle()); ps.setString(2,b.getAuthor()); ps.setString(3,b.getCategory());
            ps.setString(4,b.getIsbn()); ps.setInt(5,b.getQuantity()); ps.setInt(6,b.getQuantity());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement("DELETE FROM books WHERE id=?")) {
            ps.setInt(1,id); ps.executeUpdate();
        }
    }

    public List<Book> availableBooks() throws SQLException {
        List<Book> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
            PreparedStatement ps=c.prepareStatement("SELECT * FROM books WHERE available > 0 ORDER BY title");
            ResultSet rs=ps.executeQuery()) {
            while(rs.next()) list.add(map(rs));
        }
        return list;
    }

    private Book map(ResultSet rs) throws SQLException {
        return new Book(rs.getInt("id"),rs.getString("title"),rs.getString("author"),
                rs.getString("category"),rs.getString("isbn"),rs.getInt("quantity"),rs.getInt("available"));
    }
}
