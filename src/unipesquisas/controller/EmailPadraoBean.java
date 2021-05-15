package unipesquisas.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.model.entity.EmailPadrao;
import unipesquisas.model.service.EmailPadraoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class EmailPadraoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private EmailPadraoService epService;
	
	private EmailPadrao ep;
	
	@PostConstruct
	public void init() throws ServiceException{
		ep = epService.carregar();
	}
	
	public String salvar() throws Exception {
		try {
			if(epService.salvar(ep)) {
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

	public EmailPadrao getEp() throws ServiceException {
		if(ep == null){
			ep = new EmailPadrao();
		}
		return ep;
	}

	public void setEp(EmailPadrao ep) {
		this.ep = ep;
	}

}

