package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.SMSMarketingCursoDAO;
import unipesquisas.model.entity.SMSMarketingCurso;

public class SMSMarketingCursoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private SMSMarketingCursoDAO smsmcDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

	public boolean salvar(SMSMarketingCurso smsmc, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!emcDAO.emailPublicado(emc.getEmailmarketing().getIdemailmarketing(), emc.getCurso().getIdcurso())){
				beginTransaction();
				smsmcDAO.salvar(smsmc);
				commitTransaction();
				status = true;
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			smsmc.setIdsmsmarketingcurso(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public SMSMarketingCurso carregar(Integer idSMSMarketing) throws ServiceException {
		try {
			return smsmcDAO.carregar(idSMSMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingCurso> listaSMSMarketingCurso(
			Integer idEmpresa) throws ServiceException {
		try {
			return smsmcDAO.listaSMSMarketingCurso(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingCurso> listarPublicacaoSMSMarketingPorIdSMS(
			int idSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsmcDAO.listaPublicacaoSMSMarketingPorIdSMS(idSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingCurso> listarPublicacaoSMSMarketingPorIdCurso(
			int idCurso, Integer idEmpresa) throws ServiceException {
		try {
			return smsmcDAO.listaPublicacaoSMSMarketingPorIdCurso(idCurso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingCurso> listarPublicacaoSMSMarketingPorCurso(
			String curso, Integer idEmpresa) throws ServiceException {
		try {
			return smsmcDAO.listaPublicacaoSMSMarketingPorCurso(curso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingCurso> listarPublicacaoSMSMarketingPorAssunto(
			String assuntoSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsmcDAO.listaPublicacaoSMSMarketingPorAssunto(assuntoSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


}
