import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private final static String query="delete from user where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		
		resp.setContentType("text/html");
		
		pw.println("<link rel=\"stylesheet\" href=\"css/bootstrap.css\"></link>");
		
		int id=Integer.parseInt(req.getParameter("id"));
		
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement?serverTimezone=UTC","root","ivar123");
				PreparedStatement ps = con.prepareStatement(query);){
			
			ps.setInt(1, id);
			
			int count = ps.executeUpdate();
			pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
			if(count==1) {
				pw.println("<h2 class='bg-success text-light text-center'>Deleted Successfully!</h2>");
			}
			else {
				pw.println("<h2 class='bg-danger text-light text-center'>Deleted Failure!</h2>");
			}
		}catch(SQLException se) {
			pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		pw.println("<a href='home.html'><button class='btn btn-outline-success'>Home</button>");
		pw.println("<p></p>");
		pw.println("<a href='showdata'><button class='btn btn-outline-success'>Show User</button>");
	
		pw.println("</div>");
		pw.close();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
