package unipesquisas.controller;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.SMSMarketingInstituicao;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.SMSMarketingInstituicaoService;
import unipesquisas.model.service.SMSMarketingService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarSMSMarketingInstituicaoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject	
	private SMSMarketingInstituicaoService smsmiService;

	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private SMSMarketingService smsmService;
	
	@Inject
	private Diversos diversos;
	
	private List<SMSMarketing> listaSMSMarketing;
	private List<Instituicao> listaInstituicoes;
	private List<SMSMarketingInstituicao> listaSMSMarketingInstituicao;
	private List<StatusCandidato> listaStatusCandidato;
	private SMSMarketing smsm;
	private Instituicao instituicao;
	private SMSMarketingInstituicao smsmi;
	private Integer tipoPesquisaSMS;
	private String idTituloAssunto;
	private Integer tipoPesquisaInstituicao;
	private String idTituloInstituicao;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(smsm == null || instituicao == null || idStatusCandidato == null){
				showMessage("- Selecione um SMS, uma INSTITUIÇÃO e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getSmsmi();
			smsmi.getSmsmarketing().setIdsmsmarketing(smsm.getIdsmsmarketing());
			smsmi.setData(new Date());
			smsmi.getInstituicao().setIdinstituicao(instituicao.getIdinstituicao());
			smsmi.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(smsmiService.salvar(smsmi, diversos.getIdEmpresa())) {
				showMessage("- SMS Marketing publicado com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar SMS Marketing. Talvez já esteja publicada a esta instituição.", FacesMessage.SEVERITY_ERROR);
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
		listaInstituicoes = null;
		tipoPesquisaSMS = null;
		idTituloAssunto = null;
		tipoPesquisaInstituicao = null;
		idTituloInstituicao = null;
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
	
	public String pesquisarInstituicao() throws ServiceException{
		if(tipoPesquisaInstituicao==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Instituição.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaInstituicao==1){//codigo
			try{
				listaInstituicoes = instituicaoService.listarInstituicaoPorID(Integer.parseInt(idTituloInstituicao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo instituicao
			listaInstituicoes = instituicaoService.listarPesquisaPorInstituicao(idTituloInstituicao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoInstituicao() throws ServiceException{
		listaInstituicoes = instituicaoService.listarIntituicoes(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarPublicacao() throws ServiceException{
		if(tipoPesquisaPublicacao==null){
			showMessage("- Selecione como deseja pesquisar, por Código, Assunto ou Instituição.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPublicacao==1){//codigo pesquisa
			try{
				listaSMSMarketingInstituicao = smsmiService.listarPublicacaoSMSMarketingPorIdSMS(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo instituicao
			try{
				listaSMSMarketingInstituicao = smsmiService.listarPublicacaoSMSMarketingPorIdInstituicao(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaSMSMarketingInstituicao = smsmiService.listarPublicacaoSMSMarketingPorInstituicao(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaSMSMarketingInstituicao = smsmiService.listarPublicacaoSMSMarketingPorAssunto(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaSMSMarketingInstituicao = smsmiService.listaSMSMarketingInstituicao(diversos.getIdEmpresa()) ;
		return null;
	}
	
	public List<Instituicao> getListaInstituicoes() {
		return listaInstituicoes;
	}

	public void setListaInstituicoes(List<Instituicao> listaInstituicoes) {
		this.listaInstituicoes = listaInstituicoes;
	}

	public Instituicao getInstituicao() {
		if(instituicao == null){
			instituicao = new Instituicao();
		}
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public String getIdTituloAssunto() {
		return idTituloAssunto;
	}

	public void setIdTituloAssunto(String idTituloAssunto) {
		this.idTituloAssunto = idTituloAssunto;
	}

	public Integer getTipoPesquisaInstituicao() {
		return tipoPesquisaInstituicao;
	}

	public void setTipoPesquisaInstituicao(Integer tipoPesquisaInstituicao) {
		this.tipoPesquisaInstituicao = tipoPesquisaInstituicao;
	}

	public String getIdTituloInstituicao() {
		return idTituloInstituicao;
	}

	public void setIdTituloInstituicao(String idTituloInstituicao) {
		this.idTituloInstituicao = idTituloInstituicao;
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

	public List<SMSMarketingInstituicao> getListaSMSMarketingInstituicao() {
		return listaSMSMarketingInstituicao;
	}

	public void setListaSMSMarketingInstituicao(
			List<SMSMarketingInstituicao> listaSMSMarketingInstituicao) {
		this.listaSMSMarketingInstituicao = listaSMSMarketingInstituicao;
	}

	public SMSMarketing getSmsm() {
		if(smsm == null){
			smsm  = new SMSMarketing();
		}
		return smsm;
	}

	public void setSmsm(SMSMarketing smsm) {
		this.smsm = smsm;
	}

	public SMSMarketingInstituicao getSmsmi() {
		if(smsmi == null){
			smsmi = new SMSMarketingInstituicao();
			smsmi.setSmsmarketing(new SMSMarketing());
			smsmi.setInstituicao(new Instituicao());
			smsmi.setStatuscandidato(new StatusCandidato());
		}
		return smsmi;
	}

	public void setSmsmi(SMSMarketingInstituicao smsmi) {
		this.smsmi = smsmi;
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