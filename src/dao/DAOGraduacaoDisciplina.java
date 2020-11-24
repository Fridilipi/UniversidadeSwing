package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.GraduacaoDisciplina;
import util.DatabaseConnection;

public class DAOGraduacaoDisciplina {

	public Boolean incluirGraduacaoDisciplina(GraduacaoDisciplina graduacaoDisciplina) throws Exception {
		Boolean result = false;
		Connection connection = DatabaseConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO GraduacaoDisciplina (IdGraduacao, IdDisciplina) VALUES (?, ?)");

			statement.setString(1, graduacaoDisciplina.getGraduacao().getId());
			statement.setString(2, graduacaoDisciplina.getDisciplina().getId());

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
