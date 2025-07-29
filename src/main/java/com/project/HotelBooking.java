package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/url")
public class HotelBooking extends HttpServlet {
    private static final String QUERY = "INSERT INTO hotel_booking (first_name, last_name, phone_no, email, room_type, chech_in, chech_out, no_of_guest) VALUES (?,?,?,?,?,?,?,?)";
    private static final String QUERY2 = "INSERT INTO customer_invoice(Total_bill) VALUES ( ?)";

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        // Retrieve form data
        String fname = req.getParameter("first-name");
        String lname = req.getParameter("last-name");
        String phone_no = req.getParameter("phone");
        String email = req.getParameter("email");
        String room_type = req.getParameter("room-type");
        String check_in = req.getParameter("check-in");
        String check_out = req.getParameter("check-out");
        String no_of_guest = req.getParameter("guests");

        int guest;
        try {
            guest = Integer.parseInt(no_of_guest);
        } catch (NumberFormatException e) {
            pw.println("Invalid number of guests provided.");
            return;
        }

        // Room prices
        int price = 0;
        switch (room_type.toLowerCase()) {
            case "ac":
                price = 10000;
                break;
            case "non-ac":
                price = 5000;
                break;
            case "single":
                price = 3000;
                break;
            case "double":
                price = 8000;
                break;
            default:
                pw.println("<h3 style='color:red;'>Invalid Room Type</h3>");
                return;
        }
        
        String bill = String.valueOf(price);
        	
        try {
            // Parse dates correctly
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ud1 = sdf.parse(check_in);
            Date ud2 = sdf.parse(check_out);
            java.sql.Date sd1 = new java.sql.Date(ud1.getTime());
            java.sql.Date sd2 = new java.sql.Date(ud2.getTime());

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm", "root", "sarthak");
                 PreparedStatement ps = con.prepareStatement(QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps2 = con.prepareStatement(QUERY2)) {

                // Insert booking details
                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, phone_no);
                ps.setString(4, email);
                ps.setString(5, room_type);
                ps.setDate(6, sd1);
                ps.setDate(7, sd2);
                ps.setInt(8, guest);

                int insert = ps.executeUpdate();
                if (insert > 0) {
                    // Retrieve the generated booking ID
                    var rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                       

                        // Insert invoice with the booking ID
                        ps2.setString(1, bill);
                        
                        ps2.executeUpdate();

                        res.sendRedirect("user-pannel.html");
                    }
                } else {
                    pw.println("<h3 style='color:red;'>Booking Failed!</h3>");
                }
            }
        } catch (ClassNotFoundException e) {
            pw.println("<h3 style='color:red;'>Database Driver Not Found</h3>");
            e.printStackTrace();
        } catch (SQLException e) {
            pw.println("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        } catch (ParseException e) {
            pw.println("<h3 style='color:red;'>Invalid Date Format</h3>");
            e.printStackTrace();
        }
    }
}
