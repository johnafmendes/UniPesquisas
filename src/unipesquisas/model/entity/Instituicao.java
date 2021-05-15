package unipesquisas.model.entity;

import java.io.Serializable;

public class Instituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6564288532959217133L;
	private Integer idinstituicao;
	private String instituicao;
	private String endereco;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private String diretor;
	private String dddtel;
	private String telefone;
	private String dddcel;
	private String telefonecel;
	private String email;
	private Empresa empresa;
	private String sigla;
	private Integer total;

	public Integer getIdinstituicao() {
		return idinstituicao;
	}
	public void setIdinstituicao(Integer idinstituicao) {
		this.idinstituicao = idinstituicao;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
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
	public String getDiretor() {
		return diretor;
	}
	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getTelefonecel() {
		return telefonecel;
	}
	public void setTelefonecel(String telefonecel) {
		this.telefonecel = telefonecel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getDddtel() {
		return dddtel;
	}
	public void setDddtel(String dddtel) {
		this.dddtel = dddtel;
	}
	public String getDddcel() {
		return dddcel;
	}
	public void setDddcel(String dddcel) {
		this.dddcel = dddcel;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
