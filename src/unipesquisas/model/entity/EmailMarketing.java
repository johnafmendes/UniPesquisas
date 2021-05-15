package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class EmailMarketing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idemailmarketing;
	private String assunto;
	private String mensagem;
	private Date data;
	private Usuario usuario;
	private Integer idempresa;
	private Integer status;

	
	public Integer getIdemailmarketing() {
		return idemailmarketing;
	}
	public void setIdemailmarketing(Integer idemailmarketing) {
		this.idemailmarketing = idemailmarketing;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
