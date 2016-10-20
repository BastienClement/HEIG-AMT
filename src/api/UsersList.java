package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/users")
public class UsersList {
	@GET
	@Produces("text/plain")
	public String get() {
		return "Hello world!";
	}
}
