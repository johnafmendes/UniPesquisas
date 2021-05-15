package unipesquisas.model.entity;

import java.io.Serializable;

public class ConfiguracoesEmpresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private StatusCandidato statuscandidato;
	private Empresa empresa;

	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}

	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


}