package unipesquisas.model.service;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EmailPadraoDAO;
import unipesquisas.model.entity.EmailPadrao;

public class EmailPadraoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private EmailPadraoDAO epDAO;

	public boolean salvar(EmailPadrao ep) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			epDAO.salvar(ep);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public EmailPadrao carregar() throws ServiceException {
		try {
			return epDAO.carregar();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
