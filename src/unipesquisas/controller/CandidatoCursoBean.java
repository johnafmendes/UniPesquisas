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
import unipesquisas.model.entity.CandidatoCurso;
import unipesquisas.model.entity.Curso;
import unipesquisas.model.service.CandidatoCursoService;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.CursoService;
import unipesquisas.model.service.EmailMarketingService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class CandidatoCursoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private CursoService cursoService;
	
	@Inject
	private CandidatoCursoService candidatoCursoService;
	
	@Inject
	private CandidatoService candidatoService;
	
	@Inject
	private EmailMarketingService emailMarketingService;
	
	@Inject
	private Diversos diversos;
	
	private CandidatoCurso cc;
	private List<Curso> listaCursosDisponiveis;
	private List<CandidatoCurso> listaCursosSelecionados;
	private List<CandidatoCurso> listaCursosSelecionados2;
	private List<Candidato> listaCandidatos;
	private Integer matriculado;
	private Integer tipoPesquisaEstudante;
	private String idNomeEstudante;
	
//	public CandidatoCursoBean()  {
//		FacesContext fc = FacesContext.getCurrentInstance();
//		ExternalContext ec = fc.getExternalContext();
//		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
//		HttpSession session = request.getSession();
//
//		try{
//			Integer idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
//			Integer idCandidato = Integer.parseInt(request.getParameter("idCandidato"));
//			session.setAttribute("idEmpresa", idEmpresa);
//			session.setAttribute("idCandidato", idCandidato);
//		}catch(NumberFormatException e){
//			e.printStackTrace();
//		}
//		
//	}
	
	public String salvarEContinuar() throws Exception {
		try {
			if(candidatoCursoService.numeroCursosSelecionados(diversos.getIdCandidato(), diversos.getIdEmpresa()) == 0){
				showMessage("- Selecione ao menos um curso que você esteja cursando ou que deseja cursar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			FacesContext fc = FacesContext.getCurrentInstance(); 
			HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
			final String url = request .getRequestURI(); 
			if(url.contains("CandidatoCursos.jsf")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("Painel.jsf");
			}else{
				emailMarketingService.enviarEmailBoasVindas(candidatoService.getEmail(diversos.getIdCandidato()));
				FacesContext.getCurrentInstance().getExternalContext().redirect("CadastroConclusao.jsf?idEmpresa="+diversos.getIdEmpresa());
				diversos.encerraSessao();
			}
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String salvar() throws Exception {
		try {
			if(candidatoCursoService.atualizar(cc)){
				filtrarCursosPorCandidato(diversos.getIdCandidato());
				showMessage("- Curso Salvo com sucesso.", FacesMessage.SEVERITY_INFO);
			}else{
				showMessage("- Erro ao Salvar Curso.", FacesMessage.SEVERITY_ERROR);
			}
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public String adicionarCurso(Integer idCurso) throws ServiceException{
		cc = new CandidatoCurso();
		cc.setCandidato(new Candidato());
		cc.setCurso(new Curso());
		cc.getCandidato().setIdcandidato(diversos.getIdCandidato());
		cc.getCurso().setIdcurso(idCurso);
		if(matriculado==null){
			matriculado=0;
		}
		cc.setMatriculado(matriculado);
		matriculado=0;
		cc.setOrdem(1);
		
		if(candidatoCursoService.salvar(cc)){
			showMessage("- Curso Adicionado com sucesso.", FacesMessage.SEVERITY_INFO);
			filtrarCursosPorCandidato(diversos.getIdCandidato());
			getListaCursosDisponiveis();
		}else{
			showMessage("- Erro ao Adicionar Curso.", FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}
	
	public String removerCurso(Integer idCurso) throws ServiceException{
		if(candidatoCursoService.excluirCurso(diversos.getIdCandidato(), idCurso)){
			showMessage("- Curso Removido com sucesso.", FacesMessage.SEVERITY_INFO);
		}else{
			showMessage("- Erro ao Remover Curso.", FacesMessage.SEVERITY_ERROR);
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
	
	public String filtrarCursosPorCandidato(Integer idCandidato) throws ServiceException{
		diversos.setIdCandidato(idCandidato);
		listaCursosSelecionados2 = candidatoCursoService.listarCursosPorCandidato(diversos.getIdEmpresa(), idCandidato);
		getListaCursosDisponiveis();
		return null;
	}
	
	public String carregar(Integer idCandidatoCurso) throws ServiceException{
		cc = candidatoCursoService.carregar(idCandidatoCurso);
		return null;
	}
	
	public String excluir(Integer idCandidatoCurso) throws ServiceException{
		if(candidatoCursoService.excluirCurso(idCandidatoCurso)){
			filtrarCursosPorCandidato(diversos.getIdCandidato());
			showMessage("- Curso Excluído com sucesso.", FacesMessage.SEVERITY_INFO);
		}else{
			showMessage("- Erro ao Excluir Curso.", FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}
	
	public CandidatoCurso getCc() {
		if(cc == null){
			cc = new CandidatoCurso();
			cc.setCandidato(new Candidato());
			cc.setCurso(new Curso());
		}
		return cc;
	}

	public void setCc(CandidatoCurso cc) {
		this.cc = cc;
	}

	public List<Curso> getListaCursosDisponiveis() throws ServiceException {
		if(diversos.getIdCandidato() != null){
			listaCursosDisponiveis = cursoService.listarCursosDisponiveis(diversos.getIdEmpresa(), diversos.getIdCandidato());
		}
		return listaCursosDisponiveis;
	}

	public void setListaCursosDisponiveis(List<Curso> listaCursosDisponiveis) {
		this.listaCursosDisponiveis = listaCursosDisponiveis;
	}

	public List<CandidatoCurso> getListaCursosSelecionados() throws ServiceException {
		listaCursosSelecionados = candidatoCursoService.listarCursosPorCandidato(diversos.getIdEmpresa(), diversos.getIdCandidato());
		return listaCursosSelecionados;
	}

	public void setListaCursosSelecionados(
			List<CandidatoCurso> listaCursosSelecionados) {
		this.listaCursosSelecionados = listaCursosSelecionados;
	}

	public Integer getMatriculado() {
		return matriculado;
	}

	public void setMatriculado(Integer matriculado) {
		this.matriculado = matriculado;
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

	public List<CandidatoCurso> getListaCursosSelecionados2() {
		return listaCursosSelecionados2;
	}

	public void setListaCursosSelecionados2(
			List<CandidatoCurso> listaCursosSelecionados2) {
		this.listaCursosSelecionados2 = listaCursosSelecionados2;
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

}
