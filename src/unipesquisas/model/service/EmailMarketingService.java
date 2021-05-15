package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.mail.EmailException;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.dao.ConfiguracoesSistemaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EmailMarketingDAO;
import unipesquisas.model.dao.EmailPadraoDAO;
import unipesquisas.model.entity.ConfiguracoesSistema;
import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailPadrao;

public class EmailMarketingService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private EmailMarketingDAO emDAO;
	
	@Inject
	private ConfiguracoesSistemaDAO csDAO;
	
	@Inject
	private EmailPadraoDAO epDAO;

	@Inject
	private Diversos diversos; 

	public boolean salvar(EmailMarketing em) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			emDAO.salvar(em);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			em.setIdemailmarketing(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(EmailMarketing em) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			emDAO.atualizar(em);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idEmailMarketing) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = emDAO.excluir(idEmailMarketing);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<EmailMarketing> listarEmailMarketings(Integer idEmpresa) throws ServiceException {
		try {
			return emDAO.listarEmailMarketing(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public EmailMarketing carregar(Integer idEmailMarketing) throws ServiceException {
		try {
			return emDAO.carregar(idEmailMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketing> listarEmailMarketingPorAssunto(String assuntoEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emDAO.listarEmailMarketingPorAssunto(assuntoEmail, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
//
//	public List<Pesquisa> listarPesquisaPorEscolaridade(Integer idEscolaridade,
//			Integer idEmpresa) throws ServiceException {
//		try {
//			return pesquisaDAO.listarPesquisaPorEscolaridade(idEscolaridade, idEmpresa);
//		} catch (DAOException e) {
//			throw new ServiceException(e);
//		}
//	}
//
//	public List<Pesquisa> listarPesquisaPorInstituicao(Integer idInstituicao,
//			Integer idEmpresa) throws ServiceException {
//		try {
//			return pesquisaDAO.listarPesquisaPorInstituicao(idInstituicao, idEmpresa);
//		} catch (DAOException e) {
//			throw new ServiceException(e);
//		}
//	}
//
//	public List<Pesquisa> listarPesquisaPorCurso(Integer idCurso,
//			Integer idEmpresa) throws ServiceException {
//		try {
//			return pesquisaDAO.listarPesquisaPorCurso(idCurso, idEmpresa);
//		} catch (DAOException e) {
//			throw new ServiceException(e);
//		}
//	}

	public void enviarEmailBoasVindas(String email) throws EmailException, ServiceException {
		try {
			ConfiguracoesSistema cs = csDAO.getConfiguracaoEmail();
			EmailPadrao ep = epDAO.getEmailBoasVindas();
			diversos.enviaEmailBoasVindas(email, cs, ep);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

	public List<EmailMarketing> listarEmailMarketingPorID(int idEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emDAO.listarEmailMarketingPorId(idEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

//	public List<Pesquisa> listarPesquisasDisponiveis(Integer idCandidato) throws ServiceException {
//		try {
//			return pesquisaDAO.listarPesquisasDisponiveis(idCandidato);
//			
//		} catch (DAOException e) {
//			throw new ServiceException(e);
//		}
//	}

}
