package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class EscolaridadeBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private EscolaridadeService escolaridadeService;
	
	private Escolaridade escolaridade;
	private List<Escolaridade> listaEscolaridades;
	
	public String salvar() throws Exception {
		try {
			if (escolaridade.getIdescolaridade() == null) {
				
				if(escolaridadeService.salvar(escolaridade)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				
				if(escolaridadeService.atualizar(escolaridade)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			getListaEscolaridades();
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public String alterar(Integer idEscolaridade) throws Exception {
		escolaridade = escolaridadeService.carregar(idEscolaridade);
		return null;
	}
	
	public String limpar(){
		escolaridade = null;
		return null;
	}

	public String excluir(Integer idEscolaridade) {
		try{
			if(escolaridadeService.excluir(idEscolaridade)){
				showMessage("- Registro excluído com sucesso.", FacesMessage.SEVERITY_INFO);
				return null;
			}else{
				showMessage("- Erro ao excluir registro.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
		} catch (ServiceException e){
			showMessage("Erro: " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
			return null;
		}
	}

	public EscolaridadeService getEscolaridadeService() {
		return escolaridadeService;
	}

	public void setEscolaridadeService(EscolaridadeService escolaridadeService) {
		this.escolaridadeService = escolaridadeService;
	}

	public Escolaridade getEscolaridade() {
		if (escolaridade == null){
			escolaridade = new Escolaridade();
		}
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public List<Escolaridade> getListaEscolaridades() throws ServiceException {
		listaEscolaridades = escolaridadeService.listarEscolaridades();
		return listaEscolaridades;
	}

	public void setListaEscolaridades(List<Escolaridade> listaEscolaridades) {
		this.listaEscolaridades = listaEscolaridades;
	}

}
