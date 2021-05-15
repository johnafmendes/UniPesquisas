package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.CandidatoPesquisaEscolaridadeDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.PesquisaEscolaridadeDAO;
import unipesquisas.model.dao.PesquisaInstituicaoDAO;
//import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.PesquisaEscolaridade;

public class PesquisaEscolaridadeService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private PesquisaEscolaridadeDAO peDAO;
	
	@Inject
	private PesquisaInstituicaoDAO piDAO;
	
	@Inject 
	private CandidatoPesquisaEscolaridadeDAO cpeDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;

//	private List<Candidato> listaCandidato;
	
	public boolean salvar(PesquisaEscolaridade pe, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {		
//			if(!piDAO.pesquisaPublicada(pe.getPesquisa().getIdpesquisa())){
				if(!peDAO.pesquisaPublicada(pe.getPesquisa().getIdpesquisa(), pe.getEscolaridade().getIdescolaridade(), pe.getStatuscandidato().getIdstatuscandidato())){
					beginTransaction();
					peDAO.salvar(pe);
					//listaCandidato = candidatoDAO.listaCandidatosPorEscolaridade(pe.getEscolaridade().getIdescolaridade(), idEmpresa);
					//cpeDAO.publicar(pe.getIdpesquisaescolaridade(), listaCandidato);
					commitTransaction();
					status = true;
				}else{
					status = false;
				}
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			pe.setIdpesquisaescolaridade(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public PesquisaEscolaridade carregar(Integer idPesquisa) throws ServiceException {
		try {
			return peDAO.carregar(idPesquisa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaEscolaridade> listaPesquisaEscolaridade(
			Integer idEmpresa) throws ServiceException {
		try {
			return peDAO.listaPesquisaEscolaridade(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaEscolaridade> listaPesquisaEscolaridadeDisponiveis(
			Integer idCandidato, Integer idEmpresa, Integer idEscolaridade,
			Integer idStatusCandidato) throws ServiceException {
		try {
			return peDAO.listarPesquisaEscolaridadeDisponiveis(idCandidato, idEmpresa, idEscolaridade, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaEscolaridade> listarPublicacaoEscolaridadePorIdPesquisa(
			int idPesquisa, int idEmpresa) throws ServiceException {
		try {
			return peDAO.listarPublicacaoEscolaridadePorIdPesquisa(idPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaEscolaridade> listarPublicacaoEscolaridadePorIdEscolaridade(
			int idEscolaridade, Integer idEmpresa) throws ServiceException {
		try {
			return peDAO.listarPublicacaoEscolaridadePorIdEscolaridade(idEscolaridade, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaEscolaridade> listarPublicacaoEscolaridadePorEscolaridade(
			String escolaridade, Integer idEmpresa) throws ServiceException {
		try {
			return peDAO.listarPublicacaoEscolaridadePorEscolaridade(escolaridade, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaEscolaridade> listarPublicacaoEscolaridadePorPesquisa(
			String pesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return peDAO.listarPublicacaoEscolaridadePorPesquisa(pesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
