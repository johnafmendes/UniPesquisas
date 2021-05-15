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
import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.CursoService;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.PerguntaService;
import unipesquisas.model.service.PesquisaService;
import unipesquisas.model.service.RelatorioPesquisaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;

@Named
@SessionScoped
public class RelatorioPesquisaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private RelatorioPesquisaService rpService;
	
	@Inject
	private StatusCandidatoService scService;

	@Inject
	private PesquisaService pesquisaService;
	
	@Inject
	private PerguntaService perguntaService;
	
	@Inject
	private EscolaridadeService escolaridadeService;
	
	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private CursoService cursoService;
	
	@Inject
	private Diversos diversos;
	
	private StreamedContent file;
	private Date dataInicio;
	private Date dataFim;
	private Candidato candidato;
//	private String nomeCandidato;
	private Pesquisa pesquisa;
	private Escolaridade escolaridade;
	private Instituicao instituicao;
	private Curso curso;
	private Pergunta pergunta;
	private String resposta;
	private List<Candidato> listaCandidatos;
	private List<Pergunta> listaPerguntas;
	private List<Escolaridade> listaEscolaridades;
	private List<Pesquisa> listaPesquisas;
	private List<Instituicao> listaInstituicoes;
	private List<Curso> listaCursos;
	private List<StatusCandidato> listaStatusCandidato;
	private Integer tipoPesquisaPesquisa;
	private Integer tipoPesquisaEscolaridade;
	private Integer tipoPesquisaInstituicao;
	private Integer tipoPesquisaCurso;
	private String idTituloPesquisa;
	private String idTituloEscolaridade;
	private String idTituloInstituicao;
	private String idTituloCurso;
	private Integer idPesquisaSelecionado;
	private Integer idEscolaridadeSelecionado;
	private Integer idInstituicaoSelecionada;
	private Integer idCursoSelecionado;
	private Integer idStatusCandidato;

	public String abrirListaPesquisaAproveitamentoEscolaridade() throws Exception {
		if(dataInicio == null || dataFim == null || idStatusCandidato == null){
			showMessage("- Selecione uma Data de Início, uma Data Fim e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoEscolaridadeStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaPesquisaAproveitamento(ArquivoRelatorio, diversos.getIdEmpresa(), dataInicio, dataFim, idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaPesquisaAproveitamentoEscolaridadeIndividual() throws Exception {
		getPesquisa();
		getEscolaridade();
		if(pesquisa.getIdpesquisa() == null || escolaridade.getIdescolaridade() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Escolaridade e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoEscolaridadeIndividualStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaPesquisaAproveitamentoEscolaridadeIndividual(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa(), escolaridade.getIdescolaridade(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaPesquisaAproveitamentoInstituicaoIndividual() throws Exception {
		getPesquisa();
		getInstituicao();
		if(pesquisa.getIdpesquisa() == null || instituicao.getIdinstituicao() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Instituição e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoInstituicaoIndividualStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaPesquisaAproveitamentoInstituicaoIndividual(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa(), instituicao.getIdinstituicao(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaPesquisaAproveitamentoCursoIndividual() throws Exception {
		getPesquisa();
		getCurso();
		if(pesquisa.getIdpesquisa() == null || curso.getIdcurso() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, um Curso e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoCursoIndividualStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaPesquisaAproveitamentoCursoIndividual(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa(), curso.getIdcurso(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaPerguntasPorPesquisa() throws Exception {
		getPesquisa();
		if(pesquisa.getIdpesquisa() == null){
			showMessage("- Selecione uma Pesquisa.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaPerguntasPorPesquisa.jasper"); 
		
		byte relatorio[] = rpService.abrirListaPerguntasPorPesquisa(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa());
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaEstatisticaPerguntasPorPesquisa() throws Exception {
		getPesquisa();
		if(pesquisa.getIdpesquisa() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaEstatisticaPerguntasPorPesquisaGeralStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaEstatisticaPerguntasPorPesquisaGeral(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaEstatisticaPerguntasPorPesquisaEscolaridade() throws Exception {
		getPesquisa();
		getEscolaridade();
		if(pesquisa.getIdpesquisa() == null || escolaridade.getIdescolaridade() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Escolaridade e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaEstatisticaPerguntasPorPesquisaEscolaridadeStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaEstatisticaPerguntasPorPesquisaEscolaridade(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa(), escolaridade.getIdescolaridade(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaEstatisticaPerguntasPorPesquisaInstituicao() throws Exception {
		getPesquisa();
		getInstituicao();
		if(pesquisa.getIdpesquisa() == null || instituicao.getIdinstituicao() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Instituição e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaEstatisticaPerguntasPorPesquisaInstituicaoStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaEstatisticaPerguntasPorPesquisaInstituicao(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa(), instituicao.getIdinstituicao(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaEstatisticaPerguntasPorPesquisaCurso() throws Exception {
		getPesquisa();
		getCurso();
		if(pesquisa.getIdpesquisa() == null || curso.getIdcurso() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, um Curso e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaEstatisticaPerguntasPorPesquisaCursoStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaEstatisticaPerguntasPorPesquisaCurso(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa(), curso.getIdcurso(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirEstatisticaPerguntaPorPesquisaGeral() throws Exception {
		getPesquisa();
		getPergunta();
		if(pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Pergunta e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("EstatisticaPerguntaPorPesquisaGeralStatus.jasper");
		
		byte relatorio[] = rpService.abrirEstatisticaPerguntaPorPesquisaGeral(ArquivoRelatorio, diversos.getIdEmpresa(), pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirEstatisticaPerguntaPorPesquisaEscolaridade() throws Exception {
		getEscolaridade();
		getPesquisa();
		getPergunta();
		if(escolaridade.getIdescolaridade() == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Escolaridade, Pesquisa, uma Pergunta e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("EstatisticaPerguntaPorPesquisaEscolaridadeStatus.jasper");
		
		byte relatorio[] = rpService.abrirEstatisticaPerguntaPorPesquisaEscolaridade(ArquivoRelatorio, diversos.getIdEmpresa(), escolaridade.getIdescolaridade(), pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirEstatisticaPerguntaPorPesquisaInstituicao() throws Exception {
		getInstituicao();
		getPesquisa();
		getPergunta();
		if(instituicao.getIdinstituicao() == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Instituição, Pesquisa, uma Pergunta e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("EstatisticaPerguntaPorPesquisaInstituicaoStatus.jasper");
		
		byte relatorio[] = rpService.abrirEstatisticaPerguntaPorPesquisaInstituicao(ArquivoRelatorio, diversos.getIdEmpresa(), instituicao.getIdinstituicao(), pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirEstatisticaPerguntaPorPesquisaCurso() throws Exception {
		getCurso();
		getPesquisa();
		getPergunta();
		if(curso.getIdcurso() == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione um Curso, Pesquisa, uma Pergunta e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("EstatisticaPerguntaPorPesquisaCursoStatus.jasper");
		
		byte relatorio[] = rpService.abrirEstatisticaPerguntaPorPesquisaCurso(ArquivoRelatorio, diversos.getIdEmpresa(), curso.getIdcurso(), pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	
	public String abrirListaPesquisaAproveitamentoInstituicao() throws Exception {
		if(dataInicio == null || dataFim == null || idStatusCandidato == null){
			showMessage("- Selecione uma Data de Início, uma Data Fim e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoInstituicaoStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaPesquisaAproveitamento(ArquivoRelatorio, diversos.getIdEmpresa(), dataInicio, dataFim, idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaCandidatosPorPerguntaGeral() throws Exception {
		getPesquisa();
		getPergunta();
		if(resposta == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Pergunta, uma Alternativa e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosPorPesquisaStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaCandidatosPorPerguntaGeral(ArquivoRelatorio, diversos.getIdEmpresa(), resposta, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaCandidatosPorPerguntaEscolaridade() throws Exception {
		getEscolaridade();
		getPesquisa();
		getPergunta();
		if(escolaridade.getIdescolaridade() == null || resposta == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Escolaridade, uma Pesquisa, uma Pergunta, uma Alternativa e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosPorPesquisaEscolaridadeStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaCandidatosPorPerguntaEscolaridade(ArquivoRelatorio, diversos.getIdEmpresa(), escolaridade.getIdescolaridade(), resposta, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaCandidatosPorPerguntaInstituicao() throws Exception {
		getInstituicao();
		getPesquisa();
		getPergunta();
		if(instituicao.getIdinstituicao() == null || resposta == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Instituição, uma Pesquisa, uma Pergunta, uma Alternativa e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosPorPesquisaInstituicaoStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaCandidatosPorPerguntaInstituicao(ArquivoRelatorio, diversos.getIdEmpresa(), instituicao.getIdinstituicao(), resposta, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	public String abrirListaCandidatosPorPerguntaCurso() throws Exception {
		getCurso();
		getPesquisa();
		getPergunta();
		if(curso.getIdcurso() == null || resposta == null || pesquisa.getIdpesquisa() == null || 
				pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione um Curso, uma Pesquisa, uma Pergunta e uma Alternativa.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosPorPesquisaCursoStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaCandidatosPorPerguntaCurso(ArquivoRelatorio, diversos.getIdEmpresa(), curso.getIdcurso(), resposta, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		montaPagina(relatorio);
		return null;
	}
	
	
	public String abrirListaPesquisaAproveitamentoCurso() throws Exception {
		if(dataInicio == null || dataFim == null || idStatusCandidato == null){
			showMessage("- Selecione uma Data de Início, uma Data Fim e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoCursoStatus.jasper");
		
		byte relatorio[] = rpService.abrirListaPesquisaAproveitamento(ArquivoRelatorio, diversos.getIdEmpresa(), dataInicio, dataFim, idStatusCandidato);
		
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


	public String gerarListaPesquisaAproveitamentoEscolaridade() throws Exception {
		if(dataInicio == null || dataFim == null || idStatusCandidato == null){
			showMessage("- Selecione uma Data de Início, uma Data Fim e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoEscolaridadeStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaPesquisaAproveitamento(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, dataInicio, dataFim, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaPesquisaAproveitamentoEscolaridade.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String gerarListaPesquisaAproveitamentoInstituicao() throws Exception {
		if(dataInicio == null || dataFim == null || idStatusCandidato == null){
			showMessage("- Selecione uma Data de Início, uma Data Fim e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoInstituicaoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaPesquisaAproveitamento(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, dataInicio, dataFim, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaPesquisaAproveitamentoInstituicao.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaPesquisaAproveitamentoCurso() throws Exception {
		if(dataInicio == null || dataFim == null || idStatusCandidato == null){
			showMessage("- Selecione uma Data de Início, uma Data Fim e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoCursoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaPesquisaAproveitamento(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, dataInicio, dataFim, idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaPesquisaAproveitamentoCurso.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaPesquisaAproveitamentoEscolaridadeIndividual() throws Exception {
		getPesquisa();
		getEscolaridade();
		if(pesquisa.getIdpesquisa() == null || escolaridade.getIdescolaridade() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Escolaridade e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoEscolaridadeIndividualStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaPesquisaAproveitamentoEscolaridadeIndividual(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa(), escolaridade.getIdescolaridade(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaPesquisaAproveitamentoEscolaridadeIndividual.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaPesquisaAproveitamentoInstituicaoIndividual() throws Exception {
		getPesquisa();
		getInstituicao();
		if(pesquisa.getIdpesquisa() == null || instituicao.getIdinstituicao() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Instituição e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoInstituicaoIndividualStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaPesquisaAproveitamentoInstituicaoIndividual(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa(), instituicao.getIdinstituicao(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaPesquisaAproveitamentoInstituicaoIndividual.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaPesquisaAproveitamentoCursoIndividual() throws Exception {
		getPesquisa();
		getCurso();
		if(pesquisa.getIdpesquisa() == null || curso.getIdcurso() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, um Curso e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaPesquisaAproveitamentoCursoIndividualStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaPesquisaAproveitamentoCursoIndividual(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa(), curso.getIdcurso(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaPesquisaAproveitamentoCursoIndividual.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaPerguntasPorPesquisa() throws Exception {
		getPesquisa();
		if(pesquisa.getIdpesquisa() == null ){
			showMessage("- Selecione uma Pesquisa.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaPerguntasPorPesquisa.jasper"); 
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaPerguntasPorPesquisa(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa());
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaPerguntasPorPesquisa.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaEstatisticaPerguntasPorPesquisa() throws Exception {
		getPesquisa();
		if(pesquisa.getIdpesquisa() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaEstatisticaPerguntasPorPesquisaGeralStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaEstatisticaPerguntasPorPesquisaGeral(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaEstatisticaPerguntasPorPesquisaGeral.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaEstatisticaPerguntasPorPesquisaEscolaridade() throws Exception {
		getPesquisa();
		getEscolaridade();
		if(pesquisa.getIdpesquisa() == null || escolaridade.getIdescolaridade() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Escolaridade e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaEstatisticaPerguntasPorPesquisaEscolaridadeStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaEstatisticaPerguntasPorPesquisaEscolaridade(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa(), escolaridade.getIdescolaridade(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaEstatisticaPerguntasPorPesquisaEscolaridade.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaEstatisticaPerguntasPorPesquisaInstituicao() throws Exception {
		getPesquisa();
		getInstituicao();
		if(pesquisa.getIdpesquisa() == null || instituicao.getIdinstituicao() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Instituição e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaEstatisticaPerguntasPorPesquisaInstituicaoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaEstatisticaPerguntasPorPesquisaInstituicao(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa(), instituicao.getIdinstituicao(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaEstatisticaPerguntasPorPesquisaInstituicao.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaEstatisticaPerguntasPorPesquisaCurso() throws Exception {
		getPesquisa();
		getCurso();
		if(pesquisa.getIdpesquisa() == null || curso.getIdcurso() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, um Curso e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaEstatisticaPerguntasPorPesquisaCursoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaEstatisticaPerguntasPorPesquisaCurso(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa(), curso.getIdcurso(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaEstatisticaPerguntasPorPesquisaCurso.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarEstatisticaPerguntaPorPesquisaGeral() throws Exception {
		getPesquisa();
		getPergunta();
		if(pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Pergunta e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("EstatisticaPerguntaPorPesquisaGeralStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarEstatisticaPerguntaPorPesquisaGeral(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "EstatisticaPerguntaPorPesquisaGeral.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarEstatisticaPerguntaPorPesquisaEscolaridade() throws Exception {
		getEscolaridade();
		getPesquisa();
		getPergunta();
		if(escolaridade.getIdescolaridade() == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Escolaridade, Pesquisa, uma Pergunta e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("EstatisticaPerguntaPorPesquisaEscolaridadeStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarEstatisticaPerguntaPorPesquisaEscolaridade(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, escolaridade.getIdescolaridade(), pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "EstatisticaPerguntaPorPesquisaEscolaridade.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

	public String gerarEstatisticaPerguntaPorPesquisaInstituicao() throws Exception {
		getInstituicao();
		getPesquisa();
		getPergunta();
		if(instituicao.getIdinstituicao() == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Instituição, Pesquisa, uma Pergunta e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("EstatisticaPerguntaPorPesquisaInstituicaoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarEstatisticaPerguntaPorPesquisaInstituicao(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, instituicao.getIdinstituicao(), pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "EstatisticaPerguntaPorPesquisaInstituicao.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String gerarEstatisticaPerguntaPorPesquisaCurso() throws Exception {
		getCurso();
		getPesquisa();
		getPergunta();
		if(curso.getIdcurso() == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione um Curso, Pesquisa, uma Pergunta e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("EstatisticaPerguntaPorPesquisaCursoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarEstatisticaPerguntaPorPesquisaCurso(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, curso.getIdcurso(), pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "EstatisticaPerguntaPorPesquisaCurso.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaCandidatosPorPerguntaGeral() throws Exception {
		getPesquisa();
		getPergunta();
		if(resposta == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Pesquisa, uma Pergunta, uma Alternativa e um Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosPorPesquisaStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaCandidatosPorPerguntaGeral(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, resposta, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosPorPesquisaGeral.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaCandidatosPorPerguntaEscolaridade() throws Exception {
		getEscolaridade();
		getPesquisa();
		getPergunta();
		if(escolaridade.getIdescolaridade() == null || resposta == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Escolaridade, uma Pesquisa, uma Pergunta, uma Alternativa e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosPorPesquisaEscolaridadeStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaCandidatosPorPerguntaEscolaridade(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, escolaridade.getIdescolaridade(), resposta, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosPorPesquisaEscolaridade.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaCandidatosPorPerguntaInstituicao() throws Exception {
		getInstituicao();
		getPesquisa();
		getPergunta();
		if(instituicao.getIdinstituicao() == null || resposta == null || pesquisa.getIdpesquisa() == null || pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione uma Instituição, uma Pesquisa, uma Pergunta, uma Alternativa e Status.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosPorPesquisaInstituicaoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaCandidatosPorPerguntaInstituicao(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, instituicao.getIdinstituicao(), resposta, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosPorPesquisaInstituicao.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarListaCandidatosPorPerguntaCurso() throws Exception {
		getCurso();
		getPesquisa();
		getPergunta();
		if(curso.getIdcurso() == null || resposta == null || pesquisa.getIdpesquisa() == null || 
				pergunta.getIdpergunta() == null || idStatusCandidato == null){
			showMessage("- Selecione um Curso, uma Pesquisa, uma Pergunta e uma Alternativa.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		String ArquivoRelatorio = getArquivoRelatorio("ListaCandidatosPorPesquisaCursoStatus.jasper");
		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		String arquivo = rpService.gerarListaCandidatosPorPerguntaCurso(ArquivoRelatorio, diversos.getIdEmpresa(), sessionId, curso.getIdcurso(), resposta, pesquisa.getIdpesquisa(), pergunta.getIdpergunta(), idStatusCandidato);
		
		try{
			InputStream in = new FileInputStream(arquivo);
			file = new DefaultStreamedContent(in, "application/pdf", "ListaCandidatosPorPesquisaCurso.pdf");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String pesquisarPesquisa() throws ServiceException{
		if(tipoPesquisaPesquisa==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Título da Pesquisa.", FacesMessage.SEVERITY_ERROR);
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
		limparTudo("pesquisa");
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaPesquisas = pesquisaService.listarPesquisas(diversos.getIdEmpresa());
		limparTudo("pesquisa");
		return null;
	}
	
	public void limparTudo(String origem){
		if(!origem.equals("escolaridade")){
			listaEscolaridades = null;
		}
		if(!origem.equals("instituicao")){
			listaInstituicoes = null;
		}
		if(!origem.equals("curso")){
			listaCursos = null;
		}
		if(!origem.equals("pesquisa")){
			listaPesquisas = null;
		}
		listaPerguntas = null;
		pergunta = null;
		idPesquisaSelecionado = null;
		idEscolaridadeSelecionado = null;
		idInstituicaoSelecionada = null;
		idCursoSelecionado = null;
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
		limparTudo("escolaridade");
		return null;
	}
	
	public String listarTudoEscolaridade() throws ServiceException{
		listaEscolaridades = escolaridadeService.listarEscolaridades();
		limparTudo("escolaridade");
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
		limparTudo("instituicao");
		return null;
	}
	
	public String listarTudoInstituicao() throws ServiceException{
		listaInstituicoes = instituicaoService.listarIntituicoes(diversos.getIdEmpresa());
		limparTudo("instituicao");
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
		limparTudo("curso");
		return null;
	}
	
	public String listarTudoCurso() throws ServiceException{
		listaCursos = cursoService.listarCursos(diversos.getIdEmpresa());
		limparTudo("curso");
		return null;
	}
	
	public String pesquisarPesquisaPorEscolaridade (Integer idEscolaridade) throws ServiceException {
		idEscolaridadeSelecionado = idEscolaridade;
		listaPesquisas = pesquisaService.listarPesquisaPorEscolaridade(idEscolaridade, diversos.getIdEmpresa());
		listaPerguntas = null;
		pergunta = null;
		idPesquisaSelecionado = null;
		return null;
	}
	
	public String pesquisarPesquisaPorInstituicao (Integer idInstituicao) throws ServiceException {
		idInstituicaoSelecionada = idInstituicao;
		listaPesquisas = pesquisaService.listarPesquisaPorInstituicao(idInstituicao, diversos.getIdEmpresa());
		listaPerguntas = null;
		pergunta = null;
		idPesquisaSelecionado = null;
		return null;
	}
	
	public String pesquisarPesquisaPorCurso (Integer idCurso) throws ServiceException {
		idCursoSelecionado = idCurso;
		listaPesquisas = pesquisaService.listarPesquisaPorCurso(idCurso, diversos.getIdEmpresa());
		listaPerguntas = null;
		pergunta = null;
		idPesquisaSelecionado = null;
		return null;
	}
	
	public String filtrarEscolaridades(Integer idPesquisa) throws ServiceException{
		idPesquisaSelecionado = idPesquisa;
		listaEscolaridades = escolaridadeService.listarEscolaridadePorPesquisa(idPesquisa, diversos.getIdEmpresa());
		return null;
	}
	
	public String filtrarInstituicoes(Integer idPesquisa) throws ServiceException{
		idPesquisaSelecionado = idPesquisa;
		listaInstituicoes = instituicaoService.listarInstituicaoPorPesquisa(idPesquisa, diversos.getIdEmpresa());
		return null;
	}
	
	public String filtrarCursos(Integer idPesquisa) throws ServiceException{
		idPesquisaSelecionado = idPesquisa;
		listaCursos = cursoService.listarCursoPorPesquisa(idPesquisa, diversos.getIdEmpresa());
		return null;
	}
	
	public String filtrarPerguntas(Integer idPesquisa) throws ServiceException{
		idPesquisaSelecionado = idPesquisa;
		listaPerguntas = perguntaService.listarPerguntasPorPesquisa(idPesquisa, diversos.getIdEmpresa());
		pergunta = null;
		return null;
	}
	
	public String carregarPergunta(Integer idPergunta) throws ServiceException{
		pergunta = perguntaService.carregar(idPergunta);
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


//	public String getNomeCandidato() {
//		return nomeCandidato;
//	}
//
//
//	public void setNomeCandidato(String nomeCandidato) {
//		this.nomeCandidato = nomeCandidato;
//	}


	public List<Pesquisa> getListaPesquisas() {
		return listaPesquisas;
	}

	public void setListaPesquisas(List<Pesquisa> listaPesquisas) {
		this.listaPesquisas = listaPesquisas;
	}

	public Pesquisa getPesquisa() {
		if(pesquisa == null){
			pesquisa = new Pesquisa(); 
		}
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	public Escolaridade getEscolaridade() {
		if(escolaridade == null){
			escolaridade = new Escolaridade();
		}
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public List<Escolaridade> getListaEscolaridades() throws ServiceException {
		return listaEscolaridades;
	}

	public void setListaEscolaridades(List<Escolaridade> listaEscolaridades) {
		this.listaEscolaridades = listaEscolaridades;
	}

	public List<Instituicao> getListaInstituicoes() throws ServiceException {
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


	public Curso getCurso() {
		if(curso == null){
			curso = new Curso();
		}
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Curso> getListaCursos() throws ServiceException {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
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

	public List<Pergunta> getListaPerguntas() {
		return listaPerguntas;
	}

	public void setListaPerguntas(List<Pergunta> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
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

	public Integer getIdPesquisaSelecionado() {
		return idPesquisaSelecionado;
	}

	public void setIdPesquisaSelecionado(Integer idPesquisaSelecionado) {
		this.idPesquisaSelecionado = idPesquisaSelecionado;
	}

	public Integer getIdEscolaridadeSelecionado() {
		return idEscolaridadeSelecionado;
	}

	public void setIdEscolaridadeSelecionado(Integer idEscolaridadeSelecionado) {
		this.idEscolaridadeSelecionado = idEscolaridadeSelecionado;
	}

	public Integer getIdInstituicaoSelecionada() {
		return idInstituicaoSelecionada;
	}

	public void setIdInstituicaoSelecionada(Integer idInstituicaoSelecionada) {
		this.idInstituicaoSelecionada = idInstituicaoSelecionada;
	}

	public Integer getIdCursoSelecionado() {
		return idCursoSelecionado;
	}

	public void setIdCursoSelecionado(Integer idCursoSelecionado) {
		this.idCursoSelecionado = idCursoSelecionado;
	}

	public Integer getTipoPesquisaEscolaridade() {
		return tipoPesquisaEscolaridade;
	}

	public void setTipoPesquisaEscolaridade(Integer tipoPesquisaEscolaridade) {
		this.tipoPesquisaEscolaridade = tipoPesquisaEscolaridade;
	}

	public Integer getTipoPesquisaInstituicao() {
		return tipoPesquisaInstituicao;
	}

	public void setTipoPesquisaInstituicao(Integer tipoPesquisaInstituicao) {
		this.tipoPesquisaInstituicao = tipoPesquisaInstituicao;
	}

	public Integer getTipoPesquisaCurso() {
		return tipoPesquisaCurso;
	}

	public void setTipoPesquisaCurso(Integer tipoPesquisaCurso) {
		this.tipoPesquisaCurso = tipoPesquisaCurso;
	}

	public String getIdTituloEscolaridade() {
		return idTituloEscolaridade;
	}

	public void setIdTituloEscolaridade(String idTituloEscolaridade) {
		this.idTituloEscolaridade = idTituloEscolaridade;
	}

	public String getIdTituloInstituicao() {
		return idTituloInstituicao;
	}

	public void setIdTituloInstituicao(String idTituloInstituicao) {
		this.idTituloInstituicao = idTituloInstituicao;
	}

	public String getIdTituloCurso() {
		return idTituloCurso;
	}

	public void setIdTituloCurso(String idTituloCurso) {
		this.idTituloCurso = idTituloCurso;
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
