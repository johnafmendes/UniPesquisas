package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.InstituicaoDAO;
import unipesquisas.model.entity.Instituicao;

public class InstituicaoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 887579644558415590L;
	
	@Inject
	private InstituicaoDAO instituicaoDAO;

	/**
	 * Grava uma instituicao
	 */
	public boolean salvar(Instituicao instituicao) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			instituicaoDAO.salvar(instituicao);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}
	
	/**
	 * Atualiza uma instituicao
	 */
	public boolean atualizar(Instituicao instituicao) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			instituicaoDAO.atualizar(instituicao);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}
	
	/**
	 * Exclui uma instituicao
	 */
	public boolean excluir(Integer idInstituicao) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = instituicaoDAO.excluir(idInstituicao);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}
	
	/**
	 * Obtém a lista de instituicoes
	 */
	public List<Instituicao> listarIntituicoes(Integer idEmpresa) throws ServiceException {
		try {
			return instituicaoDAO.listarInstituicoes(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Instituicao carregar(Integer idInstituicao) throws ServiceException {
		try {
			return instituicaoDAO.carregar(idInstituicao);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Instituicao> listarPesquisaPorInstituicao(
			String tituloInstituicao, Integer idEmpresa) throws ServiceException {
		try {
			return instituicaoDAO.listarPesquisaPorInstituicao(tituloInstituicao, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Instituicao> listarInstituicaoPorPesquisa(Integer idPesquisa,
			Integer idEmpresa) throws ServiceException {
		try {
			return instituicaoDAO.listarInstituicaoPorPesquisa(idPesquisa, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Instituicao> listarInstituicaoPorID(int idInstituicao,
			Integer idEmpresa) throws ServiceException {
		try {
			return instituicaoDAO.listarInstituicaoPorId(idInstituicao, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Instituicao> listarInstituicoesDisponiveis(Integer idEmpresa,
			Integer idCandidato) throws ServiceException {
		try {
			return instituicaoDAO.listarInstituicoesDisponiveis(idCandidato, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Instituicao> listarResumoInstituicoes(Integer idEmpresa) throws ServiceException {
		try {
			return instituicaoDAO.listarResumoInstituicoes(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
