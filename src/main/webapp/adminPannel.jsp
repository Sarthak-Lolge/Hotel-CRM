<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.io.IOException, java.io.PrintWriter, java.sql.Connection, java.sql.DriverManager,
  java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException, javax.servlet.http.HttpServlet, javax.servlet.http.HttpServletRequest, 
  javax.servlet.http.HttpServletResponse" %>
<%! public static final String QUERY = "SELECT COUNT(*) FROM hotel_booking"; %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <title>Hotel CRM Dashboard</title>
    <link rel="stylesheet" href="style.css">
  <style>
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    display: flex;
  }  
  
  button.back{
    margin-top: 140%;
    margin-right: 15px;
    margin-left: 20px;
    padding: 10px 20px;
  }
 </style>
</head>
<body>

   
<% 
	int userCount = 0;
	int total_rooms=100;
	int available_rooms=0;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm", "root", "sarthak");
        PreparedStatement ps = con.prepareStatement(QUERY);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()) {
        	userCount = rs.getInt(1);
        }
        
%>
 <div class="sidebar">
        <h2>Admin Pannel</h2>
        <ul>
            <li><a href="#" id="dashboard-li">Dashboard</a></li>
            <li><a href="customer_list.jsp" id="customer-li">Customers</a></li>

            <li><a href="customer_invoice.jsp" id="invoice-li">Invoices</a></li>
            <li><a href="staff.html" id="staff-li">Staff </a></li>
            <li><a href="#" id="report-li"></a></li>
        </ul>
        <a href="index.html"><button class="back">Back</button></a>
        <a href="Admin-login.html" ><button class="back">Logout</button></a>
    </div>
    <div class="main-content">
      <div class="dashboard-pannel">
        <header>
          <h1>Dashboard</h1>
          <p>Welcome to the Hotel CRM system</p>
        </header>
        <section class="dashboard">
          <div class="card">
            <h3>Total Bookings</h3>
            <p><%= userCount %></p>
          </div>
			
		
            <div class="card">
              <h3>Available Rooms</h3>
              <p><% available_rooms = total_rooms-userCount; %><%= available_rooms %></p>
            </div>
            
           <div class="card">
            <h3>Total Rooms</h3>
            <p><%= total_rooms %></p>
          </div>
          </section>  
      </div>
      </div>

<% }catch(ClassNotFoundException cnf){
	cnf.printStackTrace();	
}catch(SQLException s){
	s.printStackTrace();
}%> 
}

</body>
</html>