package unipesquisas.model.service;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoEmpresaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.CandidatoEmpresa;

public class CandidatoEmpresaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private CandidatoEmpresaDAO candidatoEmpresaDAO;

	public boolean salvar(CandidatoEmpresa ce, Integer admin) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			candidatoEmpresaDAO.salvar(ce, admin);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idCandidato, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = candidatoEmpresaDAO.excluir(idCandidato, idEmpresa);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean existeCandidatoEmpresa(CandidatoEmpresa ce) throws ServiceException {
		try {
			return candidatoEmpresaDAO.existeCandidatoEmpresa(ce);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public CandidatoEmpresa carregar(Integer idCandidato, Integer idEmpresa) throws ServiceException {
		try {
			return candidatoEmpresaDAO.carregar(idCandidato, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void atualizar(CandidatoEmpresa ce, Integer admin) throws ServiceException {
		try {
			candidatoEmpresaDAO.atualizar(ce, admin);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
