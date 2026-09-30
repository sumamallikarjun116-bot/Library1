package com.library.web;

import com.library.dao.*;
import com.library.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/library")
public class LibraryServlet extends HttpServlet {
    private final BookDAO bookDAO=new BookDAO();
    private final MemberDAO memberDAO=new MemberDAO();
    private final IssueDAO issueDAO=new IssueDAO();

    protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        String action=req.getParameter("action");
        try {
            req.setAttribute("books",bookDAO.findAll(req.getParameter("search")));
            req.setAttribute("availableBooks",bookDAO.availableBooks());
            req.setAttribute("members",memberDAO.findAll());
            req.setAttribute("issues",issueDAO.findAll());
            req.getRequestDispatcher("/index.jsp").forward(req,resp);
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        String action=req.getParameter("action");
        try {
            if("addBook".equals(action)) {
                Book b=new Book();
                b.setTitle(req.getParameter("title")); b.setAuthor(req.getParameter("author"));
                b.setCategory(req.getParameter("category")); b.setIsbn(req.getParameter("isbn"));
                b.setQuantity(Integer.parseInt(req.getParameter("quantity")));
                bookDAO.add(b);
            } else if("deleteBook".equals(action)) {
                bookDAO.delete(Integer.parseInt(req.getParameter("id")));
            } else if("addMember".equals(action)) {
                memberDAO.add(new Member(0,req.getParameter("name"),req.getParameter("email"),req.getParameter("phone")));
            } else if("deleteMember".equals(action)) {
                memberDAO.delete(Integer.parseInt(req.getParameter("id")));
            } else if("issue".equals(action)) {
                issueDAO.issue(Integer.parseInt(req.getParameter("bookId")),
                        Integer.parseInt(req.getParameter("memberId")),
                        Date.valueOf(req.getParameter("dueDate")));
            } else if("return".equals(action)) {
                issueDAO.returnBook(Integer.parseInt(req.getParameter("issueId")));
            }
            resp.sendRedirect(req.getContextPath()+"/library");
        } catch(Exception e) {
            req.setAttribute("error",e.getMessage());
            doGet(req,resp);
        }
    }
}
