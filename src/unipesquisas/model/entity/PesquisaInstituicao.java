package unipesquisas.model.entity;

import java.io.Serializable;

public class PesquisaInstituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idpesquisainstituicao;
	private Pesquisa pesquisa;
	private Instituicao instituicao;
	private StatusCandidato statuscandidato;
	
	public Pesquisa getPesquisa() {
		return pesquisa;
	}
	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}
	public Integer getIdpesquisainstituicao() {
		return idpesquisainstituicao;
	}
	public void setIdpesquisainstituicao(Integer idpesquisainstituicao) {
		this.idpesquisainstituicao = idpesquisainstituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
