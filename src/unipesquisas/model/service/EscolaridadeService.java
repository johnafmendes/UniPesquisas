package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EscolaridadeDAO;
import unipesquisas.model.entity.Escolaridade;

public class EscolaridadeService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private EscolaridadeDAO escolaridadeDAO;

	public boolean salvar(Escolaridade escolaridade) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			escolaridadeDAO.salvar(escolaridade);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(Escolaridade escolaridade) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			escolaridadeDAO.atualizar(escolaridade);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idEscolaridade) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = escolaridadeDAO.excluir(idEscolaridade);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<Escolaridade> listarEscolaridades() throws ServiceException {
		try {
			return escolaridadeDAO.listarEscolaridades();
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Escolaridade carregar(Integer idEscolaridade) throws ServiceException {
		try {
			return escolaridadeDAO.carregar(idEscolaridade);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Escolaridade> listarPesquisaPorEscolaridade(
			String tituloEscolaridade) throws ServiceException {
		try {
			return escolaridadeDAO.listarPesquisaPorEscolaridades(tituloEscolaridade);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Escolaridade> listarEscolaridadePorPesquisa(Integer idPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return escolaridadeDAO.listarEscolaridadesPorPesquisa(idPesquisa, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Escolaridade> listarEscolaridadePorID(int idEscolaridade) throws ServiceException {
		try {
			return escolaridadeDAO.listarEscolaridadesPorId(idEscolaridade);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Escolaridade> listarResumoEscolaridades(Integer idEmpresa) throws ServiceException {
		try {
			return escolaridadeDAO.listarResummosEscolaridades(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Escolaridade> listarResumoEscolaridades() throws ServiceException {
		try {
			return escolaridadeDAO.listarResummosEscolaridades();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
