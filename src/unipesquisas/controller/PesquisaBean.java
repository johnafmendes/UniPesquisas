package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.Usuario;
import unipesquisas.model.service.PesquisaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.UsuarioService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PesquisaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private PesquisaService pesquisaService;
	
	@Inject
	private Diversos diversos;
	
	private List<Pesquisa> listaPesquisas;
//	private List<Pesquisa> listaPesquisasDisponiveis;
	
	private Pesquisa pesquisa;
	private Integer tipoPesquisaPesquisa;
	private String idTituloPesquisa;
	
	public String salvar() throws Exception {
		try {
			
			if (pesquisa.getIdpesquisa() == null) {
				
				pesquisa.setIdempresa(diversos.getIdEmpresa());
				pesquisa.getUsuario().setIdusuario(diversos.getIdUsuario());
				
				if(pesquisaService.salvar(pesquisa)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(pesquisaService.atualizar(pesquisa)){
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

	public String alterar(Integer idPesquisa) throws Exception {
		pesquisa = pesquisaService.carregar(idPesquisa);
		return null;
	}
	
	public String limpar(){
		pesquisa = null;
		return null;
	}

	public String excluir(Integer idPesquisa) {
		try{
			if(pesquisaService.excluir(idPesquisa)){
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

	public String pesquisarPesquisa() throws ServiceException{
		if(tipoPesquisaPesquisa==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Título da Pesquisa.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPesquisa==1){//codigo
			try{
				listaPesquisas = pesquisaService.listarPesquisaPorID(Integer.parseInt(idTituloPesquisa), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//nome instituicao
			listaPesquisas = pesquisaService.listarPesquisaPorTitulo(idTituloPesquisa, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaPesquisas = pesquisaService.listarPesquisas(diversos.getIdEmpresa());
		return null;
	}
	
	public List<Pesquisa> getListaPesquisas() throws ServiceException {
		return listaPesquisas;
	}

	public void setListaPesquisas(List<Pesquisa> listaPesquisas) {
		this.listaPesquisas = listaPesquisas;
	}

	public Pesquisa getPesquisa() {
		if(pesquisa == null){
			pesquisa = new Pesquisa();
			pesquisa.setUsuario(new Usuario());
		}
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	public Integer getTipoPesquisaPesquisa() {
		return tipoPesquisaPesquisa;
	}

	public void setTipoPesquisaPesquisa(Integer tipoPesquisaPesquisa) {
		this.tipoPesquisaPesquisa = tipoPesquisaPesquisa;
	}

	public String getIdTituloPesquisa() {
		return idTituloPesquisa;
	}

	public void setIdTituloPesquisa(String idTituloPesquisa) {
		this.idTituloPesquisa = idTituloPesquisa;
	}

}

