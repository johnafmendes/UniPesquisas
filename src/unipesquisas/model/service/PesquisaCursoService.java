package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.CandidatoPesquisaCursoDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.PesquisaCursoDAO;
import unipesquisas.model.dao.PesquisaEscolaridadeDAO;
import unipesquisas.model.dao.PesquisaInstituicaoDAO;
//import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.PesquisaCurso;

public class PesquisaCursoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private PesquisaInstituicaoDAO piDAO;
	
	@Inject
	private PesquisaCursoDAO pcDAO;
	
	@Inject 
	private CandidatoPesquisaCursoDAO cpcDAO;
	
	@Inject
	private CandidatoDAO candidatoDAO;
	
	@Inject
	private PesquisaEscolaridadeDAO peDAO;

//	private List<Candidato> listaCandidato;
	
	public boolean salvar(PesquisaCurso pc, Integer idEmpresa) throws ServiceException {
		boolean status;
		try {
//			if(!piDAO.pesquisaPublicada(pc.getPesquisa().getIdpesquisa())){
//				if(!peDAO.pesquisaPublicada(pc.getPesquisa().getIdpesquisa())){
					if(!pcDAO.pesquisaPublicada(pc.getPesquisa().getIdpesquisa(), pc.getCurso().getIdcurso(), pc.getStatuscandidato().getIdstatuscandidato())){
						beginTransaction();
						pcDAO.salvar(pc);
						//listaCandidato = candidatoDAO.listaCandidatosPorCurso(pc.getCurso().getIdcurso(), idEmpresa);
						//cpcDAO.publicar(pc.getIdpesquisacurso(), listaCandidato);
						commitTransaction();
						status = true;
					}else{
						status = false;
					}
//				}else{
//					status = false;
//				}
//			}else{
//				status = false;
//			}
		} catch (DAOException e) {
			pc.setIdpesquisacurso(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public PesquisaCurso carregar(Integer idPesquisa) throws ServiceException {
		try {
			return pcDAO.carregar(idPesquisa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaCurso> listaPesquisaCurso(
			Integer idEmpresa) throws ServiceException {
		try {
			return pcDAO.listaPesquisaCurso(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaCurso> listaPesquisaCursoDisponiveis(
			Integer idCandidato, Integer idEmpresa, 
			Integer idStatusCandidato) throws ServiceException {
		try {
			return pcDAO.listarPesquisaCursoDisponiveis(idCandidato, idEmpresa, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaCurso> listarPublicacaoCursoPorIdPesquisa(int idPesquisa,
			Integer idEmpresa) throws ServiceException {
		try {
			return pcDAO.listarPublicacaoCursoPorIdPesquisa(idPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaCurso> listarPublicacaoCursoPorIdCurso(
			int idCurso, Integer idEmpresa) throws ServiceException {
		try {
			return pcDAO.listarPublicacaoCursoPorIdCurso(idCurso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaCurso> listarPublicacaoCursoPorCurso(
			String tituloCurso, Integer idEmpresa) throws ServiceException {
		try {
			return pcDAO.listarPublicacaoCursoPorCurso(tituloCurso, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<PesquisaCurso> listarPublicacaoCursoPorPesquisa(
			String tituloPesquisa, Integer idEmpresa) throws ServiceException {
		try {
			return pcDAO.listarPublicacaoCursoPorPesquisa(tituloPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}

