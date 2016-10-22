package services;

import db.DatabaseAction;
import db.SimpleDatabaseAction;

import javax.ejb.Local;
import java.sql.Connection;
import java.sql.SQLException;

@Local
public interface DatabaseServiceLocal {
	<T> T run(DatabaseAction<Connection, ? extends T> action) throws SQLException;

	default void run(SimpleDatabaseAction<Connection> action) throws SQLException {
		run(action.asDatabaseAction());
	}
}
