import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditScreenServlet extends HttpServlet {
	private final static String query="select name,email,mobile,dob,city,gender from user where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		
		resp.setContentType("text/html");
		
		pw.println("<link rel=\"stylesheet\" href=\"css/bootstrap.css\"></link>");
		pw.println("<marquee><h2 class='text-warning' style='margin-top:50px;'>Edit Users</h2></marquee>");
		int id=Integer.parseInt(req.getParameter("id"));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement?serverTimezone=UTC","root","ivar123");
				PreparedStatement ps = con.prepareStatement(query);){
			
			ps.setInt(1, id);
			//set the result
			ResultSet rs=ps.executeQuery();
			rs.next();
			pw.println("<div style='margin:auto;width:500px;margin-top:50px;'>");
			pw.println("<form action='edit?id="+id+"' method='post'>");
			pw.println("<table class='table table-hover table-striped'>");
			pw.println("<tr>");
			pw.println("<td>Name</td>");
            pw.println("<td><input type='text' name='name' value='"+rs.getString(1)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Email</td>");
			pw.println("<td><input type='email' name='email' value='"+rs.getString(2)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Phone Number</td>");
			pw.println("<td><input type='text' name='mobile' value='"+rs.getString(3)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Date of Birth</td>");
			pw.println("<td><input type='date' name='dob' value='"+rs.getString(4)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>City</td>");
			pw.println("<td><input type='text' name='city' value='"+rs.getString(5)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Gender</td>");
			pw.println("<td><input type='text' name='gender' value='"+rs.getString(6)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><button type='submit' class='btn btn-outline-warning'>Edit</button></td>");
			pw.println("<td><button type='reset' class='btn btn-outline-danger'>Cancel</button></td>");
			pw.println("</table>");
			pw.println("</form>");
		}catch(SQLException se) {
			pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		pw.println("<a href='home.html'><button class='btn btn-outline-success'>Home</button>");
		pw.println("</div>");
		pw.close();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
