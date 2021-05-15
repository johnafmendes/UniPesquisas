package unipesquisas.model.service;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.transaction.UserTransaction;

/**
 * Superclasse de todos os services da aplicação.
 */
@RequestScoped
public abstract class Service implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8724272228532626051L;
	/**
	 * Objeto que representa a transação
	 */
	@Resource
	private UserTransaction ut;
	
	/**
	 * Inicia uma nova transação
	 * @throws ServiceException
	 */
	protected void beginTransaction() throws ServiceException {
		try {
			ut.begin();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Faz o commit da transação em andamento
	 * @throws ServiceException
	 */
	protected void commitTransaction() throws ServiceException {
		try {
			ut.commit();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Faz o rollback da transação em andamento
	 * @throws ServiceException
	 */
	protected void rollbackTransaction() throws ServiceException {
		try {
			ut.rollback();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
