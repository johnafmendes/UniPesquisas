package unipesquisas.model.entity;

import java.io.Serializable;

public class StatusCandidato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idstatuscandidato;
	private String status;
	private Empresa empresa;
	private Integer total;

	public Integer getIdstatuscandidato() {
		return idstatuscandidato;
	}
	public void setIdstatuscandidato(Integer idstatuscandidato) {
		this.idstatuscandidato = idstatuscandidato;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
