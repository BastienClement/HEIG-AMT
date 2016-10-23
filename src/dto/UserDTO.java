package dto;

import models.User;

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

	public static UserDTO fromUser(User user) {
		return new UserDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getMail());
	}
}
