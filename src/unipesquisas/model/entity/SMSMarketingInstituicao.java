package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SMSMarketingInstituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idsmsmarketinginstituicao;
	private SMSMarketing smsmarketing;
	private Instituicao instituicao;
	private Date data;
	private StatusCandidato statuscandidato;

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
	public Integer getIdsmsmarketinginstituicao() {
		return idsmsmarketinginstituicao;
	}
	public void setIdsmsmarketinginstituicao(Integer idsmsmarketinginstituicao) {
		this.idsmsmarketinginstituicao = idsmsmarketinginstituicao;
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
