package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.PesquisaCurso;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.CursoService;
import unipesquisas.model.service.PesquisaCursoService;
import unipesquisas.model.service.PesquisaPerguntaService;
import unipesquisas.model.service.PesquisaService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PublicarPesquisaCursoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private PesquisaCursoService pcService;

	@Inject	
	private StatusCandidatoService scService;
	
	@Inject
	private CursoService cursoService;
	
	@Inject
	private PesquisaService pesquisaService;
	
	@Inject
	private PesquisaPerguntaService ppService;
	
	@Inject
	private Diversos diversos;
	
	private List<Pesquisa> listaPesquisas;
	private List<Curso> listaCursos;
	private List<PesquisaCurso> listaPesquisaCurso;
	private List<StatusCandidato> listaStatusCandidato;
	private Pesquisa pesquisa;
	private Curso curso;
	private PesquisaCurso pc;
	private Integer tipoPesquisaPesquisa;
	private String idTituloPesquisa;
	private Integer tipoPesquisaCurso;
	private String idTituloCurso;
	private Integer tipoPesquisaPublicacao;
	private String idTituloPublicacao;
	private Integer idStatusCandidato;
	
	public String salvar() throws Exception {
		try {
			if(pesquisa == null || curso == null || idStatusCandidato == null){
				showMessage("- Selecione uma PESQUISA, um CURSO e um Status antes de publicar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			if(ppService.numeroPerguntasNaPesquisa(pesquisa.getIdpesquisa()) == 0){
				showMessage("- A Pesquisa: "+pesquisa.getIdpesquisa()+" não contém Perguntas.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getPc();
			pc.getPesquisa().setIdpesquisa(pesquisa.getIdpesquisa());
			pc.getCurso().setIdcurso(curso.getIdcurso());
			pc.getStatuscandidato().setIdstatuscandidato(idStatusCandidato);
			
			if(pcService.salvar(pc, diversos.getIdEmpresa())) {
				showMessage("- Pesquisa publicada com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao publicar a pesquisa. Talvez a pesquisa já esteja publicada a este curso.", FacesMessage.SEVERITY_ERROR);
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
		listaCursos = null;
		idTituloCurso = null;
		idTituloPublicacao = null;
		listaPesquisaCurso = null;
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
	
	public String pesquisarCurso() throws ServiceException{
		if(tipoPesquisaCurso==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Curso.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaCurso==1){//codigo
			try{
				listaCursos = cursoService.listarCursoPorID(Integer.parseInt(idTituloCurso), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo curso
			listaCursos = cursoService.listarPesquisaPorCurso(idTituloCurso, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoCurso() throws ServiceException{
		listaCursos = cursoService.listarCursos(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarPublicacao() throws ServiceException{
		if(tipoPesquisaPublicacao==null){
			showMessage("- Selecione como deseja pesquisar, por Código, Pesquisa ou Curso.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPublicacao==1){//codigo pesquisa
			try{
				listaPesquisaCurso = pcService.listarPublicacaoCursoPorIdPesquisa(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==2){//codigo curso
			try{
				listaPesquisaCurso = pcService.listarPublicacaoCursoPorIdCurso(Integer.parseInt(idTituloPublicacao), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else if(tipoPesquisaPublicacao==3){//curso
			listaPesquisaCurso = pcService.listarPublicacaoCursoPorCurso(idTituloPublicacao, diversos.getIdEmpresa());
		} else {//titulo pesquisa
			listaPesquisaCurso = pcService.listarPublicacaoCursoPorPesquisa(idTituloPublicacao, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPublicacao() throws ServiceException{
		listaPesquisaCurso = pcService.listaPesquisaCurso(diversos.getIdEmpresa()) ;
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

	public PesquisaCurso getPc() {
		if (pc == null){
			pc = new PesquisaCurso();
			pc.setCurso(new Curso());
			pc.setPesquisa(new Pesquisa());
			pc.setStatuscandidato(new StatusCandidato());
		}
		return pc;
	}

	public List<PesquisaCurso> getListaPesquisaCurso() throws ServiceException {
		return listaPesquisaCurso;
	}

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public void setListaPesquisaCurso(List<PesquisaCurso> listaPesquisaCurso) {
		this.listaPesquisaCurso = listaPesquisaCurso;
	}

	public void setPc(PesquisaCurso pc) {
		this.pc = pc;
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

	public Integer getTipoPesquisaCurso() {
		return tipoPesquisaCurso;
	}

	public void setTipoPesquisaCurso(Integer tipoPesquisaCurso) {
		this.tipoPesquisaCurso = tipoPesquisaCurso;
	}

	public String getIdTituloCurso() {
		return idTituloCurso;
	}

	public void setIdTituloCurso(String idTituloCurso) {
		this.idTituloCurso = idTituloCurso;
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