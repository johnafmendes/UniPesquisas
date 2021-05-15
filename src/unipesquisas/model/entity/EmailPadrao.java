package unipesquisas.model.entity;

import java.io.Serializable;

public class EmailPadrao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private String assuntoboasvindas;
	private String mensagemboasvindas;
	private String assuntolembretepesquisa;
	private String mensagemlembretepesquisa;

	public String getAssuntoboasvindas() {
		return assuntoboasvindas;
	}
	public void setAssuntoboasvindas(String assuntoboasvindas) {
		this.assuntoboasvindas = assuntoboasvindas;
	}
	public String getMensagemboasvindas() {
		return mensagemboasvindas;
	}
	public void setMensagemboasvindas(String mensagemboasvindas) {
		this.mensagemboasvindas = mensagemboasvindas;
	}
	public String getAssuntolembretepesquisa() {
		return assuntolembretepesquisa;
	}
	public void setAssuntolembretepesquisa(String assuntolembretepesquisa) {
		this.assuntolembretepesquisa = assuntolembretepesquisa;
	}
	public String getMensagemlembretepesquisa() {
		return mensagemlembretepesquisa;
	}
	public void setMensagemlembretepesquisa(String mensagemlembretepesquisa) {
		this.mensagemlembretepesquisa = mensagemlembretepesquisa;
	}

}