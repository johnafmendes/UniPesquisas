package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.SMSMarketingEscolaridade;
import unipesquisas.model.entity.StatusCandidato;

public class SMSMarketingEscolaridadeDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (SMSMarketingEscolaridade smsme) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO smsmarketingescolaridade (idsmsmarketing, idescolaridade, data, " +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?)", new String[] { "idsmsmarketingescolaridade" });
				stmt.setInt(1, smsme.getSmsmarketing().getIdsmsmarketing());
				stmt.setInt(2, smsme.getEscolaridade().getIdescolaridade());
				stmt.setDate(3, convertDate(smsme.getData()));
				//if(smsme.getStatuscandidato().getIdstatuscandidato() == 0){
				//	stmt.setNull(4, java.sql.Types.INTEGER);
				//}else{
					stmt.setInt(4, smsme.getStatuscandidato().getIdstatuscandidato());
				//}
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				smsme.setIdsmsmarketingescolaridade(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(SMSMarketingEscolaridade smsme, ResultSet rs) throws DAOException {
		try {
			smsme.setIdsmsmarketingescolaridade(rs.getInt("idsmsmarketingescolaridade"));
			smsme.getSmsmarketing().setIdsmsmarketing(rs.getInt("idsmsmarketing"));
			smsme.getSmsmarketing().setAssunto(rs.getString("assunto"));
			smsme.getSmsmarketing().setMensagem(rs.getString("mensagem"));
			smsme.getSmsmarketing().setData(rs.getDate("data"));
			smsme.getSmsmarketing().setStatus(rs.getInt("status"));
			smsme.getSmsmarketing().getEmpresa().setIdempresa(rs.getInt("idempresa"));
			smsme.getEscolaridade().setIdescolaridade(rs.getInt("idescolaridade"));
			smsme.getEscolaridade().setEscolaridade(rs.getString("escolaridade"));
			smsme.setData(rs.getDate("datapublicacao"));
			if(rs.getString("statuscandidato") != null){
				smsme.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				smsme.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public SMSMarketingEscolaridade carregar(Integer idSMSMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsme.idsmsmarketingescolaridade, smsme.idsmsmarketing, smsme.idescolaridade, " +
						"smsm.assunto, e.escolaridade, smsm.idsmsmarketing, dm.mensagem, smsm.data, smsm.idempresa, smsm.status, " +
						"smsme.data as datapublicacao, sc.status as statuscandidato " +
						"FROM smsmarketingescolaridade smsme " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsme.idsmsmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=smsme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsme.idstatuscandidato " +
						"WHERE smsme.idsmsmarketing = ?");
				stmt.setInt(1, idSMSMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				SMSMarketingEscolaridade smsme = new SMSMarketingEscolaridade();
				smsme.setEscolaridade(new Escolaridade());
				smsme.setSmsmarketing(new SMSMarketing());
				smsme.getSmsmarketing().setEmpresa(new Empresa());
				smsme.setStatuscandidato(new StatusCandidato());
				preencher(smsme, rs);

				return smsme;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listaSMSMarketingEscolaridade(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsme.idsmsmarketingescolaridade, smsme.idsmsmarketing, " +
						"smsme.idescolaridade, smsm.assunto, e.escolaridade, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsme.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingescolaridade smsme " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsme.idsmsmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=smsme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsme.idstatuscandidato " +
						"WHERE smsm.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<SMSMarketingEscolaridade> listaSMSME = new ArrayList<SMSMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingEscolaridade smsme = new SMSMarketingEscolaridade();
					smsme.setSmsmarketing(new SMSMarketing());
					smsme.setEscolaridade(new Escolaridade());
					smsme.getSmsmarketing().setEmpresa(new Empresa());
					smsme.setStatuscandidato(new StatusCandidato());
					preencher(smsme, rs);
					listaSMSME.add(smsme);
				}

				return listaSMSME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean smsPublicado(Integer idSMSMarketing, Integer idEscolaridade) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT * " +
						"FROM smsmarketingescolaridade " +
						"WHERE idsmsmarketing = ? AND idescolaridade = ?");
				stmt.setInt(1, idSMSMarketing);
				stmt.setInt(2, idEscolaridade);
				
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

	public List<SMSMarketingEscolaridade> listaPublicacaoSMSMarketingPorIdSMS(
			int idSMS, int idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsme.idsmsmarketingescolaridade, smsme.idsmsmarketing, " +
						"smsme.idescolaridade, smsm.assunto, e.escolaridade, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsme.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingescolaridade smsme " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsme.idsmsmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=smsme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsme.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND smsm.idsmsmarketing = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idSMS);
				
				
				List<SMSMarketingEscolaridade> listaSMSME = new ArrayList<SMSMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingEscolaridade smsme = new SMSMarketingEscolaridade();
					smsme.setSmsmarketing(new SMSMarketing());
					smsme.setEscolaridade(new Escolaridade());
					smsme.getSmsmarketing().setEmpresa(new Empresa());
					smsme.setStatuscandidato(new StatusCandidato());
					preencher(smsme, rs);
					listaSMSME.add(smsme);
				}

				return listaSMSME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listaPublicacaoSMSMarketingPorIdEscolaridade(
			int idEscolaridade, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsme.idsmsmarketingescolaridade, smsme.idsmsmarketing, " +
						"smsme.idescolaridade, smsm.assunto, e.escolaridade, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsme.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingescolaridade smsme " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsme.idsmsmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=smsme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsme.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND e.idescolaridade = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEscolaridade);
				
				
				List<SMSMarketingEscolaridade> listaSMSME = new ArrayList<SMSMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingEscolaridade smsme = new SMSMarketingEscolaridade();
					smsme.setSmsmarketing(new SMSMarketing());
					smsme.setEscolaridade(new Escolaridade());
					smsme.getSmsmarketing().setEmpresa(new Empresa());
					smsme.setStatuscandidato(new StatusCandidato());
					preencher(smsme, rs);
					listaSMSME.add(smsme);
				}

				return listaSMSME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listaPublicacaoSMSMarketingPorEscolaridade(
			String escolaridade, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsme.idsmsmarketingescolaridade, smsme.idsmsmarketing, " +
						"smsme.idescolaridade, smsm.assunto, e.escolaridade, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsme.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingescolaridade smsme " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsme.idsmsmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=smsme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsme.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND e.escolaridade like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%" + escolaridade + "%");
				
				
				List<SMSMarketingEscolaridade> listaSMSME = new ArrayList<SMSMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingEscolaridade smsme = new SMSMarketingEscolaridade();
					smsme.setSmsmarketing(new SMSMarketing());
					smsme.setEscolaridade(new Escolaridade());
					smsme.getSmsmarketing().setEmpresa(new Empresa());
					smsme.setStatuscandidato(new StatusCandidato());
					preencher(smsme, rs);
					listaSMSME.add(smsme);
				}

				return listaSMSME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingEscolaridade> listaPublicacaoSMSMarketingPorAssunto(
			String assuntoSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsme.idsmsmarketingescolaridade, smsme.idsmsmarketing, " +
						"smsme.idescolaridade, smsm.assunto, e.escolaridade, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsme.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingescolaridade smsme " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsme.idsmsmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=smsme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsme.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND smsm.assunto like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%" + assuntoSMS + "%");
				
				
				List<SMSMarketingEscolaridade> listaSMSME = new ArrayList<SMSMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingEscolaridade smsme = new SMSMarketingEscolaridade();
					smsme.setSmsmarketing(new SMSMarketing());
					smsme.setEscolaridade(new Escolaridade());
					smsme.getSmsmarketing().setEmpresa(new Empresa());
					smsme.setStatuscandidato(new StatusCandidato());
					preencher(smsme, rs);
					listaSMSME.add(smsme);
				}

				return listaSMSME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
