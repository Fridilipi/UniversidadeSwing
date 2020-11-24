package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controller.ControllerAluno;
import controller.ControllerDisciplina;
import controller.ControllerNota;
import model.Aluno;
import model.Disciplina;
import model.Nota;
import model.TrabalhoAcademico;
import util.DatabaseConnection;

public class DAOTrabalhoAcademico {

	public Boolean incluirTrabalhoAcademico(TrabalhoAcademico trabalhoAcademico) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO TrabalhoAcademico (Id, IdAluno, IdDisciplina, IdNota, ValorNota) VALUES (?, ?, ?, ?, ?)");

			statement.setString(1, trabalhoAcademico.getId());
			statement.setString(2, trabalhoAcademico.getAluno().getId());
			statement.setString(3, trabalhoAcademico.getDisciplina().getId());
			statement.setString(4, trabalhoAcademico.getNota().getId());
			statement.setDouble(5, trabalhoAcademico.getValorNota());

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

	public TrabalhoAcademico consultarTrabalhoAcademico(String id) throws Exception {
		ControllerAluno controllerAluno = null;
		ControllerDisciplina controllerDisciplina = null;
		ControllerNota controllerNota = null;

		Aluno aluno = null;
		TrabalhoAcademico trabalhoAcademico = null;
		Disciplina disciplina = null;
		Nota nota = null;

		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"SELECT Id, IdAluno, IdDisciplina, IdNota, ValorNota FROM TrabalhoAcademico WHERE Id = ?");

			statement.setString(1, id);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				trabalhoAcademico = new TrabalhoAcademico();

				trabalhoAcademico.setId(resultSet.getString("Id"));
				trabalhoAcademico.setValorNota(resultSet.getDouble("ValorNota"));

				controllerAluno = ControllerAluno.getInstance();
				aluno = controllerAluno.consultarAluno(resultSet.getString("IdAluno"));
				trabalhoAcademico.setAluno(aluno);

				controllerDisciplina = ControllerDisciplina.getInstance();
				disciplina = controllerDisciplina.consultarDisciplina(resultSet.getString("IdDisciplina"));
				trabalhoAcademico.setDisciplina(disciplina);

				controllerNota = ControllerNota.getInstance();
				nota = controllerNota.consultarNota(resultSet.getString("IdNota"));
				trabalhoAcademico.setNota(nota);
			}

			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}

		return trabalhoAcademico;
	}

	public Boolean alterarTrabalhoAcademico(TrabalhoAcademico trabalhoAcademico) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement(
					"UPDATE TrabalhoAcademico SET IdAluno = ?, IdDisciplina = ?, IdNota = ?, ValorNota = ? WHERE Id = ?");

			statement.setString(1, trabalhoAcademico.getAluno().getId());
			statement.setString(2, trabalhoAcademico.getDisciplina().getId());
			statement.setString(3, trabalhoAcademico.getNota().getId());
			statement.setDouble(4, trabalhoAcademico.getValorNota());
			statement.setString(4, trabalhoAcademico.getId());

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

	public Boolean removerTrabalhoAcademico(String id) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection.prepareStatement("DELETE FROM TrabalhoAcademico WHERE Id = ?");

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
