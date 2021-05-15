package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.mail.EmailException;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.dao.ConfiguracoesSistemaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.UsuarioDAO;
import unipesquisas.model.entity.ConfiguracoesSistema;
import unipesquisas.model.entity.Usuario;

public class UsuarioService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	private ConfiguracoesSistemaDAO csDAO;
	
	@Inject
	private Diversos diversos;

	public boolean salvar(Usuario usuario) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			usuarioDAO.salvar(usuario);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean atualizar(Usuario usuario) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			usuarioDAO.atualizar(usuario);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public boolean excluir(Integer idUsuario) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = usuarioDAO.excluir(idUsuario);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}

	public List<Usuario> listarUsuarios(Integer idEmpresa) throws ServiceException {
		try {
			return usuarioDAO.listarUsuarios(idEmpresa);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Usuario carregar(Integer idUsuario) throws ServiceException {
		try {
			return usuarioDAO.carregar(idUsuario);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean existeEmail(String email) throws ServiceException {
		try {
			return usuarioDAO.existeEmail(email);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void enviarEmailNovoUsuario(Usuario usuario, String senha) throws ServiceException, EmailException  {
		try {
			ConfiguracoesSistema cs = csDAO.getConfiguracaoEmail();
			diversos.enviarEmailNovoUsuario(usuario, senha, cs);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Usuario> listarUsuarioPorID(int idUsuario, Integer idEmpresa) throws ServiceException {
		try {
			return usuarioDAO.listarUsuarioPorId(idUsuario, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Usuario> listarPesquisaPorNome(String NomeUsuario,
			Integer idEmpresa) throws ServiceException {
		try {
			return usuarioDAO.listarPesquisarPorNome(NomeUsuario, idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
