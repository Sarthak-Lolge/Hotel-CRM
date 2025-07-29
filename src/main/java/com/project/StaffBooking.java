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

@WebServlet("/StaffServlet")
public class StaffBooking extends HttpServlet {
    private static final String QUERY = "INSERT INTO staff_booking (Name, email, phone_no, role, salary) VALUES (?, ?, ?, ?, ?)";

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        // Retrieve form data
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone_no = req.getParameter("phone");
        String role = req.getParameter("role");

        // Salary based on role
        int salary = 0;
        switch (role.toLowerCase()) {
            case "manager":
                salary = 50000;
                break;
            case "chef":
                salary = 20000;
                break;
            case "housekeeping":
                salary = 10000;
                break;
            case "waiter":
                salary = 12000;
                break;
            case "receptionist":  // Added missing receptionist role
                salary = 18000;
                break;
            default:
                pw.println("<h3 style='color:red;'>Invalid Staff Role</h3>");
                return;
        }

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm", "root", "sarthak");
                 PreparedStatement ps = con.prepareStatement(QUERY)) {

                // Insert staff details
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone_no);
                ps.setString(4, role);
                ps.setInt(5, salary);

                int insert = ps.executeUpdate();
                if (insert > 0) {
                    // Redirect to Admin Panel after successful registration
                    res.sendRedirect("adminPannel.jsp");
                } else {
                    pw.println("<h3 style='color:red;'>Staff Registration Failed!</h3>");
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
