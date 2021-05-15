package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.SMSMarketingAlternativa;
import unipesquisas.model.entity.StatusCandidato;

public class SMSMarketingAlternativaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (SMSMarketingAlternativa smsma) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO smsmarketingalternativa (idsmsmarketing, idpergunta, alternativa, " +
						"idpesquisa, data, idstatuscandidato) " +
						"VALUES (?, ?, ?, ?, ?, ?)", new String[] { "idsmsmarketingalternativa" });
				stmt.setInt(1, smsma.getSmsmarketing().getIdsmsmarketing());
				stmt.setInt(2, smsma.getPergunta().getIdpergunta());
				stmt.setString(3, smsma.getAlternativa());
				stmt.setInt(4, smsma.getPesquisa().getIdpesquisa());
				stmt.setDate(5, convertDate(smsma.getData()));
				//if(smsma.getStatuscandidato().getIdstatuscandidato() == 0){
				//	stmt.setNull(6, java.sql.Types.INTEGER);
				//}else{
					stmt.setInt(6, smsma.getStatuscandidato().getIdstatuscandidato());
				//}
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				smsma.setIdsmsmarketingalternativa(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(SMSMarketingAlternativa smsma, ResultSet rs) throws DAOException {
		try {
			smsma.setIdsmsmarketingalternativa(rs.getInt("idsmsmarketingalternativa"));
			smsma.getSmsmarketing().setIdsmsmarketing(rs.getInt("idsmsmarketing"));
			smsma.getSmsmarketing().setAssunto(rs.getString("assunto"));
			smsma.getSmsmarketing().setMensagem(rs.getString("mensagem"));
			smsma.getSmsmarketing().setData(rs.getDate("data"));
			smsma.getSmsmarketing().setStatus(rs.getInt("status"));
			smsma.getSmsmarketing().getEmpresa().setIdempresa(rs.getInt("idempresa"));
			smsma.getPergunta().setIdpergunta(rs.getInt("idpergunta"));
			smsma.getPergunta().setPergunta(rs.getString("pergunta"));
			smsma.getPesquisa().setIdpesquisa(rs.getInt("idpesquisa"));
			smsma.getPesquisa().setTitulo(rs.getString("titulo"));
			smsma.setAlternativa(rs.getString("alternativa"));
			smsma.setData(rs.getDate("datapublicacao"));
			if(rs.getString("statuscandidato") != null){
				smsma.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				smsma.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public SMSMarketingAlternativa carregar(Integer idSMSMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsma.idsmsmarketingalternativa, smsma.idsmsmarketing, smsma.idpergunta, " +
						"smsm.assunto, p.pergunta, smsm.idsmsmarketing, dm.mensagem, smsm.data, smsm.idempresa, smsm.status, " +
						"smsma.alternativa, pes.idpesquisa, pes.titulo, smsma.data AS datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingalternativa smsma " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsma.idsmsmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=smsma.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=smsma.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsma.idstatuscandidato " +
						"WHERE smsma.idsmsmarketing = ?");
				stmt.setInt(1, idSMSMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				SMSMarketingAlternativa smsma = new SMSMarketingAlternativa();
				smsma.setPergunta(new Pergunta());
				smsma.setSmsmarketing(new SMSMarketing());
				smsma.setPesquisa(new Pesquisa());
				smsma.getSmsmarketing().setEmpresa(new Empresa());
				smsma.setStatuscandidato(new StatusCandidato());
				preencher(smsma, rs);

				return smsma;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingAlternativa> listaSMSMarketingAlternativa(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsma.idsmsmarketingalternativa, smsma.idsmsmarketing, " +
						"smsma.idpergunta, smsm.assunto, p.pergunta, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsma.alternativa, pes.idpesquisa, pes.titulo, " +
						"smsma.data AS datapublicacao, sc.status as statuscandidato  " +
						"FROM smsmarketingalternativa smsma " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsma.idsmsmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=smsma.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=smsma.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsma.idstatuscandidato " +
						"WHERE smsm.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<SMSMarketingAlternativa> listaSMSMA = new ArrayList<SMSMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingAlternativa smsma = new SMSMarketingAlternativa();
					smsma.setSmsmarketing(new SMSMarketing());
					smsma.setPergunta(new Pergunta());
					smsma.setPesquisa(new Pesquisa());
					smsma.getSmsmarketing().setEmpresa(new Empresa());
					smsma.setStatuscandidato(new StatusCandidato());
					preencher(smsma, rs);
					listaSMSMA.add(smsma);
				}

				return listaSMSMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean smsPublicado(Integer idSMSMarketing, Integer idPergunta, 
			Integer idPesquisa, String alternativa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT * " +
						"FROM smsmarketingalternativa " +
						"WHERE idsmsmarketing = ? AND idpergunta = ? AND idpesquisa = ? AND alternativa = ?");
				stmt.setInt(1, idSMSMarketing);
				stmt.setInt(2, idPergunta);
				stmt.setInt(3, idPesquisa);
				stmt.setString(4, alternativa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return false;
				}
				
				return true;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingAlternativa> listaPublicacaoSMSMarketingPorIdSMS(
			int idSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsma.idsmsmarketingalternativa, smsma.idsmsmarketing, " +
						"smsma.idpergunta, smsm.assunto, p.pergunta, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsma.alternativa, pes.idpesquisa, pes.titulo, " +
						"smsma.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM smsmarketingalternativa smsma " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsma.idsmsmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=smsma.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=smsma.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsma.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND smsm.idsmsmarketing = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idSMS);
				
				
				List<SMSMarketingAlternativa> listaSMSMA = new ArrayList<SMSMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingAlternativa smsma = new SMSMarketingAlternativa();
					smsma.setSmsmarketing(new SMSMarketing());
					smsma.setPergunta(new Pergunta());
					smsma.setPesquisa(new Pesquisa());
					smsma.getSmsmarketing().setEmpresa(new Empresa());
					smsma.setStatuscandidato(new StatusCandidato());
					preencher(smsma, rs);
					listaSMSMA.add(smsma);
				}

				return listaSMSMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingAlternativa> listaPublicacaoSMSMarketingPorIdPergunta(
			int idPergunta, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsma.idsmsmarketingalternativa, smsma.idsmsmarketing, " +
						"smsma.idpergunta, smsm.assunto, p.pergunta, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsma.alternativa, pes.idpesquisa, pes.titulo, " +
						"smsma.data AS datapublicacao, sc.status as statuscandidato  " +
						"FROM smsmarketingalternativa smsma " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsma.idsmsmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=smsma.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=smsma.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsma.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND p.idpergunta = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idPergunta);
				
				
				List<SMSMarketingAlternativa> listaSMSMA = new ArrayList<SMSMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingAlternativa smsma = new SMSMarketingAlternativa();
					smsma.setSmsmarketing(new SMSMarketing());
					smsma.setPergunta(new Pergunta());
					smsma.setPesquisa(new Pesquisa());
					smsma.getSmsmarketing().setEmpresa(new Empresa());
					smsma.setStatuscandidato(new StatusCandidato());
					preencher(smsma, rs);
					listaSMSMA.add(smsma);
				}

				return listaSMSMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingAlternativa> listaPublicacaoSMSMarketingPorPergunta(
			String pergunta, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsma.idsmsmarketingalternativa, smsma.idsmsmarketing, " +
						"smsma.idpergunta, smsm.assunto, p.pergunta, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsma.alternativa, pes.idpesquisa, pes.titulo, " +
						"smsma.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM smsmarketingalternativa smsma " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsma.idsmsmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=smsma.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=smsma.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsma.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND p.pergunta like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+pergunta+"%");
				
				
				List<SMSMarketingAlternativa> listaSMSMA = new ArrayList<SMSMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingAlternativa smsma = new SMSMarketingAlternativa();
					smsma.setSmsmarketing(new SMSMarketing());
					smsma.setPergunta(new Pergunta());
					smsma.setPesquisa(new Pesquisa());
					smsma.getSmsmarketing().setEmpresa(new Empresa());
					smsma.setStatuscandidato(new StatusCandidato());
					preencher(smsma, rs);
					listaSMSMA.add(smsma);
				}

				return listaSMSMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingAlternativa> listaPublicacaoSMSMarketingPorAssunto(
			String assuntoSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsma.idsmsmarketingalternativa, smsma.idsmsmarketing, " +
						"smsma.idpergunta, smsm.assunto, p.pergunta, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsma.alternativa, pes.idpesquisa, pes.titulo, " +
						"smsma.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM smsmarketingalternativa smsma " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsma.idsmsmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=smsma.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=smsma.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsma.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND smsm.assunto like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+assuntoSMS+"%");
				
				
				List<SMSMarketingAlternativa> listaSMSMA = new ArrayList<SMSMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingAlternativa smsma = new SMSMarketingAlternativa();
					smsma.setSmsmarketing(new SMSMarketing());
					smsma.setPergunta(new Pergunta());
					smsma.setPesquisa(new Pesquisa());
					smsma.getSmsmarketing().setEmpresa(new Empresa());
					smsma.setStatuscandidato(new StatusCandidato());
					preencher(smsma, rs);
					listaSMSMA.add(smsma);
				}

				return listaSMSMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingAlternativa> listaPublicacaoSMSMarketingPorIdPesquisa(
			int idPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsma.idsmsmarketingalternativa, smsma.idsmsmarketing, " +
						"smsma.idpergunta, smsm.assunto, p.pergunta, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsma.alternativa, pes.idpesquisa, pes.titulo, " +
						"smsma.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM smsmarketingalternativa smsma " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsma.idsmsmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=smsma.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=smsma.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsma.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND pes.idpesquisa = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idPesquisa);
				
				
				List<SMSMarketingAlternativa> listaSMSMA = new ArrayList<SMSMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingAlternativa smsma = new SMSMarketingAlternativa();
					smsma.setSmsmarketing(new SMSMarketing());
					smsma.setPergunta(new Pergunta());
					smsma.setPesquisa(new Pesquisa());
					smsma.getSmsmarketing().setEmpresa(new Empresa());
					smsma.setStatuscandidato(new StatusCandidato());
					preencher(smsma, rs);
					listaSMSMA.add(smsma);
				}

				return listaSMSMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingAlternativa> listaPublicacaoSMSMarketingPorPesquisa(
			String tituloPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsma.idsmsmarketingalternativa, smsma.idsmsmarketing, " +
						"smsma.idpergunta, smsm.assunto, p.pergunta, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsma.alternativa, pes.idpesquisa, pes.titulo, " +
						"smsma.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM smsmarketingalternativa smsma " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsma.idsmsmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=smsma.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=smsma.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsma.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND pes.titulo like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+tituloPesquisa+"%");
				
				
				List<SMSMarketingAlternativa> listaSMSMA = new ArrayList<SMSMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingAlternativa smsma = new SMSMarketingAlternativa();
					smsma.setSmsmarketing(new SMSMarketing());
					smsma.setPergunta(new Pergunta());
					smsma.setPesquisa(new Pesquisa());
					smsma.getSmsmarketing().setEmpresa(new Empresa());
					smsma.setStatuscandidato(new StatusCandidato());
					preencher(smsma, rs);
					listaSMSMA.add(smsma);
				}

				return listaSMSMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
