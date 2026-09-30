package com.library.dao;

import com.library.model.Member;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class MemberDAO {
    public List<Member> findAll() throws SQLException {
        List<Member> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
            PreparedStatement ps=c.prepareStatement("SELECT * FROM members ORDER BY id DESC");
            ResultSet rs=ps.executeQuery()) {
            while(rs.next()) list.add(new Member(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("phone")));
        }
        return list;
    }

    public void add(Member m) throws SQLException {
        String sql="INSERT INTO members(name,email,phone) VALUES(?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,m.getName()); ps.setString(2,m.getEmail()); ps.setString(3,m.getPhone());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement("DELETE FROM members WHERE id=?")) {
            ps.setInt(1,id); ps.executeUpdate();
        }
    }
}
