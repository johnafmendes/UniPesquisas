package unipesquisas.model.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class RelatorioPesquisaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public byte[] abrirListaPesquisaAproveitamento(String arquivoRelatorio, Integer idEmpresa, Date dataInicio, Date dataFim,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
//		String di = new SimpleDateFormat("yyyy-MM-dd").format(dataInicio);
		parametros.put("dataInicio", dataInicio);
//		String df = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
		parametros.put("dataFim", dataFim);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	private byte[] abrirRelatorio(String arquivoRelatorio,
			Map<String, Object> parametros, Connection conn) {
		JasperPrint relatorio = null;
		try{
			relatorio = JasperFillManager.fillReport(arquivoRelatorio, parametros, conn);
			return JasperExportManager.exportReportToPdf(relatorio);
		}catch(JRException j){
			j.printStackTrace();
		}
		return null;
	}

	public String gerarListaPesquisaAproveitamento(String arquivoRelatorio, int idEmpresa, String sessionId, 
			Date dataInicio, Date dataFim, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("dataInicio", dataInicio);
		parametros.put("dataFim", dataFim);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	private String gerarRelatorio(String arquivoRelatorio,
			Map<String, Object> parametros, Connection conn, String sessionId) {
		JasperPrint relatorio = null;

		String relativeWebPath = "/temp";
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
		String arquivo = absoluteDiskPath+"\\"+sessionId+".pdf";
//		System.out.println("[=="+arquivo);
		try{
			relatorio = JasperFillManager.fillReport(arquivoRelatorio, parametros, conn);
			JasperExportManager.exportReportToPdfFile(relatorio, arquivo);
			return arquivo;
		}catch(JRException j){
			j.printStackTrace();
		}
		return null;
	}

	public byte[] abrirListaPesquisaAproveitamentoEscolaridadeIndividual(
			String arquivoRelatorio, Integer idPesquisa, Integer idEscolaridade,
			Integer idEmpresa, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaPesquisaAproveitamentoEscolaridadeIndividual(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idEscolaridade, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaPesquisaAproveitamentoInstituicaoIndividual(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idInstituicao, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaPesquisaAproveitamentoInstituicaoIndividual(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idInstituicao, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public String gerarListaPesquisaAproveitamentoCursoIndividual(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idCurso, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaPesquisaAproveitamentoCursoIndividual(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idCurso, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaPerguntasPorPesquisa(String arquivoRelatorio,
			Integer idEmpresa, String sessionId, Integer idPesquisa) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaPerguntasPorPesquisa(String arquivoRelatorio,
			Integer idEmpresa, Integer idPesquisa) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaEstatisticaPerguntasPorPesquisaGeral(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaEstatisticaPerguntasPorPesquisaGeral(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaEstatisticaPerguntasPorPesquisaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idEscolaridade, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaEstatisticaPerguntasPorPesquisaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idEscolaridade, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaEstatisticaPerguntasPorPesquisaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idInstituicao, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaEstatisticaPerguntasPorPesquisaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idInstituicao, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaEstatisticaPerguntasPorPesquisaCurso(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idCurso, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaEstatisticaPerguntasPorPesquisaCurso(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idCurso, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarEstatisticaPerguntaPorPesquisaGeral(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirEstatisticaPerguntaPorPesquisaGeral(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarEstatisticaPerguntaPorPesquisaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idEscolaridade, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirEstatisticaPerguntaPorPesquisaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, Integer idEscolaridade,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarEstatisticaPerguntaPorPesquisaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idInstituicao, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirEstatisticaPerguntaPorPesquisaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, Integer idInstituicao,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarEstatisticaPerguntaPorPesquisaCurso(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idCurso, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirEstatisticaPerguntaPorPesquisaCurso(
			String arquivoRelatorio, Integer idEmpresa, Integer idCurso,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatosPorPerguntaGeral(String arquivoRelatorio,
			Integer idEmpresa, String sessionId, String resposta,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("resposta", resposta);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaCandidatosPorPerguntaGeral(String arquivoRelatorio,
			Integer idEmpresa, String resposta, Integer idPesquisa,
			Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("resposta", resposta);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatosPorPerguntaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idEscolaridade, String resposta, Integer idPesquisa,
			Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("resposta", resposta);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaCandidatosPorPerguntaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, Integer idEscolaridade,
			String resposta, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("resposta", resposta);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatosPorPerguntaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idInstituicao, String resposta, Integer idPesquisa,
			Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("resposta", resposta);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaCandidatosPorPerguntaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, Integer idInstituicao,
			String resposta, Integer idPesquisa, Integer idPergunta, 
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("resposta", resposta);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatosPorPerguntaCurso(String arquivoRelatorio,
			Integer idEmpresa, String sessionId, Integer idCurso,
			String resposta, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("resposta", resposta);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaCandidatosPorPerguntaCurso(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, String resposta,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idPesquisa", idPesquisa);
		parametros.put("idPergunta", idPergunta);
		parametros.put("resposta", resposta);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}
	
}
