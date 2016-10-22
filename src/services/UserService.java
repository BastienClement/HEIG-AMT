package services;

import db.ResultSetReader;
import models.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static db.DSL.*;

@Stateless
public class UserService implements UserServiceLocal {
	@EJB
	private DatabaseServiceLocal db;

	private static final String USER_FIELDS = "id, firstname, lastname, mail, password";
	private static final String UPDATE_USER_QUERY = "UPDATE users SET firstname = ?, lastname = ?, password = ? WHERE id = ? LIMIT 1";

	@Override
	public List<User> findAll() {
		try {
			return db.run(query("SELECT " + USER_FIELDS + " FROM users", collect(UserService::readUser)));
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public Optional<User> findById(int id) {
		try {
			return db.run(query(
				prepare("SELECT " + USER_FIELDS + " FROM users WHERE id = ? LIMIT 1", id),
				first(UserService::readUser)));
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findByMail(String mail) {
		try {
			return db.run(query(
				prepare("SELECT " + USER_FIELDS + " FROM users WHERE mail = ? LIMIT 1", mail),
				first(UserService::readUser)));
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public int createUser(User user) {
		try {
			db.run(execute(prepare(
				"INSERT INTO users SET firstname = ?, lastname = ?, mail = ?, password = ?",
				user.getFirstname(), user.getLastname(), user.getMail(), user.getPassword()
			)));
			return findByMail(user.getMail()).get().getId();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateUser(User user) {
		try {
			return db.run(execute(prepare(
				"UPDATE users SET firstname = ?, lastname = ?, password = ? WHERE id = ? LIMIT 1",
				user.getFirstname(), user.getLastname(), user.getPassword(), user.getId()
			)));
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteUser(User user) {
		try {
			return db.run(execute(prepare("DELETE FROM users WHERE id = ? LIMIT 1", user.getId())));
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static User readUser(ResultSetReader r) throws SQLException {
		return new User(
			r.nextInt(),
			r.nextString(),
			r.nextString(),
			r.nextString(),
			r.nextString()
		);
	}
}
