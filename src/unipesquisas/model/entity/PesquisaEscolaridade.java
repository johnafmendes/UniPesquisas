package unipesquisas.model.entity;

import java.io.Serializable;

public class PesquisaEscolaridade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idpesquisaescolaridade;
	private Pesquisa pesquisa;
	private Escolaridade escolaridade;
	private StatusCandidato statuscandidato;
	
	public Integer getIdpesquisaescolaridade() {
		return idpesquisaescolaridade;
	}
	public void setIdpesquisaescolaridade(Integer idpesquisaescolaridade) {
		this.idpesquisaescolaridade = idpesquisaescolaridade;
	}
	public Pesquisa getPesquisa() {
		return pesquisa;
	}
	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
