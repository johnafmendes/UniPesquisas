package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class RespostaPesquisaEscolaridade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idrespostapesquisaescolaridade;
	private PesquisaEscolaridade pesquisaescolaridade;
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
	public Integer getIdrespostapesquisaescolaridade() {
		return idrespostapesquisaescolaridade;
	}
	public void setIdrespostapesquisaescolaridade(
			Integer idrespostapesquisaescolaridade) {
		this.idrespostapesquisaescolaridade = idrespostapesquisaescolaridade;
	}
	public PesquisaEscolaridade getPesquisaescolaridade() {
		return pesquisaescolaridade;
	}
	public void setPesquisaescolaridade(PesquisaEscolaridade pesquisaescolaridade) {
		this.pesquisaescolaridade = pesquisaescolaridade;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
