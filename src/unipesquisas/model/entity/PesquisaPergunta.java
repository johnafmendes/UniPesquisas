package unipesquisas.model.entity;

import java.io.Serializable;

public class PesquisaPergunta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;
	
	private Integer idpesquisapergunta;
	private Pesquisa pesquisa;
	private Pergunta pergunta;

	public Integer getIdpesquisapergunta() {
		return idpesquisapergunta;
	}
	public void setIdpesquisapergunta(Integer idpesquisapergunta) {
		this.idpesquisapergunta = idpesquisapergunta;
	}
	public Pesquisa getPesquisa() {
		return pesquisa;
	}
	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}
	public Pergunta getPergunta() {
		return pergunta;
	}
	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

}
