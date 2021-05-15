package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.PesquisaDAO;
import unipesquisas.model.entity.Pesquisa;

public class PesquisaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private PesquisaDAO pesquisaDAO;

	public boolean salvar(Pesquisa pesquisa) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			pesquisaDAO.salvar(pesquisa);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			pesquisa.setIdpesquisa(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(Pesquisa pesquisa) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			pesquisaDAO.atualizar(pesquisa);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idPesquisa) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = pesquisaDAO.excluir(idPesquisa);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<Pesquisa> listarPesquisas(Integer idEmpresa) throws ServiceException {
		try {
			return pesquisaDAO.listarPesquisas(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Pesquisa carregar(Integer idPesquisa) throws ServiceException {
		try {
			return pesquisaDAO.carregar(idPesquisa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorTitulo(String tituloPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return pesquisaDAO.listarPesquisaPorTitulo(tituloPesquisa, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorEscolaridade(Integer idEscolaridade,
			Integer idEmpresa) throws ServiceException {
		try {
			return pesquisaDAO.listarPesquisaPorEscolaridade(idEscolaridade, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorInstituicao(Integer idInstituicao,
			Integer idEmpresa) throws ServiceException {
		try {
			return pesquisaDAO.listarPesquisaPorInstituicao(idInstituicao, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorCurso(Integer idCurso,
			Integer idEmpresa) throws ServiceException {
		try {
			return pesquisaDAO.listarPesquisaPorCurso(idCurso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorID(int idPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return pesquisaDAO.listarPesquisaPorID(idPesquisa, idEmpresa);
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
