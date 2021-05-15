package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EmailMarketingCursoDAO;
import unipesquisas.model.entity.EmailMarketingCurso;

public class EmailMarketingCursoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private EmailMarketingCursoDAO emcDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

	public boolean salvar(EmailMarketingCurso emc, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!emcDAO.emailPublicado(emc.getEmailmarketing().getIdemailmarketing(), emc.getCurso().getIdcurso())){
				beginTransaction();
				emcDAO.salvar(emc);
				commitTransaction();
				status = true;
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			emc.setIdemailmarketingcurso(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public EmailMarketingCurso carregar(Integer idEmailMarketing) throws ServiceException {
		try {
			return emcDAO.carregar(idEmailMarketing);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingCurso> listaEmailMarketingCurso(
			Integer idEmpresa) throws ServiceException {
		try {
			return emcDAO.listaEmailMarketingCurso(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingCurso> listarPublicacaoEmailMarketingPorIdEmail(
			int idEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emcDAO.listaPublicacaoEmailMarketingPorIdEmail(idEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingCurso> listarPublicacaoEmailMarketingPorIdCurso(
			int idCurso, Integer idEmpresa) throws ServiceException {
		try {
			return emcDAO.listaPublicacaoEmailMarketingPorIdCurso(idCurso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingCurso> listarPublicacaoEmailMarketingPorCurso(
			String curso, Integer idEmpresa) throws ServiceException {
		try {
			return emcDAO.listaPublicacaoEmailMarketingPorCurso(curso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<EmailMarketingCurso> listarPublicacaoEmailMarketingPorAssunto(
			String assuntoEmail, Integer idEmpresa) throws ServiceException {
		try {
			return emcDAO.listaPublicacaoEmailMarketingPorAssunto(assuntoEmail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


}
