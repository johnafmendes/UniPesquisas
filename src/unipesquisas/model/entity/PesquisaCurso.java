package unipesquisas.model.entity;

import java.io.Serializable;

public class PesquisaCurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idpesquisacurso;
	private Pesquisa pesquisa;
	private Curso curso;
	private StatusCandidato statuscandidato;
	
	public Pesquisa getPesquisa() {
		return pesquisa;
	}
	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}
	public Integer getIdpesquisacurso() {
		return idpesquisacurso;
	}
	public void setIdpesquisacurso(Integer idpesquisacurso) {
		this.idpesquisacurso = idpesquisacurso;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
