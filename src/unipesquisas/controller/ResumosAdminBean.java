package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.AreasCurso;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.EnvioEmailMarketing;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.AreasCursoService;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.EmpresaService;
import unipesquisas.model.service.EnvioEmailMarketingService;
import unipesquisas.model.service.EscolaridadeService;
import unipesquisas.model.service.InstituicaoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.StatusCandidatoService;

@Named
@SessionScoped
public class ResumosAdminBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private EscolaridadeService escolaridadeService;
	
	@Inject
	private StatusCandidatoService scService;
	
	@Inject
	private CandidatoService candidatoService;
	
	@Inject 
	private EnvioEmailMarketingService eEMService;
	
	@Inject
	private Diversos diversos;
	
	@Inject
	private InstituicaoService instituicaoService;
	
	@Inject
	private AreasCursoService areaService;

	@Inject
	private EmpresaService empresaService;
	
	private List<Escolaridade> listaEscolaridades;
	private List<EnvioEmailMarketing> listaEnvioEmailMarketing;
	private List<Empresa> listaEmpresas;
	private Integer idEmpresa;
	private Integer totalEstudantes;
	private Integer totalEstudantesCelular;
	private List<Escolaridade> listaEscolaridadesPorEmpresa;
	private List<Instituicao> listaInstituicoesPorEmpresa;
	private List<AreasCurso> listaAreasPorEmpresa;
	private Integer totalEstudantesPorEmpresa;
	private Integer totalEstudantesCelularPorEmpresa;
	private List<StatusCandidato> listaStatusCandidatosPorEmpresa;
	private Integer totalEstudantesSemStatusPorEmpresa;
	
	public List<Escolaridade> getListaEscolaridades() throws ServiceException {
		listaEscolaridades = escolaridadeService.listarResumoEscolaridades();
		return listaEscolaridades;
	}

	public Integer getTotalEstudantes() throws ServiceException {
		totalEstudantes = candidatoService.getTotalCandidatos();
		return totalEstudantes;
	}

	public Integer getTotalEstudantesCelular() throws ServiceException {
		totalEstudantesCelular = candidatoService.getTotalCandidatosCelular();
		return totalEstudantesCelular;
	}

	public List<EnvioEmailMarketing> getListaEnvioEmailMarketing() throws ServiceException {
		listaEnvioEmailMarketing = eEMService.listarEmailMarketings();
		return listaEnvioEmailMarketing;
	}	
	
	public List<Escolaridade> getListaEscolaridadesPorEmpresa() throws ServiceException {
		return listaEscolaridadesPorEmpresa;
	}

	public List<Instituicao> getListaInstituicoesPorEmpresa() throws ServiceException {
		return listaInstituicoesPorEmpresa;
	}

	public Integer getTotalEstudantesPorEmpresa() throws ServiceException {
		return totalEstudantesPorEmpresa;
	}

	public List<AreasCurso> getListaAreasPorEmpresa() throws ServiceException {
		return listaAreasPorEmpresa;
	}

	public Integer getTotalEstudantesCelularPorEmpresa() throws ServiceException {
		return totalEstudantesCelularPorEmpresa;
	}

	public List<Empresa> getListaEmpresas() throws ServiceException {
		listaEmpresas = empresaService.listarEmpresas(); 
		return listaEmpresas;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String filtrarDadosEmpresa() throws ServiceException{
		if(idEmpresa == null){
			return null;
		}
		listaEscolaridadesPorEmpresa = escolaridadeService.listarResumoEscolaridades(idEmpresa);
		listaInstituicoesPorEmpresa = instituicaoService.listarResumoInstituicoes(idEmpresa);
		totalEstudantesPorEmpresa = candidatoService.getTotalCandidatos(idEmpresa);
		listaAreasPorEmpresa = areaService.listarResumoAreas(idEmpresa);
		totalEstudantesCelularPorEmpresa = candidatoService.getTotalCandidatosCelular(idEmpresa);
		totalEstudantesSemStatusPorEmpresa = scService.getTotalCandidatosSemStatus(idEmpresa);
		listaStatusCandidatosPorEmpresa = scService.listarResumoStatus(idEmpresa);
		return null;
	}
	
	public Integer getTotalEstudantesSemStatusPorEmpresa() throws ServiceException {
		return totalEstudantesSemStatusPorEmpresa;
	}
	
	public List<StatusCandidato> getListaStatusCandidatosPorEmpresa() throws ServiceException {
		return listaStatusCandidatosPorEmpresa;
	}
}
