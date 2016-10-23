package services;

import db.DatabaseAction;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of the Database service.
 */
@Stateless
public class DatabaseService implements DatabaseServiceLocal {
	/**
	 * The database DataSource
	 */
	@Resource(lookup = "java:/AmtDS")
	private DataSource dataSource;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T run(DatabaseAction<Connection, ? extends T> action) throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			return action.execute(connection);
		}
	}
}
