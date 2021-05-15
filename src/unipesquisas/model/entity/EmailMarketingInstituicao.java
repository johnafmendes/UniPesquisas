package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class EmailMarketingInstituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idemailmarketinginstituicao;
	private EmailMarketing emailmarketing;
	private Instituicao instituicao;
	private Date data;
	private StatusCandidato statuscandidato;
	

	public EmailMarketing getEmailmarketing() {
		return emailmarketing;
	}
	public void setEmailmarketing(EmailMarketing emailmarketing) {
		this.emailmarketing = emailmarketing;
	}
	public Integer getIdemailmarketinginstituicao() {
		return idemailmarketinginstituicao;
	}
	public void setIdemailmarketinginstituicao(Integer idemailmarketinginstituicao) {
		this.idemailmarketinginstituicao = idemailmarketinginstituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
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
