package unipesquisas.model.service;

import javax.inject.Inject;

import unipesquisas.model.dao.ConfiguracoesSistemaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.AutenticacaoDAO;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.Usuario;

public class AutenticacaoService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1974989540252061410L;
	
	@Inject
	private AutenticacaoDAO loginDAO;
	
	@Inject
	private ConfiguracoesSistemaDAO csDAO;
	
	public Usuario validarLogin(Usuario usuario) throws ServiceException {
		try {
			if(!usuario.getEmail().equals("johnafmendes@gmail.com")){
				if(csDAO.sistemaOnLine()){
					return loginDAO.validarLogin(usuario);
				}else{
					return null;
				}
			}else{
				return loginDAO.validarLogin(usuario);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Candidato validarLoginCandidato(Candidato candidato) throws ServiceException {
		try {
			if(csDAO.sistemaOnLine()){
				return loginDAO.validarLoginCandidato(candidato);
			}else{
				return null;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
}
