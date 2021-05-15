package unipesquisas.model.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.mail.EmailException;
import org.primefaces.model.UploadedFile;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.dao.ConfiguracoesEmpresaDAO;
import unipesquisas.model.dao.ConfiguracoesSistemaDAO;
import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.EmpresaDAO;
import unipesquisas.model.entity.ConfiguracoesSistema;
import unipesquisas.model.entity.Empresa;

public class EmpresaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 887579644558415590L;
	
	@Inject
	private EmpresaDAO empresaDAO;

	@Inject
	private Diversos diversos;
	
	@Inject
	private ConfiguracoesSistemaDAO csDAO;
	
	@Inject
	private ConfiguracoesEmpresaDAO ceDAO;
	
	/**
	 * Grava uma empresa
	 */
	public boolean salvar(Empresa empresa) throws ServiceException {
		boolean status;
		try {		
			beginTransaction();
			empresaDAO.salvar(empresa);
			ceDAO.incluirConfiguracoes(empresa.getIdempresa());
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}
	
	/**
	 * Atualiza uma empresa
	 */
	public boolean atualizar(Empresa empresa) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			empresaDAO.atualizar(empresa);
			commitTransaction();
			status = true;
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}
	
	/**
	 * Exclui uma empresa
	 */
	public boolean excluir(Integer idEmpresa) throws ServiceException {
		boolean status;
		try {
			beginTransaction();
			status = empresaDAO.excluir(idEmpresa);
			commitTransaction();
		} catch (DAOException e) {
			status = false;
			rollbackTransaction();
			throw new ServiceException(e);
		}
		return status;
	}
	
	/**
	 * Obtém a lista de empresas
	 */
	public List<Empresa> listarEmpresas() throws ServiceException {
		try {
			return empresaDAO.listarEmpresas();
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Empresa carregar(Integer idEmpresa) throws ServiceException {
		try {
			return empresaDAO.carregar(idEmpresa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void uploadFile(UploadedFile file, String id) throws ServiceException {
		try{
			beginTransaction();
			empresaDAO.upload(file, id);
			empresaDAO.atualizarLogomarca(id, id + "." + diversos.getExtension(file));
			commitTransaction();
		} catch(DAOException e){
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}

	public List<Empresa> listarEmpresasPorCandidato(Integer idCandidato) throws ServiceException {
		try {
			return empresaDAO.listarEmpresasPorCandidato(idCandidato);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void enviarEmailBoasVindasEmpresa(Integer idEmpresa, String email,
			String responsavel) throws EmailException, ServiceException {
		try {
			ConfiguracoesSistema cs = csDAO.getConfiguracaoEmail();
			diversos.enviarEmailBoasVindasEmpresa(idEmpresa, email, responsavel, cs);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}		
	}

	public boolean existeEmail(String email) throws ServiceException {
		try {
			return empresaDAO.existeEmail(email);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
