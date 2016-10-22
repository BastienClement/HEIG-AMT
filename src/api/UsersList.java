package api;

import services.UserServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.SQLException;

@Stateless
@Path("/users")
public class UsersList {
	@EJB
	private UserServiceLocal users;

	@GET
	@Produces("text/plain")
	public String get() throws SQLException {
		StringBuilder out = new StringBuilder();
		users.findAll().forEach(user -> {
			out.append(user.getId()).append("\t")
			   .append(user.getFirstname()).append("\t")
			   .append(user.getLastname()).append("\t")
			   .append(user.getMail()).append("\t")
			   .append(user.getPassword()).append("\n");
		});
		return out.toString();
	}
}
