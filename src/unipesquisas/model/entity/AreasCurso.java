package unipesquisas.model.entity;

import java.io.Serializable;

public class AreasCurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idareacurso;
	private String area;
	private Integer idempresa;
	private Integer total;

	public Integer getIdareacurso() {
		return idareacurso;
	}
	public void setIdareacurso(Integer idareacurso) {
		this.idareacurso = idareacurso;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(Integer idempresa) {
		this.idempresa = idempresa;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

}