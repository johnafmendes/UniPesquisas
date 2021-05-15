package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class RespostaPesquisa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idrespostapesquisa;
	private PesquisaPergunta pesquisapergunta;
	private Candidato candidato;
	private String resposta;
	private Date data;
	
	public Integer getIdrespostapesquisa() {
		return idrespostapesquisa;
	}
	public void setIdrespostapesquisa(Integer idrespostapesquisa) {
		this.idrespostapesquisa = idrespostapesquisa;
	}
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
}
