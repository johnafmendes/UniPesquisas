package unipesquisas.controller;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailMarketingAlternativa;
import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.EmailMarketingAlternativaService;
import unipesquisas.model.service.EmailMarketingService;
import unipesquisas.model.service.PerguntaService;
import unipesquisas.model.service.PesquisaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarEmailMarketingAlternativaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject	
	private EmailMarketingAlternativaService emaService;

	@Inject
	private PerguntaService perguntaService;
	
	@Inject 
	private PesquisaService pesquisaService;
	
	@Inject
	private EmailMarketingService emService;
	
	@Inject
	private Diversos diversos;
	
	private List<EmailMarketing> listaEmailMarketing;
	private List<Pergunta> listaPerguntas;
	private List<Pesquisa> listaPesquisas;
	private List<EmailMarketingAlternativa> listaEmailMarketingAlternativa;
	private List<StatusCandidato> listaStatusCandidato;
	private EmailMarketing em;
	private EmailMarketingAlternativa ema;
	private String alternativa;
	private Pesquisa pesquisa;
	private Pergunta pergunta;
	private Integer tipoPesquisaEmail;
	private String idTituloAssunto;
	private Integer tipoPesquisaPesquisa;
	private String idTituloPesquisa;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idPesquisa;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(em == null || pesquisa == null || pergunta == null || alternativa == null || idStatusCandidato == null){
				showMessage("- Selecione um EMAIL, uma PESQUISA, uma PERGUNTA, uma ALTERNATIVA e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getEma();
			
			ema.getEmailmarketing().setIdemailmarketing(em.getIdemailmarketing());
			ema.getPesquisa().setIdpesquisa(pesquisa.getIdpesquisa());
			ema.getPergunta().setIdpergunta(pergunta.getIdpergunta());
			ema.setAlternativa(alternativa);
			ema.setData(new Date());
			ema.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(emaService.salvar(ema, diversos.getIdEmpresa())) {
				showMessage("- Email Marketing publicado com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar Email Marketing. Talvez já esteja publicada a esta alternativa.", FacesMessage.SEVERITY_ERROR);
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
		listaPesquisas = null;
		listaPerguntas = null;
		alternativa = null;
		pergunta = null;
		pesquisa = null;
		tipoPesquisaEmail = null;
		idTituloAssunto = null;
		tipoPesquisaPesquisa = null;
		idTituloPesquisa = null;
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
	
	public String pesquisarPesquisa() throws ServiceException{
		if(tipoPesquisaPesquisa==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Pesquisa.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPesquisa==1){//codigo
			try{
				listaPesquisas = pesquisaService.listarPesquisaPorID(Integer.parseInt(idTituloPesquisa), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo pesquisa
			listaPesquisas = pesquisaService.listarPesquisaPorTitulo(idTituloPesquisa, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPesquisa() throws ServiceException{
		listaPesquisas = pesquisaService.listarPesquisas(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarPublicacao() throws ServiceException{
		if(tipoPesquisaPublicacao==null){
			showMessage("- Selecione como deseja pesquisar, por Código, Assunto ou Pergunta.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPublicacao==1){//codigo pesquisa
			try{
				listaEmailMarketingAlternativa = emaService.listarPublicacaoEmailMarketingPorIdEmail(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo pergunta
			try{
				listaEmailMarketingAlternativa = emaService.listarPublicacaoEmailMarketingPorIdPergunta(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaEmailMarketingAlternativa = emaService.listarPublicacaoEmailMarketingPorPergunta(idTituloPublicacao, diversos.getIdEmpresa());
		} else if(tipoPesquisaPublicacao==0){//titulo pesquisa
			listaEmailMarketingAlternativa = emaService.listarPublicacaoEmailMarketingPorAssunto(idTituloPublicacao, diversos.getIdEmpresa());
		} else if(tipoPesquisaPublicacao==4){
			try{
				listaEmailMarketingAlternativa = emaService.listarPublicacaoEmailMarketingPorIdPesquisa(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {
			listaEmailMarketingAlternativa = emaService.listarPublicacaoEmailMarketingPorPesquisa(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaEmailMarketingAlternativa = emaService.listaEmailMarketingAlternativa(diversos.getIdEmpresa()) ;
		return null;
	}
	
	public String filtrarPerguntas(Integer idPesquisa) throws ServiceException{
		this.idPesquisa = idPesquisa;
		listaPerguntas = perguntaService.listarPerguntasPorPesquisa(idPesquisa, diversos.getIdEmpresa());
		pergunta = null;
		return null;
	}
	
	public String carregarPergunta(Integer idPergunta) throws ServiceException{
		pergunta = perguntaService.carregar(idPergunta);
		return null;
	}

	public List<EmailMarketing> getListaEmailMarketing() {
		return listaEmailMarketing;
	}

	public void setListaEmailMarketing(List<EmailMarketing> listaEmailMarketing) {
		this.listaEmailMarketing = listaEmailMarketing;
	}

	public EmailMarketing getEm() {
		if(em == null){
			em = new EmailMarketing();
		}
		return em;
	}

	public void setEm(EmailMarketing em) {
		this.em = em;
	}

	public List<Pergunta> getListaPerguntas() {
		return listaPerguntas;
	}

	public void setListaPerguntas(List<Pergunta> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

	public List<Pesquisa> getListaPesquisas() {
		return listaPesquisas;
	}

	public void setListaPesquisas(List<Pesquisa> listaPesquisas) {
		this.listaPesquisas = listaPesquisas;
	}

	public List<EmailMarketingAlternativa> getListaEmailMarketingAlternativa() throws ServiceException {
		return listaEmailMarketingAlternativa;
	}

	public void setListaEmailMarketingAlternativa(
			List<EmailMarketingAlternativa> listaEmailMarketingAlternativa) {
		this.listaEmailMarketingAlternativa = listaEmailMarketingAlternativa;
	}

	public EmailMarketingAlternativa getEma() {
		if(ema == null){
			ema = new EmailMarketingAlternativa();
			ema.setEmailmarketing(new EmailMarketing());
			ema.setPergunta(new Pergunta());
			ema.setPesquisa(new Pesquisa());
			ema.setStatuscandidato(new StatusCandidato());
		}
		return ema;
	}

	public void setEma(EmailMarketingAlternativa ema) {
		this.ema = ema;
	}

	public String getAlternativa() {
		return alternativa;
	}

	public void setAlternativa(String alternativa) {
		this.alternativa = alternativa;
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

	public Integer getTipoPesquisaPesquisa() {
		return tipoPesquisaPesquisa;
	}

	public void setTipoPesquisaPesquisa(Integer tipoPesquisaPesquisa) {
		this.tipoPesquisaPesquisa = tipoPesquisaPesquisa;
	}

	public String getIdTituloPesquisa() {
		return idTituloPesquisa;
	}

	public void setIdTituloPesquisa(String idTituloPesquisa) {
		this.idTituloPesquisa = idTituloPesquisa;
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

	public Integer getIdPesquisa() {
		return idPesquisa;
	}

	public void setIdPesquisa(Integer idPesquisa) {
		this.idPesquisa = idPesquisa;
	}

	public Pesquisa getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
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