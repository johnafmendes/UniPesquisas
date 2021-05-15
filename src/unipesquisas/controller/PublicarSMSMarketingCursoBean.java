package unipesquisas.controller;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.SMSMarketingCurso;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.CursoService;
import unipesquisas.model.service.SMSMarketingCursoService;
import unipesquisas.model.service.SMSMarketingService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarSMSMarketingCursoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject	
	private SMSMarketingCursoService smsmcService;

	@Inject
	private CursoService cursoService;
	
	@Inject
	private SMSMarketingService smsmService;
	
	@Inject
	private Diversos diversos;
	
	private List<SMSMarketing> listaSMSMarketing;
	private List<Curso> listaCursos;
	private List<SMSMarketingCurso> listaSMSMarketingCurso;
	private List<StatusCandidato> listaStatusCandidato;
	private SMSMarketing smsm;
	private Curso curso;
	private SMSMarketingCurso smsmc;
	private Integer tipoPesquisaSMS;
	private String idTituloAssunto;
	private Integer tipoPesquisaCurso;
	private String idTituloCurso;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(smsm == null || curso == null || idStatusCandidato == null){
				showMessage("- Selecione um SMS, um CURSO e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getSmsmc();
			smsmc.getSmsmarketing().setIdsmsmarketing(smsm.getIdsmsmarketing());
			smsmc.setData(new Date());
			smsmc.getCurso().setIdcurso(curso.getIdcurso());
			smsmc.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(smsmcService.salvar(smsmc, diversos.getIdEmpresa())) {
				showMessage("- SMS Marketing publicado com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar SMS Marketing. Talvez já esteja publicada a este curso.", FacesMessage.SEVERITY_ERROR);
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
		listaCursos = null;
		tipoPesquisaSMS = null;
		idTituloAssunto = null;
		tipoPesquisaCurso = null;
		idTituloCurso = null;
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
				listaSMSMarketingCurso = smsmcService.listarPublicacaoSMSMarketingPorIdSMS(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo escolaridade
			try{
				listaSMSMarketingCurso = smsmcService.listarPublicacaoSMSMarketingPorIdCurso(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaSMSMarketingCurso = smsmcService.listarPublicacaoSMSMarketingPorCurso(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaSMSMarketingCurso = smsmcService.listarPublicacaoSMSMarketingPorAssunto(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaSMSMarketingCurso= smsmcService.listaSMSMarketingCurso(diversos.getIdEmpresa()) ;
		return null;
	}

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
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

	public List<SMSMarketing> getListaSMSMarketing() {
		return listaSMSMarketing;
	}

	public void setListaSMSMarketing(List<SMSMarketing> listaSMSMarketing) {
		this.listaSMSMarketing = listaSMSMarketing;
	}

	public List<SMSMarketingCurso> getListaSMSMarketingCurso() {
		return listaSMSMarketingCurso;
	}

	public void setListaSMSMarketingCurso(
			List<SMSMarketingCurso> listaSMSMarketingCurso) {
		this.listaSMSMarketingCurso = listaSMSMarketingCurso;
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

	public SMSMarketingCurso getSmsmc() {
		if(smsmc == null){
			smsmc = new SMSMarketingCurso();
			smsmc.setSmsmarketing(new SMSMarketing());
			smsmc.setCurso(new Curso());
			smsmc.setStatuscandidato(new StatusCandidato());
		}
		return smsmc;
	}

	public void setSmsmc(SMSMarketingCurso smsmc) {
		this.smsmc = smsmc;
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