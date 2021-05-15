package unipesquisas.model.service;

import java.util.Date;

import javax.inject.Inject;

import unipesquisas.model.dao.DAOException;
import unipesquisas.model.dao.RelatorioPesquisaDAO;

public class RelatorioPesquisaService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780718281615892591L;
	
	@Inject
	private RelatorioPesquisaDAO rpDAO;

	public byte[] abrirListaPesquisaAproveitamento(String arquivoRelatorio, Integer idEmpresa, Date dataInicio, Date dataFim,
			Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaPesquisaAproveitamento(arquivoRelatorio, idEmpresa, dataInicio, dataFim, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaPesquisaAproveitamento(String arquivoRelatorio, int idEmpresa, String sessionId, 
			Date dataInicio, Date dataFim, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaPesquisaAproveitamento(arquivoRelatorio, idEmpresa, sessionId, dataInicio, dataFim, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaPesquisaAproveitamentoEscolaridadeIndividual(
			String arquivoRelatorio, Integer idEmpresa, String sessionId, Integer idPesquisa,
			Integer idEscolaridade, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaPesquisaAproveitamentoEscolaridadeIndividual(arquivoRelatorio, idEmpresa, sessionId, idPesquisa, idEscolaridade, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaPesquisaAproveitamentoEscolaridadeIndividual(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idEscolaridade, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaPesquisaAproveitamentoEscolaridadeIndividual(arquivoRelatorio, idPesquisa, idEscolaridade, idEmpresa, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaPesquisaAproveitamentoInstituicaoIndividual(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idinstituicao, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaPesquisaAproveitamentoInstituicaoIndividual(arquivoRelatorio, idEmpresa, idPesquisa, idinstituicao, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaPesquisaAproveitamentoInstituicaoIndividual(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idInstituicao, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaPesquisaAproveitamentoInstituicaoIndividual(arquivoRelatorio, idEmpresa, sessionId, idPesquisa, idInstituicao, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaPesquisaAproveitamentoCursoIndividual(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idCurso, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaPesquisaAproveitamentoCursoIndividual(arquivoRelatorio, idEmpresa, sessionId, idPesquisa, idCurso, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaPesquisaAproveitamentoCursoIndividual(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idCurso, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaPesquisaAproveitamentoCursoIndividual(arquivoRelatorio, idEmpresa, idPesquisa, idCurso, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaPerguntasPorPesquisa(String arquivoRelatorio,
			Integer idEmpresa, String sessionId, Integer idPesquisa) throws ServiceException {
		try {
			return rpDAO.gerarListaPerguntasPorPesquisa(arquivoRelatorio, idEmpresa, sessionId, idPesquisa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaPerguntasPorPesquisa(String arquivoRelatorio,
			Integer idEmpresa, Integer idPesquisa) throws ServiceException {
		try {
			return rpDAO.abrirListaPerguntasPorPesquisa(arquivoRelatorio, idEmpresa, idPesquisa);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaEstatisticaPerguntasPorPesquisaGeral(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaEstatisticaPerguntasPorPesquisaGeral(arquivoRelatorio, idEmpresa, sessionId, idPesquisa, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaEstatisticaPerguntasPorPesquisaGeral(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaEstatisticaPerguntasPorPesquisaGeral(arquivoRelatorio, idEmpresa, idPesquisa, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaEstatisticaPerguntasPorPesquisaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idEscolaridade, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaEstatisticaPerguntasPorPesquisaEscolaridade(arquivoRelatorio, idEmpresa, sessionId, idPesquisa, idEscolaridade, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaEstatisticaPerguntasPorPesquisaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idEscolaridade, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaEstatisticaPerguntasPorPesquisaEscolaridade(arquivoRelatorio, idEmpresa, idPesquisa, idEscolaridade, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaEstatisticaPerguntasPorPesquisaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idInstituicao, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaEstatisticaPerguntasPorPesquisaInstituicao(arquivoRelatorio, idEmpresa, sessionId, idPesquisa, idInstituicao, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaEstatisticaPerguntasPorPesquisaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idInstituicao, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaEstatisticaPerguntasPorPesquisaInstituicao(arquivoRelatorio, idEmpresa, idPesquisa, idInstituicao, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaEstatisticaPerguntasPorPesquisaCurso(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idCurso, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaEstatisticaPerguntasPorPesquisaCurso(arquivoRelatorio, idEmpresa, sessionId, idPesquisa, idCurso, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaEstatisticaPerguntasPorPesquisaCurso(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idCurso, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaEstatisticaPerguntasPorPesquisaCurso(arquivoRelatorio, idEmpresa, idPesquisa, idCurso, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarEstatisticaPerguntaPorPesquisaGeral(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarEstatisticaPerguntaPorPesquisaGeral(arquivoRelatorio, idEmpresa, sessionId, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirEstatisticaPerguntaPorPesquisaGeral(
			String arquivoRelatorio, Integer idEmpresa, Integer idPesquisa,
			Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirEstatisticaPerguntaPorPesquisaGeral(arquivoRelatorio, idEmpresa, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarEstatisticaPerguntaPorPesquisaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idEscolaridade, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarEstatisticaPerguntaPorPesquisaEscolaridade(arquivoRelatorio, idEmpresa, sessionId, idEscolaridade, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirEstatisticaPerguntaPorPesquisaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, Integer idEscolaridade,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirEstatisticaPerguntaPorPesquisaEscolaridade(arquivoRelatorio, idEmpresa, idEscolaridade, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarEstatisticaPerguntaPorPesquisaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idInstituicao, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarEstatisticaPerguntaPorPesquisaInstituicao(arquivoRelatorio, idEmpresa, sessionId, idInstituicao, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirEstatisticaPerguntaPorPesquisaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, Integer idInstituicao,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirEstatisticaPerguntaPorPesquisaInstituicao(arquivoRelatorio, idEmpresa, idInstituicao, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarEstatisticaPerguntaPorPesquisaCurso(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idCurso, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarEstatisticaPerguntaPorPesquisaCurso(arquivoRelatorio, idEmpresa, sessionId, idCurso, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirEstatisticaPerguntaPorPesquisaCurso(
			String arquivoRelatorio, Integer idEmpresa, Integer idCurso,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirEstatisticaPerguntaPorPesquisaCurso(arquivoRelatorio, idEmpresa, idCurso, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatosPorPerguntaGeral(String arquivoRelatorio,
			Integer idEmpresa, String sessionId, String resposta,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaCandidatosPorPerguntaGeral(arquivoRelatorio, idEmpresa, sessionId, resposta, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatosPorPerguntaGeral(String arquivoRelatorio,
			Integer idEmpresa, String resposta, Integer idPesquisa,
			Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaCandidatosPorPerguntaGeral(arquivoRelatorio, idEmpresa, resposta, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatosPorPerguntaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idEscolaridade, String resposta, Integer idPesquisa,
			Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaCandidatosPorPerguntaEscolaridade(arquivoRelatorio, idEmpresa, sessionId, idEscolaridade, resposta, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatosPorPerguntaEscolaridade(
			String arquivoRelatorio, Integer idEmpresa, Integer idEscolaridade,
			String resposta, Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaCandidatosPorPerguntaEscolaridade(arquivoRelatorio, idEmpresa, idEscolaridade, resposta, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatosPorPerguntaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, String sessionId,
			Integer idInstituicao, String resposta, Integer idPesquisa,
			Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaCandidatosPorPerguntaInstituicao(arquivoRelatorio, idEmpresa, sessionId, idInstituicao, resposta, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatosPorPerguntaInstituicao(
			String arquivoRelatorio, Integer idEmpresa, Integer idInstituicao,
			String resposta, Integer idPesquisa, Integer idPergunta, 
			Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaCandidatosPorPerguntaInstituicao(arquivoRelatorio, idEmpresa, idInstituicao, resposta, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public String gerarListaCandidatosPorPerguntaCurso(String arquivoRelatorio,
			Integer idEmpresa, String sessionId, Integer idCurso,
			String resposta, Integer idPesquisa, Integer idPergunta,
			Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.gerarListaCandidatosPorPerguntaCurso(arquivoRelatorio, idEmpresa, sessionId, idCurso, resposta, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public byte[] abrirListaCandidatosPorPerguntaCurso(String arquivoRelatorio,
			Integer idEmpresa, Integer idCurso, String resposta,
			Integer idPesquisa, Integer idPergunta, Integer idStatusCandidato) throws ServiceException {
		try {
			return rpDAO.abrirListaCandidatosPorPerguntaCurso(arquivoRelatorio, idEmpresa, idCurso, resposta, idPesquisa, idPergunta, idStatusCandidato);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
