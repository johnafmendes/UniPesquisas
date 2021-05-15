package unipesquisas.controller;

//import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.service.EmpresaService;
import unipesquisas.model.service.ServiceException;

@Named
@RequestScoped
public class DefaultBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8147938659893798051L;

	@Inject
	private EmpresaService empresaService;
	
	@Inject
	private Diversos diversos;
	
	private Empresa empresa;
	private Integer idEmpresa=0;
		
	public DefaultBean()  {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		
		try{
			idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
		}catch(NumberFormatException e){
			
		}
		session.setAttribute("idEmpresa", idEmpresa);
	}
	
//	public String linkCandidatos() throws IOException{
//		FacesContext.getCurrentInstance().getExternalContext().redirect("/Candidatos2.jsf");
//		return null;
//	}
	
	@PostConstruct
	public void carregar () throws ServiceException {
		empresa = empresaService.carregar(idEmpresa);
	}

	public EmpresaService getEmpresaService() {
		return empresaService;
	}

	public void setEmpresaService(EmpresaService empresaService) {
		this.empresaService = empresaService;
	}

	public Empresa getEmpresa() {
		if(empresa == null){
			empresa = new Empresa();
		}
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Diversos getDiversos() {
		return diversos;
	}

	public void setDiversos(Diversos diversos) {
		this.diversos = diversos;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
