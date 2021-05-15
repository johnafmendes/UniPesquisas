package unipesquisas.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.CandidatoPesquisaCurso;
import unipesquisas.model.entity.CandidatoPesquisaEscolaridade;
import unipesquisas.model.entity.CandidatoPesquisaInstituicao;
import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.PesquisaCurso;
import unipesquisas.model.entity.PesquisaEscolaridade;
import unipesquisas.model.entity.PesquisaInstituicao;
import unipesquisas.model.entity.PesquisaPergunta;
import unipesquisas.model.entity.RespostaPesquisa;
import unipesquisas.model.entity.RespostaPesquisaCurso;
import unipesquisas.model.entity.RespostaPesquisaEscolaridade;
import unipesquisas.model.entity.RespostaPesquisaInstituicao;
import unipesquisas.model.entity.StatusCandidato;
import unipesquisas.model.service.CandidatoPesquisaCursoService;
import unipesquisas.model.service.CandidatoPesquisaEscolaridadeService;
import unipesquisas.model.service.CandidatoPesquisaInstituicaoService;
import unipesquisas.model.service.PerguntaService;
import unipesquisas.model.service.PesquisaPerguntaService;
import unipesquisas.model.service.PesquisaService;
import unipesquisas.model.service.RespostaPesquisaCursoService;
import unipesquisas.model.service.RespostaPesquisaEscolaridadeService;
import unipesquisas.model.service.RespostaPesquisaInstituicaoService;
import unipesquisas.model.service.ServiceException;
import unipesquisas.model.service.ValidationException;

@Named
@SessionScoped
public class PesquisaPerguntaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7045789147863763553L;

	@Inject	
	private PesquisaPerguntaService ppService;
	
	@Inject
	private PesquisaService pesquisaService;
	
	@Inject
	private PerguntaService perguntaService;
	
	@Inject
	private RespostaPesquisaEscolaridadeService rpeService;
	
	@Inject
	private RespostaPesquisaInstituicaoService rpiService;

	@Inject
	private RespostaPesquisaCursoService rpcService;

	@Inject
	private CandidatoPesquisaEscolaridadeService cpeService;
	
	@Inject
	private CandidatoPesquisaInstituicaoService cpiService;
	
	@Inject
	private CandidatoPesquisaCursoService cpcService;
	
	@Inject
	private Diversos diversos;
	
	private List<Pergunta> listaPerguntas;
	private List<Pesquisa> listaPesquisas;
	private List<PesquisaPergunta> listaPPs;
	private PesquisaPergunta pp;
	private RespostaPesquisa rp;
	private RespostaPesquisaEscolaridade rpe;
	private RespostaPesquisaInstituicao rpi;
	private RespostaPesquisaCurso rpc;
	private Pesquisa pesquisa;
	private Pergunta pergunta;
	private String resposta;
	private List<String> selectedRespostas;
	private Integer idPesquisa;
	private Integer idPesquisaEscolaridade;
	private Integer idPesquisaInstituicao;
	private Integer idPesquisaCurso;
	private CandidatoPesquisaEscolaridade cpe;
	private CandidatoPesquisaInstituicao cpi;
	private CandidatoPesquisaCurso cpc;
	private String pesquisaPor;
	private Integer tipoPesquisaPesquisa;
	private String idTituloPesquisa;
	private Integer tipoPesquisaPergunta;
	private String idTituloPergunta;
	
	public String salvar() throws Exception {
		try {
			if(pesquisa == null || pergunta == null){
				showMessage("- Selecione uma PESQUISA e uma PERGUNTA antes de salvar.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
			getPp();
			pp.getPesquisa().setIdpesquisa(pesquisa.getIdpesquisa());
			
			pp.getPergunta().setIdpergunta(pergunta.getIdpergunta());
			
			if(ppService.salvar(pp)) {
				showMessage("- Pergunta adicionada a pesquisa com sucesso.", FacesMessage.SEVERITY_INFO);
			}else {
				showMessage("- Erro ao adicionar pergunta a pesquisa. Talvez a pergunta já esteja adicionada dentro da pesquia.", FacesMessage.SEVERITY_ERROR);
			}
			getListaPPs(pesquisa.getIdpesquisa());
			return null;
		} catch (ValidationException e) {
			showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String salvarEContinuar() throws Exception {
		carregar(idPesquisa, pergunta.getIdpergunta());
		if(pergunta.getTipo().toString().equals("MultiplaOpcoes")){
			for(int i = 0; i < selectedRespostas.size(); i++){
				salvarResposta(i, pergunta.getTipo().toString());
			}
		}else{//única opcao
			salvarResposta(0, pergunta.getTipo().toString());
		}
		proximaPergunta();
		return null;
	}

	private void salvarResposta(Integer idResposta, String tipo) throws ServiceException, IOException {
		if(pesquisaPor.equals("escolaridade")){
			rpe = new RespostaPesquisaEscolaridade();
			rpe.setCandidato(new Candidato());
			rpe.setPesquisaescolaridade(new PesquisaEscolaridade());
			rpe.setPesquisapergunta(new PesquisaPergunta());
			rpe.setStatuscandidato(new StatusCandidato());
			
			rpe.getCandidato().setIdcandidato(diversos.getIdCandidato());
			rpe.getPesquisaescolaridade().setIdpesquisaescolaridade(idPesquisaEscolaridade);
			rpe.getPesquisapergunta().setIdpesquisapergunta(pp.getIdpesquisapergunta());
			if(tipo.equals("UnicaOpcao")){
				rpe.setResposta(resposta);
			}else{//multiplas opcoes
				rpe.setResposta(selectedRespostas.get(idResposta));
			}
			rpe.setData(new Timestamp(System.currentTimeMillis()));
			rpe.getStatuscandidato().setIdstatuscandidato(diversos.getIdStatusCandidato());
			try {
				if(rpeService.salvar(rpe)) {
					showMessage("- Respondido com sucesso. Responda a próxima pergunta.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao responder a pergunta da pesquisa.", FacesMessage.SEVERITY_ERROR);
				}
			} catch (ValidationException e) {
				showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
				addMessageToRequest(e.getMessage());
			}		
		}
		
		if(pesquisaPor.equals("instituicao")){
			rpi = new RespostaPesquisaInstituicao();
			rpi.setCandidato(new Candidato());
			rpi.setPesquisainstituicao(new PesquisaInstituicao());
			rpi.setPesquisapergunta(new PesquisaPergunta());
			rpi.setStatuscandidato(new StatusCandidato());
			
			rpi.getCandidato().setIdcandidato(diversos.getIdCandidato());
			rpi.getPesquisainstituicao().setIdpesquisainstituicao(idPesquisaInstituicao);
			rpi.getPesquisapergunta().setIdpesquisapergunta(pp.getIdpesquisapergunta());
			if(tipo.equals("UnicaOpcao")){
				rpi.setResposta(resposta);
			}else{//multiplas opcoes
				rpi.setResposta(selectedRespostas.get(idResposta));
			}
			rpi.setData(new Timestamp(System.currentTimeMillis()));
			rpi.getStatuscandidato().setIdstatuscandidato(diversos.getIdStatusCandidato());
			try {
				if(rpiService.salvar(rpi)) {
					showMessage("- Respondido com sucesso. Responda a próxima pergunta.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao responder a pergunta da pesquisa.", FacesMessage.SEVERITY_ERROR);
				}
			} catch (ValidationException e) {
				showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
				addMessageToRequest(e.getMessage());
			}		
		}
		
		if(pesquisaPor.equals("curso")){
			rpc = new RespostaPesquisaCurso();
			rpc.setCandidato(new Candidato());
			rpc.setPesquisacurso(new PesquisaCurso());
			rpc.setPesquisapergunta(new PesquisaPergunta());
			rpc.setStatuscandidato(new StatusCandidato());
			
			rpc.getCandidato().setIdcandidato(diversos.getIdCandidato());
			rpc.getPesquisacurso().setIdpesquisacurso(idPesquisaCurso);
			rpc.getPesquisapergunta().setIdpesquisapergunta(pp.getIdpesquisapergunta());
			if(tipo.equals("UnicaOpcao")){
				rpc.setResposta(resposta);
			}else{//multiplas opcoes
				rpc.setResposta(selectedRespostas.get(idResposta));
			}
			rpc.setData(new Timestamp(System.currentTimeMillis()));
			rpc.getStatuscandidato().setIdstatuscandidato(diversos.getIdStatusCandidato());
			try {
				if(rpcService.salvar(rpc)) {
					showMessage("- Respondido com sucesso. Responda a próxima pergunta.", FacesMessage.SEVERITY_INFO);
				}else {
					showMessage("- Erro ao responder a pergunta da pesquisa.", FacesMessage.SEVERITY_ERROR);
				}
			} catch (ValidationException e) {
				showMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
				addMessageToRequest(e.getMessage());
			}		
		}
	}

	private void proximaPergunta() throws ServiceException, IOException {
		if(pesquisaPor.equals("escolaridade")){
			pesquisaPerguntaPorPesquisaEscolaridade(idPesquisa);
		}
		if(pesquisaPor.equals("instituicao")){
			pesquisaPerguntaPorPesquisaInstituicao(idPesquisa);
		}
		if(pesquisaPor.equals("curso")){
			pesquisaPerguntaPorPesquisaCurso(idPesquisa);
		}
		resposta = null;
		if(selectedRespostas != null){
			selectedRespostas.clear();
		}
		if(listaPerguntas.size() > 0){
			getPergunta();
			pergunta.setIdpergunta(listaPerguntas.get(0).getIdpergunta());
			pergunta.setPergunta(listaPerguntas.get(0).getPergunta());
			pergunta.setAlta(listaPerguntas.get(0).getAlta());
			pergunta.setAltb(listaPerguntas.get(0).getAltb());
			pergunta.setAltc(listaPerguntas.get(0).getAltc());
			pergunta.setAltd(listaPerguntas.get(0).getAltd());
			pergunta.setAlte(listaPerguntas.get(0).getAlte());
			pergunta.setTipo(listaPerguntas.get(0).getTipo());
		}else{
			if(pesquisaPor.equals("escolaridade")){
				getCpe();
				cpe.getCandidato().setIdcandidato(diversos.getIdCandidato());
				cpe.getPesquisaescolaridade().setIdpesquisaescolaridade(this.idPesquisaEscolaridade);
				cpeService.atualizar(cpe);
			}
			if(pesquisaPor.equals("instituicao")){
				getCpi();
				cpi.getCandidato().setIdcandidato(diversos.getIdCandidato());
				cpi.getPesquisainstituicao().setIdpesquisainstituicao(this.idPesquisaInstituicao);
				cpiService.atualizar(cpi);
			}
			if(pesquisaPor.equals("curso")){
				getCpc();
				cpc.getCandidato().setIdcandidato(diversos.getIdCandidato());
				cpc.getPesquisacurso().setIdpesquisacurso(this.idPesquisaCurso);
				cpcService.atualizar(cpc);
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("Painel.jsf");
		}
	}
	
	public String ResponderPesquisaEscolaridade(Integer idPesquisa, Integer idPesquisaEscolaridade) throws IOException, ServiceException{
		this.idPesquisa = idPesquisa;
		System.out.println("=="+idPesquisa);
		this.idPesquisaEscolaridade = idPesquisaEscolaridade;
		pesquisaPor = "escolaridade";
		FacesContext.getCurrentInstance().getExternalContext().redirect("ResponderPesquisa.jsf");
		proximaPergunta();
		
		return null;
	}
	
	public String ResponderPesquisaInstituicao(Integer idPesquisa, Integer idPesquisaInstituicao) throws IOException, ServiceException{
		this.idPesquisa = idPesquisa;
		this.idPesquisaInstituicao = idPesquisaInstituicao;
		pesquisaPor = "instituicao";
		FacesContext.getCurrentInstance().getExternalContext().redirect("ResponderPesquisa.jsf");
		proximaPergunta();
		
		return null;
	}
	
	public String ResponderPesquisaCurso(Integer idPesquisa, Integer idPesquisaCurso) throws IOException, ServiceException{
		this.idPesquisa = idPesquisa;
		this.idPesquisaCurso = idPesquisaCurso;
		pesquisaPor = "curso";
		FacesContext.getCurrentInstance().getExternalContext().redirect("ResponderPesquisa.jsf");
		proximaPergunta();
		
		return null;
	}
	public String limpar() {
		listaPesquisas = null;
		listaPerguntas = null;
		idTituloPesquisa = null;
		idTituloPergunta = null;
		listaPPs = null;
		return null;
	}
	
	public String filtrar(Integer idPesquisa) throws ServiceException{
		getListaPPs(idPesquisa);
		return null;
	}

	public String excluir(Integer idPP) {
		try{
			if(ppService.excluir(idPP)){
				showMessage("- Pergunta excluída com sucesso.", FacesMessage.SEVERITY_INFO);
				return null;
			}else{
				showMessage("- Não é possível excluir a pergunta.", FacesMessage.SEVERITY_ERROR);
				return null;
			}
		} catch (ServiceException e){
			showMessage("- Não é possível excluir a pergunta.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}

	public void carregar(Integer idPesquisa, Integer idPergunta) throws Exception {
		pp = ppService.carregar(idPesquisa, idPergunta);
	}
	
	public String pesquisarPesquisa() throws ServiceException{
		if(tipoPesquisaPesquisa==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Título da Pesquisa.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPesquisa==1){//codigo
			try{
				listaPesquisas = pesquisaService.listarPesquisaPorID(Integer.parseInt(idTituloPesquisa), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo pesquisa
			listaPesquisas = pesquisaService.listarPesquisaPorTitulo(idTituloPesquisa, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPesquisa() throws ServiceException{
		listaPesquisas = pesquisaService.listarPesquisas(diversos.getIdEmpresa());
		return null;
	}
	
	public String pesquisarPergunta() throws ServiceException{
		if(tipoPesquisaPergunta==null){
			showMessage("- Selecione como deseja pesquisar, por Código ou Título da Pergunta.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		if(tipoPesquisaPergunta==1){//codigo
			try{
				listaPerguntas = perguntaService.listarPerguntaPorID(Integer.parseInt(idTituloPergunta), diversos.getIdEmpresa());
			}catch(Exception e){
				showMessage("- Digite um número para pesquisar.", FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		} else {//titulo pergunta
			listaPerguntas = perguntaService.listarPesquisaPergunta(idTituloPergunta, diversos.getIdEmpresa());
		}
		return null;
	}
	
	public String listarTudoPergunta() throws ServiceException{
		listaPerguntas = perguntaService.listarPerguntas(diversos.getIdEmpresa());
		return null;
	}
	
	public List<PesquisaPergunta> getListaPPs() throws ServiceException {
		//listaPPs = ppService.listarPPs(idPesquisa);
		return listaPPs;
	}
	
	public List<PesquisaPergunta> getListaPPs(Integer idPesquisa) throws ServiceException {
		listaPPs = ppService.listarPPs(idPesquisa);
		return listaPPs;
	}
	
	public String pesquisaPerguntaPorPesquisaEscolaridade(Integer idPesquisa) throws ServiceException{
		listaPerguntas = perguntaService.listarPerguntasPorPesquisaEscolaridade(idPesquisa, diversos.getIdCandidato());
		return null;
	}
	
	public String pesquisaPerguntaPorPesquisaInstituicao(Integer idPesquisa) throws ServiceException{
		listaPerguntas = perguntaService.listarPerguntasPorPesquisaInstituicao(idPesquisa, diversos.getIdCandidato());
		return null;
	}
	
	public String pesquisaPerguntaPorPesquisaCurso(Integer idPesquisa) throws ServiceException{
		listaPerguntas = perguntaService.listarPerguntasPorPesquisaCurso(idPesquisa, diversos.getIdCandidato());
		return null;
	}

	public void setListaPPs(List<PesquisaPergunta> listaPPs) {
		this.listaPPs = listaPPs;
	}

	public List<Pergunta> getListaPerguntas() {
		return listaPerguntas;
	}
	
	public void setListaPerguntas(List<Pergunta> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}
	
	public PesquisaPergunta getPp() {
		if(pp == null){
			pp = new PesquisaPergunta();
			pp.setPesquisa(new Pesquisa());
			pp.setPergunta(new Pergunta());
		}
		return pp;
	}
	
	public void setPp(PesquisaPergunta pp) {
		this.pp = pp;
	}

	public List<Pesquisa> getListaPesquisas() {		
		return listaPesquisas;
	}

	public void setListaPesquisas(List<Pesquisa> listaPesquisas) {
		this.listaPesquisas = listaPesquisas;
	}

	public Pesquisa getPesquisa() {
		if(pesquisa == null){
			pesquisa = new Pesquisa();
		}
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	public Pergunta getPergunta() {
		if(pergunta == null){
			pergunta = new Pergunta();
		}
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public RespostaPesquisa getRp() {
		if(rp == null){
			rp = new RespostaPesquisa();
			rp.setCandidato(new Candidato());
			rp.setPesquisapergunta(new PesquisaPergunta());
		}
		return rp;
	}

	public void setRp(RespostaPesquisa rp) {
		this.rp = rp;
	}

	public Integer getIdPesquisa() {
		return idPesquisa;
	}

	public void setIdPesquisa(Integer idPesquisa) {
		this.idPesquisa = idPesquisa;
	}

	public Integer getIdPesquisaEscolaridade() {
		return idPesquisaEscolaridade;
	}

	public void setIdPesquisaEscolaridade(Integer idPesquisaEscolaridade) {
		this.idPesquisaEscolaridade = idPesquisaEscolaridade;
	}

	public CandidatoPesquisaEscolaridade getCpe() {
		if (cpe == null){
			cpe = new CandidatoPesquisaEscolaridade();
			cpe.setCandidato(new Candidato());
			cpe.setPesquisaescolaridade(new PesquisaEscolaridade());
		}
		return cpe;
	}

	public void setCpe(CandidatoPesquisaEscolaridade cpe) {
		this.cpe = cpe;
	}

	public String getPesquisaPor() {
		return pesquisaPor;
	}

	public void setPesquisaPor(String pesquisaPor) {
		this.pesquisaPor = pesquisaPor;
	}

	public CandidatoPesquisaInstituicao getCpi() {
		if(cpi == null){
			cpi = new CandidatoPesquisaInstituicao();
			cpi.setCandidato(new Candidato());
			cpi.setPesquisainstituicao(new PesquisaInstituicao());
		}
		return cpi;
	}

	public void setCpi(CandidatoPesquisaInstituicao cpi) {
		this.cpi = cpi;
	}

	public CandidatoPesquisaCurso getCpc() {
		if(cpc == null){
			cpc = new CandidatoPesquisaCurso();
			cpc.setCandidato(new Candidato());
			cpc.setPesquisacurso(new PesquisaCurso());
		}
		return cpc;
	}

	public void setCpc(CandidatoPesquisaCurso cpc) {
		this.cpc = cpc;
	}

	public List<String> getSelectedRespostas() {
		return selectedRespostas;
	}

	public void setSelectedRespostas(List<String> selectedRespostas) {
		this.selectedRespostas = selectedRespostas;
	}

	public Integer getTipoPesquisaPesquisa() {
		return tipoPesquisaPesquisa;
	}

	public void setTipoPesquisaPesquisa(Integer tipoPesquisaPesquisa) {
		this.tipoPesquisaPesquisa = tipoPesquisaPesquisa;
	}

	public String getIdTituloPesquisa() {
		return idTituloPesquisa;
	}

	public void setIdTituloPesquisa(String idTituloPesquisa) {
		this.idTituloPesquisa = idTituloPesquisa;
	}

	public Integer getTipoPesquisaPergunta() {
		return tipoPesquisaPergunta;
	}

	public void setTipoPesquisaPergunta(Integer tipoPesquisaPergunta) {
		this.tipoPesquisaPergunta = tipoPesquisaPergunta;
	}

	public String getIdTituloPergunta() {
		return idTituloPergunta;
	}

	public void setIdTituloPergunta(String idTituloPergunta) {
		this.idTituloPergunta = idTituloPergunta;
	}
	
}