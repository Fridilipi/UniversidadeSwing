package controller;

import dao.DAONota;
import model.Nota;

public class ControllerNota {

	private static ControllerNota controllerNota;
	private DAONota daoNota;

	public ControllerNota() throws Exception {
		daoNota = new DAONota();
	}

	public static ControllerNota getInstance() throws Exception {
		if (controllerNota == null) {
			controllerNota = new ControllerNota();
		}

		return controllerNota;
	}

	private Boolean validarNota(Nota nota) throws Exception {
		if (nota.getId().isEmpty()) {
			throw new Exception("Nota.id is null");
		}

		if (nota.getNome().isEmpty()) {
			throw new Exception("Nota.nome is null");
		}

		return true;
	}

	public Boolean incluirNota(Nota nota) throws Exception {
		Boolean result = false;

		if (validarNota(nota)) {
			result = daoNota.incluirNota(nota);
		}

		return result;
	}

	public Nota consultarNota(String id) throws Exception {
		return daoNota.consultarNota(id);
	}

	public Boolean alterarNota(Nota nota) throws Exception {
		Boolean result = false;

		if (validarNota(nota)) {
			result = daoNota.alterarNota(nota);
		}

		return result;
	}

	public Boolean removerNota(String id) throws Exception {
		return daoNota.removerNota(id);
	}

}
