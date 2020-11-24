package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Graduacao;
import util.DatabaseConnection;

public class DAOGraduacao {

	public Boolean incluirGraduacao(Graduacao graduacao) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("INSERT INTO Graduacao (Id, Nome) VALUES (?, ?)");

			statement.setString(1, graduacao.getId());
			statement.setString(2, graduacao.getNome());

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

	public Graduacao consultarGraduacao(String id) throws Exception {
		Graduacao graduacao = null;

		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = connection.prepareStatement("SELECT Id, Nome FROM Graduacao WHERE Id = ?");

			statement.setString(1, id);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				graduacao = new Graduacao();

				graduacao.setId(resultSet.getString("Id"));
				graduacao.setNome(resultSet.getString("Nome"));
			}

			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}

		return graduacao;
	}

	public Boolean alterarGraduacao(Graduacao graduacao) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("UPDATE Graduacao SET Nome = ? WHERE Id = ?");

			statement.setString(1, graduacao.getNome());
			statement.setString(2, graduacao.getId());

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

	public Boolean removerGraduacao(String id) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("DELETE FROM Graduacao WHERE Id = ?");

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

	public ArrayList<Graduacao> listarGraduacao() throws Exception {
		ArrayList<Graduacao> arrayListGraduacao = null;

		Graduacao graduacao = null;

		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = connection.prepareStatement("SELECT Id, Nome FROM Graduacao");

			ResultSet resultSet = statement.executeQuery();

			arrayListGraduacao = new ArrayList<Graduacao>();

			while (resultSet.next()) {
				graduacao = new Graduacao();

				graduacao.setId(resultSet.getString("Id"));
				graduacao.setNome(resultSet.getString("Nome"));

				arrayListGraduacao.add(graduacao);
			}
		} catch (Exception exception) {
			System.err.println("DAOGraduacao listarGraduacao() - " + exception.getMessage());

			throw new Exception(exception.getMessage());
		}

		return arrayListGraduacao;
	}

}
