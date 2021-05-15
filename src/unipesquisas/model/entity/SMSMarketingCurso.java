package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SMSMarketingCurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274724511746556506L;
	
	private Integer idsmsmarketingcurso;
	private SMSMarketing smsmarketing;
	private Curso curso;
	private Date data;
	private StatusCandidato statuscandidato;
	

	public Integer getIdsmsmarketingcurso() {
		return idsmsmarketingcurso;
	}
	public void setIdsmsmarketingcurso(Integer idsmsmarketingcurso) {
		this.idsmsmarketingcurso = idsmsmarketingcurso;
	}
	public SMSMarketing getSmsmarketing() {
		return smsmarketing;
	}
	public void setSmsmarketing(SMSMarketing smsmarketing) {
		this.smsmarketing = smsmarketing;
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
