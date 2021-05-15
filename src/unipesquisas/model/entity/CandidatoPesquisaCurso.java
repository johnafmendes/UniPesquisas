package unipesquisas.model.entity;

import java.io.Serializable;

public class CandidatoPesquisaCurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idcandidatopesquisacurso;
	private Candidato candidato;
	private PesquisaCurso pesquisacurso;
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
	public Integer getIdcandidatopesquisacurso() {
		return idcandidatopesquisacurso;
	}
	public void setIdcandidatopesquisacurso(Integer idcandidatopesquisacurso) {
		this.idcandidatopesquisacurso = idcandidatopesquisacurso;
	}
	public PesquisaCurso getPesquisacurso() {
		return pesquisacurso;
	}
	public void setPesquisacurso(PesquisaCurso pesquisacurso) {
		this.pesquisacurso = pesquisacurso;
	}
	
}
