package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.PesquisaEscolaridade;
import unipesquisas.model.service.PesquisaEscolaridadeService;
import unipesquisas.model.service.ServiceException;

@Named
@SessionScoped
public class PesquisaEscolaridadeBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private PesquisaEscolaridadeService peService;
	
	@Inject
	private Diversos diversos;
	
	private List<PesquisaEscolaridade> listaPesquisasDisponiveis;

	public PesquisaEscolaridadeService getPeService() {
		return peService;
	}

	public void setPeService(PesquisaEscolaridadeService peService) {
		this.peService = peService;
	}

	public Diversos getDiversos() {
		return diversos;
	}

	public void setDiversos(Diversos diversos) {
		this.diversos = diversos;
	}

	public List<PesquisaEscolaridade> getListaPesquisasDisponiveis() throws ServiceException {
		listaPesquisasDisponiveis = peService.listaPesquisaEscolaridadeDisponiveis(diversos.getIdCandidato(), diversos.getIdEmpresa(), diversos.getIdEscolaridade(), diversos.getIdStatusCandidato());
		return listaPesquisasDisponiveis;
	}

	public void setListaPesquisasDisponiveis(
			List<PesquisaEscolaridade> listaPesquisasDisponiveis) {
		this.listaPesquisasDisponiveis = listaPesquisasDisponiveis;
	}
	
}