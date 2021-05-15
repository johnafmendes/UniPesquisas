package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Usuario;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.UsuarioService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class UsuarioBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private Diversos diversos;
	
	private Usuario usuario;
	private List<Usuario> listaUsuarios;
	private String senha;
	private String emailAtual;
	private boolean manterSenha;
	private String senhaAtual;
	private Integer tipoPesquisaUsuario;
	private String idNomeUsuario;

	

	public String salvar() throws Exception {
		try {
			if(!diversos.emailValido(usuario.getEmail())){
				showMessage("- e-Mail inválido.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if (usuario.getIdusuario() == null) {
				if(usuarioService.existeEmail(usuario.getEmail())){
					showMessage("- e-Mail já existe.", FacesMessage.SEVERITY_ERROR);
					return null;
				}
				
				if (!usuario.getPassword().equals(senha) || senha.equals("")){
					showMessage("- Senhas inválidas", FacesMessage.SEVERITY_ERROR);
					return null;
				}else{
					usuario.setPassword(diversos.getMD5(usuario.getPassword()));
				}
				
				usuario.setIdempresa(diversos.getIdEmpresa());
				
				if(usuarioService.salvar(usuario)) {
					usuarioService.enviarEmailNovoUsuario(usuario, senha);
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(!emailAtual.equals(usuario.getEmail())){
					if(usuarioService.existeEmail(usuario.getEmail())){
						showMessage("- e-Mail já existe.", FacesMessage.SEVERITY_ERROR);
						return null;
					}
				}
				
				if(!manterSenha){
					if (!usuario.getPassword().equals(senha) || senha.equals("")){
						showMessage("- Senhas inválidas", FacesMessage.SEVERITY_ERROR);
						return null;
					}else{
						usuario.setPassword(diversos.getMD5(usuario.getPassword()));
					}
				}else{
					usuario.setPassword(senhaAtual);
				}
				
				if(usuarioService.atualizar(usuario)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public String criarLogin(Integer idEmpresa, String email, String responsavel) throws Exception {
		if(idEmpresa == 0 || idEmpresa == null || email == null || email.equals("") || responsavel == null || responsavel.equals("")){
			showMessage("- Erro ao Criar Login. Você deve salvar primeiro a Empresa com Email e Responsável preenchido antes de Criar o Login.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		try {
			if(usuarioService.existeEmail(email)){
				showMessage("- e-Mail já existe.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			
			senha = "12345678";
			usuario = new Usuario();
			usuario.setPassword(diversos.getMD5(senha));
			
			usuario.setIdempresa(idEmpresa);
			
			usuario.setNome(responsavel);
			
			usuario.setEmail(email);
			
			usuario.setStatus(1);
			if(usuarioService.salvar(usuario)) {
				usuarioService.enviarEmailNovoUsuario(usuario, senha);
				showMessage("- Registro salvo com sucesso com senha "+senha, FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao Criar o Usuário.", FacesMessage.SEVERITY_ERROR);
			}
				
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String alterar(Integer idUsuario) throws Exception {
		usuario = usuarioService.carregar(idUsuario);
		emailAtual = usuario.getEmail();
		senhaAtual = usuario.getPassword();
		System.out.println("senha: ["+senhaAtual+"]");
		return null;
	}
	
	public String limpar(){
		usuario = null;
		senhaAtual = null;
		senha = null;
		emailAtual = null;
		return null;
	}

	public String excluir(Integer idUsuario) {
		try{
			if(usuarioService.excluir(idUsuario)){
				showMessage("- Registro excluído com sucesso.", FacesMessage.SEVERITY_INFO);
				return null;
			}else{
				showMessage("- Não é possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
		} catch (ServiceException e){
			showMessage("- Não é possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}
	
	public String pesquisarUsuario() throws ServiceException{
		if(tipoPesquisaUsuario==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Nome do Usuário.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaUsuario==1){//codigo
			try{
				listaUsuarios = usuarioService.listarUsuarioPorID(Integer.parseInt(idNomeUsuario), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//nome usuario
			listaUsuarios = usuarioService.listarPesquisaPorNome(idNomeUsuario, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaUsuarios = usuarioService.listarUsuarios(diversos.getIdEmpresa());
		return null;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isManterSenha() {
		return manterSenha;
	}

	public void setManterSenha(boolean manterSenha) {
		this.manterSenha = manterSenha;
	}

	public String getEmailAtual() {
		return emailAtual;
	}

	public void setEmailAtual(String emailAtual) {
		this.emailAtual = emailAtual;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public Integer getTipoPesquisaUsuario() {
		return tipoPesquisaUsuario;
	}

	public void setTipoPesquisaUsuario(Integer tipoPesquisaUsuario) {
		this.tipoPesquisaUsuario = tipoPesquisaUsuario;
	}

	public String getIdNomeUsuario() {
		return idNomeUsuario;
	}

	public void setIdNomeUsuario(String idNomeUsuario) {
		this.idNomeUsuario = idNomeUsuario;
	}

	public Usuario getUsuario() {
		if(usuario == null){
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() throws ServiceException {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

}
