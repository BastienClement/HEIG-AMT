package dto;

import models.User;

public class UserCreationRequest {
	public String firstname;
	public String lastname;
	public String mail;
	public String password;

	public User toUser() {
		return new User(0, firstname, lastname, mail, password);
	}

	public boolean isValid() {
		return firstname != null &&
				lastname != null &&
				mail != null &&
				password != null &&
				mail.contains("@") &&
				password.length() > 3;
	}
}
