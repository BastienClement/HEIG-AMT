package services;

import models.User;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

/**
 * A service for managing the users database.
 */
@Local
public interface UserServiceLocal {
	/**
	 * Returns a list of every users in the database.
	 *
	 * @return a list of every users in the database
	 */
	List<User> findAll();

	/**
	 * Finds a user with the requested ID.
	 *
	 * @param id the user id to search for
	 * @return the user with a matching id, if it exists
	 */
	Optional<User> findById(int id);

	/**
	 * Finds a user with the requested mail address.
	 *
	 * @param mail the mail address to search for
	 * @return the user with a matching mail, if it exists
	 */
	Optional<User> findByMail(String mail);

	/**
	 * Creates a new user.
	 * <p>
	 * The id field of the parameter object is ignored.
	 *
	 * @param user the user data to save
	 * @return the id of the created user
	 */
	int createUser(User user);

	/**
	 * Updates a user.
	 * <p>
	 * Only firstname, lastname and password are update-able.
	 * The mail field is ignored.
	 *
	 * @param user the new user data
	 * @return the number of row affected (should be 1)
	 */
	int updateUser(User user);

	/**
	 * Deletes a user.
	 * <p>
	 * Only the user id is read from the input object.
	 *
	 * @param user the new user data
	 * @return the number of row affected (should be 1)
	 */
	int deleteUser(User user);
}
