package services;

import db.DatabaseAction;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Stateless
public class DatabaseService implements DatabaseServiceLocal {
	@Resource(lookup = "java:/AmtDS")
	private DataSource dataSource;

	@Override
	public <T> T run(DatabaseAction<Connection, ? extends T> action) throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			return action.execute(connection);
		}
	}
}
