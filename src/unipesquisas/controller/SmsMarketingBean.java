package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.Usuario;
import unipesquisas.model.service.SMSMarketingService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.UsuarioService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class SmsMarketingBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private SMSMarketingService smsMService;
	
	@Inject
	private Diversos diversos;
	
	private List<SMSMarketing> listaSMSMarketing;
	
	private SMSMarketing smsm;
	private Integer tipoPesquisaSMS;
	private String idTituloAssunto;
	
	public String salvar() throws Exception {
		try {
			
			if (smsm.getIdsmsmarketing() == null) {
				
				smsm.getEmpresa().setIdempresa(diversos.getIdEmpresa());
				smsm.getUsuario().setIdusuario(diversos.getIdUsuario());
				
				if(smsMService.salvar(smsm)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(smsMService.atualizar(smsm)){
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

	public String alterar(Integer idSMSMarketing) throws Exception {
		smsm = smsMService.carregar(idSMSMarketing);
		return null;
	}
	
	public String limpar(){
		smsm = null;
		return null;
	}

	public String excluir(Integer idSMSMarketing) {
		try{
			if(smsMService.excluir(idSMSMarketing)){
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
	
	public String pesquisarSMS() throws ServiceException{
		if(tipoPesquisaSMS==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Assunto.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaSMS==1){//codigo
			try{
				listaSMSMarketing = smsMService.listarSMSMarketingPorID(Integer.parseInt(idTituloAssunto), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//Assunto
			listaSMSMarketing = smsMService.listarSMSMarketingPorAssunto(idTituloAssunto, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaSMSMarketing = smsMService.listarSMSMarketings(diversos.getIdEmpresa());
		return null;
	}

	public String getIdTituloAssunto() {
		return idTituloAssunto;
	}

	public void setIdTituloAssunto(String idTituloAssunto) {
		this.idTituloAssunto = idTituloAssunto;
	}

	public List<SMSMarketing> getListaSMSMarketing() {
		return listaSMSMarketing;
	}

	public void setListaSMSMarketing(List<SMSMarketing> listaSMSMarketing) {
		this.listaSMSMarketing = listaSMSMarketing;
	}

	public SMSMarketing getSmsm() {
		if(smsm == null){
			smsm = new SMSMarketing();
			smsm.setEmpresa(new Empresa());
			smsm.setUsuario(new Usuario());
		}
		return smsm;
	}

	public void setSmsm(SMSMarketing smsm) {
		this.smsm = smsm;
	}

	public Integer getTipoPesquisaSMS() {
		return tipoPesquisaSMS;
	}

	public void setTipoPesquisaSMS(Integer tipoPesquisaSMS) {
		this.tipoPesquisaSMS = tipoPesquisaSMS;
	}


}

