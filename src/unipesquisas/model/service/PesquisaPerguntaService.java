package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.PesquisaPerguntaDAO;
import unipesquisas.model.entity.PesquisaPergunta;

public class PesquisaPerguntaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private PesquisaPerguntaDAO ppDAO;

	public boolean salvar(PesquisaPergunta pp) throws ServiceException {
		boolean status;
		try {		
			if(!ppDAO.existePP(pp.getPesquisa().getIdpesquisa(), pp.getPergunta().getIdpergunta())){
				beginTransaction();
				ppDAO.salvar(pp);
				commitTransaction();
				status = true;
			} else {
				status = false;
			}
		} catch (DAOException e) {
			pp.setIdpesquisapergunta(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idPP) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = ppDAO.excluir(idPP);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<PesquisaPergunta> listarPPs(Integer idPesquisa) throws ServiceException {
		try {
			return ppDAO.listarPPs(idPesquisa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public PesquisaPergunta carregar(Integer idPesquisa, Integer idPergunta) throws ServiceException {
		try {
			return ppDAO.carregar(idPesquisa, idPergunta);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public int numeroPerguntasNaPesquisa(Integer idPesquisa) throws ServiceException {
		try {
			return ppDAO.numeroPerguntasNaPesquisa(idPesquisa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
}
