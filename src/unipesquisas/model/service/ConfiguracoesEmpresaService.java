package unipesquisas.model.service;

import javax.inject.Inject;

import unipesquisas.model.dao.ConfiguracoesEmpresaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.ConfiguracoesEmpresa;

public class ConfiguracoesEmpresaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private ConfiguracoesEmpresaDAO ceDAO;

	public boolean salvar(ConfiguracoesEmpresa ce) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			ceDAO.salvar(ce);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public ConfiguracoesEmpresa carregar(Integer idEmpresa) throws ServiceException {
		try {
			return ceDAO.carregar(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
