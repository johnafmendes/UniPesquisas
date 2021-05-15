package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class EmailMarketingCurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idemailmarketingcurso;
	private EmailMarketing emailmarketing;
	private Curso curso;
	private Date data;
	private StatusCandidato statuscandidato;
	

	public EmailMarketing getEmailmarketing() {
		return emailmarketing;
	}
	public void setEmailmarketing(EmailMarketing emailmarketing) {
		this.emailmarketing = emailmarketing;
	}
	public Integer getIdemailmarketingcurso() {
		return idemailmarketingcurso;
	}
	public void setIdemailmarketingcurso(Integer idemailmarketingcurso) {
		this.idemailmarketingcurso = idemailmarketingcurso;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
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
