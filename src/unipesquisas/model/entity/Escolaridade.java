package unipesquisas.model.entity;

import java.io.Serializable;

public class Escolaridade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idescolaridade;
	private String escolaridade;
	private Integer total;

	public Integer getIdescolaridade() {
		return idescolaridade;
	}
	public void setIdescolaridade(Integer idescolaridade) {
		this.idescolaridade = idescolaridade;
	}
	public String getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

}
