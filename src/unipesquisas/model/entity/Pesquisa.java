package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Pesquisa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idpesquisa;
	private String titulo;
	private String descricao;
	private Date data;
	private Usuario usuario;
	private Integer idempresa;
	private Integer status;

	public Integer getIdpesquisa() {
		return idpesquisa;
	}
	public void setIdpesquisa(Integer idpesquisa) {
		this.idpesquisa = idpesquisa;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
