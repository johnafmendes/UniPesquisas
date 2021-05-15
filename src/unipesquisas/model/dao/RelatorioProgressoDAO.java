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

public class RelatorioProgressoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public byte[] abrirListaProgresso(String arquivoRelatorio, Integer idEmpresa, Date dataInicio, Date dataFim) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
//		String di = new SimpleDateFormat("yyyy-MM-dd").format(dataInicio);
		parametros.put("dataInicio", dataInicio);
//		String df = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
		parametros.put("dataFim", dataFim);
		
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

	public String gerarListaProgresso(String arquivoRelatorio, int idEmpresa, String sessionId, Date dataInicio, Date dataFim) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
//		String di = new SimpleDateFormat("yyyy-MM-dd").format(dataInicio);
		parametros.put("dataInicio", dataInicio);
//		String df = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
		parametros.put("dataFim", dataFim);
		
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

	public byte[] abrirListaProgressoCandidato(String arquivoRelatorio,
			Integer idEmpresa, Date dataInicio2, Date dataFim2,
			Integer idCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("dataInicio", dataInicio2);
		parametros.put("dataFim", dataFim2);
		parametros.put("idCandidato", idCandidato);
		
		return abrirRelatorio(arquivoRelatorio, parametros, conn);
	}

	public String gerarListaProgressoCandidato(String arquivoRelatorio,
			Integer idEmpresa, String sessionId, Date dataInicio2,
			Date dataFim2, Integer idCandidato) throws DAOException {
		Connection conn = null;

		conn = getConnection();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("dataInicio", dataInicio2);
		parametros.put("dataFim", dataFim2);
		parametros.put("idCandidato", idCandidato);
		
		return gerarRelatorio(arquivoRelatorio, parametros, conn, sessionId);
	}

}
