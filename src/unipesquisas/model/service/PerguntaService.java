package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.PerguntaDAO;
import unipesquisas.model.entity.Pergunta;

public class PerguntaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private PerguntaDAO perguntaDAO;

	public boolean salvar(Pergunta pergunta) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			perguntaDAO.salvar(pergunta);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			pergunta.setIdpergunta(null);
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(Pergunta pergunta) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			perguntaDAO.atualizar(pergunta);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idPergunta) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = perguntaDAO.excluir(idPergunta);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<Pergunta> listarPerguntas(Integer idEmpresa) throws ServiceException {
		try {
			return perguntaDAO.listarPerguntas(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Pergunta carregar(Integer idPergunta) throws ServiceException {
		try {
			return perguntaDAO.carregar(idPergunta);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pergunta> listarPesquisaPergunta(String tituloPergunta, Integer idEmpresa) throws ServiceException {
		try {
			return perguntaDAO.listarPesquisaPerguntas(tituloPergunta, idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorPesquisaEscolaridade(Integer idPesquisa, Integer idCandidato) throws ServiceException {
		try {
			return perguntaDAO.listarPerguntasPorPesquisaEscolaridade(idPesquisa, idCandidato);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorPesquisaInstituicao(
			Integer idPesquisa, Integer idCandidato) throws ServiceException {
		try {
			return perguntaDAO.listarPerguntasPorPesquisaInstituicao(idPesquisa, idCandidato);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorPesquisaCurso(Integer idPesquisa, Integer idCandidato) throws ServiceException {
		try {
			return perguntaDAO.listarPerguntasPorPesquisaCurso(idPesquisa, idCandidato);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorPesquisa(Integer idPesquisa,
			Integer idEmpresa) throws ServiceException {
		try {
			return perguntaDAO.listarPerguntasPorPesquisa(idPesquisa, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pergunta> listarPerguntaPorID(int idPergunta, Integer idEmpresa) throws ServiceException {
		try {
			return perguntaDAO.listarPerguntasPorId(idPergunta, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
