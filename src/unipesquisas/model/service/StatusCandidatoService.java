package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.StatusCandidatoDAO;
import unipesquisas.model.entity.StatusCandidato;

public class StatusCandidatoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private StatusCandidatoDAO scDAO;
	
	public List<StatusCandidato> listarStatus(Integer idEmpresa) throws ServiceException {
		try {
			return scDAO.listarStatus(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean salvar(StatusCandidato sc) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			scDAO.salvar(sc);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			sc.setIdstatuscandidato(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(StatusCandidato sc) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			scDAO.atualizar(sc);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public StatusCandidato carregar(Integer idStatusCandidato) throws ServiceException {
		try {
			return scDAO.carregar(idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean excluir(Integer idStatusCandidato) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = scDAO.excluir(idStatusCandidato);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<StatusCandidato> listarResumoStatus(Integer idEmpresa) throws ServiceException {
		try {
			return scDAO.listarResumosStatus(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Integer getTotalCandidatosSemStatus(Integer idEmpresa) throws ServiceException {
		try {
			return scDAO.getTotalCandidatosSemStatus(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	

}
