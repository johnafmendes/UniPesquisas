package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class EmailMarketingEscolaridade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idemailmarketingescolaridade;
	private EmailMarketing emailmarketing;
	private Escolaridade escolaridade;
	private Date data;
	private StatusCandidato statuscandidato;
	

	public Integer getIdemailmarketingescolaridade() {
		return idemailmarketingescolaridade;
	}
	public void setIdemailmarketingescolaridade(Integer idemailmarketingescolaridade) {
		this.idemailmarketingescolaridade = idemailmarketingescolaridade;
	}
	public EmailMarketing getEmailmarketing() {
		return emailmarketing;
	}
	public void setEmailmarketing(EmailMarketing emailmarketing) {
		this.emailmarketing = emailmarketing;
	}
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
