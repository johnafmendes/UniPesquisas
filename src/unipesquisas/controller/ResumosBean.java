package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.AreasCurso;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.AreasCursoService;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;

@Named
@SessionScoped
public class ResumosBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private EscolaridadeService escolaridadeService;
	
	@Inject
	private StatusCandidatoService scService;
	
	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private CandidatoService candidatoService;
	
	@Inject
	private AreasCursoService areaService;
	
	@Inject
	private Diversos diversos;
	
	private List<Escolaridade> listaEscolaridades;
	private List<Instituicao> listaInstituicoes;
	private List<AreasCurso> listaAreas;
	private List<StatusCandidato> listaStatusCandidatos;
	private Integer totalEstudantes;
	private Integer totalEstudantesCelular;
	private Integer totalEstudantesSemStatus;
	
	public List<Escolaridade> getListaEscolaridades() throws ServiceException {
		listaEscolaridades = escolaridadeService.listarResumoEscolaridades(diversos.getIdEmpresa());
		return listaEscolaridades;
	}

	public List<Instituicao> getListaInstituicoes() throws ServiceException {
		listaInstituicoes = instituicaoService.listarResumoInstituicoes(diversos.getIdEmpresa());
		return listaInstituicoes;
	}
	
	public List<StatusCandidato> getListaStatusCandidatos() throws ServiceException {
		listaStatusCandidatos = scService.listarResumoStatus(diversos.getIdEmpresa());
		return listaStatusCandidatos;
	}

	public Integer getTotalEstudantes() throws ServiceException {
		totalEstudantes = candidatoService.getTotalCandidatos(diversos.getIdEmpresa());
		return totalEstudantes;
	}

	public List<AreasCurso> getListaAreas() throws ServiceException {
		listaAreas = areaService.listarResumoAreas(diversos.getIdEmpresa());
		return listaAreas;
	}

	public Integer getTotalEstudantesCelular() throws ServiceException {
		totalEstudantesCelular = candidatoService.getTotalCandidatosCelular(diversos.getIdEmpresa());
		return totalEstudantesCelular;
	}

	public Integer getTotalEstudantesSemStatus() throws ServiceException {
		totalEstudantesSemStatus = scService.getTotalCandidatosSemStatus(diversos.getIdEmpresa());
		return totalEstudantesSemStatus;
	}
	
}
