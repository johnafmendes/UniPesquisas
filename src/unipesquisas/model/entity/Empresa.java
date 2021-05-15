package unipesquisas.model.entity;

import java.io.File;
import java.io.Serializable;

public class Empresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6564288532959217133L;
	private Integer idempresa;
	private String nome;
	private String endereco;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private String site;
	private String email;
	private String ddd;
	private String telefone;
	private String responsavel;
	private String logomarca;
	private Integer status;
	private File arquivo;
	
	public Integer getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(Integer idempresa) {
		this.idempresa = idempresa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getLogomarca() {
		return logomarca;
	}
	public void setLogomarca(String logomarca) {
		this.logomarca = logomarca;
	}
	public File getArquivo() {
		return arquivo;
	}
	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	
}
