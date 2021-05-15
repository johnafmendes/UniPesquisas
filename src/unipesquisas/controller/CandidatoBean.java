package unipesquisas.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.CandidatoEmpresa;
import unipesquisas.model.entity.ConfiguracoesEmpresa;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.AutenticacaoService;
import unipesquisas.model.service.CandidatoEmpresaService;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.ConfiguracoesEmpresaService;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class CandidatoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private ConfiguracoesEmpresaService confEmpresaService; 
	
	@Inject
	private CandidatoService candidatoService;
	
	@Inject
	private StatusCandidatoService scService;
	
	@Inject
	private EscolaridadeService escolaridadeService;
	
	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private CandidatoEmpresaService ceService;
	
	@Inject
	private AutenticacaoService autenticacaoService;
	
	@Inject
	private Diversos diversos;
	
	private Candidato candidato;
	private CandidatoEmpresa candidatoEmpresa;
	private CandidatoEmpresa ce;
	private ConfiguracoesEmpresa confEmpresa;
	private List<Candidato> listaCandidatos;
	private List<Escolaridade> listaEscolaridades;
	private List<StatusCandidato> listaStatusCandidato;
	private String senha;
	private String emailAtual;
	private boolean manterSenha;
	private String senhaAtual;
	private Integer idEmpresa;
	private Integer tipoPesquisaCandidato;
	private String idNomeCandidato;
	
	public CandidatoBean()  {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();

		emailAtual = request.getParameter("email");
		senhaAtual = request.getParameter("id");
		
		try{
			idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
			session.setAttribute("idEmpresa", idEmpresa);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		
	}
	
	@PostConstruct
	public void init() throws Exception{
		if(diversos.getIdCandidato() != null){
//			System.out.println("{="+diversos.getIdCandidato());
			alterar(diversos.getIdCandidato());
		}
	}

	public String salvar() throws Exception {
		candidatoEmpresa.setRecebersms(1);
		candidatoEmpresa.setIdempresa(diversos.getIdEmpresa());
		candidatoEmpresa.setIdcandidato(candidato.getIdcandidato());
		try {
			if(!diversos.emailValido(candidato.getEmail())){
				showMessage("- e-Mail inválido.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if (candidato.getIdcandidato() == null) {
				if(candidatoService.existeEmail(candidato.getEmail())){
					showMessage("- e-Mail já existe.", FacesMessage.SEVERITY_ERROR);
					return null;
				}
				
				if (!candidato.getPassword().equals(senha) || senha.equals("")){
					showMessage("- Senhas inválidas", FacesMessage.SEVERITY_ERROR);
					return null;
				}else{
					candidato.setPassword(diversos.getMD5(candidato.getPassword()));
				}
				
				if(candidatoService.salvar(candidato, candidatoEmpresa, diversos.getAdmin())) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(!emailAtual.equals(candidato.getEmail())){
					if(candidatoService.existeEmail(candidato.getEmail())){
						showMessage("- e-Mail já existe.", FacesMessage.SEVERITY_ERROR);
						return null;
					}
				}
				
				if(!manterSenha){
					if (!candidato.getPassword().equals(senha) || senha.equals("")){
						showMessage("- Senhas inválidas", FacesMessage.SEVERITY_ERROR);
						return null;
					}else{
						candidato.setPassword(diversos.getMD5(candidato.getPassword()));
					}
				}else{
					candidato.setPassword(senhaAtual);
				}

				if(candidatoService.atualizar(candidato, candidatoEmpresa, diversos.getAdmin())){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String salvarEContinuar() throws Exception {
		candidatoEmpresa.setRecebersms(1);
		candidatoEmpresa.setIdempresa(diversos.getIdEmpresa());
		candidatoEmpresa.setIdcandidato(candidato.getIdcandidato());
		candidatoEmpresa.getStatuscandidato().setIdstatuscandidato(diversos.getIdStatusCandidato());
		try {
			FacesContext fc0 = FacesContext.getCurrentInstance();
			ExternalContext ec0 = fc0.getExternalContext();
			HttpServletRequest request0 = (HttpServletRequest) ec0.getRequest();
			HttpSession session0 = request0.getSession();

			try{
				idEmpresa = Integer.parseInt(request0.getParameter("idEmpresa"));			
				session0.setAttribute("idEmpresa", idEmpresa);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			if(diversos.getIdEmpresa() == null){
				showMessage("- Atualize a página antes de prosseguir (F5)", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(candidato.getEmail().equals("") || candidato.getEmail() == null){
				showMessage("- Digite um endereço de e-Mail.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(!diversos.emailValido(candidato.getEmail())){
				showMessage("- e-Mail inválido.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if (candidato.getIdcandidato() == null) {
				if(candidatoService.existeEmail(candidato.getEmail())){
					showMessage("- e-Mail já existe.", FacesMessage.SEVERITY_ERROR);
					return null;
				}
				
				if (!candidato.getPassword().equals(senha) || senha.equals("")){
					showMessage("- Senhas inválidas", FacesMessage.SEVERITY_ERROR);
					return null;
				}else{
					candidato.setPassword(diversos.getMD5(candidato.getPassword()));
				}
				diversos.setAdmin(0);
				
				if(candidatoService.salvar(candidato, candidatoEmpresa, diversos.getAdmin())) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
					diversos.setIdCandidato(candidato.getIdcandidato());
					FacesContext fc = FacesContext.getCurrentInstance(); 
					HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
					final String url = request.getRequestURI(); 
					if(url.contains("Candidatos.jsf")){
						FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatoInstituicoes.jsf");
					}else{
						FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatoInstituicoesSA.jsf?idEmpresa="+diversos.getIdEmpresa()+"&idCandidato="+diversos.getIdCandidato());
					}
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(!emailAtual.equals(candidato.getEmail())){
					if(candidatoService.existeEmail(candidato.getEmail())){
						showMessage("- e-Mail já existe.", FacesMessage.SEVERITY_ERROR);
						return null;
					}
				}
				
				if(!manterSenha){
					if (!candidato.getPassword().equals(senha) || senha.equals("")){
						showMessage("- Senhas inválidas", FacesMessage.SEVERITY_ERROR);
						return null;
					}else{
						candidato.setPassword(diversos.getMD5(candidato.getPassword()));
					}
				}else{
					candidato.setPassword(senhaAtual);
				}
				
				if(candidatoService.atualizar(candidato, candidatoEmpresa, diversos.getAdmin())){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
					FacesContext fc = FacesContext.getCurrentInstance(); 
					HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
					final String url = request .getRequestURI(); 
					if(url.contains("Candidatos.jsf")){
						FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatoInstituicoes.jsf");
					}else{
						FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatoInstituicoesSA.jsf");
					}
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String verificarEContinuar() throws Exception {
		try {
			if(candidato.getEmail().equals("") || candidato.getEmail() == null){
				showMessage("- Digite um endereço de e-Mail.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(!diversos.emailValido(candidato.getEmail())){
				showMessage("- e-Mail inválido.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(diversos.getIdEmpresa() == null){
				showMessage("- Atualize a página antes de prosseguir (F5)", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			confEmpresa = confEmpresaService.carregar(diversos.getIdEmpresa());
			diversos.setIdStatusCandidato(confEmpresa.getStatuscandidato().getIdstatuscandidato());
			if(candidatoService.existeEmail(candidato.getEmail())){
				if(!candidatoService.existeEmailEmpresa(candidato.getEmail(), diversos.getIdEmpresa())){
					FacesContext.getCurrentInstance().getExternalContext().redirect("LoginIntegracaoContasSA.jsf?idEmpresa="+diversos.getIdEmpresa());
				}else{
					FacesContext.getCurrentInstance().getExternalContext().redirect("default.jsf?msg=Cadastro Existente! Faça o Login.&idEmpresa="+diversos.getIdEmpresa());
					diversos.encerraSessao();
				}
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatosSA.jsf?idEmpresa="+diversos.getIdEmpresa());
			}
			
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String integrarContasEContinuar() throws Exception {
		try {
			if(candidato.getEmail().equals("") || candidato.getEmail() == null){
				showMessage("- Digite um endereço de e-Mail.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(diversos.getIdEmpresa() == null){
				showMessage("- Atualize a página antes de prosseguir (F5)", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(candidatoService.existeEmail(candidato.getEmail())){
				if(!candidatoService.existeEmailEmpresa(candidato.getEmail(), diversos.getIdEmpresa())){
					if (candidato.getPassword().equals("")){
						showMessage("- Senha inválida", FacesMessage.SEVERITY_ERROR);
						return null;
					}else{
						candidato.setPassword(diversos.getMD5(candidato.getPassword()));
					}
			
					candidato = autenticacaoService.validarLoginCandidato(candidato);
					if(candidato != null){
						ce = new CandidatoEmpresa();
						ce.setIdcandidato(candidato.getIdcandidato());
						ce.setIdempresa(diversos.getIdEmpresa());
						ce.setReceberemail(1);
						ce.setRecebersms(1);
						diversos.setAdmin(0);
						if(ceService.salvar(ce, diversos.getAdmin())){
							showMessage("- Contas Integradas com sucesso.", FacesMessage.SEVERITY_INFO);
							alterar(candidato.getIdcandidato());
							diversos.setIdCandidato(candidato.getIdcandidato());
							FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatosSA.jsf");
						}else{
							showMessage("- Falha ao Integradas Contas. Contate o administrador do sistema: suporte@unipesquisas.com", FacesMessage.SEVERITY_ERROR);
							return null;
						}
					} else {
						showMessage("- Erro ao autenticar. Verifique o e-Mail e Senha.", FacesMessage.SEVERITY_ERROR);
						return null;
					}
				}else{
					FacesContext.getCurrentInstance().getExternalContext().redirect("default.jsf?msg=Cadastro Existente! Faça o Login.&idEmpresa="+diversos.getIdEmpresa());
				}
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatoSA.jsf?idEmpresa="+diversos.getIdEmpresa());
			}
			
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String criarNovaSenha() throws ServiceException, IOException{
		if (!candidato.getPassword().equals(senha) || senha.equals("")){
			showMessage("- Senhas inválidas", FacesMessage.SEVERITY_ERROR);
			return null;
		}else{
			candidato.setPassword(diversos.getMD5(candidato.getPassword()));
		}
		if(candidatoService.criarNovaSenha(emailAtual, senhaAtual, candidato.getPassword())){
			FacesContext.getCurrentInstance().getExternalContext().redirect("NovaSenhaFimSucesso.jsf");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("NovaSenhaFimError.jsf");
		}
		diversos.encerraSessao();
		return null;
	}
	
	public String cancelarAssinatura() throws ServiceException, IOException{
		if (senhaAtual.equals("") || idEmpresa.equals("") || emailAtual.equals("")){
			showMessage("- e-Mail, ID ou Empresa inválidos", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(candidatoService.cancelarAssinatura(emailAtual, senhaAtual, idEmpresa)){
			FacesContext.getCurrentInstance().getExternalContext().redirect("CancelarAssinaturaFimSucesso.jsf");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("CancelarAssinaturaFimError.jsf");
		}
		diversos.encerraSessao();
		return null;
	}
	
	public String enviarEmailNovaSenha() throws Exception{
		if(diversos.emailValido(candidato.getEmail())){
			String senha = candidatoService.getSenha(candidato.getEmail());
			candidatoService.enviarEmailNovaSenha(candidato.getEmail(), senha);
			FacesContext.getCurrentInstance().getExternalContext().redirect("EsqueciSenhaFim.jsf");
			diversos.encerraSessao();
		}else{
			showMessage("- e-Mail inválido.", FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	public String alterar(Integer idCandidato) throws Exception {
		candidato = candidatoService.carregar(idCandidato);
		emailAtual = candidato.getEmail();
		senhaAtual = candidato.getPassword();
		candidatoEmpresa = ceService.carregar(idCandidato, diversos.getIdEmpresa());
		return null;
	}
	
	public String limpar(){
		candidato = null;
		senhaAtual = null;
		senha = null;
		emailAtual = null;
		candidatoEmpresa = null;
		return null;
	}

	public String excluir(Integer idCandidato) {
		try{
			if(candidatoService.excluir(idCandidato)){
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
	
	public String pesquisarCandidato() throws ServiceException{
		if(tipoPesquisaCandidato==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Nome do Estudante.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaCandidato==1){//codigo
			try{
				listaCandidatos = candidatoService.listarCandidatosPorID(Integer.parseInt(idNomeCandidato), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//nome candidato
			listaCandidatos = candidatoService.listarCandidatosPorNome(idNomeCandidato, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaCandidatos = candidatoService.listarCandidatos(diversos.getIdEmpresa());
		return null;
	}

	public Candidato getCandidato() {
		if(candidato == null){
			candidato = new Candidato();
			candidato.setEscolaridade(new Escolaridade());
		}
		return candidato;
	}
	
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	
	public List<Candidato> getListaCandidatos() throws ServiceException {
		return listaCandidatos;
	}
	
	public void setListaCandidatos(List<Candidato> listaCandidatos) {
		this.listaCandidatos = listaCandidatos;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isManterSenha() {
		return manterSenha;
	}

	public void setManterSenha(boolean manterSenha) {
		this.manterSenha = manterSenha;
	}

	public String getEmailAtual() {
		return emailAtual;
	}

	public void setEmailAtual(String emailAtual) {
		this.emailAtual = emailAtual;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public List<Escolaridade> getListaEscolaridades() throws ServiceException {
		listaEscolaridades = escolaridadeService.listarEscolaridades();
		return listaEscolaridades;
	}

	public void setListaEscolaridades(List<Escolaridade> listaEscolaridades) {
		this.listaEscolaridades = listaEscolaridades;
	}

	public CandidatoEmpresa getCe() {
		if(ce == null){
			ce = new CandidatoEmpresa();
		}
		return ce;
	}

	public void setCe(CandidatoEmpresa ce) {
		this.ce = ce;
	}

	public Integer getTipoPesquisaCandidato() {
		return tipoPesquisaCandidato;
	}

	public void setTipoPesquisaCandidato(Integer tipoPesquisaCandidato) {
		this.tipoPesquisaCandidato = tipoPesquisaCandidato;
	}

	public String getIdNomeCandidato() {
		return idNomeCandidato;
	}

	public void setIdNomeCandidato(String idNomeCandidato) {
		this.idNomeCandidato = idNomeCandidato;
	}

	public CandidatoEmpresa getCandidatoEmpresa() {
		if(candidatoEmpresa == null){
			candidatoEmpresa = new CandidatoEmpresa();
			candidatoEmpresa.setStatuscandidato(new StatusCandidato());
		}
		return candidatoEmpresa;
	}

	public void setCandidatoEmpresa(CandidatoEmpresa candidatoEmpresa) {
		this.candidatoEmpresa = candidatoEmpresa;
	}

	public Diversos getDiversos() {
		return diversos;
	}

	public void setDiversos(Diversos diversos) {
		this.diversos = diversos;
	}

	public List<StatusCandidato> getListaStatusCandidato() throws ServiceException {
		listaStatusCandidato = scService.listarStatus(diversos.getIdEmpresa());
		return listaStatusCandidato;
	}

	public void setListaStatusCandidato(List<StatusCandidato> listaStatusCandidato) {
		this.listaStatusCandidato = listaStatusCandidato;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
