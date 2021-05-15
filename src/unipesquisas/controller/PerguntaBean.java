package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.service.PerguntaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PerguntaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private PerguntaService perguntaService;
	
	@Inject
	private Diversos diversos;
	
	private List<Pergunta> listaPerguntas;
	private Pergunta pergunta;
	private Integer tipoPesquisaPergunta;
	private String idTituloPergunta;
	
	public String salvar() throws Exception {
		try {
			
			if (pergunta.getIdpergunta() == null) {
				
				pergunta.setIdempresa(diversos.getIdEmpresa());

				if(perguntaService.salvar(pergunta)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(perguntaService.atualizar(pergunta)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			return null;
		} catch (ValidationException e) {
			showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public String alterar(Integer idPergunta) throws Exception {
		pergunta = perguntaService.carregar(idPergunta);
		return null;
	}
	
	public String limpar(){
		pergunta = null;
		return null;
	}

	public String excluir(Integer idPergunta) {
		try{
			if(perguntaService.excluir(idPergunta)){
				showMessage("- Registro excluído com sucesso.", FacesMessage.SEVERITY_INFO);
				return null;
			}else{
				showMessage("- Não foi possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
		} catch (ServiceException e){
			showMessage("- Não foi possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}
	
	public String pesquisarPergunta() throws ServiceException{
		if(tipoPesquisaPergunta==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Título da Pergunta.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPergunta==1){//codigo
			try{
				listaPerguntas = perguntaService.listarPerguntaPorID(Integer.parseInt(idTituloPergunta), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo pergunta
			listaPerguntas = perguntaService.listarPesquisaPergunta(idTituloPergunta, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaPerguntas = perguntaService.listarPerguntas(diversos.getIdEmpresa());
		return null;
	}
	

	public List<Pergunta> getListaPerguntas() throws ServiceException {
		return listaPerguntas;
	}

	public void setListaPerguntas(List<Pergunta> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

	public Pergunta getPergunta() {
		if(pergunta == null){
			pergunta = new Pergunta();
		}
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public Integer getTipoPesquisaPergunta() {
		return tipoPesquisaPergunta;
	}

	public void setTipoPesquisaPergunta(Integer tipoPesquisaPergunta) {
		this.tipoPesquisaPergunta = tipoPesquisaPergunta;
	}

	public String getIdTituloPergunta() {
		return idTituloPergunta;
	}

	public void setIdTituloPergunta(String idTituloPergunta) {
		this.idTituloPergunta = idTituloPergunta;
	}

}