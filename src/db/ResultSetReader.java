package db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetReader {
	private ResultSet rs;
	private int cursor = 1;

	public ResultSetReader(ResultSet rs) {
		this.rs = rs;
	}

	public void reset() {
		cursor = 1;
	}

	public int nextInt() throws SQLException {
		return rs.getInt(cursor++);
	}

	public String nextString() throws SQLException {
		return rs.getString(cursor++);
	}
}
