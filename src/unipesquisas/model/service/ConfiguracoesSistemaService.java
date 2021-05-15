package unipesquisas.model.service;

import javax.inject.Inject;

import unipesquisas.model.dao.ConfiguracoesSistemaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.ConfiguracoesSistema;

public class ConfiguracoesSistemaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private ConfiguracoesSistemaDAO csDAO;

	public boolean salvar(ConfiguracoesSistema cs) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			csDAO.salvar(cs);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public ConfiguracoesSistema carregar() throws ServiceException {
		try {
			return csDAO.carregar();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
