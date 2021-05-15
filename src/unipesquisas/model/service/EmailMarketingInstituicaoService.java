package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EmailMarketingInstituicaoDAO;
import unipesquisas.model.entity.EmailMarketingInstituicao;

public class EmailMarketingInstituicaoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private EmailMarketingInstituicaoDAO emiDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

	public boolean salvar(EmailMarketingInstituicao emi, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!emiDAO.emailPublicado(emi.getEmailmarketing().getIdemailmarketing(), emi.getInstituicao().getIdinstituicao())){
				beginTransaction();
				emiDAO.salvar(emi);
				commitTransaction();
				status = true;
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			emi.setIdemailmarketinginstituicao(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public EmailMarketingInstituicao carregar(Integer idEmailMarketing) throws ServiceException {
		try {
			return emiDAO.carregar(idEmailMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingInstituicao> listaEmailMarketingInstituicao(
			Integer idEmpresa) throws ServiceException {
		try {
			return emiDAO.listaEmailMarketingInstituicao(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingInstituicao> listarPublicacaoEmailMarketingPorIdEmail(
			int idEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emiDAO.listaPublicacaoEmailMarketingPorIdEmail(idEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingInstituicao> listarPublicacaoEmailMarketingPorIdInstituicao(
			int idInstituicao, Integer idEmpresa) throws ServiceException {
		try {
			return emiDAO.listaPublicacaoEmailMarketingPorIdInstituicao(idInstituicao, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingInstituicao> listarPublicacaoEmailMarketingPorInstituicao(
			String instituicao, Integer idEmpresa) throws ServiceException {
		try {
			return emiDAO.listaPublicacaoEmailMarketingPorInstituicao(instituicao, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingInstituicao> listarPublicacaoEmailMarketingPorAssunto(
			String assuntoEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emiDAO.listaPublicacaoEmailMarketingPorAssunto(assuntoEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


}
