package api;

import dto.ApiError;
import dto.UserCreationRequest;
import dto.UserDTO;
import dto.UserPatchRequest;
import services.UserServiceLocal;
import utils.PATCH;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Path("/users")
public class Users {
	/**
	 * The user manager service.
	 */
	@EJB
	private UserServiceLocal users;

	/**
	 * Retrieves a list of all users from the database.
	 */
	@GET
	@Produces("application/json")
	public List<UserDTO> list() {
		return users.findAll().stream().map(UserDTO::fromUser).collect(Collectors.toList());
	}

	/**
	 * Registers a new user in the database.
	 * <p>
	 * The request must include the following fields:
	 * { firstname, lastname, mail, password }
	 * <p>
	 * Additionally, the mail address must contain a "@" sign,
	 * and the password must be at least 4 characters long.
	 *
	 * @param ucr the user creation request
	 * @return the ID of the newly created user
	 */
	@POST
	@Produces("application/json")
	public Response create(UserCreationRequest ucr) {
		if (ucr == null || !ucr.isValid()) {
			throw new ApiError("Invalid request").toWAE(400);
		}
		int id = users.createUser(ucr.toUser());
		return Response.status(200)
		               .entity(id)
		               // TODO: Add location header to the response with the URL to
		               // the newly created user.
		               //.header("Location", "")
		               .build();
	}

	/**
	 * Retrieves information about a specific user, by ID.
	 *
	 * @param id the user ID
	 * @return the user information
	 */
	@GET
	@Path("{id}")
	@Produces("application/json")
	public UserDTO user(@PathParam("id") int id) {
		return users.findById(id)
		            .map(UserDTO::fromUser)
		            .orElseThrow(() -> new ApiError("This user does not exist").toWAE(404));
	}

	/**
	 * Updates information about a user.
	 *
	 * @param id  the user ID
	 * @param upr the user update request data
	 * @return the new user information as stored in the database
	 */
	@PATCH
	@Path("{id}")
	@Produces("application/json")
	public UserDTO update(@PathParam("id") int id, UserPatchRequest upr) {
		return users.findById(id)
		            .map(u -> {
			            if (upr.firstname != null) u.setFirstname(upr.firstname);
			            if (upr.lastname != null) u.setLastname(upr.lastname);
			            if (upr.password != null) u.setPassword(upr.password);
			            if (users.updateUser(u) < 1) throw new ApiError("Patch failed").toWAE(500);
			            return u;
		            })
		            .map(UserDTO::fromUser)
		            .orElseThrow(() -> new ApiError("This user does not exist").toWAE(404));
	}

	/**
	 * Deletes the user with the requested user ID.
	 *
	 * @param id the ID of the user to delete
	 * @return a 204 No Content response
	 */
	@DELETE
	@Path("{id}")
	@Produces("application/json")
	public Response delete(@PathParam("id") int id) {
		if (users.findById(id).map(users::deleteUser).orElse(0) > 0) {
			return Response.status(204).build();
		} else {
			throw new ApiError("This user does not exist").toWAE(404);
		}
	}
}
