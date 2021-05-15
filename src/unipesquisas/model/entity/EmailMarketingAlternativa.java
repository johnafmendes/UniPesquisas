package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class EmailMarketingAlternativa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idemailmarketingalternativa;
	private EmailMarketing emailmarketing;
	private Pergunta pergunta;
	private String alternativa;
	private Pesquisa pesquisa;
	private Date data;
	private StatusCandidato statuscandidato;
	

	public EmailMarketing getEmailmarketing() {
		return emailmarketing;
	}
	public void setEmailmarketing(EmailMarketing emailmarketing) {
		this.emailmarketing = emailmarketing;
	}
	public Integer getIdemailmarketingalternativa() {
		return idemailmarketingalternativa;
	}
	public void setIdemailmarketingalternativa(Integer idemailmarketingalternativa) {
		this.idemailmarketingalternativa = idemailmarketingalternativa;
	}
	public Pergunta getPergunta() {
		return pergunta;
	}
	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}
	public String getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(String alternativa) {
		this.alternativa = alternativa;
	}
	public Pesquisa getPesquisa() {
		return pesquisa;
	}
	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
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
