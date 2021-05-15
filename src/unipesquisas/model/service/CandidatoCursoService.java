package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoCursoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.CandidatoCurso;

public class CandidatoCursoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private CandidatoCursoDAO candidatoCursoDAO;

	public boolean salvar(CandidatoCurso cc) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			candidatoCursoDAO.salvar(cc);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idCandidato) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = candidatoCursoDAO.excluir(idCandidato);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<CandidatoCurso> listarCursosPorCandidato(Integer idEmpresa,
			Integer idCandidato) throws ServiceException {
		try {
			return candidatoCursoDAO.listarCursosPorCandidato(idEmpresa, idCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Boolean excluirCurso(Integer idCandidato, Integer idCurso) throws ServiceException {
		try {
			return candidatoCursoDAO.excluirCurso(idCandidato, idCurso);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public CandidatoCurso carregar(Integer idCandidatoCurso) throws ServiceException {
		try {
			return candidatoCursoDAO.carregar(idCandidatoCurso);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean excluirCurso(Integer idCandidatoCurso) throws ServiceException {
		try {
			return candidatoCursoDAO.excluirCurso(idCandidatoCurso);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean atualizar(CandidatoCurso cc) throws ServiceException {
		try {
			return candidatoCursoDAO.atualizar(cc);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public int numeroCursosSelecionados(Integer idCandidato, Integer idEmpresa) throws ServiceException {
		try {
			return candidatoCursoDAO.numeroCursosSelecionados(idCandidato, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
