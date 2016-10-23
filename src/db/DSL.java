package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Some helper functions to construct DatabaseActions for the
 * DatabaseService.
 */
public interface DSL {
	/**
	 * Constructs a DatabaseAction performing a SELECT-like query on the input Connection
	 * and feeding the ResultSet to the given action.
	 *
	 * @param statementProvider a action producing a PreparedStatement to execute
	 * @param action            the action to execute on the result set
	 * @param <T>               the return type of the action
	 */
	static <T> DatabaseAction<Connection, T> query(
			DatabaseAction<Connection, PreparedStatement> statementProvider,
			DatabaseAction<ResultSet, T> action) {
		return statementProvider.andThen(ps -> {
			try (ResultSet rs = ps.executeQuery()) {
				return action.execute(rs);
			} finally {
				ps.close();
			}
		});
	}

	/**
	 * Constructs a DatabaseAction performing a SELECT-like query on the input Connection
	 * and feeding the ResultSet to the given action.
	 *
	 * @param query  the SQL query to execute
	 * @param action the action to execute on the result set
	 * @param <T>    the return type of the action
	 */
	static <T> DatabaseAction<Connection, T> query(String query, DatabaseAction<ResultSet, T> action) {
		return query(con -> con.prepareStatement(query), action);
	}

	/**
	 * Constructs a DatabaseAction performing a SELECT-like query on the input Connection
	 * and feeding the ResultSet to the given action.
	 *
	 * @param statementProvider a action producing a PreparedStatement to execute
	 * @param action            the action to execute on the result set
	 */
	static SimpleDatabaseAction<Connection> query(
			DatabaseAction<Connection, PreparedStatement> statementProvider,
			SimpleDatabaseAction<ResultSet> action) {
		return query(statementProvider, action.asDatabaseAction()).asSimpleDatabaseAction();
	}

	/**
	 * Constructs a DatabaseAction performing a SELECT-like query on the input Connection
	 * and feeding the ResultSet to the given action.
	 *
	 * @param query  the SQL query to execute
	 * @param action the action to execute on the result set
	 */
	static SimpleDatabaseAction<Connection> query(String query, SimpleDatabaseAction<ResultSet> action) {
		return query(con -> con.prepareStatement(query), action);
	}


	/**
	 * Constructs a DatabaseAction performing an UPDATE-like query on the input Connection
	 * and returning the number of rows affected.
	 *
	 * @param statementProvider a action producing a PreparedStatement to execute
	 */
	static DatabaseAction<Connection, Integer> execute(DatabaseAction<Connection, PreparedStatement> statementProvider) {
		return statementProvider.andThen(PreparedStatement::executeUpdate);
	}

	/**
	 * Constructs a DatabaseAction performing an UPDATE-like query on the input Connection
	 * and returning the number of rows affected.
	 *
	 * @param query the SQL query to execute
	 */
	static DatabaseAction<Connection, Integer> execute(String query) {
		return execute(con -> con.prepareStatement(query));
	}

	/**
	 * Constructs a DatabaseAction that returns a PreparedStatement for the input Connection.
	 *
	 * @param query the SQL query to prepare
	 * @param args  arguments for placeholders
	 */
	static DatabaseAction<Connection, PreparedStatement> prepare(String query, Object... args) {
		return con -> {
			PreparedStatement ps = con.prepareStatement(query);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			return ps;
		};
	}

	/**
	 * Constructs a DatabaseAction that collects all rows from a result set into a list.
	 *
	 * @param adapter an adapter used to read the result set into Java objects
	 * @param <T>     the type of objects produced by the adapter
	 */
	static <T> DatabaseAction<ResultSet, List<T>> collect(DatabaseAction<ResultSetReader, T> adapter) {
		return rs -> {
			ArrayList<T> list = new ArrayList<>();
			ResultSetReader rsr = new ResultSetReader(rs);
			while (rs.next()) {
				list.add(adapter.execute(rsr));
				rsr.reset();
			}
			return list;
		};
	}

	/**
	 * Constructs a DatabaseAction that reads the first row a result set.
	 *
	 * @param adapter an adapter used to read the result set into Java objects
	 * @param <T>     the type of objects produced by the adapter
	 */
	static <T> DatabaseAction<ResultSet, Optional<T>> first(DatabaseAction<ResultSetReader, T> adapter) {
		return rs -> {
			if (rs.next()) {
				return Optional.ofNullable(adapter.execute(new ResultSetReader(rs)));
			} else {
				return Optional.empty();
			}
		};
	}
}
