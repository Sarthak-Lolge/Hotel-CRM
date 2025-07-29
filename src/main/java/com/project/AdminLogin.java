package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin-login")
public class AdminLogin extends HttpServlet{
	public static final String QUERY = "select * from admin_login";
	public void service(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		
		String admin_username = req.getParameter("username");
		String admin_password = req.getParameter("password");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRM","root","sarthak");
			PreparedStatement ps = con.prepareStatement(QUERY);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(admin_username.equals(rs.getString(1)) && admin_password.equals(rs.getString(2))) {
					res.sendRedirect("adminPannel.jsp");
//					pw.println("successfull");
				}
				else {
					pw.println("<h1> Error </h1>");
				}
			}
			
		}catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
