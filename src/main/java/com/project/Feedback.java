package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/feedback")
public class Feedback extends HttpServlet {
    private static final String QUERY = "INSERT INTO feedback (name, email, rating, comments) VALUES (?, ?, ?, ?)";

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        // Retrieve form data
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String rating = req.getParameter("rating");
        String comments = req.getParameter("comments");

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm", "root", "sarthak");
                 PreparedStatement ps = con.prepareStatement(QUERY)) {

                // Insert feedback details
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, rating);
                ps.setString(4, comments);

                int insert = ps.executeUpdate();
                if (insert > 0) {
                	pw.println("<h1 style='text-align:center'>Thank You</h1>");
                    res.sendRedirect("index.html"); 
                } else {
                    pw.println("<h3 style='color:red;'>Feedback Submission Failed!</h3>");
                }
            }
        } catch (ClassNotFoundException e) {
            pw.println("<h3 style='color:red;'>Database Driver Not Found</h3>");
            e.printStackTrace();
        } catch (SQLException e) {
            pw.println("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
} 
