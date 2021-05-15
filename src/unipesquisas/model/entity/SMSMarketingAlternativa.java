package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SMSMarketingAlternativa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idsmsmarketingalternativa;
	private SMSMarketing smsmarketing;
	private Pergunta pergunta;
	private String alternativa;
	private Pesquisa pesquisa;
	private Date data;
	private StatusCandidato statuscandidato;

	public Integer getIdsmsmarketingalternativa() {
		return idsmsmarketingalternativa;
	}
	public void setIdsmsmarketingalternativa(Integer idsmsmarketingalternativa) {
		this.idsmsmarketingalternativa = idsmsmarketingalternativa;
	}
	public SMSMarketing getSmsmarketing() {
		return smsmarketing;
	}
	public void setSmsmarketing(SMSMarketing smsmarketing) {
		this.smsmarketing = smsmarketing;
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
