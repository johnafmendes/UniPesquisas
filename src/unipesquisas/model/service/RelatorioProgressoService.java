package unipesquisas.model.service;

import java.util.Date;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.RelatorioProgressoDAO;

public class RelatorioProgressoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private RelatorioProgressoDAO rpDAO;

	public byte[] abrirListaProgresso(String arquivoRelatorio, Integer idEmpresa, Date dataInicio, Date dataFim) throws ServiceException {
		try {
			return rpDAO.abrirListaProgresso(arquivoRelatorio, idEmpresa, dataInicio, dataFim);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaProgresso(String arquivoRelatorio, int idEmpresa, String sessionId, Date dataInicio, Date dataFim) throws ServiceException {
		try {
			return rpDAO.gerarListaProgresso(arquivoRelatorio, idEmpresa, sessionId, dataInicio, dataFim);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaProgressoCandidato(String arquivoRelatorio,
			Integer idEmpresa, Date dataInicio2, Date dataFim2,
			Integer idCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaProgressoCandidato(arquivoRelatorio, idEmpresa, dataInicio2, dataFim2, idCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaProgressoCandidato(String arquivoRelatorio,
			Integer idEmpresa, String sessionId, Date dataInicio2,
			Date dataFim2, Integer idCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaProgressoCandidato(arquivoRelatorio, idEmpresa, sessionId, dataInicio2, dataFim2, idCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
