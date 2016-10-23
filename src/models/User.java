package models;

/**
 * A user.
 */
public class User {
	private int id;
	private String firstname;
	private String lastname;
	private String mail;
	private String password;

	public User(int id, String firstname, String lastname, String mail, String password) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mail = mail;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
