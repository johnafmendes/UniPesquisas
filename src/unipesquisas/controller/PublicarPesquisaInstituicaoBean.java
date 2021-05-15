package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.PesquisaInstituicao;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.PesquisaInstituicaoService;
import unipesquisas.model.service.PesquisaPerguntaService;
import unipesquisas.model.service.PesquisaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarPesquisaInstituicaoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private PesquisaInstituicaoService piService;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private PesquisaService pesquisaService;
	
	@Inject
	private PesquisaPerguntaService ppService;
	
	@Inject
	private Diversos diversos;
	
	private List<Pesquisa> listaPesquisas;
	private List<Instituicao> listaInstituicoes;
	private List<PesquisaInstituicao> listaPesquisaInstituicao;
	private List<StatusCandidato> listaStatusCandidato;
	private Pesquisa pesquisa;
	private Instituicao instituicao;
	private PesquisaInstituicao pi;
	private Integer tipoPesquisaPesquisa;
	private String idTituloPesquisa;
	private Integer tipoPesquisaInstituicao;
	private String idTituloInstituicao;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(pesquisa == null || instituicao == null || idStatusCandidato == null){
				showMessage("- Selecione uma PESQUISA, uma INSTITUIÇÃO e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(ppService.numeroPerguntasNaPesquisa(pesquisa.getIdpesquisa()) == 0){
				showMessage("- A Pesquisa: "+pesquisa.getIdpesquisa()+" não contém Perguntas.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getPi();
			pi.getPesquisa().setIdpesquisa(pesquisa.getIdpesquisa());
			pi.getInstituicao().setIdinstituicao(instituicao.getIdinstituicao());
			pi.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(piService.salvar(pi, diversos.getIdEmpresa())) {
				showMessage("- Pesquisa publicada com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar a pesquisa. Talvez a pesquisa já esteja publicada a esta instituição.", FacesMessage.SEVERITY_ERROR);
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
		listaInstituicoes = null;
		idTituloInstituicao = null;
		listaPesquisaInstituicao = null;
		idTituloPublicacao = null;
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
	
	public String listarTudoPesquisa() throws ServiceException{
		listaPesquisas = pesquisaService.listarPesquisas(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarInstituicao() throws ServiceException{
		if(tipoPesquisaInstituicao==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Instituição.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaInstituicao==1){//codigo
			try{
				listaInstituicoes = instituicaoService.listarInstituicaoPorID(Integer.parseInt(idTituloInstituicao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo instituicao
			listaInstituicoes = instituicaoService.listarPesquisaPorInstituicao(idTituloInstituicao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoInstituicao() throws ServiceException{
		listaInstituicoes = instituicaoService.listarIntituicoes(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarPublicacao() throws ServiceException{
		if(tipoPesquisaPublicacao==null){
			showMessage("- Selecione como deseja pesquisar, por Código, Pesquisa ou Instituição.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPublicacao==1){//codigo pesquisa
			try{
				listaPesquisaInstituicao = piService.listarPublicacaoInstituicaoPorIdPesquisa(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo instituicao
			try{
				listaPesquisaInstituicao = piService.listarPublicacaoInstituicaoPorIdInstituicao(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//Instituicao
			listaPesquisaInstituicao = piService.listarPublicacaoInstituicaoPorInstituicao(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaPesquisaInstituicao = piService.listarPublicacaoInstituicaoPorPesquisa(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaPesquisaInstituicao = piService.listaPesquisaInstituicao(diversos.getIdEmpresa()) ;
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

	public PesquisaInstituicao getPi() {
		if (pi == null){
			pi = new PesquisaInstituicao();
			pi.setInstituicao(new Instituicao());
			pi.setPesquisa(new Pesquisa());
			pi.setStatuscandidato(new StatusCandidato());
		}
		return pi;
	}

	public List<PesquisaInstituicao> getListaPesquisaInstituicao() throws ServiceException {
		return listaPesquisaInstituicao;
	}

	public List<Instituicao> getListaInstituicoes() {
		return listaInstituicoes;
	}

	public void setListaInstituicoes(List<Instituicao> listaInstituicoes) {
		this.listaInstituicoes = listaInstituicoes;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public void setListaPesquisaInstituicao(
			List<PesquisaInstituicao> listaPesquisaInstituicao) {
		this.listaPesquisaInstituicao = listaPesquisaInstituicao;
	}

	public void setPi(PesquisaInstituicao pi) {
		this.pi = pi;
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

	public Integer getTipoPesquisaInstituicao() {
		return tipoPesquisaInstituicao;
	}

	public void setTipoPesquisaInstituicao(Integer tipoPesquisaInstituicao) {
		this.tipoPesquisaInstituicao = tipoPesquisaInstituicao;
	}

	public String getIdTituloInstituicao() {
		return idTituloInstituicao;
	}

	public void setIdTituloInstituicao(String idTituloInstituicao) {
		this.idTituloInstituicao = idTituloInstituicao;
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