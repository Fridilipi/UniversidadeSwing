package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Disciplina;
import util.DatabaseConnection;

public class DAODisciplina {

	public Boolean incluirDisciplina(Disciplina disciplina) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO Disciplina (Id, Nome) VALUES (?, ?)");

			statement.setString(1, disciplina.getId());
			statement.setString(2, disciplina.getNome());

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

	public Disciplina consultarDisciplina(String id) throws Exception {
		Disciplina disciplina = null;

		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = connection.prepareStatement("SELECT Id, Nome FROM Disciplina WHERE Id = ?");

			statement.setString(1, id);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				disciplina = new Disciplina();

				disciplina.setId(resultSet.getString("Id"));
				disciplina.setNome(resultSet.getString("Nome"));
			}

			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}

		return disciplina;
	}

	public Boolean alterarDisciplina(Disciplina disciplina) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("UPDATE Disciplina SET Nome = ? WHERE Id = ?");

			statement.setString(1, disciplina.getNome());
			statement.setString(2, disciplina.getId());

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

	public Boolean removerDisciplina(String id) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("DELETE FROM Disciplina WHERE Id = ?");

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
