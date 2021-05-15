package unipesquisas.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.model.entity.ConfiguracoesSistema;
import unipesquisas.model.service.ConfiguracoesSistemaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class ConfiguracoesSistemaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private ConfiguracoesSistemaService csService;
	
	private ConfiguracoesSistema cs;
	
	@PostConstruct
	public void init() throws ServiceException{
		cs = csService.carregar();
	}
	
	public String salvar() throws Exception {
		try {
			if(csService.salvar(cs)) {
				showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
			}
			return null;
		} catch (ValidationException e) {
			showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public ConfiguracoesSistema getCs() {
		if(cs == null){
			cs = new ConfiguracoesSistema();
		}
		return cs;
	}

	public void setCs(ConfiguracoesSistema cs) {
		this.cs = cs;
	}


}

