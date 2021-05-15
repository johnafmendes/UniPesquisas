package unipesquisas.model.service;


import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoPesquisaEscolaridadeDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.CandidatoPesquisaEscolaridade;

public class CandidatoPesquisaEscolaridadeService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private CandidatoPesquisaEscolaridadeDAO cpeDAO;

	public boolean atualizar(CandidatoPesquisaEscolaridade cpe) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			cpeDAO.atualizar(cpe);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

}