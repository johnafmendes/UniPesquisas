package unipesquisas.controller;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.SMSMarketingAlternativa;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.PerguntaService;
import unipesquisas.model.service.PesquisaService;
import unipesquisas.model.service.SMSMarketingAlternativaService;
import unipesquisas.model.service.SMSMarketingService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarSMSMarketingAlternativaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject	
	private SMSMarketingAlternativaService smsmaService;

	@Inject
	private PerguntaService perguntaService;
	
	@Inject 
	private PesquisaService pesquisaService;
	
	@Inject
	private SMSMarketingService smsmService;
	
	@Inject
	private Diversos diversos;
	
	private List<SMSMarketing> listaSMSMarketing;
	private List<Pergunta> listaPerguntas;
	private List<Pesquisa> listaPesquisas;
	private List<SMSMarketingAlternativa> listaSMSMarketingAlternativa;
	private List<StatusCandidato> listaStatusCandidato;
	private SMSMarketing smsm;
	private SMSMarketingAlternativa smsma;
	private String alternativa;
	private Pesquisa pesquisa;
	private Pergunta pergunta;
	private Integer tipoPesquisaSMS;
	private String idTituloAssunto;
	private Integer tipoPesquisaPesquisa;
	private String idTituloPesquisa;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idPesquisa;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(smsm == null || pesquisa == null || pergunta == null || alternativa == null || idStatusCandidato == null){
				showMessage("- Selecione um SMS, uma PESQUISA, uma PERGUNTA, uma ALTERNATIVA e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getSmsma();
			
			smsma.getSmsmarketing().setIdsmsmarketing(smsm.getIdsmsmarketing());
			smsma.getPesquisa().setIdpesquisa(pesquisa.getIdpesquisa());
			smsma.getPergunta().setIdpergunta(pergunta.getIdpergunta());
			smsma.setAlternativa(alternativa);
			smsma.setData(new Date());
			smsma.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(smsmaService.salvar(smsma, diversos.getIdEmpresa())) {
				showMessage("- SMS Marketing publicado com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar SMS Marketing. Talvez já esteja publicada a esta alternativa.", FacesMessage.SEVERITY_ERROR);
			}
			return null;
		} catch (ValidationException e) {
			showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String limpar() {
		listaSMSMarketing = null;
		listaPesquisas = null;
		listaPerguntas = null;
		alternativa = null;
		pergunta = null;
		pesquisa = null;
		tipoPesquisaSMS = null;
		idTituloAssunto = null;
		tipoPesquisaPesquisa = null;
		idTituloPesquisa = null;
		tipoPesquisaPublicacao = null;
		idTituloPublicacao = null;
		return null;
	}
	
	public String pesquisarSMS() throws ServiceException{
		if(tipoPesquisaSMS==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Assunto.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaSMS==1){//codigo
			try{
				listaSMSMarketing = smsmService.listarSMSMarketingPorID(Integer.parseInt(idTituloAssunto), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//Assunto
			listaSMSMarketing = smsmService.listarSMSMarketingPorAssunto(idTituloAssunto, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoSMS() throws ServiceException{
		listaSMSMarketing = smsmService.listarSMSMarketings(diversos.getIdEmpresa());
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
				listaSMSMarketingAlternativa = smsmaService.listarPublicacaoSMSMarketingPorIdSMS(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo pergunta
			try{
				listaSMSMarketingAlternativa = smsmaService.listarPublicacaoSMSMarketingPorIdPergunta(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaSMSMarketingAlternativa = smsmaService.listarPublicacaoSMSMarketingPorPergunta(idTituloPublicacao, diversos.getIdEmpresa());
		} else if(tipoPesquisaPublicacao==0){//titulo pesquisa
			listaSMSMarketingAlternativa = smsmaService.listarPublicacaoSMSMarketingPorAssunto(idTituloPublicacao, diversos.getIdEmpresa());
		} else if(tipoPesquisaPublicacao==4){
			try{
				listaSMSMarketingAlternativa = smsmaService.listarPublicacaoSMSMarketingPorIdPesquisa(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {
			listaSMSMarketingAlternativa = smsmaService.listarPublicacaoSMSMarketingPorPesquisa(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaSMSMarketingAlternativa = smsmaService.listaSMSMarketingAlternativa(diversos.getIdEmpresa()) ;
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

	public List<SMSMarketing> getListaSMSMarketing() {
		return listaSMSMarketing;
	}

	public void setListaSMSMarketing(List<SMSMarketing> listaSMSMarketing) {
		this.listaSMSMarketing = listaSMSMarketing;
	}

	public List<SMSMarketingAlternativa> getListaSMSMarketingAlternativa() {
		return listaSMSMarketingAlternativa;
	}

	public void setListaSMSMarketingAlternativa(
			List<SMSMarketingAlternativa> listaSMSMarketingAlternativa) {
		this.listaSMSMarketingAlternativa = listaSMSMarketingAlternativa;
	}

	public SMSMarketing getSmsm() {
		if(smsm == null){
			smsm = new SMSMarketing();
		}
		return smsm;
	}

	public void setSmsm(SMSMarketing smsm) {
		this.smsm = smsm;
	}

	public SMSMarketingAlternativa getSmsma() {
		if(smsma == null){
			smsma = new SMSMarketingAlternativa();
			smsma.setSmsmarketing(new SMSMarketing());
			smsma.setPesquisa(new Pesquisa());
			smsma.setPergunta(new Pergunta());
			smsma.setStatuscandidato(new StatusCandidato());
		}
		return smsma;
	}

	public void setSmsma(SMSMarketingAlternativa smsma) {
		this.smsma = smsma;
	}

	public Integer getTipoPesquisaSMS() {
		return tipoPesquisaSMS;
	}

	public void setTipoPesquisaSMS(Integer tipoPesquisaSMS) {
		this.tipoPesquisaSMS = tipoPesquisaSMS;
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