<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.IOException, java.io.PrintWriter, java.sql.Connection, java.sql.DriverManager,
  java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException, javax.servlet.http.HttpServlet, javax.servlet.http.HttpServletRequest, 
  javax.servlet.http.HttpServletResponse" %>
<%! public static final String QUERY = "SELECT hb.customer_id,hb.first_name,hb.last_name,hb.phone_no,hb.email, hb.room_type,  ci.total_bill FROM hotel_booking hb JOIN customer_invoice ci ON hb.customer_id = ci.cust_id;"; %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel Booking Table</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f4f4f4;
        }
        table {
            width: 90%;
            border-collapse: collapse;
            background: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background: #2c3e50;
            color: white;
        }
        td a{
        	text-decoration:none;
        	padding:5px 10px;
        	margin:5px;
        	background-color:red;
        	color:white;
        }
    </style>
</head>
<body>
<a href="adminPannel.jsp" class="back-button">Back</a>
<table>
    <tr>
        <th>Customer ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Phone Number</th>
        <th>Email</th>
         <th>Room Type</th>
        <th>Invoice</th>
    </tr>
<% 
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm", "root", "sarthak");
        PreparedStatement ps = con.prepareStatement(QUERY);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
%> 
        <tr>
            <td><%= rs.getInt("customer_id") %></td>
            <td><%= rs.getString("First_Name") %></td>
            <td><%= rs.getString("Last_Name") %></td>
            <td><%= rs.getString("Phone_no") %></td>
            <td><%= rs.getString("email") %></td>
            <td><%= rs.getString("Room_type") %></td>
            <td><%= rs.getString("Total_bill") %></td>
         
        </tr>
<% 
        }
        rs.close();
        ps.close();
        con.close();
    } catch (ClassNotFoundException cnf) {
%>
        <tr><td colspan="9">Database driver not found!</td></tr>
<% 
        cnf.printStackTrace();
    } catch (SQLException s) {
%>
        <tr><td colspan="9">Error fetching data. Please try again later.</td></tr>
<% 
        s.printStackTrace();
    }
%>
</table>

</body>
</html>
