package unipesquisas.model.entity;

import java.io.Serializable;

public class Curso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idcurso;
	private String curso;
	private AreasCurso areacurso;
	private Instituicao instituicao;
	private Integer matriculado;
	
	public Integer getIdcurso() {
		return idcurso;
	}
	public void setIdcurso(Integer idcurso) {
		this.idcurso = idcurso;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public AreasCurso getAreacurso() {
		return areacurso;
	}
	public void setAreacurso(AreasCurso areacurso) {
		this.areacurso = areacurso;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Integer getMatriculado() {
		return matriculado;
	}
	public void setMatriculado(Integer matriculado) {
		this.matriculado = matriculado;
	}
	
}
