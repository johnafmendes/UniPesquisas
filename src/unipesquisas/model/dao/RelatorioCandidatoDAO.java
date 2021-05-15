package unipesquisas.model.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class RelatorioCandidatoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public byte[] abrirListaCandidato(String arquivoRelatorio, Integer idEmpresa, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
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

	public String gerarListaCandidato(String arquivoRelatorio, int idEmpresa, String sessionId, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
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

	public byte[] abrirListaCandidatoEscolaridade(String arquivoRelatorio,
			Integer idEmpresa, Integer idEscolaridade, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatoEscolaridade(String arquivoRelatorio,
			Integer idEmpresa, Integer idEscolaridade, String sessionId, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idEscolaridade", idEscolaridade);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaCandidatoInstituicao(String arquivoRelatorio,
			Integer idEmpresa, Integer idInstituicao, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatoInstituicao(String arquivoRelatorio,
			Integer idEmpresa, Integer idInstituicao, String sessionId, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idInstituicao", idInstituicao);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaCandidatoCurso(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatoCurso(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, String sessionId, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idCurso", idCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaCandidatoArea(String arquivoRelatorio,
			Integer idEmpresa, Integer idAreaCurso, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idArea", idAreaCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatoArea(String arquivoRelatorio,
			Integer idEmpresa, Integer idAreaCurso, String sessionId, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idArea", idAreaCurso);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

	public byte[] abrirListaCandidatoCursoMatriculado(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, Integer matriculado, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idCurso", idCurso);
		parametros.put("matriculado", matriculado);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaCandidatoCursoMatriculado(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, String sessionId,
			Integer matriculado, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("idCurso", idCurso);
		parametros.put("matriculado", matriculado);
		parametros.put("idStatusCandidato", idStatusCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

}
