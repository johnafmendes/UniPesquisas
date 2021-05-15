package unipesquisas.controller;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailMarketingCurso;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.CursoService;
import unipesquisas.model.service.EmailMarketingCursoService;
import unipesquisas.model.service.EmailMarketingService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarEmailMarketingCursoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject	
	private EmailMarketingCursoService emcService;

	@Inject
	private CursoService cursoService;
	
	@Inject
	private EmailMarketingService emService;
	
	@Inject
	private Diversos diversos;
	
	private List<EmailMarketing> listaEmailMarketing;
	private List<Curso> listaCursos;
	private List<EmailMarketingCurso> listaEmailMarketingCurso;
	private List<StatusCandidato> listaStatusCandidato;
	private EmailMarketing em;
	private Curso curso;
	private EmailMarketingCurso emc;
	private Integer tipoPesquisaEmail;
	private String idTituloAssunto;
	private Integer tipoPesquisaCurso;
	private String idTituloCurso;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(em == null || curso == null || idStatusCandidato == null){
				showMessage("- Selecione um EMAIL, um CURSO e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getEmc();
			emc.getEmailmarketing().setIdemailmarketing(em.getIdemailmarketing());
			emc.setData(new Date());
			emc.getCurso().setIdcurso(curso.getIdcurso());
			emc.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(emcService.salvar(emc, diversos.getIdEmpresa())) {
				showMessage("- Email Marketing publicado com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar Email Marketing. Talvez já esteja publicada a este curso.", FacesMessage.SEVERITY_ERROR);
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
		listaCursos = null;
		tipoPesquisaEmail = null;
		idTituloAssunto = null;
		tipoPesquisaCurso = null;
		idTituloCurso = null;
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
	
	public String pesquisarCurso() throws ServiceException{
		if(tipoPesquisaCurso==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Curso.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaCurso==1){//codigo
			try{
				listaCursos = cursoService.listarCursoPorID(Integer.parseInt(idTituloCurso), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo curso
			listaCursos = cursoService.listarPesquisaPorCurso(idTituloCurso, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoCurso() throws ServiceException{
		listaCursos = cursoService.listarCursos(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarPublicacao() throws ServiceException{
		if(tipoPesquisaPublicacao==null){
			showMessage("- Selecione como deseja pesquisar, por Código, Assunto ou Curso.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPublicacao==1){//codigo pesquisa
			try{
				listaEmailMarketingCurso = emcService.listarPublicacaoEmailMarketingPorIdEmail(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo escolaridade
			try{
				listaEmailMarketingCurso = emcService.listarPublicacaoEmailMarketingPorIdCurso(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaEmailMarketingCurso = emcService.listarPublicacaoEmailMarketingPorCurso(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaEmailMarketingCurso = emcService.listarPublicacaoEmailMarketingPorAssunto(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaEmailMarketingCurso= emcService.listaEmailMarketingCurso(diversos.getIdEmpresa()) ;
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

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public List<EmailMarketingCurso> getListaEmailMarketingCurso() throws ServiceException {
		return listaEmailMarketingCurso;
	}

	public void setListaEmailMarketingCurso(
			List<EmailMarketingCurso> listaEmailMarketingCurso) {
		this.listaEmailMarketingCurso = listaEmailMarketingCurso;
	}

	public Curso getCurso() {
		if(curso == null){
			curso = new Curso();
		}
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public EmailMarketingCurso getEmc() {
		if(emc == null){
			emc = new EmailMarketingCurso();
			emc.setEmailmarketing(new EmailMarketing());
			emc.setCurso(new Curso());
			emc.setStatuscandidato(new StatusCandidato());
		}
		return emc;
	}

	public void setEmc(EmailMarketingCurso emc) {
		this.emc = emc;
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

	public Integer getTipoPesquisaCurso() {
		return tipoPesquisaCurso;
	}

	public void setTipoPesquisaCurso(Integer tipoPesquisaCurso) {
		this.tipoPesquisaCurso = tipoPesquisaCurso;
	}

	public String getIdTituloCurso() {
		return idTituloCurso;
	}

	public void setIdTituloCurso(String idTituloCurso) {
		this.idTituloCurso = idTituloCurso;
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