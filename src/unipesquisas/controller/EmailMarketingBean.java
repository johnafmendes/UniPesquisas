package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.Usuario;
import unipesquisas.model.service.EmailMarketingService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.UsuarioService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class EmailMarketingBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private EmailMarketingService emService;
	
	@Inject
	private Diversos diversos;
	
	private List<EmailMarketing> listaEmailMarketing;
	
	private EmailMarketing em;
	private Integer tipoPesquisaEmail;
	private String idTituloAssunto;
	
	public String salvar() throws Exception {
		try {
			
			if (em.getIdemailmarketing() == null) {
				
				em.setIdempresa(diversos.getIdEmpresa());
				em.getUsuario().setIdusuario(diversos.getIdUsuario());
				
				if(emService.salvar(em)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(emService.atualizar(em)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			return null;
		} catch (ValidationException e) {
			showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public String alterar(Integer idEmailMarketing) throws Exception {
		em = emService.carregar(idEmailMarketing);
		return null;
	}
	
	public String limpar(){
		em = null;
		return null;
	}

	public String excluir(Integer idEmailMarketing) {
		try{
			if(emService.excluir(idEmailMarketing)){
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
	
	public String pesquisarEmail() throws ServiceException{
		if(tipoPesquisaEmail==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Assunto.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaEmail==1){//codigo
			try{
				listaEmailMarketing = emService.listarEmailMarketingPorID(Integer.parseInt(idTituloAssunto), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//Assunto
			listaEmailMarketing = emService.listarEmailMarketingPorAssunto(idTituloAssunto, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaEmailMarketing = emService.listarEmailMarketings(diversos.getIdEmpresa());
		return null;
	}

	public List<EmailMarketing> getListaEmailMarketing() throws ServiceException {
		return listaEmailMarketing;
	}

	public EmailMarketing getEm() {
		if(em == null){
			em = new EmailMarketing();
			em.setUsuario(new Usuario());
		}
		return em;
	}

	public void setEm(EmailMarketing em) {
		this.em = em;
	}

	public void setListaEmailMarketing(List<EmailMarketing> listaEmailMarketing) {
		this.listaEmailMarketing = listaEmailMarketing;
	}

	public Integer getTipoPesquisaEmail() {
		return tipoPesquisaEmail;
	}

	public void setTipoPesquisaEmail(Integer tipoPesquisaEmail) {
		this.tipoPesquisaEmail = tipoPesquisaEmail;
	}

	public String getIdTituloAssunto() {
		return idTituloAssunto;
	}

	public void setIdTituloAssunto(String idTituloAssunto) {
		this.idTituloAssunto = idTituloAssunto;
	}


}

