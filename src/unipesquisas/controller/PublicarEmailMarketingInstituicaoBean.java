package unipesquisas.controller;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailMarketingInstituicao;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.EmailMarketingInstituicaoService;
import unipesquisas.model.service.EmailMarketingService;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarEmailMarketingInstituicaoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject	
	private EmailMarketingInstituicaoService emiService;

	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private EmailMarketingService emService;
	
	@Inject
	private Diversos diversos;
	
	private List<EmailMarketing> listaEmailMarketing;
	private List<Instituicao> listaInstituicoes;
	private List<EmailMarketingInstituicao> listaEmailMarketingInstituicao;
	private List<StatusCandidato> listaStatusCandidato;
	private EmailMarketing em;
	private Instituicao instituicao;
	private EmailMarketingInstituicao emi;
	private Integer tipoPesquisaEmail;
	private String idTituloAssunto;
	private Integer tipoPesquisaInstituicao;
	private String idTituloInstituicao;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(em == null || instituicao == null || idStatusCandidato == null){
				showMessage("- Selecione um EMAIL, uma INSTITUIÇÃO e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getEmi();
			emi.getEmailmarketing().setIdemailmarketing(em.getIdemailmarketing());
			emi.setData(new Date());
			emi.getInstituicao().setIdinstituicao(instituicao.getIdinstituicao());
			emi.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(emiService.salvar(emi, diversos.getIdEmpresa())) {
				showMessage("- Email Marketing publicado com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar Email Marketing. Talvez já esteja publicada a esta instituição.", FacesMessage.SEVERITY_ERROR);
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
		listaInstituicoes = null;
		tipoPesquisaEmail = null;
		idTituloAssunto = null;
		tipoPesquisaInstituicao = null;
		idTituloInstituicao = null;
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
				listaEmailMarketingInstituicao = emiService.listarPublicacaoEmailMarketingPorIdEmail(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo instituicao
			try{
				listaEmailMarketingInstituicao = emiService.listarPublicacaoEmailMarketingPorIdInstituicao(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaEmailMarketingInstituicao = emiService.listarPublicacaoEmailMarketingPorInstituicao(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaEmailMarketingInstituicao = emiService.listarPublicacaoEmailMarketingPorAssunto(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaEmailMarketingInstituicao = emiService.listaEmailMarketingInstituicao(diversos.getIdEmpresa()) ;
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

	public List<Instituicao> getListaInstituicoes() {
		return listaInstituicoes;
	}

	public void setListaInstituicoes(List<Instituicao> listaInstituicoes) {
		this.listaInstituicoes = listaInstituicoes;
	}

	public List<EmailMarketingInstituicao> getListaEmailMarketingInstituicao() throws ServiceException {
		return listaEmailMarketingInstituicao;
	}

	public void setListaEmailMarketingInstituicao(
			List<EmailMarketingInstituicao> listaEmailMarketingInstituicao) {
		this.listaEmailMarketingInstituicao = listaEmailMarketingInstituicao;
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

	public EmailMarketingInstituicao getEmi() {
		if(emi == null){
			emi = new EmailMarketingInstituicao();
			emi.setEmailmarketing(new EmailMarketing());
			emi.setInstituicao(new Instituicao());
			emi.setStatuscandidato(new StatusCandidato());
		}
		return emi;
	}

	public void setEmi(EmailMarketingInstituicao emi) {
		this.emi = emi;
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