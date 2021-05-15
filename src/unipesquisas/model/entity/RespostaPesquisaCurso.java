package unipesquisas.model.entity;

import java.io.Serializable;
import java.util.Date;

public class RespostaPesquisaCurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private Integer idrespostapesquisacurso;
	private PesquisaCurso pesquisacurso;
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
	public Integer getIdrespostapesquisacurso() {
		return idrespostapesquisacurso;
	}
	public void setIdrespostapesquisacurso(Integer idrespostapesquisacurso) {
		this.idrespostapesquisacurso = idrespostapesquisacurso;
	}
	public PesquisaCurso getPesquisacurso() {
		return pesquisacurso;
	}
	public void setPesquisacurso(PesquisaCurso pesquisacurso) {
		this.pesquisacurso = pesquisacurso;
	}
	public StatusCandidato getStatuscandidato() {
		return statuscandidato;
	}
	public void setStatuscandidato(StatusCandidato statuscandidato) {
		this.statuscandidato = statuscandidato;
	}
	
}
