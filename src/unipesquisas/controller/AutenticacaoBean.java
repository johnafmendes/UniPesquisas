package unipesquisas.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.Usuario;
import unipesquisas.model.service.AutenticacaoService;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.EmpresaService;
import unipesquisas.model.service.ServiceException;

@Named
@SessionScoped
public class AutenticacaoBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6074100201194046160L;
	
	@Inject
	private AutenticacaoService autenticacaoService;
	
	@Inject
	private EmpresaService empresaService;
		
	@Inject
	private CandidatoService candidatoService;
	
	@Inject
	private Diversos diversos;
	
	private Empresa empresa;
	private Candidato candidato;
	private Usuario usuario;
	private boolean autorizado;
	private Integer maxInactiveInterval;
	private Integer idEmpresa;
	
	public AutenticacaoBean (){
		HttpServletRequest request = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
		HttpSession session = request.getSession();
		maxInactiveInterval = session.getMaxInactiveInterval();
		empresa = getEmpresa();
	}
	
	@PostConstruct
	public void init(){
	}
		
	public AutenticacaoService getAutenticacaoService() {
		return autenticacaoService;
	}


	public void setAutenticacaoService(AutenticacaoService autenticacaoService) {
		this.autenticacaoService = autenticacaoService;
	}


	public Usuario getUsuario() {
		if (usuario == null){
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String login() throws ServiceException, IOException {
		usuario.setPassword(diversos.getMD5(usuario.getPassword()));
		usuario.setIdempresa(diversos.getIdEmpresa());
		usuario = autenticacaoService.validarLogin(usuario); 
		if(usuario != null){
			autorizado = true;
			
			diversos.setIdEmpresa(usuario.getIdempresa());
			diversos.setIdUsuario(usuario.getIdusuario());
			diversos.setAdmin(1);
			empresa = empresaService.carregar(usuario.getIdempresa());
			//showMessage("Bem vindo: " + empresa.getNome(), FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().getExternalContext().redirect("Painel.jsf");
			return null;
		}else{
			autorizado = false;
			//showMessage("Usuário ou senha incorretos.", FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().getExternalContext().redirect("default.jsf?msg=Acesso Negado.");
			return null;
		}
	}

	public String loginCandidato() throws ServiceException, IOException {
		candidato.setPassword(diversos.getMD5(candidato.getPassword()));
		candidato = autenticacaoService.validarLoginCandidato(candidato);
		if(candidato != null){
			autorizado = true;
			
			diversos.setIdCandidato(candidato.getIdcandidato());
			diversos.setIdEscolaridade(candidato.getEscolaridade().getIdescolaridade());
			//idEmpresa = diversos.getIdEmpresa();
			//diversos.setIdEmpresa(idEmpresa);
			diversos.setAdmin(0);
			
			//if(diversos.getIdEmpresa() > 0){
			//	FacesContext.getCurrentInstance().getExternalContext().redirect("Painel.jsf");
			//} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("EscolhaEmpresa.jsf");
			//}
			return null;
		}else{
			autorizado = false;
			//showMessage("Usuário ou senha incorretos.", FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().getExternalContext().redirect("default.jsf?msg=Acesso Negado.");
			return null;
		}
	}

	public void verificarAutorizacao() throws IOException {
		if (!autorizado) {
			showMessage("Acesso negado.", FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().getExternalContext().redirect("default.jsf?msg=Acesso Negado.");
			//redirect("default");
		}
	}
	
	public void logout () throws IOException{
		autorizado = false;
		diversos.encerraSessao();
		FacesContext.getCurrentInstance().getExternalContext().redirect("default.jsf");
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}

	public Diversos getDiversos() {
		return diversos;
	}

	public void setDiversos(Diversos diversos) {
		this.diversos = diversos;
	}

	public Integer getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(Integer maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
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

	public CandidatoService getCandidatoService() {
		return candidatoService;
	}

	public void setCandidatoService(CandidatoService candidatoService) {
		this.candidatoService = candidatoService;
	}

	public Candidato getCandidato() {
		if(candidato == null){
			candidato = new Candidato();
		}
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
		
}
