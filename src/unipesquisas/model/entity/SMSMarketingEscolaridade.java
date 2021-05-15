package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SMSMarketingEscolaridade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idsmsmarketingescolaridade;
	private SMSMarketing smsmarketing;
	private Escolaridade escolaridade;
	private Date data;
	private StatusCandidato statuscandidato;
	

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
	public Integer getIdsmsmarketingescolaridade() {
		return idsmsmarketingescolaridade;
	}
	public void setIdsmsmarketingescolaridade(Integer idsmsmarketingescolaridade) {
		this.idsmsmarketingescolaridade = idsmsmarketingescolaridade;
	}
	public SMSMarketing getSmsmarketing() {
		return smsmarketing;
	}
	public void setSmsmarketing(SMSMarketing smsmarketing) {
		this.smsmarketing = smsmarketing;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
