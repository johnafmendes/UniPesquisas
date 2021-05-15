package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoInstituicaoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.CandidatoInstituicao;

public class CandidatoInstituicaoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private CandidatoInstituicaoDAO candidatoInstituicaoDAO;

	public boolean salvar(CandidatoInstituicao ci) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			candidatoInstituicaoDAO.salvar(ci);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<CandidatoInstituicao> listarInstituicoesPorCandidato(Integer idEmpresa,
			Integer idCandidato) throws ServiceException {
		try {
			return candidatoInstituicaoDAO.listarInstituicoesPorCandidato(idEmpresa, idCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Boolean excluirInstituicao(Integer idCandidato, Integer idInstituicao) throws ServiceException {
		try {
			return candidatoInstituicaoDAO.excluirInstituicao(idCandidato, idInstituicao);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean excluirInstituicao(Integer idCandidatoInstituicao) throws ServiceException {
		try {
			return candidatoInstituicaoDAO.excluirInstituicao(idCandidatoInstituicao);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public int numeroInstituicoesSelecionados(Integer idCandidato, Integer idEmpresa) throws ServiceException {
		try {
			return candidatoInstituicaoDAO.numeroInstituicoesSelecionados(idCandidato, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
