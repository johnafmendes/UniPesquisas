package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.dao.ConfiguracoesSistemaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EmailPadraoDAO;
import unipesquisas.model.dao.SMSMarketingDAO;
import unipesquisas.model.entity.SMSMarketing;

public class SMSMarketingService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private SMSMarketingDAO smsMDAO;
	
	@Inject
	private ConfiguracoesSistemaDAO csDAO;
	
	@Inject
	private EmailPadraoDAO epDAO;

	@Inject
	private Diversos diversos; 

	public boolean salvar(SMSMarketing smsm) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			smsMDAO.salvar(smsm);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			smsm.setIdsmsmarketing(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(SMSMarketing smsm) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			smsMDAO.atualizar(smsm);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idSMSMarketing) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = smsMDAO.excluir(idSMSMarketing);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<SMSMarketing> listarSMSMarketings(Integer idEmpresa) throws ServiceException {
		try {
			return smsMDAO.listarSMSMarketing(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public SMSMarketing carregar(Integer idSMSMarketing) throws ServiceException {
		try {
			return smsMDAO.carregar(idSMSMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketing> listarSMSMarketingPorAssunto(String assuntoSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsMDAO.listarSMSMarketingPorAssunto(assuntoSMS, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketing> listarSMSMarketingPorID(int idSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsMDAO.listarSMSMarketingPorId(idSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
