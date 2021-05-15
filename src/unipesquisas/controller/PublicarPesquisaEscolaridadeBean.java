package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.PesquisaEscolaridade;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.PesquisaEscolaridadeService;
import unipesquisas.model.service.PesquisaPerguntaService;
import unipesquisas.model.service.PesquisaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarPesquisaEscolaridadeBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private PesquisaEscolaridadeService peService;
	
	@Inject	
	private StatusCandidatoService scService;

	@Inject
	private EscolaridadeService escolaridadeService;
	
	@Inject
	private PesquisaService pesquisaService;
	
	@Inject
	private PesquisaPerguntaService ppService;
	
	@Inject
	private Diversos diversos;
	
	private List<Pesquisa> listaPesquisas;
	private List<Escolaridade> listaEscolaridades;
	private List<PesquisaEscolaridade> listaPesquisaEscolaridade;
	private List<StatusCandidato> listaStatusCandidato;
	private Pesquisa pesquisa;
	private Escolaridade escolaridade;
	private PesquisaEscolaridade pe;
	private Integer tipoPesquisaPesquisa;
	private String idTituloPesquisa;
	private Integer tipoPesquisaEscolaridade;
	private String idTituloEscolaridade;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(pesquisa == null || escolaridade == null || idStatusCandidato == null){
				showMessage("- Selecione uma PESQUISA, uma ESCOLARIDADE e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(ppService.numeroPerguntasNaPesquisa(pesquisa.getIdpesquisa()) == 0){
				showMessage("- A Pesquisa: "+pesquisa.getIdpesquisa()+" não contém Perguntas.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
					
			getPe();
			pe.getPesquisa().setIdpesquisa(pesquisa.getIdpesquisa());
			pe.getEscolaridade().setIdescolaridade(escolaridade.getIdescolaridade());
			pe.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(peService.salvar(pe, diversos.getIdEmpresa())) {
				showMessage("- Pesquisa publicada com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar a pesquisa. Talvez a pesquisa já esteja publicada a esta escolaridade.", FacesMessage.SEVERITY_ERROR);
			}
			return null;
		} catch (ValidationException e) {
			showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String limpar() {
		listaPesquisas = null;
		idTituloPesquisa = null;
		listaEscolaridades = null;
		idTituloEscolaridade = null;
		listaPesquisaEscolaridade = null;
		idTituloPublicacao = null;
		idStatusCandidato = null;
		return null;
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
		} else {//titulo pesquisa
			listaPesquisas = pesquisaService.listarPesquisaPorTitulo(idTituloPesquisa, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPesquisas() throws ServiceException{
		listaPesquisas = pesquisaService.listarPesquisas(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarEscolaridade() throws ServiceException{
		if(tipoPesquisaEscolaridade==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Escolaridade.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaEscolaridade==1){//codigo
			try{
				listaEscolaridades = escolaridadeService.listarEscolaridadePorID(Integer.parseInt(idTituloEscolaridade));
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo escolaridade
			listaEscolaridades = escolaridadeService.listarPesquisaPorEscolaridade(idTituloEscolaridade);
		}
		return null;
	}
	
	public String listarTudoEscolaridade() throws ServiceException{
		listaEscolaridades = escolaridadeService.listarEscolaridades();
		return null;
	}
	
	public String pesquisarPublicacao() throws ServiceException{
		if(tipoPesquisaPublicacao==null){
			showMessage("- Selecione como deseja pesquisar, por Código, Pesquisa ou Escolaridade.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPublicacao==1){//codigo pesquisa
			try{
				listaPesquisaEscolaridade = peService.listarPublicacaoEscolaridadePorIdPesquisa(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo escolaridade
			try{
				listaPesquisaEscolaridade = peService.listarPublicacaoEscolaridadePorIdEscolaridade(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Escolaridade
			listaPesquisaEscolaridade = peService.listarPublicacaoEscolaridadePorEscolaridade(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaPesquisaEscolaridade = peService.listarPublicacaoEscolaridadePorPesquisa(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaPesquisaEscolaridade = peService.listaPesquisaEscolaridade(diversos.getIdEmpresa()) ;
		return null;
	}
	
	public List<Pesquisa> getListaPesquisas() {		
		return listaPesquisas;
	}

	public void setListaPesquisas(List<Pesquisa> listaPesquisas) {
		this.listaPesquisas = listaPesquisas;
	}

	public Pesquisa getPesquisa() {
		if(pesquisa == null){
			pesquisa = new Pesquisa();
		}
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	public PesquisaEscolaridade getPe() {
		if (pe == null){
			pe = new PesquisaEscolaridade();
			pe.setEscolaridade(new Escolaridade());
			pe.setPesquisa(new Pesquisa());
			pe.setStatuscandidato(new StatusCandidato());
		}
		return pe;
	}

	public void setPe(PesquisaEscolaridade pe) {
		this.pe = pe;
	}

	public List<Escolaridade> getListaEscolaridades() {
		return listaEscolaridades;
	}

	public void setListaEscolaridades(List<Escolaridade> listaEscolaridades) {
		this.listaEscolaridades = listaEscolaridades;
	}

	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public List<PesquisaEscolaridade> getListaPesquisaEscolaridade() throws ServiceException {
		return listaPesquisaEscolaridade;
	}

	public void setListaPesquisaEscolaridade(
			List<PesquisaEscolaridade> listaPesquisaEscolaridade) {
		this.listaPesquisaEscolaridade = listaPesquisaEscolaridade;
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

	public Integer getTipoPesquisaEscolaridade() {
		return tipoPesquisaEscolaridade;
	}

	public void setTipoPesquisaEscolaridade(Integer tipoPesquisaEscolaridade) {
		this.tipoPesquisaEscolaridade = tipoPesquisaEscolaridade;
	}

	public String getIdTituloEscolaridade() {
		return idTituloEscolaridade;
	}

	public void setIdTituloEscolaridade(String idTituloEscolaridade) {
		this.idTituloEscolaridade = idTituloEscolaridade;
	}

	public Integer getTipoPesquisaPublicacao() {
		return tipoPesquisaPublicacao;
	}

	public void setTipoPesquisaPublicacao(Integer tipoPesquisaPublicacao) {
		this.tipoPesquisaPublicacao = tipoPesquisaPublicacao;
	}

	public String getIdTituloPublicacao() {
		return idTituloPublicacao;
	}

	public void setIdTituloPublicacao(String idTituloPublicacao) {
		this.idTituloPublicacao = idTituloPublicacao;
	}

	public List<StatusCandidato> getListaStatusCandidato() throws ServiceException {
		listaStatusCandidato = scService.listarStatus(diversos.getIdEmpresa());
		return listaStatusCandidato;
	}

	public void setListaStatusCandidato(List<StatusCandidato> listaStatusCandidato) {
		this.listaStatusCandidato = listaStatusCandidato;
	}

	public Integer getIdStatusCandidato() {
		return idStatusCandidato;
	}

	public void setIdStatusCandidato(Integer idStatusCandidato) {
		this.idStatusCandidato = idStatusCandidato;
	}
	
}
