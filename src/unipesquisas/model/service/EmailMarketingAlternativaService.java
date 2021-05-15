package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EmailMarketingAlternativaDAO;
import unipesquisas.model.entity.EmailMarketingAlternativa;

public class EmailMarketingAlternativaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private EmailMarketingAlternativaDAO emaDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

	public boolean salvar(EmailMarketingAlternativa ema, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!emaDAO.emailPublicado(ema.getEmailmarketing().getIdemailmarketing(), ema.getPergunta().getIdpergunta(), ema.getPesquisa().getIdpesquisa(), ema.getAlternativa())){
				beginTransaction();
				emaDAO.salvar(ema);
				commitTransaction();
				status = true;
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			ema.setIdemailmarketingalternativa(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public EmailMarketingAlternativa carregar(Integer idEmailMarketing) throws ServiceException {
		try {
			return emaDAO.carregar(idEmailMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingAlternativa> listaEmailMarketingAlternativa(
			Integer idEmpresa) throws ServiceException {
		try {
			return emaDAO.listaEmailMarketingAlternativa(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingAlternativa> listarPublicacaoEmailMarketingPorIdEmail(
			int idEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emaDAO.listaPublicacaoEmailMarketingPorIdEmail(idEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingAlternativa> listarPublicacaoEmailMarketingPorIdPergunta(
			int idPergunta, Integer idEmpresa) throws ServiceException {
		try {
			return emaDAO.listaPublicacaoEmailMarketingPorIdPergunta(idPergunta, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingAlternativa> listarPublicacaoEmailMarketingPorPergunta(
			String pergunta, Integer idEmpresa) throws ServiceException {
		try {
			return emaDAO.listaPublicacaoEmailMarketingPorPergunta(pergunta, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingAlternativa> listarPublicacaoEmailMarketingPorAssunto(
			String assuntoEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emaDAO.listaPublicacaoEmailMarketingPorAssunto(assuntoEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingAlternativa> listarPublicacaoEmailMarketingPorIdPesquisa(
			int idPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return emaDAO.listaPublicacaoEmailMarketingPorIdPesquisa(idPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingAlternativa> listarPublicacaoEmailMarketingPorPesquisa(
			String tituloPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return emaDAO.listaPublicacaoEmailMarketingPorPesquisa(tituloPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


}
