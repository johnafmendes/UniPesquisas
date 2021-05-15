package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class RespostaPesquisaInstituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idrespostapesquisainstituicao;
	private PesquisaInstituicao pesquisainstituicao;
	private PesquisaPergunta pesquisapergunta;
	private Candidato candidato;
	private String resposta;
	private Date data;
	private StatusCandidato statuscandidato;
	
	public PesquisaPergunta getPesquisapergunta() {
		return pesquisapergunta;
	}
	public void setPesquisapergunta(PesquisaPergunta pesquisapergunta) {
		this.pesquisapergunta = pesquisapergunta;
	}
	public Candidato getCandidato() {
		return candidato;
	}
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getIdrespostapesquisainstituicao() {
		return idrespostapesquisainstituicao;
	}
	public void setIdrespostapesquisainstituicao(
			Integer idrespostapesquisainstituicao) {
		this.idrespostapesquisainstituicao = idrespostapesquisainstituicao;
	}
	public PesquisaInstituicao getPesquisainstituicao() {
		return pesquisainstituicao;
	}
	public void setPesquisainstituicao(PesquisaInstituicao pesquisainstituicao) {
		this.pesquisainstituicao = pesquisainstituicao;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
