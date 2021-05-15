package unipesquisas.model.entity;

import java.io.Serializable;

public class CandidatoCurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idcandidatocurso;
	private Candidato candidato;
	private Curso curso;
	private Integer matriculado;
	private Integer ordem;
	private Integer confirmado;
	private String outrocurso;
	
	public Integer getIdcandidatocurso() {
		return idcandidatocurso;
	}
	public void setIdcandidatocurso(Integer idcandidatocurso) {
		this.idcandidatocurso = idcandidatocurso;
	}
	public Integer getMatriculado() {
		return matriculado;
	}
	public void setMatriculado(Integer matriculado) {
		this.matriculado = matriculado;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public Integer getConfirmado() {
		return confirmado;
	}
	public void setConfirmado(Integer confirmado) {
		this.confirmado = confirmado;
	}
	public String getOutrocurso() {
		return outrocurso;
	}
	public void setOutrocurso(String outrocurso) {
		this.outrocurso = outrocurso;
	}
	public Candidato getCandidato() {
		return candidato;
	}
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
}
