package controller;

import dao.DAODisciplina;
import model.Disciplina;

public class ControllerDisciplina {

	private static ControllerDisciplina controllerDisciplina;
	private DAODisciplina daoDisciplina;

	public ControllerDisciplina() throws Exception {
		daoDisciplina = new DAODisciplina();
	}

	public static ControllerDisciplina getInstance() throws Exception {
		if (controllerDisciplina == null) {
			controllerDisciplina = new ControllerDisciplina();
		}

		return controllerDisciplina;
	}

	private Boolean validarDisciplina(Disciplina disciplina) throws Exception {
		if (disciplina.getId().isEmpty()) {
			throw new Exception("Disciplina.id is null");
		}

		if (disciplina.getNome().isEmpty()) {
			throw new Exception("Disciplina.nome is null");
		}

		return true;
	}

	public Boolean incluirDisciplina(Disciplina disciplina) throws Exception {
		Boolean result = false;

		if (validarDisciplina(disciplina)) {
			result = daoDisciplina.incluirDisciplina(disciplina);
		}

		return result;
	}

	public Disciplina consultarDisciplina(String id) throws Exception {
		return daoDisciplina.consultarDisciplina(id);
	}

	public Boolean alterarDisciplina(Disciplina disciplina) throws Exception {
		Boolean result = false;

		if (validarDisciplina(disciplina)) {
			result = daoDisciplina.alterarDisciplina(disciplina);
		}

		return result;
	}

	public Boolean removerDisciplina(String id) throws Exception {
		return daoDisciplina.removerDisciplina(id);
	}

}
