package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Progresso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idprogressodetalhado;
	private String observacao;
	private Candidato candidato;
	private Usuario usuario;
	private Integer idempresa;
	private Date data;

	public Integer getIdprogressodetalhado() {
		return idprogressodetalhado;
	}
	public void setIdprogressodetalhado(Integer idprogressodetalhado) {
		this.idprogressodetalhado = idprogressodetalhado;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Candidato getCandidato() {
		return candidato;
	}
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(Integer idempresa) {
		this.idempresa = idempresa;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
}