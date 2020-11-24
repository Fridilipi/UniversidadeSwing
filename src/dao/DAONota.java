package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Nota;
import util.DatabaseConnection;

public class DAONota {

	public Boolean incluirNota(Nota nota) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("INSERT INTO Nota (Id, Nome) VALUES (?, ?)");

			statement.setString(1, nota.getId());
			statement.setString(2, nota.getNome());

			result = statement.execute();

			statement.close();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception exception) {
			connection.rollback();
			connection.setAutoCommit(true);

			throw new Exception(exception.getMessage());
		}

		return result;
	}

	public Nota consultarNota(String id) throws Exception {
		Nota nota = null;

		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = connection.prepareStatement("SELECT Id, Nome FROM Nota WHERE Id = ?");

			statement.setString(1, id);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				nota = new Nota();

				nota.setId(resultSet.getString("Id"));
				nota.setNome(resultSet.getString("Nome"));
			}

			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}

		return nota;
	}

	public Boolean alterarNota(Nota nota) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("UPDATE Nota SET Nome = ? WHERE Id = ?");

			statement.setString(1, nota.getNome());
			statement.setString(2, nota.getId());

			result = statement.execute();

			statement.close();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception exception) {
			connection.rollback();
			connection.setAutoCommit(true);

			throw new Exception(exception.getMessage());
		}

		return result;
	}

	public Boolean removerNota(String id) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("DELETE FROM Nota WHERE Id = ?");

			statement.setString(1, id);

			result = statement.execute();

			statement.close();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception exception) {
			connection.rollback();
			connection.setAutoCommit(true);

			throw new Exception(exception.getMessage());
		}

		return result;
	}

}
