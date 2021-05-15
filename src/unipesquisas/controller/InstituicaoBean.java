package unipesquisas.controller;


import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.controller.AbstractBean;
import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class InstituicaoBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390197682176838852L;

	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private Diversos diversos;
	
	private Instituicao instituicao;

	private List<Instituicao> listaInstituicoes;
	private Integer tipoPesquisaInstituicao;
	private String idNomeInstituicao;
	
	/**
	 * Salva ou atualiza a Instituicao
	 */
	public String salvar() throws Exception {
		try {
			if (instituicao.getIdinstituicao() == null) {
				instituicao.getEmpresa().setIdempresa(diversos.getIdEmpresa());
				if(instituicaoService.salvar(instituicao)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
			} else {
				if(instituicaoService.atualizar(instituicao)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			}
			return null;
		
		} catch (ValidationException e) {
			// Ocorreu um erro de validação de negócio
			addMessageToRequest(e.getMessage());
			showMessage("Erro: " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
			return null;
		}
	}

	/**
	 * Carrega uma instituicao existente para que ele possa ser alterado
	 */
	public String alterar(Integer idInstituicao) throws Exception {
		instituicao = instituicaoService.carregar(idInstituicao);
		return null;
	}
	
	public String limpar(){
		instituicao = null;
		return null;
	}
	
	public String excluir(Integer idInstituicao) {
		try{
			if(instituicaoService.excluir(idInstituicao)){
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

	public String pesquisarInstituicao() throws ServiceException{
		if(tipoPesquisaInstituicao==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Nome da Instituição.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaInstituicao==1){//codigo
			try{
				listaInstituicoes = instituicaoService.listarInstituicaoPorID(Integer.parseInt(idNomeInstituicao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//nome instituicao
			listaInstituicoes = instituicaoService.listarPesquisaPorInstituicao(idNomeInstituicao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaInstituicoes = instituicaoService.listarIntituicoes(diversos.getIdEmpresa());
		return null;
	}

	public Instituicao getInstituicao() {
		if(instituicao == null){
			instituicao = new Instituicao();
			instituicao.setEmpresa(new Empresa());
		}
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public List<Instituicao> getListaInstituicoes() throws ServiceException {
		return listaInstituicoes;
	}

	public void setListaInstituicoes(List<Instituicao> listaInstituicoes) {
		this.listaInstituicoes = listaInstituicoes;
	}

	public Integer getTipoPesquisaInstituicao() {
		return tipoPesquisaInstituicao;
	}

	public void setTipoPesquisaInstituicao(Integer tipoPesquisaInstituicao) {
		this.tipoPesquisaInstituicao = tipoPesquisaInstituicao;
	}

	public String getIdNomeInstituicao() {
		return idNomeInstituicao;
	}

	public void setIdNomeInstituicao(String idNomeInstituicao) {
		this.idNomeInstituicao = idNomeInstituicao;
	}

}
