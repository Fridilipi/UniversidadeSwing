package controller;

import dao.DAOTrabalhoAcademico;
import model.TrabalhoAcademico;

public class ControllerTrabalhoAcademico {

	private static ControllerTrabalhoAcademico controllerTrabalhoAcademico;
	private DAOTrabalhoAcademico daoTrabalhoAcademico;

	public ControllerTrabalhoAcademico() throws Exception {
		daoTrabalhoAcademico = new DAOTrabalhoAcademico();
	}

	public static ControllerTrabalhoAcademico getInstance() throws Exception {
		if (controllerTrabalhoAcademico == null) {
			controllerTrabalhoAcademico = new ControllerTrabalhoAcademico();
		}

		return controllerTrabalhoAcademico;
	}

	private Boolean validarTrabalhoAcademico(TrabalhoAcademico trabalhoAcademico) throws Exception {
		if (trabalhoAcademico.getAluno().getId().isEmpty()) {
			throw new Exception("TrabalhoAcademico.Aluno.id is empty");
		}

		if (trabalhoAcademico.getDisciplina().getId().isEmpty()) {
			throw new Exception("TrabalhoAcademico.Disciplina.id is empty");
		}

		if (trabalhoAcademico.getId().isEmpty()) {
			throw new Exception("TrabalhoAcademico.id is empty");
		}

		if (trabalhoAcademico.getNota().getId().isEmpty()) {
			throw new Exception("TrabalhoAcademico.Nota.id is empty");
		}

		if (trabalhoAcademico.getValorNota().isNaN()) {
			throw new Exception("TrabalhoAcademico.ValorNota is NaN");
		}

		return true;
	}

	public Boolean incluirTrabalhoAcademico(TrabalhoAcademico trabalhoAcademico) throws Exception {
		Boolean result = false;

		if (validarTrabalhoAcademico(trabalhoAcademico)) {
			result = daoTrabalhoAcademico.incluirTrabalhoAcademico(trabalhoAcademico);
		}

		return result;
	}

	public TrabalhoAcademico consultarTrabalhoAcademico(String id) throws Exception {
		return daoTrabalhoAcademico.consultarTrabalhoAcademico(id);
	}

	public Boolean alterarTrabalhoAcademico(TrabalhoAcademico trabalhoAcademico) throws Exception {
		Boolean result = false;

		if (validarTrabalhoAcademico(trabalhoAcademico)) {
			result = daoTrabalhoAcademico.alterarTrabalhoAcademico(trabalhoAcademico);
		}

		return result;
	}

	public Boolean removerTrabalhoAcademico(String id) throws Exception {
		return daoTrabalhoAcademico.removerTrabalhoAcademico(id);
	}

}
