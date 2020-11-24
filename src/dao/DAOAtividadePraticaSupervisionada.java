package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controller.ControllerAluno;
import controller.ControllerDisciplina;
import controller.ControllerNota;
import model.Aluno;
import model.AtividadePraticaSupervisionada;
import model.Disciplina;
import model.Nota;
import util.DatabaseConnection;

public class DAOAtividadePraticaSupervisionada {

	public Boolean incluirAtividadePraticaSupervisionada(AtividadePraticaSupervisionada atividadePraticaSupervisionada)
			throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO AtividadePraticaSupervisionada (Id, IdAluno, IdDisciplina, IdNota, ValorNota) VALUES (?, ?, ?, ?, ?)");

			statement.setString(1, atividadePraticaSupervisionada.getId());
			statement.setString(2, atividadePraticaSupervisionada.getAluno().getId());
			statement.setString(3, atividadePraticaSupervisionada.getDisciplina().getId());
			statement.setString(4, atividadePraticaSupervisionada.getNota().getId());
			statement.setDouble(5, atividadePraticaSupervisionada.getValorNota());

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

	public AtividadePraticaSupervisionada consultarAtividadePraticaSupervisionada(String id) throws Exception {
		ControllerAluno controllerAluno = null;
		ControllerDisciplina controllerDisciplina = null;
		ControllerNota controllerNota = null;

		Aluno aluno = null;
		AtividadePraticaSupervisionada atividadePraticaSupervisionada = null;
		Disciplina disciplina = null;
		Nota nota = null;

		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"SELECT Id, IdAluno, IdDisciplina, IdNota, ValorNota FROM AtividadePraticaSupervisionada WHERE Id = ?");

			statement.setString(1, id);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				atividadePraticaSupervisionada = new AtividadePraticaSupervisionada();

				atividadePraticaSupervisionada.setId(resultSet.getString("Id"));
				atividadePraticaSupervisionada.setValorNota(resultSet.getDouble("ValorNota"));

				controllerAluno = ControllerAluno.getInstance();
				aluno = controllerAluno.consultarAluno(resultSet.getString("IdAluno"));
				atividadePraticaSupervisionada.setAluno(aluno);

				controllerDisciplina = ControllerDisciplina.getInstance();
				disciplina = controllerDisciplina.consultarDisciplina(resultSet.getString("IdDisciplina"));
				atividadePraticaSupervisionada.setDisciplina(disciplina);

				controllerNota = ControllerNota.getInstance();
				nota = controllerNota.consultarNota(resultSet.getString("IdNota"));
				atividadePraticaSupervisionada.setNota(nota);
			}

			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}

		return atividadePraticaSupervisionada;
	}

	public Boolean alterarAtividadePraticaSupervisionada(AtividadePraticaSupervisionada atividadePraticaSupervisionada)
			throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement(
					"UPDATE AtividadePraticaSupervisionada SET IdAluno = ?, IdDisciplina = ?, IdNota = ?, ValorNota = ? WHERE Id = ?");

			statement.setString(1, atividadePraticaSupervisionada.getAluno().getId());
			statement.setString(2, atividadePraticaSupervisionada.getDisciplina().getId());
			statement.setString(3, atividadePraticaSupervisionada.getNota().getId());
			statement.setDouble(4, atividadePraticaSupervisionada.getValorNota());
			statement.setString(4, atividadePraticaSupervisionada.getId());

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

	public Boolean removerAtividadePraticaSupervisionada(String id) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM AtividadePraticaSupervisionada WHERE Id = ?");

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
