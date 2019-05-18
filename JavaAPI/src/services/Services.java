package services;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import entities.User;

@Path("/services")
public class Services {

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	void doConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/kgJukQ0geQ", "kgJukQ0geQ", "haebUrLDc8");
	}

	public Services() {
		// TODO Auto-generated constructor stub
		try {
			doConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GET
	@Path("/getAll")
	@Produces("application/json")
	public List<User> getAllRecords() {
		List<User> li = new ArrayList<User>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM tbl_student_details");
			while (rs.next()) {
				li.add(new User(rs.getInt("ID"), rs.getString("Email_ID"), rs.getString("Password")));
			}
			rs.close();
			st.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;

	}

	@POST
	@Path("/authenticate")
	@Produces("application/json")
	@Consumes("application/json")
	public boolean authenticate(User user) {
		try {
			if (user.getType().equals("Student")) {
				ps = con.prepareStatement("SELECT * FROM tbl_student_details WHERE Email_Id = ? AND Password = ?");
				ps.setString(1, user.getEmailId());
				ps.setString(2, user.getPassword());
				rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getString("Email_Id").equals(user.getEmailId())
							&& rs.getString("Password").equals(user.getPassword()))
						return true;
					else
						return false;
				}
			} else if (user.getType().equals("Admin")) {
				ps = con.prepareStatement("SELECT * FROM tbl_admin_details WHERE Email_Id = ? AND Password = ?");
				ps.setString(1, user.getEmailId());
				ps.setString(2, user.getPassword());
				rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getString("Email_Id").equals(user.getEmailId())
							&& rs.getString("Password").equals(user.getPassword()))
						return true;
					else
						return false;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@POST
	@Path("/register")
	@Produces("application/json")
	@Consumes("application/json")
	public String register(User user) {
		if (checkIfAlreadyRegistered(user.getEmailId())) {
			return "Already Registered";
		} else {
			try {
				ps = con.prepareStatement("INSERT INTO tbl_student_details(Email_Id,Password) values(?,?)");
				ps.setString(1,user.getEmailId());
				ps.setString(2,user.getPassword());
				ps.execute();
				return "Done";
			}catch(Exception e) {
				e.printStackTrace();
				return "Error";
			}
		}
	}

	private boolean checkIfAlreadyRegistered(String emailId) {
		try {
			ps = con.prepareStatement("SELECT Email_Id FROM tbl_student_details WHERE Email_Id = ?");
			ps.setString(1, emailId);
			return ps.executeQuery().first();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	 @Override protected void finalize() throws Throwable { super.finalize(); con.close(); }
	 
}
