package in.mh.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/regForm")
public class Registration extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		String name1 = req.getParameter("name1");
		String email1 = req.getParameter("email1");
		String pass1 = req.getParameter("pass1");
		String gender1 = req.getParameter("gender1");
		String city1 = req.getParameter("city1");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_login", "root", "root");
			
			PreparedStatement ps = con.prepareStatement("insert into register values(?, ?, ?, ?, ?)");
			ps.setString(1, name1);
			ps.setString(2, email1);
			ps.setString(3, pass1);
			ps.setString(4, gender1);
			ps.setString(5, city1);
			
			int count = ps.executeUpdate();
			
			if(count>0)
			{
				resp.setContentType("text/html");
				out.println("<h3 style='color:green'>Registration Succesful</h3>");
				RequestDispatcher rd = req.getRequestDispatcher("/message.jsp");
				rd.include(req, resp);
			}
			else
			{
				resp.setContentType("text/html");
				out.println("<h3 style='color:red'>Registration Unsuccesful</h3>");
				RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
				rd.include(req, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			resp.setContentType("text/html");
			out.println("<h3 style='color:red'>Exception Occured : " + e.getMessage() + "</h3>");
			RequestDispatcher rd = req.getRequestDispatcher("/message.jsp");
			rd.include(req, resp);
		}
	}

}
