package controller;

import java.util.ArrayList;

import dao.DAOGraduacao;
import model.Graduacao;

public class ControllerGraduacao {

	private static ControllerGraduacao controllerGraduacao;
	private DAOGraduacao daoGraduacao;

	public ControllerGraduacao() throws Exception {
		daoGraduacao = new DAOGraduacao();
	}

	public static ControllerGraduacao getInstance() throws Exception {
		if (controllerGraduacao == null) {
			controllerGraduacao = new ControllerGraduacao();
		}

		return controllerGraduacao;
	}

	private Boolean validarGraduacao(Graduacao graduacao) throws Exception {
		if (graduacao.getId().isEmpty()) {
			throw new Exception("Graduacao.id is null");
		}

		if (graduacao.getNome().isEmpty()) {
			throw new Exception("Graduacao.nome is null");
		}

		return true;
	}

	public Boolean incluirGraduacao(Graduacao graduacao) throws Exception {
		Boolean result = false;

		if (validarGraduacao(graduacao)) {
			result = daoGraduacao.incluirGraduacao(graduacao);
		}

		return result;
	}

	public Graduacao consultarGraduacao(String id) throws Exception {
		return daoGraduacao.consultarGraduacao(id);
	}

	public Boolean alterarGraduacao(Graduacao graduacao) throws Exception {
		Boolean result = false;

		if (validarGraduacao(graduacao)) {
			result = daoGraduacao.alterarGraduacao(graduacao);
		}

		return result;
	}

	public Boolean removerGraduacao(String id) throws Exception {
		return daoGraduacao.removerGraduacao(id);
	}

	public ArrayList<Graduacao> listarGraduacao() throws Exception {
		return daoGraduacao.listarGraduacao();
	}

}
