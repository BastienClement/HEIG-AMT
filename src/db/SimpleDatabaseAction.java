package db;

import java.sql.SQLException;

@FunctionalInterface
public interface SimpleDatabaseAction<I> extends DatabaseAction<I, Void> {
	void executeSimple(I input) throws SQLException;

	default Void execute(I input) throws SQLException {
		executeSimple(input);
		return null;
	}

	default DatabaseAction<I, Void> asDatabaseAction() {
		return this;
	}

	@Override
	default SimpleDatabaseAction<I> asSimpleDatabaseAction() {
		return this;
	}
}
