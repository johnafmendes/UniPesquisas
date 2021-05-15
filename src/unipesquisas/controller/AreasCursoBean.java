package unipesquisas.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.AreasCurso;
import unipesquisas.model.service.AreasCursoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class AreasCursoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private AreasCursoService areasCursoService;
	
	@Inject
	private Diversos diversos;

	private AreasCurso areasCurso;
	private List<AreasCurso> listaAreas;
	
	public String salvar() throws Exception {
		try {
			if (areasCurso.getIdareacurso() == null) {
			
				areasCurso.setIdempresa(diversos.getIdEmpresa());
				
				if(areasCursoService.salvar(areasCurso)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				
				if(areasCursoService.atualizar(areasCurso)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			getListaAreas();
			return null;
		} catch (ValidationException e) {
			showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			//addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public String alterar(Integer idAreaCurso) throws Exception {
		areasCurso = areasCursoService.carregar(idAreaCurso);
		return null;
	}
	
	public String limpar(){
		areasCurso = null;
		return null;
	}

	public String excluir(Integer idAreaCurso) {
		try{
			if(areasCursoService.excluir(idAreaCurso)){
				showMessage("- Registro excluído com sucesso.", FacesMessage.SEVERITY_INFO);
				return null;
			}else{
				showMessage("- Não é possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
		} catch (ServiceException e){
			showMessage("- Não é possível excluir o registro.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}

	public AreasCursoService getAreasCursoService() {
		return areasCursoService;
	}

	public void setAreasCursoService(AreasCursoService areasCursoService) {
		this.areasCursoService = areasCursoService;
	}

	public AreasCurso getAreasCurso() {
		if(areasCurso == null){
			areasCurso = new AreasCurso();
		}
		return areasCurso;
	}

	public void setAreasCurso(AreasCurso areasCurso) {
		this.areasCurso = areasCurso;
	}

	public List<AreasCurso> getListaAreas() throws ServiceException {
		listaAreas = areasCursoService.listarAreas(diversos.getIdEmpresa());
		return listaAreas;
	}

	public void setListaAreas(List<AreasCurso> listaAreas) {
		this.listaAreas = listaAreas;
	}

}