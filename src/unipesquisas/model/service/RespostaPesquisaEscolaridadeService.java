package unipesquisas.model.service;


import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.RespostaPesquisaEscolaridadeDAO;
import unipesquisas.model.entity.RespostaPesquisaEscolaridade;

public class RespostaPesquisaEscolaridadeService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private RespostaPesquisaEscolaridadeDAO rpeDAO;

	public boolean salvar(RespostaPesquisaEscolaridade rpe) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			rpeDAO.salvar(rpe);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			rpe.setIdrespostapesquisaescolaridade(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public void atualizar(Integer idCandidato, Integer idStatusCandidato,
			Integer idEmpresa) throws ServiceException {
		try {		
			beginTransaction();
			rpeDAO.atualizar(idCandidato, idStatusCandidato, idEmpresa);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}

}
