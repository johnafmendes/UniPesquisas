package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.SMSMarketingEscolaridadeDAO;
import unipesquisas.model.entity.SMSMarketingEscolaridade;

public class SMSMarketingEscolaridadeService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private SMSMarketingEscolaridadeDAO smsMEDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

	public boolean salvar(SMSMarketingEscolaridade smsme, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!emeDAO.emailPublicado(eme.getEmailmarketing().getIdemailmarketing(), eme.getEscolaridade().getIdescolaridade())){
				beginTransaction();
				smsMEDAO.salvar(smsme);
				commitTransaction();
				status = true;
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			smsme.setIdsmsmarketingescolaridade(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public SMSMarketingEscolaridade carregar(Integer idSMSMarketing) throws ServiceException {
		try {
			return smsMEDAO.carregar(idSMSMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listaSMSMarketingEscolaridade(
			Integer idEmpresa) throws ServiceException {
		try {
			return smsMEDAO.listaSMSMarketingEscolaridade(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listarPublicacaoSMSMarketingPorIdSMS(
			int idSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsMEDAO.listaPublicacaoSMSMarketingPorIdSMS(idSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listarPublicacaoSMSMarketingPorIdEscolaridade(
			int idEscolaridade, Integer idEmpresa) throws ServiceException {
		try {
			return smsMEDAO.listaPublicacaoSMSMarketingPorIdEscolaridade(idEscolaridade, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listarPublicacaoSMSMarketingPorEscolaridade(
			String escolaridade, Integer idEmpresa) throws ServiceException {
		try {
			return smsMEDAO.listaPublicacaoSMSMarketingPorEscolaridade(escolaridade, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listarPublicacaoSMSMarketingPorAssunto(
			String assuntoSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsMEDAO.listaPublicacaoSMSMarketingPorAssunto(assuntoSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
