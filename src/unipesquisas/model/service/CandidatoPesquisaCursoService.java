package unipesquisas.model.service;


import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoPesquisaCursoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.CandidatoPesquisaCurso;

public class CandidatoPesquisaCursoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private CandidatoPesquisaCursoDAO cpcDAO;

	public boolean atualizar(CandidatoPesquisaCurso cpc) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			cpcDAO.atualizar(cpc);
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