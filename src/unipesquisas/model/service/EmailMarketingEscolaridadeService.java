package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EmailMarketingEscolaridadeDAO;
import unipesquisas.model.entity.EmailMarketingEscolaridade;

public class EmailMarketingEscolaridadeService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private EmailMarketingEscolaridadeDAO emeDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

	public boolean salvar(EmailMarketingEscolaridade eme, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!emeDAO.emailPublicado(eme.getEmailmarketing().getIdemailmarketing(), eme.getEscolaridade().getIdescolaridade())){
				beginTransaction();
				emeDAO.salvar(eme);
				commitTransaction();
				status = true;
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			eme.setIdemailmarketingescolaridade(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public EmailMarketingEscolaridade carregar(Integer idEmailMarketing) throws ServiceException {
		try {
			return emeDAO.carregar(idEmailMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listaEmailMarketingEscolaridade(
			Integer idEmpresa) throws ServiceException {
		try {
			return emeDAO.listaEmailMarketingEscolaridade(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listarPublicacaoEmailMarketingPorIdEmail(
			int idEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emeDAO.listaPublicacaoEmailMarketingPorIdEmail(idEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listarPublicacaoEmailMarketingPorIdEscolaridade(
			int idEscolaridade, Integer idEmpresa) throws ServiceException {
		try {
			return emeDAO.listaPublicacaoEmailMarketingPorIdEscolaridade(idEscolaridade, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listarPublicacaoEmailMarketingPorEscolaridade(
			String escolaridade, Integer idEmpresa) throws ServiceException {
		try {
			return emeDAO.listaPublicacaoEmailMarketingPorEscolaridade(escolaridade, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listarPublicacaoEmailMarketingPorAssunto(
			String assuntoEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emeDAO.listaPublicacaoEmailMarketingPorAssunto(assuntoEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

//	public List<PesquisaEscolaridade> listaPesquisaEscolaridadeDisponiveis(
//			Integer idCandidato, Integer idEmpresa) throws ServiceException {
//		try {
//			return peDAO.listarPesquisaEscolaridadeDisponiveis(idCandidato, idEmpresa);
//		} catch (DAOException e) {
//			throw new ServiceException(e);
//		}
//	}

}
