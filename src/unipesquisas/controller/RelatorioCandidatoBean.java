package unipesquisas.controller;

import java.io.FileInputStream;
import java.io.IOException;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.InputStream;
import java.util.List;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.AreasCurso;
import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.AreasCursoService;
import unipesquisas.model.service.CursoService;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.RelatorioCandidatoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;

@Named
@SessionScoped
public class RelatorioCandidatoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private RelatorioCandidatoService rcService;
	
	@Inject
	private StatusCandidatoService scService;
	
	@Inject
	private EscolaridadeService escolaridadeService;
	
	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private CursoService cursoService;
	
	@Inject
	private AreasCursoService areaCursoService;
	
	@Inject
	private Diversos diversos;
	
	private StreamedContent file;
	private Integer idInstituicao;
	private Integer idEscolaridade;
	private Integer idCurso;
	private Integer idCursoMatriculado;
	private Integer idAreaCurso;
	private Integer matriculado;
	private Integer idStatusCandidato;
	private List<Escolaridade> listaEscolaridades;
	private List<Instituicao> listaInstituicoes;
	private List<Curso> listaCursos;
	private List<AreasCurso> listaAreas;
	private List<StatusCandidato> listaStatusCandidato;

//	@PostConstruct
//	public void init () throws ServiceException{
//		listaEscolaridades = escolaridadeService.listarEscolaridades();
//	}
	
	public String abrirListaCandidato() throws Exception {
		if(idStatusCandidato == null){
			showMessage("- Selecione um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosStatus.jasper");

		byte relatorio[] = rcService.abrirListaCandidato(ArquivoRelatorio, diversos.getIdEmpresa(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}

	
	private void montaPagina(byte[] relatorio) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) ec.getResponse();
		
		try{
			response.setContentType("application/pdf");
			response.getOutputStream().write(relatorio);
		}catch(IOException e){
			e.printStackTrace();
		}
		fc.responseComplete();
		
	}


	private String getArquivoRelatorio(String arquivo) {
		String relativeWebPath = "/relatorios/";
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
		
		return absoluteDiskPath + "/"+arquivo;
	}


	public String gerarListaCandidato() throws Exception {
		if(idStatusCandidato == null){
			showMessage("- Selecione um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rcService.gerarListaCandidato(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatos.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String abrirListaCandidatoEscolaridade() throws Exception {
		if(idEscolaridade == null || idStatusCandidato == null){
			showMessage("- Selecione um Status e uma Escolaridade.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosEscolaridadeStatus.jasper");
		
		byte relatorio[] = rcService.abrirListaCandidatoEscolaridade(ArquivoRelatorio, diversos.getIdEmpresa(), idEscolaridade, idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}

	
	public String gerarListaCandidatoEscolaridade() throws Exception {
		if(idEscolaridade == null || idStatusCandidato == null){
			showMessage("- Selecione um Status e uma Escolaridade.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosEscolaridadeStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rcService.gerarListaCandidatoEscolaridade(ArquivoRelatorio, diversos.getIdEmpresa(), idEscolaridade, sessionId, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosEscolaridade.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String abrirListaCandidatoInstituicao() throws Exception {
		if(idInstituicao == null || idStatusCandidato == null){
			showMessage("- Selecione uma Instituição e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosInstituicaoStatus.jasper");
		
		byte relatorio[] = rcService.abrirListaCandidatoInstituicao(ArquivoRelatorio, diversos.getIdEmpresa(), idInstituicao, idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}

	
	public String gerarListaCandidatoInstituicao() throws Exception {
		if(idInstituicao == null || idStatusCandidato == null){
			showMessage("- Selecione uma Instituição e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosInstituicaoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rcService.gerarListaCandidatoInstituicao(ArquivoRelatorio, diversos.getIdEmpresa(), idInstituicao, sessionId, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosInstituicao.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String abrirListaCandidatoCurso() throws Exception {
		if(idCurso == null || idStatusCandidato == null){
			showMessage("- Selecione um Curso e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosCursoStatus.jasper");
		
		byte relatorio[] = rcService.abrirListaCandidatoCurso(ArquivoRelatorio, diversos.getIdEmpresa(), idCurso, idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}

	
	public String gerarListaCandidatoCurso() throws Exception {
		if(idCurso == null || idStatusCandidato == null){
			showMessage("- Selecione um Curso e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosCursoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rcService.gerarListaCandidatoCurso(ArquivoRelatorio, diversos.getIdEmpresa(), idCurso, sessionId, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosCurso.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String abrirListaCandidatoArea() throws Exception {
		if(idAreaCurso == null || idStatusCandidato == null){
			showMessage("- Selecione uma Área e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosAreaStatus.jasper");
		
		byte relatorio[] = rcService.abrirListaCandidatoArea(ArquivoRelatorio, diversos.getIdEmpresa(), idAreaCurso, idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}

	
	public String gerarListaCandidatoArea() throws Exception {
		if(idAreaCurso == null || idStatusCandidato == null){
			showMessage("- Selecione uma Área e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosAreaStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rcService.gerarListaCandidatoArea(ArquivoRelatorio, diversos.getIdEmpresa(), idAreaCurso, sessionId, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosArea.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String abrirListaCandidatoCursoMatriculado() throws Exception {
		if(idCursoMatriculado == null || matriculado == null || idStatusCandidato == null){
			showMessage("- Selecione um Curso, uma opção (Matriculado ou Não Matriculado) e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosCursoMatriculadoStatus.jasper");
		
		byte relatorio[] = rcService.abrirListaCandidatoCursoMatriculado(ArquivoRelatorio, diversos.getIdEmpresa(), idCursoMatriculado, matriculado, idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}

	
	public String gerarListaCandidatoCursoMatriculado() throws Exception {
		if(idCursoMatriculado == null || matriculado == null || idStatusCandidato == null){
			showMessage("- Selecione um Curso, uma opção (Matriculado ou Não Matriculado) e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosCursoMatriculadoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rcService.gerarListaCandidatoCursoMatriculado(ArquivoRelatorio, diversos.getIdEmpresa(), idCursoMatriculado, sessionId, matriculado, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosCurso.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public StreamedContent getFile() {
		return file;
	}
	
	public List<Escolaridade> getListaEscolaridades() throws ServiceException {
		listaEscolaridades = escolaridadeService.listarEscolaridades();
		return listaEscolaridades;
	}

	public void setListaEscolaridades(List<Escolaridade> listaEscolaridades) {
		this.listaEscolaridades = listaEscolaridades;
	}

	public List<Instituicao> getListaInstituicoes() throws ServiceException {
		listaInstituicoes = instituicaoService.listarIntituicoes(diversos.getIdEmpresa());
		return listaInstituicoes;
	}


	public void setListaInstituicoes(List<Instituicao> listaInstituicoes) {
		this.listaInstituicoes = listaInstituicoes;
	}

	public List<Curso> getListaCursos() throws ServiceException {
		listaCursos = cursoService.listarCursos(diversos.getIdEmpresa());
		return listaCursos;
	}


	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public List<AreasCurso> getListaAreas() throws ServiceException {
		listaAreas = areaCursoService.listarAreas(diversos.getIdEmpresa());
		return listaAreas;
	}

	public void setListaAreas(List<AreasCurso> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public Integer getMatriculado() {
		return matriculado;
	}

	public void setMatriculado(Integer matriculado) {
		this.matriculado = matriculado;
	}


	public Integer getIdInstituicao() {
		return idInstituicao;
	}


	public void setIdInstituicao(Integer idInstituicao) {
		this.idInstituicao = idInstituicao;
	}


	public Integer getIdEscolaridade() {
		return idEscolaridade;
	}


	public void setIdEscolaridade(Integer idEscolaridade) {
		this.idEscolaridade = idEscolaridade;
	}


	public Integer getIdCurso() {
		return idCurso;
	}


	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}


	public Integer getIdCursoMatriculado() {
		return idCursoMatriculado;
	}


	public void setIdCursoMatriculado(Integer idCursoMatriculado) {
		this.idCursoMatriculado = idCursoMatriculado;
	}


	public Integer getIdAreaCurso() {
		return idAreaCurso;
	}


	public void setIdAreaCurso(Integer idAreaCurso) {
		this.idAreaCurso = idAreaCurso;
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