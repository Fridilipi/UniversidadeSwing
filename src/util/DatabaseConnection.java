package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	static DatabaseConnection databaseConnection;
	Connection connection;

	private DatabaseConnection() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/universidade", "dba", "dba123");
		} catch (SQLException exception) {
			System.err.println(exception.getMessage());
		}
	}

	public static Connection getConnection() {
		try {
			if (databaseConnection == null) {
				databaseConnection = new DatabaseConnection();
			}
		} catch (Exception exception) {
			System.err.println("DatabaseConnection getConnection() - " + exception.getMessage());
		}

		return databaseConnection.connection;
	}

	public void close() throws Exception {
		try {
			connection.close();
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}
	
}
