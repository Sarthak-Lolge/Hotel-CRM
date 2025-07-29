
package com.project;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteBooking")
public class DeleteCustomer extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        
        String customerId = req.getParameter("customer_id");

        // Check if customer_id is not null or empty
        if (customerId == null || customerId.isEmpty()) {
            pw.println("<h3 style='color:red;'>Invalid Customer ID</h3>");
            return;
        }

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRM", "root", "sarthak");

            // DELETE query
            String QUERY = "DELETE FROM hotel_booking WHERE customer_id = ?";
            PreparedStatement ps = con.prepareStatement(QUERY);
            ps.setInt(1, Integer.parseInt(customerId));

            // Execute update
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
//                pw.println("<h3 style='color:green;'>Booking Deleted Successfully!</h3>");
            	res.sendRedirect("customer_list.jsp");
            } else {
                pw.println("<h3 style='color:red;'>No Booking Found with Given ID</h3>");
            }

            // Close resources
            ps.close();
            con.close();
        } catch (ClassNotFoundException e) {
            pw.println("<h3 style='color:red;'>Database Driver Not Found</h3>");
            e.printStackTrace();
        } catch (SQLException e) {
            pw.println("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            pw.println("<h3 style='color:red;'>Invalid Customer ID Format</h3>");
            e.printStackTrace();
        }
    }
}
