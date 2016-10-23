package services;

import db.DatabaseAction;
import db.SimpleDatabaseAction;

import javax.ejb.Local;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A service providing Database access.
 */
@Local
public interface DatabaseServiceLocal {
	/**
	 * Executes a Database Action.
	 *
	 * @param action the action to execute
	 * @param <T>    the return type of the action
	 * @return the return value of the action
	 * @throws SQLException
	 */
	<T> T run(DatabaseAction<Connection, ? extends T> action) throws SQLException;

	/**
	 * Executes a Simple Database Action
	 *
	 * @param action the action to execute
	 * @throws SQLException
	 */
	default void run(SimpleDatabaseAction<Connection> action) throws SQLException {
		run(action.asDatabaseAction());
	}
}
