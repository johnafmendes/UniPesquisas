package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.AreasCursoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.AreasCurso;

public class AreasCursoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private AreasCursoDAO areasCursoDAO;

	public boolean salvar(AreasCurso areas) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			areasCursoDAO.salvar(areas);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(AreasCurso areas) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			areasCursoDAO.atualizar(areas);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idAreasCurso) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = areasCursoDAO.excluir(idAreasCurso);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<AreasCurso> listarAreas(Integer idEmpresa) throws ServiceException {
		try {
			return areasCursoDAO.listarAreas(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public AreasCurso carregar(Integer idAreasCurso) throws ServiceException {
		try {
			return areasCursoDAO.carregar(idAreasCurso);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<AreasCurso> listarResumoAreas(Integer idEmpresa) throws ServiceException {
		try {
			return areasCursoDAO.listarResumoAreas(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
