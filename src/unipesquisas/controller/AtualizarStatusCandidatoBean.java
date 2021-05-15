package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.CandidatoEmpresa;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.CandidatoEmpresaService;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.EmpresaService;
import unipesquisas.model.service.RespostaPesquisaCursoService;
import unipesquisas.model.service.RespostaPesquisaEscolaridadeService;
import unipesquisas.model.service.RespostaPesquisaInstituicaoService;
import unipesquisas.model.service.StatusCandidatoService;

@Named
@SessionScoped
public class AtualizarStatusCandidatoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private EmpresaService empresaService;
	
	@Inject
	private RespostaPesquisaEscolaridadeService rpeService;
	
	@Inject
	private RespostaPesquisaInstituicaoService rpiService;
	
	@Inject
	private RespostaPesquisaCursoService rpcService;
	
	@Inject
	private CandidatoEmpresaService ceService;
	
	@Inject
	private StatusCandidatoService scService;
	
	@Inject
	private CandidatoService cService;
	
	@Inject
	private Diversos diversos;

	private List<Empresa> listaEmpresas;
	private List<Candidato> listaCandidatos;
	private StatusCandidato sc;
	private CandidatoEmpresa ce;
	
	public String atualizar() throws Exception {
		listaEmpresas = empresaService.listarEmpresas();
		
		for (Empresa e : listaEmpresas) {
			sc = new StatusCandidato();
			sc.setEmpresa(new Empresa());
			sc.setStatus("Estudante Externo");
			sc.getEmpresa().setIdempresa(e.getIdempresa());
			scService.salvar(sc);
			
			listaCandidatos = cService.listarCandidatos(e.getIdempresa());
			
			for (Candidato c : listaCandidatos) {
				ce = ceService.carregar(c.getIdcandidato(), e.getIdempresa());
				ce.getStatuscandidato().setIdstatuscandidato(sc.getIdstatuscandidato());
				ceService.atualizar(ce, 1);
				
				rpeService.atualizar(c.getIdcandidato(), sc.getIdstatuscandidato(), e.getIdempresa());
				rpiService.atualizar(c.getIdcandidato(), sc.getIdstatuscandidato(), e.getIdempresa());
				rpcService.atualizar(c.getIdcandidato(), sc.getIdstatuscandidato(), e.getIdempresa());
			}
		}
		showMessage("- Atualizado com Sucesso.", FacesMessage.SEVERITY_INFO);
		return null;
	}

}
