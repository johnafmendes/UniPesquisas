package unipesquisas.model.service;


import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.RespostaPesquisaCursoDAO;
import unipesquisas.model.entity.RespostaPesquisaCurso;

public class RespostaPesquisaCursoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private RespostaPesquisaCursoDAO rpcDAO;

	public boolean salvar(RespostaPesquisaCurso rpc) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			rpcDAO.salvar(rpc);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			rpc.setIdrespostapesquisacurso(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public void atualizar(Integer idCandidato, Integer idStatusCandidato,
			Integer idEmpresa) throws ServiceException {
		try {		
			beginTransaction();
			rpcDAO.atualizar(idCandidato, idStatusCandidato, idEmpresa);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}

}
