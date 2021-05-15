package unipesquisas.model.entity;

import java.io.Serializable;

public class CandidatoEmpresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idcandidatoempresa;
	private Integer idcandidato;
	private Integer idempresa;
	private Integer receberemail;
	private Integer recebersms;
	private String codigo;
	private StatusCandidato statuscandidato;

	public Integer getIdcandidato() {
		return idcandidato;
	}
	public void setIdcandidato(Integer idcandidato) {
		this.idcandidato = idcandidato;
	}
	public Integer getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(Integer idempresa) {
		this.idempresa = idempresa;
	}
	public Integer getIdcandidatoempresa() {
		return idcandidatoempresa;
	}
	public void setIdcandidatoempresa(Integer idcandidatoempresa) {
		this.idcandidatoempresa = idcandidatoempresa;
	}
	public Integer getReceberemail() {
		return receberemail;
	}
	public void setReceberemail(Integer receberemail) {
		this.receberemail = receberemail;
	}
	public Integer getRecebersms() {
		return recebersms;
	}
	public void setRecebersms(Integer recebersms) {
		this.recebersms = recebersms;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
