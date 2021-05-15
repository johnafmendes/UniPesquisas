package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.PesquisaInstituicao;
import unipesquisas.model.service.PesquisaInstituicaoService;
import unipesquisas.model.service.ServiceException;

@Named
@SessionScoped
public class PesquisaInstituicaoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private PesquisaInstituicaoService piService;
	
	@Inject
	private Diversos diversos;
	
//	private List<PesquisaInstituicao> listaPesquisaInstituicoes;
	private List<PesquisaInstituicao> listaPesquisasDisponiveis;

	public Diversos getDiversos() {
		return diversos;
	}

	public void setDiversos(Diversos diversos) {
		this.diversos = diversos;
	}

//	public List<PesquisaInstituicao> getListaPesquisaInstituicoes() throws ServiceException {
//		listaPesquisaInstituicoes = piService.listaPesquisaInstituicao(diversos.getIdEmpresa());
//		return listaPesquisaInstituicoes;
//	}

	public List<PesquisaInstituicao> getListaPesquisasDisponiveis() throws ServiceException {
		listaPesquisasDisponiveis = piService.listaPesquisaInstituicaoDisponiveis(diversos.getIdCandidato(), diversos.getIdEmpresa(), diversos.getIdStatusCandidato());
		return listaPesquisasDisponiveis;
	}

	public PesquisaInstituicaoService getPiService() {
		return piService;
	}

	public void setPiService(PesquisaInstituicaoService piService) {
		this.piService = piService;
	}

//	public void setListaPesquisaInstituicoes(
//			List<PesquisaInstituicao> listaPesquisaInstituicoes) {
//		this.listaPesquisaInstituicoes = listaPesquisaInstituicoes;
//	}

	public void setListaPesquisasDisponiveis(
			List<PesquisaInstituicao> listaPesquisasDisponiveis) {
		this.listaPesquisasDisponiveis = listaPesquisasDisponiveis;
	}

}