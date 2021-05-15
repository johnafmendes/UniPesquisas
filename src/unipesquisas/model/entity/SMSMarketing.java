package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SMSMarketing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idsmsmarketing;
	private String assunto;
	private String mensagem;
	private Date data;
	private Usuario usuario;
	private Empresa empresa;
	private Integer status;

	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIdsmsmarketing() {
		return idsmsmarketing;
	}
	public void setIdsmsmarketing(Integer idsmsmarketing) {
		this.idsmsmarketing = idsmsmarketing;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
