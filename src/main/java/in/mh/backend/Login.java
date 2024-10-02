package in.mh.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginForm")
public class Login extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		String email1 = req.getParameter("email1");
		String pass1 = req.getParameter("pass1");
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_login", "root", "root");
			
			PreparedStatement ps = con.prepareStatement("select * from register where email=? and password=?");
			ps.setString(1, email1);
			ps.setString(2, pass1);
			
			ResultSet rs = ps.executeQuery();
			
			System.out.println(rs.toString());
			
			if(rs.next())
			{
				HttpSession session = req.getSession();
				session.setAttribute("session_name", rs.getString("name"));
				
				RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
				rd.include(req, resp);
			}
			else
			{
				resp.setContentType("text/html");
				out.println("<h3 style='color:red'>Email or Password didn't matched.</h3>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/message.jsp");
				rd.include(req, resp);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			resp.setContentType("text/html");
			
			RequestDispatcher rd = req.getRequestDispatcher("/logpage.html");
			rd.include(req, resp);
			
			out.print("<h3 style='color:red'>" + e.getMessage() + "</h3>");
		}
	}

}
