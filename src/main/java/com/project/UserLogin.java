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
@WebServlet("/user-login")
public class UserLogin extends HttpServlet {
		public static final String QUERY = "select * from user_signup";
		public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
		{
			res.setContentType("text/html");
			PrintWriter pw = res.getWriter();
			String username = req.getParameter("username");
			String password = req.getParameter("password");	
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRM","root","sarthak");
				PreparedStatement ps  = con.prepareStatement(QUERY);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					if(username.equals(rs.getString(4)) && password.equals(rs.getString(5))) {
						pw.println("<script> alert('Login Successfull')</script>");
						res.sendRedirect("user-pannel.html");
					}
					else {
						pw.println("<script> alert('Invalid Credentails')</script>");
					}
				}
			}catch(ClassNotFoundException cnf) {
				cnf.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}
}
