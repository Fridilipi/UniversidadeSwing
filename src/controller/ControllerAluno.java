package controller;

import java.util.ArrayList;

import dao.DAOAluno;
import model.Aluno;

public class ControllerAluno {

    private static ControllerAluno controllerAluno;
    private final DAOAluno daoAluno;

    public ControllerAluno() {
        daoAluno = new DAOAluno();
    }

    public static ControllerAluno getInstance() {
        if (controllerAluno == null) {
            controllerAluno = new ControllerAluno();
        }

        return controllerAluno;
    }

    private Boolean validarAluno(Aluno aluno) throws Exception {
        if (aluno.getDataNascimento() == null) {
            throw new Exception("Aluno.DataNascimento is null");
        }

        if (aluno.getId().isEmpty()) {
            throw new Exception("Aluno.Id is null");
        }

        if (aluno.getGraduacao().getId().isEmpty()) {
            throw new Exception("Aluno.IdGraduacao is null");
        }

        if (aluno.getNome().isEmpty()) {
            throw new Exception("Aluno.Nome is null");
        }

        return true;
    }

    public Boolean incluirAluno(Aluno aluno) throws Exception {
        Boolean result = false;

        if (validarAluno(aluno)) {
            result = daoAluno.incluirAluno(aluno);
        }

        return result;
    }

    public Aluno consultarAluno(String id) throws Exception {
        return daoAluno.consultarAluno(id);
    }

    public Boolean alterarAluno(Aluno aluno) throws Exception {
        Boolean result = false;

        if (validarAluno(aluno)) {
            result = daoAluno.alterarAluno(aluno);
        }

        return result;
    }

    public Boolean removerAluno(String id) throws Exception {
        return daoAluno.removerAluno(id);
    }

    public ArrayList<Aluno> listarAluno() throws Exception {
        return daoAluno.listarAluno();
    }

}
