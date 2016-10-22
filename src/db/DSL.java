package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface DSL {
	static <T> DatabaseAction<Connection, T> query(
		DatabaseAction<Connection, PreparedStatement> statementProvider,
		DatabaseAction<ResultSet, T> action)
	{
		return statementProvider.andThen(ps -> {
			try (ResultSet rs = ps.executeQuery()) {
				return action.execute(rs);
			} finally {
				ps.close();
			}
		});
	}

	static <T> DatabaseAction<Connection, T> query(String query, DatabaseAction<ResultSet, T> action) {
		return query(con -> con.prepareStatement(query), action);
	}

	static SimpleDatabaseAction<Connection> query(
		DatabaseAction<Connection, PreparedStatement> statementProvider,
		SimpleDatabaseAction<ResultSet> action)
	{
		return query(statementProvider, action.asDatabaseAction()).asSimpleDatabaseAction();
	}

	static SimpleDatabaseAction<Connection> query(String query, SimpleDatabaseAction<ResultSet> action) {
		return query(con -> con.prepareStatement(query), action);
	}

	static DatabaseAction<Connection, Integer> execute(DatabaseAction<Connection, PreparedStatement> statementProvider) {
		return statementProvider.andThen(PreparedStatement::executeUpdate);
	}

	static DatabaseAction<Connection, Integer> execute(String query) {
		return execute(con -> con.prepareStatement(query));
	}

	static DatabaseAction<Connection, PreparedStatement> prepare(String query, Object... args) {
		return con -> {
			PreparedStatement ps = con.prepareStatement(query);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			return ps;
		};
	}

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
