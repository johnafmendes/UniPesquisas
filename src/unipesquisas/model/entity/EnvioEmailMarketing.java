package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class EnvioEmailMarketing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idenvioemailmarketing;
	private Date data;
	private Integer numeroemails;
	private String tempo;

	public Integer getIdenvioemailmarketing() {
		return idenvioemailmarketing;
	}
	public void setIdenvioemailmarketing(Integer idenvioemailmarketing) {
		this.idenvioemailmarketing = idenvioemailmarketing;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getNumeroemails() {
		return numeroemails;
	}
	public void setNumeroemails(Integer numeroemails) {
		this.numeroemails = numeroemails;
	}
	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

}
