package db;

import java.sql.SQLException;

/**
 * A simple database action with an input but no return value.
 * <p>
 * This second interface is required to work around a limitation of the
 * Java 8 lambda syntax and generics. It is not possible to have the "void" type
 * as a type parameter and thus DatabaseAction lambdas are forced to use the
 * return keyword in their body.
 * <p>
 * The "Void" object type does not work as expected, still requiring a return
 * statement in the lambda.
 *
 * @param <I> the input to the action
 */
@FunctionalInterface
public interface SimpleDatabaseAction<I> extends DatabaseAction<I, Void> {
	/**
	 * Executes this simple action.
	 *
	 * @param input the input to the action
	 * @throws SQLException
	 */
	void executeSimple(I input) throws SQLException;

	/**
	 * Implements a DatabaseAction-compatible execute method
	 *
	 * @param input the action input
	 * @return nothing useful
	 * @throws SQLException
	 */
	default Void execute(I input) throws SQLException {
		executeSimple(input);
		return null;
	}

	/**
	 * Transforms this simple action to a full database action.
	 * Since SDA inherits from DA, this is only a type-level operation.
	 *
	 * @return this object
	 */
	default DatabaseAction<I, Void> asDatabaseAction() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return this object
	 */
	@Override
	default SimpleDatabaseAction<I> asSimpleDatabaseAction() {
		return this;
	}
}
