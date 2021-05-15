package unipesquisas.model.service;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.RelatorioCandidatoDAO;

public class RelatorioCandidatoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private RelatorioCandidatoDAO rcDAO;

	public byte[] abrirListaCandidato(String arquivoRelatorio, Integer idEmpresa, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.abrirListaCandidato(arquivoRelatorio, idEmpresa, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidato(String arquivoRelatorio, int idEmpresa, String sessionId, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.gerarListaCandidato(arquivoRelatorio, idEmpresa, sessionId, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatoEscolaridade(String arquivoRelatorio,
			Integer idEmpresa, Integer idEscolaridade, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.abrirListaCandidatoEscolaridade(arquivoRelatorio, idEmpresa, idEscolaridade, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatoEscolaridade(String arquivoRelatorio,
			Integer idEmpresa, Integer idEscolaridade, String sessionId, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.gerarListaCandidatoEscolaridade(arquivoRelatorio, idEmpresa, idEscolaridade, sessionId, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatoInstituicao(String arquivoRelatorio,
			Integer idEmpresa, Integer idInstituicao, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.abrirListaCandidatoInstituicao(arquivoRelatorio, idEmpresa, idInstituicao, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatoInstituicao(String arquivoRelatorio,
			Integer idEmpresa, Integer idInstituicao, String sessionId, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.gerarListaCandidatoInstituicao(arquivoRelatorio, idEmpresa, idInstituicao, sessionId, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatoCurso(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.abrirListaCandidatoCurso(arquivoRelatorio, idEmpresa, idCurso, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatoCurso(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, String sessionId, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.gerarListaCandidatoCurso(arquivoRelatorio, idEmpresa, idCurso, sessionId, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatoArea(String arquivoRelatorio,
			Integer idEmpresa, Integer idAreaCurso, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.abrirListaCandidatoArea(arquivoRelatorio, idEmpresa, idAreaCurso, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatoArea(String arquivoRelatorio,
			Integer idEmpresa, Integer idAreaCurso, String sessionId, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.gerarListaCandidatoArea(arquivoRelatorio, idEmpresa, idAreaCurso, sessionId, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatoCursoMatriculado(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, Integer matriculado, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.abrirListaCandidatoCursoMatriculado(arquivoRelatorio, idEmpresa, idCurso, matriculado, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatoCursoMatriculado(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, String sessionId,
			Integer matriculado, Integer idStatusCandidato) throws ServiceException {
		try {
			return rcDAO.gerarListaCandidatoCursoMatriculado(arquivoRelatorio, idEmpresa, idCurso, sessionId, matriculado, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
