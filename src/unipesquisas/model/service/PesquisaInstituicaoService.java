package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.CandidatoPesquisaInstituicaoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.PesquisaEscolaridadeDAO;
import unipesquisas.model.dao.PesquisaInstituicaoDAO;
//import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.PesquisaInstituicao;

public class PesquisaInstituicaoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private PesquisaInstituicaoDAO piDAO;
	
	@Inject 
	private CandidatoPesquisaInstituicaoDAO cpiDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;
	
	@Inject
	private PesquisaEscolaridadeDAO peDAO;

//	private List<Candidato> listaCandidato;
	
	public boolean salvar(PesquisaInstituicao pi, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {
//			if(!peDAO.pesquisaPublicada(pi.getPesquisa().getIdpesquisa())){
				if(!piDAO.pesquisaPublicada(pi.getPesquisa().getIdpesquisa(), pi.getInstituicao().getIdinstituicao(), pi.getStatuscandidato().getIdstatuscandidato())){
					beginTransaction();
					piDAO.salvar(pi);
					//listaCandidato = candidatoDAO.listaCandidatosPorInstituicao(pi.getInstituicao().getIdinstituicao(), idEmpresa);
					//cpiDAO.publicar(pi.getIdpesquisainstituicao(), listaCandidato);
					commitTransaction();
					status = true;
				}else{
					status = false;
				}
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			pi.setIdpesquisainstituicao(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public PesquisaInstituicao carregar(Integer idPesquisa) throws ServiceException {
		try {
			return piDAO.carregar(idPesquisa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaInstituicao> listaPesquisaInstituicao(
			Integer idEmpresa) throws ServiceException {
		try {
			return piDAO.listaPesquisaInstituicao(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaInstituicao> listaPesquisaInstituicaoDisponiveis(
			Integer idCandidato, Integer idEmpresa, Integer idStatusCandidato) throws ServiceException {
		try {
			return piDAO.listarPesquisaInstituicaoDisponiveis(idCandidato, idEmpresa, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaInstituicao> listarPublicacaoInstituicaoPorIdPesquisa(
			int idPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return piDAO.listarPublicacaoInstituicaoPorIdPesquisa(idPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaInstituicao> listarPublicacaoInstituicaoPorIdInstituicao(
			int idInstituicao, Integer idEmpresa) throws ServiceException {
		try {
			return piDAO.listarPublicacaoInstituicaoPorIdInstituicao(idInstituicao, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaInstituicao> listarPublicacaoInstituicaoPorInstituicao(
			String instituicao, Integer idEmpresa) throws ServiceException {
		try {
			return piDAO.listarPublicacaoInstituicaoPorInstituicao(instituicao, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaInstituicao> listarPublicacaoInstituicaoPorPesquisa(
			String tituloPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return piDAO.listarPublicacaoInstituicaoPorPesquisa(tituloPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
