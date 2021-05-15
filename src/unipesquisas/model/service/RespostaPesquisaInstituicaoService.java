package unipesquisas.model.service;


import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.RespostaPesquisaInstituicaoDAO;
import unipesquisas.model.entity.RespostaPesquisaInstituicao;

public class RespostaPesquisaInstituicaoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private RespostaPesquisaInstituicaoDAO rpiDAO;

	public boolean salvar(RespostaPesquisaInstituicao rpi) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			rpiDAO.salvar(rpi);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			rpi.setIdrespostapesquisainstituicao(null);
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
			rpiDAO.atualizar(idCandidato, idStatusCandidato, idEmpresa);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}

}
