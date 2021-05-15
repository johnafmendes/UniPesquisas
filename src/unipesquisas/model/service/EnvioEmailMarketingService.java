package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EnvioEmailMarketingDAO;
import unipesquisas.model.entity.EnvioEmailMarketing;

public class EnvioEmailMarketingService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private EnvioEmailMarketingDAO eEMDAO;

	public List<EnvioEmailMarketing> listarEmailMarketings() throws ServiceException {
		try {
			return eEMDAO.listarEnvioEmailMarketing();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
}
