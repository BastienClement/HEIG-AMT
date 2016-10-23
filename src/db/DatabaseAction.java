package db;

import java.sql.SQLException;

/**
 * A database action that can be executed on some input to
 * produce some value. The execution may throw SQLException.
 *
 * @param <I> the input type for the action
 * @param <T> the return type for the action
 */
@FunctionalInterface
public interface DatabaseAction<I, T> {
	/**
	 * Executes the action.
	 *
	 * @param input the action input
	 * @return the action return value
	 * @throws SQLException
	 */
	T execute(I input) throws SQLException;

	/**
	 * Returns a new database action that perform this action first,
	 * and then executes the given action.
	 *
	 * @param action the action to execute after this one
	 * @param <U>    the return type of the action
	 * @return the return value of the second action
	 */
	default <U> DatabaseAction<I, U> andThen(DatabaseAction<T, U> action) {
		return in -> action.execute(execute(in));
	}

	/**
	 * Converts this DatabaseAction to a SimpleDatabaseAction by
	 * discarding the return value of this action.
	 *
	 * @return a simple action with the same behavior but no return value
	 */
	default SimpleDatabaseAction<I> asSimpleDatabaseAction() {
		return this::execute;
	}
}
