package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.dao.CandidatoDAO;
import unipesquisas.model.dao.CandidatoEmpresaDAO;
import unipesquisas.model.dao.ConfiguracoesSistemaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.CandidatoEmpresa;
import unipesquisas.model.entity.ConfiguracoesSistema;

public class CandidatoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private CandidatoDAO candidatoDAO;
	
	@Inject
	private ConfiguracoesSistemaDAO csDAO;
	
	@Inject 
	private Diversos diversos;

	@Inject
	private CandidatoEmpresaDAO ceDAO;
	
	public boolean salvar(Candidato candidato, CandidatoEmpresa ce, Integer admin) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			candidatoDAO.salvar(candidato);
			ce.setIdcandidato(candidato.getIdcandidato());
			if(!ceDAO.existeCandidatoEmpresa(ce)){
				ceDAO.salvar(ce, admin);
			}
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			candidato.setIdcandidato(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(Candidato candidato, CandidatoEmpresa ce, Integer admin) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			candidatoDAO.atualizar(candidato);
			ceDAO.atualizar(ce, admin);
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
			status = candidatoDAO.excluir(idCandidato);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<Candidato> listarCandidatos(Integer idEmpresa) throws ServiceException {
		try {
			return candidatoDAO.listarCandidatos(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Candidato carregar(Integer idCandidato) throws ServiceException {
		try {
			return candidatoDAO.carregar(idCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean existeEmail(String email) throws ServiceException {
		try {
			return candidatoDAO.existeEmail(email);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Candidato> listarCandidatosPorNome(String nome, Integer idEmpresa) throws ServiceException {
		try {
			return candidatoDAO.listarCandidatosPorNome(nome, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}	
	}

	public List<Candidato> listarCandidatosPorID(Integer idCandidato, Integer idEmpresa) throws ServiceException {
		try {
			return candidatoDAO.listarCandidatosPorID(idCandidato, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean existeEmailEmpresa(String eMail, Integer idEmpresa) throws ServiceException {
		try {
			return candidatoDAO.existeEmailEmpresa(eMail, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String getEmail(Integer idCandidato) throws ServiceException {
		try {
			return candidatoDAO.getEmail(idCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String getSenha(String email) throws ServiceException {
		try {
			return candidatoDAO.getSenha(email);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void enviarEmailNovaSenha(String email, String senha) throws Exception {
		try {
			ConfiguracoesSistema cs = csDAO.getConfiguracaoEmail();
			diversos.enviaEmailNovaSenha(email, senha, cs);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public Boolean criarNovaSenha(String emailAtual, String senha, String novaSenha) throws ServiceException {
		try {
			return candidatoDAO.criarNovaSenha(emailAtual, senha, novaSenha);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

	public Integer getTotalCandidatos(Integer idEmpresa) throws ServiceException {
		try {
			return candidatoDAO.getTotalCandidatos(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Integer getTotalCandidatosCelular(Integer idEmpresa) throws ServiceException {
		try {
			return candidatoDAO.getTotalCandidatosCelular(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Integer getTotalCandidatos() throws ServiceException {
		try {
			return candidatoDAO.getTotalCandidatos();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Integer getTotalCandidatosCelular() throws ServiceException {
		try {
			return candidatoDAO.getTotalCandidatosCelular();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean cancelarAssinatura(String emailAtual, String senhaAtual, Integer idEmpresa) throws ServiceException {
		try {
			return candidatoDAO.cancelarAssinatura(emailAtual, senhaAtual, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
