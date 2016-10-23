package dto;

import models.User;

/**
 * The User Data Transfer Object
 */
public class UserDTO {
	public int id;
	public String firstname;
	public String lastname;
	public String mail;

	public UserDTO(int id, String firstname, String lastname, String mail) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mail = mail;
	}

	/**
	 * Constructs a DTO object from a User object
	 *
	 * @param user the source object object
	 * @return a DTO for the given user
	 */
	public static UserDTO fromUser(User user) {
		return new UserDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getMail());
	}
}
