package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.PesquisaCurso;
import unipesquisas.model.service.PesquisaCursoService;
import unipesquisas.model.service.ServiceException;

@Named
@SessionScoped
public class PesquisaCursoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private PesquisaCursoService pcService;
	
	@Inject
	private Diversos diversos;
	
	private List<PesquisaCurso> listaPesquisasDisponiveis;

	public Diversos getDiversos() {
		return diversos;
	}

	public void setDiversos(Diversos diversos) {
		this.diversos = diversos;
	}

	public List<PesquisaCurso> getListaPesquisasDisponiveis() throws ServiceException {
		listaPesquisasDisponiveis = pcService.listaPesquisaCursoDisponiveis(diversos.getIdCandidato(), diversos.getIdEmpresa(), diversos.getIdStatusCandidato());
		return listaPesquisasDisponiveis;
	}

	public PesquisaCursoService getPcService() {
		return pcService;
	}

	public void setPcService(PesquisaCursoService pcService) {
		this.pcService = pcService;
	}

	public void setListaPesquisasDisponiveis(
			List<PesquisaCurso> listaPesquisasDisponiveis) {
		this.listaPesquisasDisponiveis = listaPesquisasDisponiveis;
	}

}