package unipesquisas.model.service;


import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.RespostaPesquisaDAO;
import unipesquisas.model.entity.RespostaPesquisa;

public class RespostaPesquisaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private RespostaPesquisaDAO rpDAO;

	public boolean salvar(RespostaPesquisa rp) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			rpDAO.salvar(rp);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			rp.setIdrespostapesquisa(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

}
