package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.SMSMarketingInstituicaoDAO;
import unipesquisas.model.entity.SMSMarketingInstituicao;

public class SMSMarketingInstituicaoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private SMSMarketingInstituicaoDAO smsmiDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

	public boolean salvar(SMSMarketingInstituicao smsmi, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!emiDAO.emailPublicado(emi.getEmailmarketing().getIdemailmarketing(), emi.getInstituicao().getIdinstituicao())){
				beginTransaction();
				smsmiDAO.salvar(smsmi);
				commitTransaction();
				status = true;
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			smsmi.setIdsmsmarketinginstituicao(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public SMSMarketingInstituicao carregar(Integer idSMSMarketing) throws ServiceException {
		try {
			return smsmiDAO.carregar(idSMSMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingInstituicao> listaSMSMarketingInstituicao(
			Integer idEmpresa) throws ServiceException {
		try {
			return smsmiDAO.listaSMSMarketingInstituicao(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingInstituicao> listarPublicacaoSMSMarketingPorIdSMS(
			int idSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsmiDAO.listaPublicacaoSMSMarketingPorIdSMS(idSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingInstituicao> listarPublicacaoSMSMarketingPorIdInstituicao(
			int idInstituicao, Integer idEmpresa) throws ServiceException {
		try {
			return smsmiDAO.listaPublicacaoSMSMarketingPorIdInstituicao(idInstituicao, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingInstituicao> listarPublicacaoSMSMarketingPorInstituicao(
			String instituicao, Integer idEmpresa) throws ServiceException {
		try {
			return smsmiDAO.listaPublicacaoSMSMarketingPorInstituicao(instituicao, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingInstituicao> listarPublicacaoSMSMarketingPorAssunto(
			String assuntoSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsmiDAO.listaPublicacaoSMSMarketingPorAssunto(assuntoSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


}
