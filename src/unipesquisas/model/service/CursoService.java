package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CursoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.Curso;

public class CursoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private CursoDAO cursoDAO;

	public boolean salvar(Curso curso) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			cursoDAO.salvar(curso);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			curso.setIdcurso(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(Curso curso) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			cursoDAO.atualizar(curso);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idCurso) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = cursoDAO.excluir(idCurso);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<Curso> listarCursos(Integer idEmpresa) throws ServiceException {
		try {
			return cursoDAO.listarCursos(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Curso carregar(Integer idCurso) throws ServiceException {
		try {
			return cursoDAO.carregar(idCurso);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Curso> listarCursosEscolhidos(Integer idEmpresa,
			Integer idCandidato) throws ServiceException {
		try {
			return cursoDAO.listarCursosEscolhidos(idEmpresa, idCandidato);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Curso> listarCursosDisponiveis(Integer idEmpresa,
			Integer idCandidato) throws ServiceException {
		try {
			return cursoDAO.listarCursosDisponiveis(idEmpresa, idCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Curso> listarPesquisaPorCurso(String tituloCurso,
			Integer idEmpresa) throws ServiceException {
		try {
			return cursoDAO.listarPesquisaPorCurso(tituloCurso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Curso> listarCursoPorPesquisa(Integer idPesquisa,
			Integer idEmpresa) throws ServiceException {
		try {
			return cursoDAO.listarCursoPorPesquisa(idPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Curso> listarCursoPorID(int idCurso, Integer idEmpresa) throws ServiceException {
		try {
			return cursoDAO.listarCursoPorId(idCurso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
