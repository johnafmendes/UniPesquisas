package unipesquisas.controller;


import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.controller.AbstractBean;
import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class StatusCandidatoBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390197682176838852L;

	@Inject
	private StatusCandidatoService scService;
	
	@Inject
	private Diversos diversos;
	
	private StatusCandidato statusCandidato;
	private List<StatusCandidato> listaStatus;

	/**
	 * Salva ou atualiza o status
	 */
	public String salvar() throws Exception {
		try {
			if (statusCandidato.getIdstatuscandidato() == null) {
				statusCandidato.getEmpresa().setIdempresa(diversos.getIdEmpresa());
				if(scService.salvar(statusCandidato)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
			} else {
				if(scService.atualizar(statusCandidato)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			}
			return null;
		
		} catch (ValidationException e) {
			// Ocorreu um erro de validação de negócio
			addMessageToRequest(e.getMessage());
			showMessage("Erro: " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
			return null;
		}
	}

	/**
	 * Carrega uma instituicao existente para que ele possa ser alterado
	 */
	public String alterar(Integer idStatusCandidato) throws Exception {
		statusCandidato = scService.carregar(idStatusCandidato);
		return null;
	}
	
	public String limpar(){
		statusCandidato = null;
		return null;
	}
	
	public String excluir(Integer idStatusCandidato) {
		try{
			if(scService.excluir(idStatusCandidato)){
				showMessage("- Registro excluído com sucesso.", FacesMessage.SEVERITY_INFO);
				return null;
			}else{
				showMessage("- Não é possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
		} catch (ServiceException e){
			showMessage("- Não é possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}

	public StatusCandidato getStatusCandidato() {
		if(statusCandidato == null){
			statusCandidato = new StatusCandidato();
			statusCandidato.setEmpresa(new Empresa());
		}
		return statusCandidato;
	}

	public void setStatusCandidato(StatusCandidato statusCandidato) {
		this.statusCandidato = statusCandidato;
	}

	public List<StatusCandidato> getListaStatus() throws ServiceException {
		listaStatus = scService.listarStatus(diversos.getIdEmpresa());
		return listaStatus;
	}

	public void setListaStatus(List<StatusCandidato> listaStatus) {
		this.listaStatus = listaStatus;
	}


}
