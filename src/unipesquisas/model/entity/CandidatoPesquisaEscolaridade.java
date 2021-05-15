package unipesquisas.model.entity;

import java.io.Serializable;

public class CandidatoPesquisaEscolaridade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idcandidatopesquisaescolaridade;
	private Candidato candidato;
	private PesquisaEscolaridade pesquisaescolaridade;
	private Integer status;
	
	public Integer getIdcandidatopesquisaescolaridade() {
		return idcandidatopesquisaescolaridade;
	}
	public void setIdcandidatopesquisaescolaridade(
			Integer idcandidatopesquisaescolaridade) {
		this.idcandidatopesquisaescolaridade = idcandidatopesquisaescolaridade;
	}
	public Candidato getCandidato() {
		return candidato;
	}
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	public PesquisaEscolaridade getPesquisaescolaridade() {
		return pesquisaescolaridade;
	}
	public void setPesquisaescolaridade(PesquisaEscolaridade pesquisaescolaridade) {
		this.pesquisaescolaridade = pesquisaescolaridade;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
