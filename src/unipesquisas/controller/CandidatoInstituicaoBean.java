package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.CandidatoInstituicao;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.service.CandidatoInstituicaoService;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.EmailMarketingService;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class CandidatoInstituicaoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private CandidatoInstituicaoService candidatoInstituicaoService;
	
	@Inject
	private CandidatoService candidatoService;
	
	@Inject
	private EmailMarketingService emailMarketingService;
	
	@Inject
	private Diversos diversos;
	
	private CandidatoInstituicao ci;
	private List<Instituicao> listaInstituicoesDisponiveis;
	private List<CandidatoInstituicao> listaInstituicoesSelecionados;
	private List<CandidatoInstituicao> listaInstituicoesSelecionados2;
	private List<Candidato> listaCandidatos;
	private Integer tipoPesquisaEstudante;
	private String idNomeEstudante;
	
	public String salvarEContinuar() throws Exception {
		try {
			if(candidatoInstituicaoService.numeroInstituicoesSelecionados(diversos.getIdCandidato(), diversos.getIdEmpresa()) == 0){
				showMessage("- Selecione a última instituição que você cursou ou que está cursando.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			FacesContext fc = FacesContext.getCurrentInstance(); 
			HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
			final String url = request .getRequestURI(); 
			if(url.contains("CandidatoInstituicoes.jsf")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatoCursos.jsf");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("CandidatoCursosSA.jsf?idEmpresa="+diversos.getIdEmpresa()+"&idCandidato="+diversos.getIdCandidato());
			}
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String adicionarInstituicao(Integer idInstituicao) throws ServiceException{
		ci = new CandidatoInstituicao();
		ci.setCandidato(new Candidato());
		ci.setInstituicao(new Instituicao());
		ci.getCandidato().setIdcandidato(diversos.getIdCandidato());
		ci.getInstituicao().setIdinstituicao(idInstituicao);
		
		if(candidatoInstituicaoService.salvar(ci)){
			showMessage("- Instituição Adicionado com sucesso.", FacesMessage.SEVERITY_INFO);
			filtrarInstituicoesPorCandidato(diversos.getIdCandidato());
			getListaInstituicoesDisponiveis();
		}else{
			showMessage("- Erro ao Adicionar Instituição.", FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}
	
	public String removerInstituicao(Integer idInstituicao) throws ServiceException{
		if(candidatoInstituicaoService.excluirInstituicao(diversos.getIdCandidato(), idInstituicao)){
			showMessage("- Instituição Removido com sucesso.", FacesMessage.SEVERITY_INFO);
		}else{
			showMessage("- Erro ao Remover Instituição.", FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}
	
	public String pesquisarCandidato() throws ServiceException{
		if(tipoPesquisaEstudante==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Nome.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaEstudante==1){//codigo
			try{
				listaCandidatos = candidatoService.listarCandidatosPorID(Integer.parseInt(idNomeEstudante), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		}else{//nome
			listaCandidatos = candidatoService.listarCandidatosPorNome(idNomeEstudante, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaCandidatos = candidatoService.listarCandidatos(diversos.getIdEmpresa());
		return null;
	}
	
	public String filtrarInstituicoesPorCandidato(Integer idCandidato) throws ServiceException{
		diversos.setIdCandidato(idCandidato);
		listaInstituicoesSelecionados2 = candidatoInstituicaoService.listarInstituicoesPorCandidato(diversos.getIdEmpresa(), idCandidato);
		getListaInstituicoesDisponiveis();
		return null;
	}
	
	public String excluir(Integer idCandidatoInstituicao) throws ServiceException{
		if(candidatoInstituicaoService.excluirInstituicao(idCandidatoInstituicao)){
			filtrarInstituicoesPorCandidato(diversos.getIdCandidato());
			showMessage("- Instituição Excluído com sucesso.", FacesMessage.SEVERITY_INFO);
		}else{
			showMessage("- Erro ao Excluir Instituição.", FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}
	
	public List<Instituicao> getListaInstituicoesDisponiveis() throws ServiceException {
		if(diversos.getIdCandidato() != null){
			listaInstituicoesDisponiveis = instituicaoService.listarInstituicoesDisponiveis(diversos.getIdEmpresa(), diversos.getIdCandidato());
		}
		return listaInstituicoesDisponiveis;
	}

	public List<CandidatoInstituicao> getListaInstituicoesSelecionados() throws ServiceException {
		listaInstituicoesSelecionados = candidatoInstituicaoService.listarInstituicoesPorCandidato(diversos.getIdEmpresa(), diversos.getIdCandidato());
		return listaInstituicoesSelecionados;
	}

	public Integer getTipoPesquisaEstudante() {
		return tipoPesquisaEstudante;
	}

	public void setTipoPesquisaEstudante(Integer tipoPesquisaEstudante) {
		this.tipoPesquisaEstudante = tipoPesquisaEstudante;
	}

	public List<Candidato> getListaCandidatos() {
		return listaCandidatos;
	}

	public void setListaCandidatos(List<Candidato> listaCandidatos) {
		this.listaCandidatos = listaCandidatos;
	}

	public Diversos getDiversos() {
		return diversos;
	}

	public void setDiversos(Diversos diversos) {
		this.diversos = diversos;
	}

	public String getIdNomeEstudante() {
		return idNomeEstudante;
	}

	public void setIdNomeEstudante(String idNomeEstudante) {
		this.idNomeEstudante = idNomeEstudante;
	}

	public List<CandidatoInstituicao> getListaInstituicoesSelecionados2() {
		return listaInstituicoesSelecionados2;
	}

	public void setListaInstituicoesSelecionados2(
			List<CandidatoInstituicao> listaInstituicoesSelecionados2) {
		this.listaInstituicoesSelecionados2 = listaInstituicoesSelecionados2;
	}

	public void setListaInstituicoesDisponiveis(
			List<Instituicao> listaInstituicoesDisponiveis) {
		this.listaInstituicoesDisponiveis = listaInstituicoesDisponiveis;
	}

	public void setListaInstituicoesSelecionados(
			List<CandidatoInstituicao> listaInstituicoesSelecionados) {
		this.listaInstituicoesSelecionados = listaInstituicoesSelecionados;
	}

}
