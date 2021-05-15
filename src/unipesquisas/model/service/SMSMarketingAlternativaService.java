package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.SMSMarketingAlternativaDAO;
import unipesquisas.model.entity.SMSMarketingAlternativa;

public class SMSMarketingAlternativaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private SMSMarketingAlternativaDAO smsmaDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

	public boolean salvar(SMSMarketingAlternativa smsma, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!emaDAO.emailPublicado(ema.getEmailmarketing().getIdemailmarketing(), ema.getPergunta().getIdpergunta(), ema.getPesquisa().getIdpesquisa(), ema.getAlternativa())){
				beginTransaction();
				smsmaDAO.salvar(smsma);
				commitTransaction();
				status = true;
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			smsma.setIdsmsmarketingalternativa(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public SMSMarketingAlternativa carregar(Integer idSMSMarketing) throws ServiceException {
		try {
			return smsmaDAO.carregar(idSMSMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingAlternativa> listaSMSMarketingAlternativa(
			Integer idEmpresa) throws ServiceException {
		try {
			return smsmaDAO.listaSMSMarketingAlternativa(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingAlternativa> listarPublicacaoSMSMarketingPorIdSMS(
			int idSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsmaDAO.listaPublicacaoSMSMarketingPorIdSMS(idSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingAlternativa> listarPublicacaoSMSMarketingPorIdPergunta(
			int idPergunta, Integer idEmpresa) throws ServiceException {
		try {
			return smsmaDAO.listaPublicacaoSMSMarketingPorIdPergunta(idPergunta, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingAlternativa> listarPublicacaoSMSMarketingPorPergunta(
			String pergunta, Integer idEmpresa) throws ServiceException {
		try {
			return smsmaDAO.listaPublicacaoSMSMarketingPorPergunta(pergunta, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingAlternativa> listarPublicacaoSMSMarketingPorAssunto(
			String assuntoSMS, Integer idEmpresa) throws ServiceException {
		try {
			return smsmaDAO.listaPublicacaoSMSMarketingPorAssunto(assuntoSMS, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingAlternativa> listarPublicacaoSMSMarketingPorIdPesquisa(
			int idPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return smsmaDAO.listaPublicacaoSMSMarketingPorIdPesquisa(idPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<SMSMarketingAlternativa> listarPublicacaoSMSMarketingPorPesquisa(
			String tituloPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return smsmaDAO.listaPublicacaoSMSMarketingPorPesquisa(tituloPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


}
