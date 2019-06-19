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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import entities.User;
import entities.Event;

@Path("/services")
public class Services {

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	void doConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/kgJukQ0geQ?useSSL=false", "kgJukQ0geQ", "haebUrLDc8");
	}

	public Services() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Path("/getAll")
	@Produces("application/json")
	public List<User> getAllRecords() {
		List<User> li = new ArrayList<User>();
		try {
			doConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM tbl_student_details");
			while (rs.next()) {
				li.add(new User(rs.getInt("ID"), rs.getString("Email_ID"), rs.getString("Password")));
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return li;

	}

	@POST
	@Path("/authenticate")
	@Produces("application/json")
	@Consumes("application/json")
	public String authenticate(User user) {
		try {
			doConnection();
			if (user.getType().equals("Student")) {
				ps = con.prepareStatement("SELECT * FROM tbl_student_details WHERE Email_Id = ? AND Password = ?");
				ps.setString(1, user.getEmailId());
				ps.setString(2, user.getPassword());
				rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getString("Email_Id").equals(user.getEmailId())
							&& rs.getString("Password").equals(user.getPassword())) {
						con.close();
						return "Valid";
					} else {
						con.close();
						return "Invalid";
					}
				}
			} else if (user.getType().equals("Admin")) {
				ps = con.prepareStatement("SELECT * FROM tbl_admin_details WHERE Email_Id = ? AND Password = ?");
				ps.setString(1, user.getEmailId());
				ps.setString(2, user.getPassword());

				rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getString("Email_Id").equals(user.getEmailId())
							&& rs.getString("Password").equals(user.getPassword())) {
						con.close();
						return "Valid";
					} else {
						con.close();
						return "Invalid";
					}
				}
			}
			con.close();
			return "Invalid";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "Error";
		}
	}

	@POST
	@Path("/register")
	@Produces("application/json")
	@Consumes("application/json")
	public String register(User user) {
		if (checkIfAlreadyRegistered(user.getEmailId())) {
			try {
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Already Registered";
		} else {
			try {
				ps = con.prepareStatement("INSERT INTO tbl_student_details(Email_Id,Password) values(?,?)");
				ps.setString(1, user.getEmailId());
				ps.setString(2, user.getPassword());
				ps.execute();
				con.close();
				return "Done";
			} catch (Exception e) {
				e.printStackTrace();
				try {
					con.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return "Error";
			}
		}
	}

	private boolean checkIfAlreadyRegistered(String emailId) {
		try {
			doConnection();
			ps = con.prepareStatement("SELECT Email_Id FROM tbl_student_details WHERE Email_Id = ?");
			ps.setString(1, emailId);
			return ps.executeQuery().first();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@POST
	@Path("/recovery")
	@Produces("application/json")
	@Consumes("application/json")
	public String passwordRecovery(User user) {
		try {
			if (checkIfAlreadyRegistered(user.getEmailId())) {
				ps = con.prepareStatement("UPDATE tbl_student_details SET Password = ? WHERE Email_Id = ?");
				ps.setString(1, user.getPassword());
				ps.setString(2, user.getEmailId());
				if (ps.executeUpdate() > 0) {
					con.close();
					return "Done";
				} else {
					con.close();
					return "Error";
				}
			} else {
				con.close();
				return "Not Registered";
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "Error";
		}
	}

	@GET
	@Path("/getAllEvent")
	@Produces("application/json")
	@Consumes("application/json")
	public List<Event> getAllEvent() {
		List<Event> events = new ArrayList<Event>();
		try {
			doConnection();
			ps = con.prepareStatement("SELECT * FROM tbl_event_details");
			rs = ps.executeQuery();
			while (rs.next()) {
				Event event = new Event(rs.getInt("ID"), rs.getString("Date"), rs.getString("Grp"),
						rs.getString("Start_Time"), rs.getString("End_Time"), rs.getString("Activity"),
						rs.getString("Description"), rs.getString("Venue"));
				events.add(event);
				event = null;
			}
			con.close();
			return events;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	
	@GET
	@Path("/getAllEventDateWise")
	@Produces("application/json")
	@Consumes("application/json")
	public List<Event> getAllEventDateWise(Event event) {
		List<Event> events = new ArrayList<Event>();
		try {
			doConnection();
			ps = con.prepareStatement("SELECT * FROM tbl_event_details WHERE Date = ?");
			ps.setString(1,event.getDate());
			rs = ps.executeQuery();
			while (rs.next()) {
				Event e = new Event(rs.getInt("ID"), rs.getString("Date"), rs.getString("Grp"),
						rs.getString("Start_Time"), rs.getString("End_Time"), rs.getString("Activity"),
						rs.getString("Description"), rs.getString("Venue"));
				events.add(e);
				e = null;
			}
			con.close();
			return events;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	@POST
	@Path("/addEvent")
	@Produces("application/json")
	@Consumes("application/json")
	public String addEvent(Event event) {
		try {
			doConnection();
			ps = con.prepareStatement(
					"INSERT INTO tbl_event_details(Date, Grp, Start_Time, End_Time, Activity, Description, Venue) VALUES"
							+ "(?,?,?,?,?,?,?)");
			ps.setString(1, event.getDate());
			ps.setString(2, event.getGrp());
			ps.setString(3, event.getStartTime());
			ps.setString(4, event.getEndTime());
			ps.setString(5, event.getActivity());
			ps.setString(6, event.getDescription());
			ps.setString(7, event.getVenue());
			ps.execute();
			ps.close();
			con.close();
			return "Done";
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "Error";
		}
	}

	@DELETE
	@Path("/deleteEvent/{id}")
	@Produces("application/json")
	public String deleteEvent(@PathParam("id") int id) {
		try {
			doConnection();
			ps = con.prepareStatement("DELETE FROM tbl_event_details WHERE ID = ?");
			ps.setInt(1, id);
			ps.execute();
			ps.close();
			con.close();
			return "Done";
		}catch(Exception e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "Error";
		}
	}
	
	@POST
	@Path("/updateEvent")
	@Produces("application/json")
	public String updateEvent(Event event) {
		try {
			doConnection();
			ps = con.prepareStatement("UPDATE tbl_event_details SET Date = ?, Grp = ?, Start_Time = ?, End_Time = ?, "
					+ "Activity = ?, Description = ?, Venue = ? WHERE ID = ?");
			ps.setString(1, event.getDate());
			ps.setString(2, event.getGrp());
			ps.setString(3, event.getStartTime());
			ps.setString(4, event.getEndTime());
			ps.setString(5, event.getActivity());
			ps.setString(6, event.getDescription());
			ps.setString(7, event.getVenue());
			ps.setInt(8, event.getId());
			ps.execute();
			ps.close();
			con.close();
			return "Done";
		}catch(Exception e) {
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "Error";
		}
	}
}
