package dto;

import models.User;

/**
 * A request to create a new user
 */
public class UserCreationRequest {
	public String firstname;
	public String lastname;
	public String mail;
	public String password;

	/**
	 * Transforms this DTO to a full User instance.
	 *
	 * @return a User instance matching this request
	 */
	public User toUser() {
		return new User(0, firstname, lastname, mail, password);
	}

	/**
	 * Checks if the request is valid.
	 * A request is valid if every fields are given and the
	 * mail contains "@" and the password is at least 3 chars long.
	 *
	 * @return true, if the request is valid
	 */
	public boolean isValid() {
		return firstname != null &&
				lastname != null &&
				mail != null &&
				password != null &&
				mail.contains("@") &&
				password.length() > 3;
	}
}
