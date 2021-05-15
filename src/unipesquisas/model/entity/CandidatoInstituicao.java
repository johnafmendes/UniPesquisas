package unipesquisas.model.entity;

import java.io.Serializable;

public class CandidatoInstituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idcandidatoinstituicao;
	private Candidato candidato;
	private Instituicao instituicao;
	
	public Candidato getCandidato() {
		return candidato;
	}
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	public Integer getIdcandidatoinstituicao() {
		return idcandidatoinstituicao;
	}
	public void setIdcandidatoinstituicao(Integer idcandidatoinstituicao) {
		this.idcandidatoinstituicao = idcandidatoinstituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	
}
