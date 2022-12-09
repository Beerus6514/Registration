package com.bhushan.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("name");
		String password = request.getParameter("pass");
		String email = request.getParameter("email");
		String mobile = request.getParameter("contact");
		
		RequestDispatcher requestDispatcher = null;
		
//		PrintWriter out = response.getWriter();
//		out.println(username);
//		out.println(password);
//		out.println(email);
//		out.println(mobile);
		
		//inside try catch we write databases code to insert above details in the database
		Connection con = null;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false", "root", "Bhushan@99");
			
			PreparedStatement pstmt = con.prepareStatement("insert into users(uname, upwd, uemail, umobile) values(?,?,?,?)");
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			pstmt.setString(4, mobile);
			
			int rowCount = pstmt.executeUpdate();
			requestDispatcher = request.getRequestDispatcher("registration.jsp");
			if(rowCount > 0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
				requestDispatcher.forward(request, response);
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}











