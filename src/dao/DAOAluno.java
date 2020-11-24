package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import controller.ControllerGraduacao;
import model.Aluno;
import model.Graduacao;
import util.DatabaseConnection;

public class DAOAluno {

    public Boolean incluirAluno(Aluno aluno) throws Exception {
        boolean result;
        Connection connection = DatabaseConnection.getConnection();

        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO Aluno (Id, IdGraduacao, Nome, DataNascimento) VALUES (?, ?, ?, ?)");

            statement.setString(1, aluno.getId());
            statement.setString(2, aluno.getGraduacao().getId());
            statement.setString(3, aluno.getNome());
            statement.setDate(4, new Date(aluno.getDataNascimento().getTime()));

            result = (statement.executeUpdate() > 0);

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

    public Aluno consultarAluno(String id) throws Exception {
        ControllerGraduacao controllerGraduacao;

        Aluno aluno = null;
        Graduacao graduacao;

        try {
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection
                    .prepareStatement("SELECT Id, IdGraduacao, Nome, DataNascimento FROM Aluno WHERE Id = ?");

            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                aluno = new Aluno();

                aluno.setDataNascimento(resultSet.getDate("DataNascimento"));
                aluno.setId(resultSet.getString("Id"));
                aluno.setNome(resultSet.getString("Nome"));

                controllerGraduacao = ControllerGraduacao.getInstance();
                graduacao = controllerGraduacao.consultarGraduacao(resultSet.getString("IdGraduacao"));

                aluno.setGraduacao(graduacao);
            }

            resultSet.close();
            statement.close();
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }

        return aluno;
    }

    public Boolean alterarAluno(Aluno aluno) throws Exception {
        boolean result;
        Connection connection = DatabaseConnection.getConnection();

        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection
                    .prepareStatement("UPDATE Aluno SET IdGraduacao = ?, Nome = ?, DataNascimento = ? WHERE Id = ?");

            statement.setString(1, aluno.getGraduacao().getId());
            statement.setString(2, aluno.getNome());
            statement.setDate(3, new Date(aluno.getDataNascimento().getTime()));
            statement.setString(4, aluno.getId());

            result = (statement.executeUpdate() > 0);

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

    public Boolean removerAluno(String id) throws Exception {
        boolean result;
        Connection connection = DatabaseConnection.getConnection();

        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement("DELETE FROM Aluno WHERE Id = ?");

            statement.setString(1, id);

            result = (statement.executeUpdate() > 0);

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

    public ArrayList<Aluno> listarAluno() throws Exception {
        ControllerGraduacao controllerGraduacao;

        Aluno aluno;

        ArrayList<Aluno> arrayListAluno;

        try {
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection
                    .prepareStatement("SELECT Id, IdGraduacao, Nome, DataNascimento FROM Aluno");

            ResultSet resultSet = statement.executeQuery();

            arrayListAluno = new ArrayList<>();

            while (resultSet.next()) {
                aluno = new Aluno();

                aluno.setDataNascimento(resultSet.getDate("DataNascimento"));
                aluno.setId(resultSet.getString("Id"));
                aluno.setNome(resultSet.getString("Nome"));

                controllerGraduacao = ControllerGraduacao.getInstance();
                aluno.setGraduacao(controllerGraduacao.consultarGraduacao(resultSet.getString("IdGraduacao")));

                arrayListAluno.add(aluno);
            }
        } catch (Exception exception) {
            System.err.println("DAOAluno listarAluno() - " + exception.getMessage());

            throw new Exception(exception.getMessage());
        }

        return arrayListAluno;
    }

    public DefaultTableModel listarTableModel() throws Exception {
        Connection connection = DatabaseConnection.getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement sql = connection
                    .prepareStatement("SELECT Id, IdGraduacao, Nome, DataNascimento FROM Aluno");
            ResultSet resultado = sql.executeQuery();

            Vector<String> cabecalho = new Vector<>();

            cabecalho.add("Id");
            cabecalho.add("IdGraduacao");
            cabecalho.add("Nome");
            cabecalho.add("Data de Nascimento");

            Vector<Vector<Object>> lista = new Vector<>();
            ResultSetMetaData md = resultado.getMetaData();
            int numColunas = md.getColumnCount();

            while (resultado.next()) {

                Vector<Object> linha = new Vector<>();

                for (int i = 1; i <= numColunas; i++) {
                    linha.add(resultado.getObject(i));
                }

                lista.add(linha);
            }
            sql.close();
            return new DefaultTableModel(lista, cabecalho);
        } catch (Exception exception) {
            System.err.println("DAOAluno listarTableModel() - " + exception.getMessage());

            throw new Exception(exception.getMessage());
        }
    }

}
