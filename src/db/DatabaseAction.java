package db;

import java.sql.SQLException;

@FunctionalInterface
public interface DatabaseAction<I, T> {
	T execute(I input) throws SQLException;

	default <U> DatabaseAction<I, U> andThen(DatabaseAction<T, U> action) {
		return in -> action.execute(execute(in));
	}

	default SimpleDatabaseAction<I> asSimpleDatabaseAction() {
		return this::execute;
	}
}
