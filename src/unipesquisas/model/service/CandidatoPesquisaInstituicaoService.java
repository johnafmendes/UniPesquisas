package unipesquisas.model.service;


import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoPesquisaInstituicaoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.CandidatoPesquisaInstituicao;

public class CandidatoPesquisaInstituicaoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private CandidatoPesquisaInstituicaoDAO cpiDAO;

	public boolean atualizar(CandidatoPesquisaInstituicao cpi) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			cpiDAO.atualizar(cpi);
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