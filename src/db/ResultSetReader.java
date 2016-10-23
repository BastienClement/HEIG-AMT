package db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A JDBC ResultSet reader with a cursor.
 */
public class ResultSetReader {
	/**
	 * The result set being read
	 */
	private ResultSet rs;

	/**
	 * The current reading cursor index
	 */
	private int cursor = 1;

	/**
	 * Constructs a new ResultSet reader.
	 *
	 * @param rs the result set to read
	 */
	public ResultSetReader(ResultSet rs) {
		this.rs = rs;
	}

	/**
	 * Resets the cursor to the first column.
	 */
	public void reset() {
		cursor = 1;
	}

	/**
	 * Returns the next int value and advances the cursor.
	 *
	 * @return the next int value
	 * @throws SQLException
	 */
	public int nextInt() throws SQLException {
		return rs.getInt(cursor++);
	}

	/**
	 * Returns the next string value and advances the cursor.
	 *
	 * @return the next string value
	 * @throws SQLException
	 */
	public String nextString() throws SQLException {
		return rs.getString(cursor++);
	}
}
