package unipesquisas.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.ConfiguracoesEmpresa;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.ConfiguracoesEmpresaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class ConfiguracoesEmpresaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private ConfiguracoesEmpresaService ceService;
	
	@Inject	
	private StatusCandidatoService scService;
	
	@Inject
	private Diversos diversos;
	
	private List<StatusCandidato> listaStatusCandidato;
	private ConfiguracoesEmpresa ce;
	
	@PostConstruct
	public void init() throws ServiceException{
		ce = ceService.carregar(diversos.getIdEmpresa());
	}
	
	public String salvar() throws Exception {
		try {
			if(ceService.salvar(ce)) {
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

	public ConfiguracoesEmpresa getCe() {
		if(ce == null){
			ce = new ConfiguracoesEmpresa();
			ce.setEmpresa(new Empresa());
		}
		return ce;
	}

	public void setCe(ConfiguracoesEmpresa ce) {
		this.ce = ce;
	}

	public List<StatusCandidato> getListaStatusCandidato() throws ServiceException {
		listaStatusCandidato = scService.listarStatus(diversos.getIdEmpresa());
		return listaStatusCandidato;
	}

	public void setListaStatusCandidato(List<StatusCandidato> listaStatusCandidato) {
		this.listaStatusCandidato = listaStatusCandidato;
	}

}