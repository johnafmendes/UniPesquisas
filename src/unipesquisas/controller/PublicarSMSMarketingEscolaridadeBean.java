package unipesquisas.controller;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.SMSMarketingEscolaridade;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.SMSMarketingEscolaridadeService;
import unipesquisas.model.service.SMSMarketingService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarSMSMarketingEscolaridadeBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject	
	private SMSMarketingEscolaridadeService smsmeService;

	@Inject
	private EscolaridadeService escolaridadeService;
	
	@Inject
	private SMSMarketingService smsmService;
	
	@Inject
	private Diversos diversos;
	
	private List<SMSMarketing> listaSMSMarketing;
	private List<Escolaridade> listaEscolaridades;
	private List<SMSMarketingEscolaridade> listaSMSMarketingEscolaridade;
	private List<StatusCandidato> listaStatusCandidato;
	private SMSMarketing smsm;
	private Escolaridade escolaridade;
	private SMSMarketingEscolaridade smsme;
	private Integer tipoPesquisaSMS;
	private String idTituloAssunto;
	private Integer tipoPesquisaEscolaridade;
	private String idTituloEscolaridade;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(smsm == null || escolaridade == null || idStatusCandidato == null){
				showMessage("- Selecione um SMS, uma ESCOLARIDADE e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getSmsme();
			smsme.getSmsmarketing().setIdsmsmarketing(smsm.getIdsmsmarketing());
			smsme.getEscolaridade().setIdescolaridade(escolaridade.getIdescolaridade());
			smsme.setData(new Date());
			smsme.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(smsmeService.salvar(smsme, diversos.getIdEmpresa())) {
				showMessage("- SMS Marketing publicado com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar SMS Marketing. Talvez já esteja publicada a esta escolaridade.", FacesMessage.SEVERITY_ERROR);
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
		listaEscolaridades = null;
		tipoPesquisaSMS = null;
		idTituloAssunto = null;
		tipoPesquisaEscolaridade = null;
		idTituloEscolaridade = null;
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
				listaSMSMarketingEscolaridade = smsmeService.listarPublicacaoSMSMarketingPorIdSMS(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo escolaridade
			try{
				listaSMSMarketingEscolaridade = smsmeService.listarPublicacaoSMSMarketingPorIdEscolaridade(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaSMSMarketingEscolaridade = smsmeService.listarPublicacaoSMSMarketingPorEscolaridade(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaSMSMarketingEscolaridade = smsmeService.listarPublicacaoSMSMarketingPorAssunto(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaSMSMarketingEscolaridade = smsmeService.listaSMSMarketingEscolaridade(diversos.getIdEmpresa()) ;
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

	public List<SMSMarketing> getListaSMSMarketing() {
		return listaSMSMarketing;
	}

	public void setListaSMSMarketing(List<SMSMarketing> listaSMSMarketing) {
		this.listaSMSMarketing = listaSMSMarketing;
	}

	public List<SMSMarketingEscolaridade> getListaSMSMarketingEscolaridade() {
		return listaSMSMarketingEscolaridade;
	}

	public void setListaSMSMarketingEscolaridade(
			List<SMSMarketingEscolaridade> listaSMSMarketingEscolaridade) {
		this.listaSMSMarketingEscolaridade = listaSMSMarketingEscolaridade;
	}

	public SMSMarketing getSmsm() {
		return smsm;
	}

	public void setSmsm(SMSMarketing smsm) {
		this.smsm = smsm;
	}

	public SMSMarketingEscolaridade getSmsme() {
		if(smsme == null){
			smsme = new SMSMarketingEscolaridade();
			smsme.setEscolaridade(new Escolaridade());
			smsme.setSmsmarketing(new SMSMarketing());
			smsme.setStatuscandidato(new StatusCandidato());
		}
		return smsme;
	}

	public void setSmsme(SMSMarketingEscolaridade smsme) {
		this.smsme = smsme;
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