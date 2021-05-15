package unipesquisas.controller;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailMarketingEscolaridade;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.EmailMarketingEscolaridadeService;
import unipesquisas.model.service.EmailMarketingService;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarEmailMarketingEscolaridadeBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject	
	private EmailMarketingEscolaridadeService emeService;

	@Inject
	private EscolaridadeService escolaridadeService;
	
	@Inject
	private EmailMarketingService emService;
	
	@Inject
	private Diversos diversos;
	
	private List<EmailMarketing> listaEmailMarketing;
	private List<Escolaridade> listaEscolaridades;
	private List<EmailMarketingEscolaridade> listaEmailMarketingEscolaridade;
	private List<StatusCandidato> listaStatusCandidato;
	private EmailMarketing em;
	private Escolaridade escolaridade;
	private EmailMarketingEscolaridade eme;
	private Integer tipoPesquisaEmail;
	private String idTituloAssunto;
	private Integer tipoPesquisaEscolaridade;
	private String idTituloEscolaridade;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(em == null || escolaridade == null || idStatusCandidato == null){
				showMessage("- Selecione um EMAIL, uma ESCOLARIDADE e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getEme();
			eme.getEmailmarketing().setIdemailmarketing(em.getIdemailmarketing());
			eme.getEscolaridade().setIdescolaridade(escolaridade.getIdescolaridade());
			eme.setData(new Date());
			eme.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(emeService.salvar(eme, diversos.getIdEmpresa())) {
				showMessage("- Email Marketing publicado com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar Email Marketing. Talvez já esteja publicada a esta escolaridade.", FacesMessage.SEVERITY_ERROR);
			}
			return null;
		} catch (ValidationException e) {
			showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String limpar() {
		listaEmailMarketing = null;
		listaEscolaridades = null;
		tipoPesquisaEmail = null;
		idTituloAssunto = null;
		tipoPesquisaEscolaridade = null;
		idTituloEscolaridade = null;
		tipoPesquisaPublicacao = null;
		idTituloPublicacao = null;
		return null;
	}
	
	public String pesquisarEmail() throws ServiceException{
		if(tipoPesquisaEmail==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Assunto.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaEmail==1){//codigo
			try{
				listaEmailMarketing = emService.listarEmailMarketingPorID(Integer.parseInt(idTituloAssunto), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//Assunto
			listaEmailMarketing = emService.listarEmailMarketingPorAssunto(idTituloAssunto, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoEmail() throws ServiceException{
		listaEmailMarketing = emService.listarEmailMarketings(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarEscolaridade() throws ServiceException{
		if(tipoPesquisaEscolaridade==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Escolaridade.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaEscolaridade==1){//codigo
			try{
				listaEscolaridades = escolaridadeService.listarEscolaridadePorID(Integer.parseInt(idTituloEscolaridade));
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo escolaridade
			listaEscolaridades = escolaridadeService.listarPesquisaPorEscolaridade(idTituloEscolaridade);
		}
		return null;
	}
	
	public String listarTudoEscolaridade() throws ServiceException{
		listaEscolaridades = escolaridadeService.listarEscolaridades();
		return null;
	}
	
	public String pesquisarPublicacao() throws ServiceException{
		if(tipoPesquisaPublicacao==null){
			showMessage("- Selecione como deseja pesquisar, por Código, Assunto ou Escolaridade.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPublicacao==1){//codigo pesquisa
			try{
				listaEmailMarketingEscolaridade = emeService.listarPublicacaoEmailMarketingPorIdEmail(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo escolaridade
			try{
				listaEmailMarketingEscolaridade = emeService.listarPublicacaoEmailMarketingPorIdEscolaridade(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaEmailMarketingEscolaridade = emeService.listarPublicacaoEmailMarketingPorEscolaridade(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaEmailMarketingEscolaridade = emeService.listarPublicacaoEmailMarketingPorAssunto(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaEmailMarketingEscolaridade = emeService.listaEmailMarketingEscolaridade(diversos.getIdEmpresa()) ;
		return null;
	}
	
	
	public EscolaridadeService getEscolaridadeService() {
		return escolaridadeService;
	}

	public void setEscolaridadeService(EscolaridadeService escolaridadeService) {
		this.escolaridadeService = escolaridadeService;
	}

	public List<Escolaridade> getListaEscolaridades() {
		return listaEscolaridades;
	}

	public void setListaEscolaridades(List<Escolaridade> listaEscolaridades) {
		this.listaEscolaridades = listaEscolaridades;
	}

	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public List<EmailMarketingEscolaridade> getListaEmailMarketingEscolaridade() throws ServiceException {
		return listaEmailMarketingEscolaridade;
	}

	public List<EmailMarketing> getListaEmailMarketing() {
		return listaEmailMarketing;
	}

	public void setListaEmailMarketing(List<EmailMarketing> listaEmailMarketing) {
		this.listaEmailMarketing = listaEmailMarketing;
	}

	public EmailMarketing getEm() {
		return em;
	}

	public void setEm(EmailMarketing em) {
		this.em = em;
	}

	public EmailMarketingEscolaridade getEme() {
		if(eme == null){
			eme = new EmailMarketingEscolaridade();
			eme.setEmailmarketing(new EmailMarketing());
			eme.setEscolaridade(new Escolaridade());
			eme.setStatuscandidato(new StatusCandidato());
		}
		return eme;
	}

	public void setEme(EmailMarketingEscolaridade eme) {
		this.eme = eme;
	}

	public void setListaEmailMarketingEscolaridade(
			List<EmailMarketingEscolaridade> listaEmailMarketingEscolaridade) {
		this.listaEmailMarketingEscolaridade = listaEmailMarketingEscolaridade;
	}

	public Integer getTipoPesquisaEmail() {
		return tipoPesquisaEmail;
	}

	public void setTipoPesquisaEmail(Integer tipoPesquisaEmail) {
		this.tipoPesquisaEmail = tipoPesquisaEmail;
	}

	public String getIdTituloAssunto() {
		return idTituloAssunto;
	}

	public void setIdTituloAssunto(String idTituloAssunto) {
		this.idTituloAssunto = idTituloAssunto;
	}

	public Integer getTipoPesquisaEscolaridade() {
		return tipoPesquisaEscolaridade;
	}

	public void setTipoPesquisaEscolaridade(Integer tipoPesquisaEscolaridade) {
		this.tipoPesquisaEscolaridade = tipoPesquisaEscolaridade;
	}

	public String getIdTituloEscolaridade() {
		return idTituloEscolaridade;
	}

	public void setIdTituloEscolaridade(String idTituloEscolaridade) {
		this.idTituloEscolaridade = idTituloEscolaridade;
	}

	public Integer getTipoPesquisaPublicacao() {
		return tipoPesquisaPublicacao;
	}

	public void setTipoPesquisaPublicacao(Integer tipoPesquisaPublicacao) {
		this.tipoPesquisaPublicacao = tipoPesquisaPublicacao;
	}

	public String getIdTituloPublicacao() {
		return idTituloPublicacao;
	}

	public void setIdTituloPublicacao(String idTituloPublicacao) {
		this.idTituloPublicacao = idTituloPublicacao;
	}

	public List<StatusCandidato> getListaStatusCandidato() throws ServiceException {
		listaStatusCandidato = scService.listarStatus(diversos.getIdEmpresa());
		return listaStatusCandidato;
	}

	public void setListaStatusCandidato(List<StatusCandidato> listaStatusCandidato) {
		this.listaStatusCandidato = listaStatusCandidato;
	}

	public Integer getIdStatusCandidato() {
		return idStatusCandidato;
	}

	public void setIdStatusCandidato(Integer idStatusCandidato) {
		this.idStatusCandidato = idStatusCandidato;
	}

}