package unipesquisas.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.Progresso;
import unipesquisas.model.entity.Usuario;
import unipesquisas.model.service.CandidatoService;
import unipesquisas.model.service.ProgressoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.UsuarioService;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class ProgressoBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject
	private CandidatoService candidatoService;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private ProgressoService progressoService;
	
	@Inject
	private Diversos diversos;
	
	private Progresso progresso;
	private List<Progresso> listaProgressos;
	private List<Candidato> listaCandidatos;
	private List<Candidato> listaCandidatos2;
	private Integer opcaoPesquisa;
	private Integer idCandidato;
	private Candidato candidato;
	private Candidato candidato2;
	private Integer tipoPesquisaCandidato;
	private String idNomeCandidato;
	private Integer tipoPesquisaCandidato2;
	private String idNomeCandidato2;
	
	public String salvar() throws Exception {
		try {
			
			if (progresso.getIdprogressodetalhado() == null) {
				getCandidato();
				if(candidato.getIdcandidato() == null){
					showMessage("- Selecione um Estudante antes de salvar.", FacesMessage.SEVERITY_ERROR);
					return null;
				}
				progresso.setIdempresa(diversos.getIdEmpresa());
				progresso.getUsuario().setIdusuario(diversos.getIdUsuario());
				progresso.getCandidato().setIdcandidato(candidato.getIdcandidato());
				Timestamp tm = new Timestamp(System.currentTimeMillis());
				progresso.setData(tm);
				
				if(progressoService.salvar(progresso)) {
					showMessage("- Registro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao salvar registro", FacesMessage.SEVERITY_ERROR);
				}
				
			} else {
				if(progressoService.atualizar(progresso)){
					showMessage("- Registro atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
				} else {
					showMessage("- Erro ao atualizar registro", FacesMessage.SEVERITY_ERROR);
				}
			}
			//getListaProgressos();
//			alterar(progresso.getIdprogressodetalhado());
			return null;
		} catch (ValidationException e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public String limpar(){
		progresso = null;
		listaCandidatos = null;
		idNomeCandidato = null;
		return null;
	}

	public String excluir(Integer idProgresso) {
		try{
			if(progressoService.excluir(idProgresso)){
				showMessage("- Registro excluído com sucesso.", FacesMessage.SEVERITY_INFO);
				return null;
			}else{
				showMessage("- Erro ao excluir registro.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
		} catch (ServiceException e){
			showMessage("Erro: " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
			return null;
		}
	}
	
	public String pesquisarCandidato() throws ServiceException{
		if(tipoPesquisaCandidato==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Nome.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaCandidato==1){//codigo
			try{
				listaCandidatos = candidatoService.listarCandidatosPorID(Integer.parseInt(idNomeCandidato), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//nome 
			listaCandidatos = candidatoService.listarCandidatosPorNome(idNomeCandidato, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoCandidato() throws ServiceException{
		listaCandidatos = candidatoService.listarCandidatos(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarCandidato2() throws ServiceException{
		if(tipoPesquisaCandidato2==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Nome.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaCandidato2==1){//codigo
			try{
				listaCandidatos2 = candidatoService.listarCandidatosPorID(Integer.parseInt(idNomeCandidato2), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//nome 
			listaCandidatos2 = candidatoService.listarCandidatosPorNome(idNomeCandidato2, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoCandidato2() throws ServiceException{
		listaCandidatos2 = candidatoService.listarCandidatos(diversos.getIdEmpresa());
		return null;
	}
	
	
	public String filtraProgressoPorCandidato(Integer idCandidato) throws ServiceException{
		listaProgressos = progressoService.listarProgressos(idCandidato);
		return null;
	}

	public Progresso getProgresso() {
		if(progresso == null){
			progresso = new Progresso();
			progresso.setCandidato(new Candidato());
			progresso.setUsuario(new Usuario());
		}
		return progresso;
	}

	public void setProgresso(Progresso progresso) {
		this.progresso = progresso;
	}

	public List<Progresso> getListaProgressos() throws ServiceException {
		//listaProgressos = progressoService.listarProgressos(idCandidato);
		return listaProgressos;
	}

	public void setListaProgressos(List<Progresso> listaProgressos) {
		this.listaProgressos = listaProgressos;
	}

	public List<Candidato> getListaCandidatos() {
		return listaCandidatos;
	}

	public void setListaCandidatos(List<Candidato> listaCandidatos) {
		this.listaCandidatos = listaCandidatos;
	}

	public Integer getOpcaoPesquisa() {
		return opcaoPesquisa;
	}

	public void setOpcaoPesquisa(Integer opcaoPesquisa) {
		this.opcaoPesquisa = opcaoPesquisa;
	}

	public Integer getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(Integer idCandidato) {
		this.idCandidato = idCandidato;
	}


	public Candidato getCandidato() {
		if(candidato == null){
			candidato = new Candidato();
		}
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public List<Candidato> getListaCandidatos2() {
		return listaCandidatos2;
	}

	public void setListaCandidatos2(List<Candidato> listaCandidatos2) {
		this.listaCandidatos2 = listaCandidatos2;
	}

	public Candidato getCandidato2() {
		return candidato2;
	}

	public void setCandidato2(Candidato candidato2) {
		this.candidato2 = candidato2;
	}

	public Integer getTipoPesquisaCandidato() {
		return tipoPesquisaCandidato;
	}

	public void setTipoPesquisaCandidato(Integer tipoPesquisaCandidato) {
		this.tipoPesquisaCandidato = tipoPesquisaCandidato;
	}

	public String getIdNomeCandidato() {
		return idNomeCandidato;
	}

	public void setIdNomeCandidato(String idNomeCandidato) {
		this.idNomeCandidato = idNomeCandidato;
	}

	public Integer getTipoPesquisaCandidato2() {
		return tipoPesquisaCandidato2;
	}

	public void setTipoPesquisaCandidato2(Integer tipoPesquisaCandidato2) {
		this.tipoPesquisaCandidato2 = tipoPesquisaCandidato2;
	}

	public String getIdNomeCandidato2() {
		return idNomeCandidato2;
	}

	public void setIdNomeCandidato2(String idNomeCandidato2) {
		this.idNomeCandidato2 = idNomeCandidato2;
	}

}

