package controller;

import dao.DAOAtividadePraticaSupervisionada;
import model.AtividadePraticaSupervisionada;

public class ControllerAtividadePraticaSupervisionada {

	private static ControllerAtividadePraticaSupervisionada controllerAtividadePraticaSupervisionada;
	private DAOAtividadePraticaSupervisionada daoAtividadePraticaSupervisionada;

	public ControllerAtividadePraticaSupervisionada() throws Exception {
		daoAtividadePraticaSupervisionada = new DAOAtividadePraticaSupervisionada();
	}

	public static ControllerAtividadePraticaSupervisionada getInstance() throws Exception {
		if (controllerAtividadePraticaSupervisionada == null) {
			controllerAtividadePraticaSupervisionada = new ControllerAtividadePraticaSupervisionada();
		}

		return controllerAtividadePraticaSupervisionada;
	}

	private Boolean validarAtividadePraticaSupervisionada(AtividadePraticaSupervisionada atividadePraticaSupervisionada)
			throws Exception {
		if (atividadePraticaSupervisionada.getAluno().getId().isEmpty()) {
			throw new Exception("AtividadePraticaSupervisionada.Aluno.id is empty");
		}

		if (atividadePraticaSupervisionada.getDisciplina().getId().isEmpty()) {
			throw new Exception("AtividadePraticaSupervisionada.Disciplina.id is empty");
		}

		if (atividadePraticaSupervisionada.getId().isEmpty()) {
			throw new Exception("AtividadePraticaSupervisionada.id is empty");
		}

		if (atividadePraticaSupervisionada.getNota().getId().isEmpty()) {
			throw new Exception("AtividadePraticaSupervisionada.Nota.id is empty");
		}

		if (atividadePraticaSupervisionada.getValorNota().isNaN()) {
			throw new Exception("AtividadePraticaSupervisionada.ValorNota is NaN");
		}

		return true;
	}

	public Boolean incluirAtividadePraticaSupervisionada(AtividadePraticaSupervisionada atividadePraticaSupervisionada)
			throws Exception {
		Boolean result = false;

		if (validarAtividadePraticaSupervisionada(atividadePraticaSupervisionada)) {
			result = daoAtividadePraticaSupervisionada
					.incluirAtividadePraticaSupervisionada(atividadePraticaSupervisionada);
		}

		return result;
	}

	public AtividadePraticaSupervisionada consultarAtividadePraticaSupervisionada(String id) throws Exception {
		return daoAtividadePraticaSupervisionada.consultarAtividadePraticaSupervisionada(id);
	}

	public Boolean alterarAtividadePraticaSupervisionada(AtividadePraticaSupervisionada atividadePraticaSupervisionada)
			throws Exception {
		Boolean result = false;

		if (validarAtividadePraticaSupervisionada(atividadePraticaSupervisionada)) {
			result = daoAtividadePraticaSupervisionada
					.alterarAtividadePraticaSupervisionada(atividadePraticaSupervisionada);
		}

		return result;
	}

	public Boolean removerAtividadePraticaSupervisionada(String id) throws Exception {
		return daoAtividadePraticaSupervisionada.removerAtividadePraticaSupervisionada(id);
	}

}
