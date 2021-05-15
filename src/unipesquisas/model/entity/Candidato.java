package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Candidato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idcandidato;
	private String nome;
	private Date datanascimento;
	private String dddres;
	private String telefoneres;
	private String dddcel;
	private String telefonecel;
	private String email;
	private String password;
	private Escolaridade escolaridade;
	private String sexo;

	public Integer getIdcandidato() {
		return idcandidato;
	}
	public void setIdcandidato(Integer idcandidato) {
		this.idcandidato = idcandidato;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDatanascimento() {
		return datanascimento;
	}
	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}
	public String getTelefoneres() {
		return telefoneres;
	}
	public void setTelefoneres(String telefoneres) {
		this.telefoneres = telefoneres;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getDddres() {
		return dddres;
	}
	public void setDddres(String dddres) {
		this.dddres = dddres;
	}
	public String getDddcel() {
		return dddcel;
	}
	public void setDddcel(String dddcel) {
		this.dddcel = dddcel;
	}
	
}
