package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.ProgressoDAO;
import unipesquisas.model.entity.Progresso;

public class ProgressoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private ProgressoDAO progressoDAO;

	public boolean salvar(Progresso progresso) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			progressoDAO.salvar(progresso);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			progresso.setIdprogressodetalhado(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(Progresso progresso) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			progressoDAO.atualizar(progresso);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idProgresso) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = progressoDAO.excluir(idProgresso);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<Progresso> listarProgressos(Integer idCandidato) throws ServiceException {
		try {
			return progressoDAO.listarProgressos(idCandidato);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Progresso carregar(Integer idProgresso) throws ServiceException {
		try {
			return progressoDAO.carregar(idProgresso);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
