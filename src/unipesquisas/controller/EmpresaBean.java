package unipesquisas.controller;


import java.io.IOException;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.mail.EmailException;
import org.primefaces.event.FileUploadEvent;

import unipesquisas.controller.AbstractBean;
import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.CandidatoEmpresa;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.service.CandidatoEmpresaService;
import unipesquisas.model.service.EmpresaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class EmpresaBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390197682176838852L;

	@Inject
	private EmpresaService empresaService;
	
	@Inject
	private CandidatoEmpresaService ceService;

	@Inject
	private AutenticacaoBean autenticacaoBean;
	
	@Inject
	private Diversos diversos;
	
	private Empresa empresa;
	private List<Empresa> listaEmpresas;
	private String emailAtual;
	private CandidatoEmpresa candidatoempresa;
	
	public void onUpload(FileUploadEvent event) throws ServiceException {
		if(empresa.getIdempresa() != null){
			empresaService.uploadFile(event.getFile(), empresa.getIdempresa().toString());
			showMessage("- Logo Salvo com sucesso.", FacesMessage.SEVERITY_INFO);
		}else{
			showMessage("- Você precisa salvar o registro primeiro antes de enviar a logo.", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	/**
	 * Salva ou atualiza a Empresa
	 */
	public String salvar(/*FileUploadEvent event*/) throws Exception {
		try {
			if(!diversos.emailValido(empresa.getEmail())){
				showMessage("- e-Mail inválido.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			
			if (empresa.getIdempresa() == null) {
				if(empresaService.existeEmail(empresa.getEmail())){
					showMessage("- Erro ao salvar: e-Mail já Existe", FacesMessage.SEVERITY_ERROR);
					return null;
				}
//				System.out.println("IDEmpresa:" + empresa.getIdempresa());
				if(empresaService.salvar(empresa)) {
					emailAtual = empresa.getEmail();
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Não é possível salvar o registro", FacesMessage.SEVERITY_ERROR);
				}
			} else {
				if(!emailAtual.equals(empresa.getEmail())){
					if(empresaService.existeEmail(empresa.getEmail())){
						showMessage("- Erro ao Salvar: e-Mail já existe.", FacesMessage.SEVERITY_ERROR);
						return null;
					}
				}
				if(empresaService.atualizar(empresa)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Não é possível atualizar o registro", FacesMessage.SEVERITY_ERROR);
				}
				
			}
			getListaEmpresas();
			//empresa = null;
			return null;//redirect("Empresas");
		
		} catch (ValidationException e) {
			// Ocorreu um erro de validação de negócio
			addMessageToRequest(e.getMessage());
			showMessage("Erro: " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
			return null;
		}
	}

	/**
	 * Carrega uma empresa existente para que ele possa ser alterado
	 */
	public String alterar(Integer idEmpresa) throws Exception {
		empresa = empresaService.carregar(idEmpresa);
		emailAtual = empresa.getEmail();
		return null;
	}
	
	public String limpar(){
		empresa = null;
		return null;
	}
	
	public String excluir(Integer idEmpresa) {
		try{
			if(empresaService.excluir(idEmpresa)){
				showMessage("- Registro excluído com sucesso.", FacesMessage.SEVERITY_INFO);
				return null;
			}else{
				showMessage("- Não é possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
		} catch (ServiceException e){
			showMessage("- Não é possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
			return null;
		}
	}
	
	public String selecionar(Integer idEmpresa) throws IOException, ServiceException{
		diversos.setIdEmpresa(idEmpresa);
		empresa = getEmpresa();
		empresa.setIdempresa(idEmpresa);
		autenticacaoBean.setIdEmpresa(idEmpresa);
		candidatoempresa = ceService.carregar(diversos.getIdCandidato(), idEmpresa);
		diversos.setIdStatusCandidato(candidatoempresa.getStatuscandidato().getIdstatuscandidato());
		FacesContext.getCurrentInstance().getExternalContext().redirect("Painel.jsf");
		return null;
	}
	
	public String enviarEmailBoasVindasEmpresa(Integer idEmpresa, String email, String responsavel) throws EmailException, ServiceException{
		if(idEmpresa == 0 || idEmpresa == null || email == null || email.equals("") || responsavel == null || responsavel.equals("")){
			showMessage("- Erro ao enviar Email de Boas Vindas. Você deve salvar primeiro a Empresa com Email e Responsável preenchido antes de Enviar o Email.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		try{
			empresaService.enviarEmailBoasVindasEmpresa(idEmpresa, email, responsavel);
			showMessage("- Email enviado com sucesso.", FacesMessage.SEVERITY_INFO);
			return null;
		} catch (ServiceException e){
			showMessage("- Erro ao enviar email.", FacesMessage.SEVERITY_ERROR);
			showMessage("Erro: " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
			return null;
		}
	}
	
	public Empresa getEmpresa() {
		if (empresa == null) {
			empresa = new Empresa();
		}
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getListaEmpresas() throws ServiceException {
		listaEmpresas = empresaService.listarEmpresas();
		return listaEmpresas;
	}
	
	public List<Empresa> getListaEmpresasPorCandidato() throws ServiceException {
		listaEmpresas = empresaService.listarEmpresasPorCandidato(diversos.getIdCandidato());
		return listaEmpresas;
	}

	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}
	
}
