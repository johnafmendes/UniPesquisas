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
import java.util.Date;
import java.util.List;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.RelatorioProgressoService;
import unipesquisas.model.service.ServiceException;

@Named
@SessionScoped
public class RelatorioProgressoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private RelatorioProgressoService rpService;
	
	@Inject
	private CandidatoService candidatoService;
	
	@Inject
	private Diversos diversos;
	
	private StreamedContent file;
	private Date dataInicio;
	private Date dataFim;
	private List<Candidato> listaCandidatos;
	private Candidato candidato;
	private String nomeCandidato;

	public String abrirListaProgresso() throws Exception {
		if(dataInicio == null || dataFim == null){
			showMessage("- Selecione uma Data de Início e uma Data Fim.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaProgresso.jasper"); 
		
		byte relatorio[] = rpService.abrirListaProgresso(ArquivoRelatorio, diversos.getIdEmpresa(), dataInicio, dataFim);
		
		montaPagina(relatorio);
		return null;
	}

	public String abrirListaProgressoCandidato() throws Exception {
		getCandidato();
		if(dataInicio == null || dataFim == null || candidato.getIdcandidato() == null){
			showMessage("- Selecione uma Data de Início e uma Data Fim e um Candidato.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaProgressoCandidato.jasper"); 
		
		byte relatorio[] = rpService.abrirListaProgressoCandidato(ArquivoRelatorio, diversos.getIdEmpresa(), dataInicio, dataFim, candidato.getIdcandidato());
		
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


	public String gerarListaProgresso() throws Exception {
		if(dataInicio == null || dataFim == null){
			showMessage("- Selecione uma Data de Início e uma Data Fim.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaProgresso.jasper"); 
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaProgresso(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, dataInicio, dataFim);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaProgresso.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String gerarListaProgressoCandidato() throws Exception {
		getCandidato();
		if(dataInicio == null || dataFim == null || candidato.getIdcandidato() == null){
			showMessage("- Selecione uma Data de Início e uma Data Fim e um Candidato.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaProgressoCandidato.jasper"); 
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaProgressoCandidato(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, dataInicio, dataFim, candidato.getIdcandidato());
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaProgressoCandidato.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String pesquisarCandidatoPorNome () throws ServiceException {
		listaCandidatos = candidatoService.listarCandidatosPorNome(nomeCandidato, diversos.getIdEmpresa());
		return null;
	}


	public StreamedContent getFile() {
		return file;
	}


	public void setFile(StreamedContent file) {
		this.file = file;
	}


	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	public Date getDataFim() {
		return dataFim;
	}


	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public List<Candidato> getListaCandidatos() {
		
		return listaCandidatos;
	}


	public void setListaCandidatos(List<Candidato> listaCandidatos) {
		this.listaCandidatos = listaCandidatos;
	}


	public Candidato getCandidato() {
		if(candidato == null) {
			candidato = new Candidato();
		}
		return candidato;
	}


	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}


	public String getNomeCandidato() {
		return nomeCandidato;
	}


	public void setNomeCandidato(String nomeCandidato) {
		this.nomeCandidato = nomeCandidato;
	}


}