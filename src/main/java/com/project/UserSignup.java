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
@WebServlet("/user-signup")
public class UserSignup extends HttpServlet{
	public static final String query = "insert into user_signup values(?,?,?,?,?,?)";
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String mobileno = req.getParameter("mobileno");
		int count=0;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRM","root","sarthak");
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, count);
		ps.setString(2, name);
		ps.setString(3, email);
		ps.setString(4, username);
		ps.setString(5, password);
		ps.setString(6, mobileno);
		int insert = ps.executeUpdate();
		if(insert!=0) {
			pw.println("<script> alert('Successfully enters the Credentails') </script>");
			res.sendRedirect("user-login.html");
		}
		else {
			pw.println("<script>alert('An Error Occurs') </script>");
		}
		
		}catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException s) {
			s.printStackTrace();
		}
		
	}
	
}
