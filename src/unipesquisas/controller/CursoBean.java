package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.AreasCurso;
import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.service.AreasCursoService;
import unipesquisas.model.service.CursoService;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class CursoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private CursoService cursoService;
	
	@Inject
	private AreasCursoService areasCursoService;
	
	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private Diversos diversos;
	
	private Curso curso;
	private List<Curso> listaCursos;
	private List<AreasCurso> listaAreas;
	private List<Instituicao> listaInstituicoes;
	private Integer idArea;
	private Integer idInstituicao;
	private Integer tipoPesquisaCurso;
	private String idNomeCurso;
	
	public String salvar() throws Exception {
		try {
			curso.getAreacurso().setIdareacurso(idArea);
			curso.getInstituicao().setIdinstituicao(idInstituicao);
			
			if (curso.getIdcurso() == null) {
				if(cursoService.salvar(curso)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(cursoService.atualizar(curso)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public String alterar(Integer idCurso) throws Exception {
		System.out.println("=="+idCurso);
		curso = cursoService.carregar(idCurso);
		idArea = curso.getAreacurso().getIdareacurso();
		idInstituicao = curso.getInstituicao().getIdinstituicao();
		return null;
	}
	
	public String limpar(){
		curso = null;
		idArea = null;
		idInstituicao = null;
		return null;
	}

	public String excluir(Integer idCurso) {
		try{
			if(cursoService.excluir(idCurso)){
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

	public String pesquisarCurso() throws ServiceException{
		if(tipoPesquisaCurso==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Nome do Curso.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaCurso==1){//codigo
			try{
				listaCursos = cursoService.listarCursoPorID(Integer.parseInt(idNomeCurso), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//nome curso
			listaCursos = cursoService.listarPesquisaPorCurso(idNomeCurso, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudo() throws ServiceException{
		listaCursos = cursoService.listarCursos(diversos.getIdEmpresa());
		return null;
	}
	
	public Curso getCurso() {
		if(curso == null){
			curso = new Curso();
			curso.setAreacurso(new AreasCurso());
			curso.setInstituicao(new Instituicao());
		}
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Curso> getListaCursos() throws ServiceException {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public List<AreasCurso> getListaAreas() throws ServiceException {
		listaAreas = areasCursoService.listarAreas(diversos.getIdEmpresa());
		return listaAreas;
	}

	public void setListaAreas(List<AreasCurso> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public List<Instituicao> getListaInstituicoes() throws ServiceException {
		listaInstituicoes = instituicaoService.listarIntituicoes(diversos.getIdEmpresa());
		return listaInstituicoes;
	}

	public void setListaInstituicoes(List<Instituicao> listaInstituicoes) {
		this.listaInstituicoes = listaInstituicoes;
	}

	public Integer getIdArea() {
		return idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	public Integer getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Integer idInstituicao) {
		this.idInstituicao = idInstituicao;
	}

	public Integer getTipoPesquisaCurso() {
		return tipoPesquisaCurso;
	}

	public void setTipoPesquisaCurso(Integer tipoPesquisaCurso) {
		this.tipoPesquisaCurso = tipoPesquisaCurso;
	}

	public String getIdNomeCurso() {
		return idNomeCurso;
	}

	public void setIdNomeCurso(String idNomeCurso) {
		this.idNomeCurso = idNomeCurso;
	}

}
