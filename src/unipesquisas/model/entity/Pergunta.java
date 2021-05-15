package unipesquisas.model.entity;

import java.io.Serializable;

//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;

public class Pergunta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;
	
	public enum Tipo {UnicaOpcao, MultiplaOpcoes};

	private Integer idpergunta;
	private String pergunta;
	private String alta;
	private String altb;
	private String altc;
	private String altd;
	private String alte;
	private Integer idempresa;
	
//	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	
	public Integer getIdpergunta() {
		return idpergunta;
	}
	public void setIdpergunta(Integer idpergunta) {
		this.idpergunta = idpergunta;
	}
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public String getAlta() {
		return alta;
	}
	public void setAlta(String alta) {
		this.alta = alta;
	}
	public String getAltb() {
		return altb;
	}
	public void setAltb(String altb) {
		this.altb = altb;
	}
	public String getAltc() {
		return altc;
	}
	public void setAltc(String altc) {
		this.altc = altc;
	}
	public String getAltd() {
		return altd;
	}
	public void setAltd(String altd) {
		this.altd = altd;
	}
	public String getAlte() {
		return alte;
	}
	public void setAlte(String alte) {
		this.alte = alte;
	}
	public Integer getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(Integer idempresa) {
		this.idempresa = idempresa;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
