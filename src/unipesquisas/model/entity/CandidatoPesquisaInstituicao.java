package unipesquisas.model.entity;

import java.io.Serializable;

public class CandidatoPesquisaInstituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idcandidatopesquisainstituicao;
	private Candidato candidato;
	private PesquisaInstituicao pesquisainstituicao;
	private Integer status;
	
	public Candidato getCandidato() {
		return candidato;
	}
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIdcandidatopesquisainstituicao() {
		return idcandidatopesquisainstituicao;
	}
	public void setIdcandidatopesquisainstituicao(
			Integer idcandidatopesquisainstituicao) {
		this.idcandidatopesquisainstituicao = idcandidatopesquisainstituicao;
	}
	public PesquisaInstituicao getPesquisainstituicao() {
		return pesquisainstituicao;
	}
	public void setPesquisainstituicao(PesquisaInstituicao pesquisainstituicao) {
		this.pesquisainstituicao = pesquisainstituicao;
	}
	
}
