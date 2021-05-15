package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.SMSMarketingInstituicao;
import unipesquisas.model.entity.StatusCandidato;

public class SMSMarketingInstituicaoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (SMSMarketingInstituicao smsmi) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO smsmarketinginstituicao (idsmsmarketing, idinstituicao, data, " +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?)", new String[] { "idsmsmarketinginstituicao" });
				stmt.setInt(1, smsmi.getSmsmarketing().getIdsmsmarketing());
				stmt.setInt(2, smsmi.getInstituicao().getIdinstituicao());
				stmt.setDate(3, convertDate(smsmi.getData()));
				//if(smsmi.getStatuscandidato().getIdstatuscandidato() == 0){
				//	stmt.setNull(4, java.sql.Types.INTEGER);
				//}else{
					stmt.setInt(4, smsmi.getStatuscandidato().getIdstatuscandidato());
				//}
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				smsmi.setIdsmsmarketinginstituicao(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(SMSMarketingInstituicao smsmi, ResultSet rs) throws DAOException {
		try {
			smsmi.setIdsmsmarketinginstituicao(rs.getInt("idsmsmarketinginstituicao"));
			smsmi.getSmsmarketing().setIdsmsmarketing(rs.getInt("idsmsmarketing"));
			smsmi.getSmsmarketing().setAssunto(rs.getString("assunto"));
			smsmi.getSmsmarketing().setMensagem(rs.getString("mensagem"));
			smsmi.getSmsmarketing().setData(rs.getDate("data"));
			smsmi.getSmsmarketing().setStatus(rs.getInt("status"));
			smsmi.getSmsmarketing().getEmpresa().setIdempresa(rs.getInt("idempresa"));
			smsmi.getInstituicao().setIdinstituicao(rs.getInt("idinstituicao"));
			smsmi.getInstituicao().setInstituicao(rs.getString("instituicao"));
			smsmi.setData(rs.getDate("datapublicacao"));
			if(rs.getString("statuscandidato") != null){
				smsmi.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				smsmi.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public SMSMarketingInstituicao carregar(Integer idSMSMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmi.idsmsmarketinginstituicao, smsmi.idsmsmarketing, smsmi.idinstituicao, " +
						"smsm.assunto, i.instituicao, smsm.idsmsmarketing, dm.mensagem, smsm.data, smsm.idempresa, smsm.status, " +
						"smsmi.data as datapublicacao, sc.status as statuscandidato " +
						"FROM smsmarketinginstituicao smsmi " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmi.idsmsmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=smsmi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmi.idstatuscandidato " +
						"WHERE smsmi.idsmsmarketing = ?");
				stmt.setInt(1, idSMSMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				SMSMarketingInstituicao smsmi = new SMSMarketingInstituicao();
				smsmi.setInstituicao(new Instituicao());
				smsmi.setSmsmarketing(new SMSMarketing());
				smsmi.getSmsmarketing().setEmpresa(new Empresa());
				smsmi.setStatuscandidato(new StatusCandidato());
				preencher(smsmi, rs);

				return smsmi;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingInstituicao> listaSMSMarketingInstituicao(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmi.idsmsmarketinginstituicao, smsmi.idsmsmarketing, " +
						"smsmi.idinstituicao, smsm.assunto, i.instituicao, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmi.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketinginstituicao smsmi " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmi.idsmsmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=smsmi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmi.idstatuscandidato " +
						"WHERE smsm.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<SMSMarketingInstituicao> listaSMSMI = new ArrayList<SMSMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingInstituicao smsmi = new SMSMarketingInstituicao();
					smsmi.setSmsmarketing(new SMSMarketing());
					smsmi.setInstituicao(new Instituicao());
					smsmi.getSmsmarketing().setEmpresa(new Empresa());
					smsmi.setStatuscandidato(new StatusCandidato());
					preencher(smsmi, rs);
					listaSMSMI.add(smsmi);
				}

				return listaSMSMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean smsPublicado(Integer idSMSMarketing, Integer idInstituicao) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT * " +
						"FROM smsmarketinginstituicao " +
						"WHERE idsmsmarketing = ? AND idinstituicao = ?");
				stmt.setInt(1, idSMSMarketing);
				stmt.setInt(2, idInstituicao);
				
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

	public List<SMSMarketingInstituicao> listaPublicacaoSMSMarketingPorIdSMS(
			int idSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmi.idsmsmarketinginstituicao, smsmi.idsmsmarketing, " +
						"smsmi.idinstituicao, smsm.assunto, i.instituicao, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmi.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketinginstituicao smsmi " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmi.idsmsmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=smsmi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmi.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND smsm.idsmsmarketing = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idSMS);
				
				
				List<SMSMarketingInstituicao> listaSMSMI = new ArrayList<SMSMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingInstituicao smsmi = new SMSMarketingInstituicao();
					smsmi.setSmsmarketing(new SMSMarketing());
					smsmi.setInstituicao(new Instituicao());
					smsmi.getSmsmarketing().setEmpresa(new Empresa());
					smsmi.setStatuscandidato(new StatusCandidato());
					preencher(smsmi, rs);
					listaSMSMI.add(smsmi);
				}

				return listaSMSMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingInstituicao> listaPublicacaoSMSMarketingPorIdInstituicao(
			int idInstituicao, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmi.idsmsmarketinginstituicao, smsmi.idsmsmarketing, " +
						"smsmi.idinstituicao, smsm.assunto, i.instituicao, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmi.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketinginstituicao smsmi " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmi.idsmsmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=smsmi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmi.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND i.idinstituicao = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idInstituicao);
				
				
				List<SMSMarketingInstituicao> listaSMSMI = new ArrayList<SMSMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingInstituicao smsmi = new SMSMarketingInstituicao();
					smsmi.setSmsmarketing(new SMSMarketing());
					smsmi.setInstituicao(new Instituicao());
					smsmi.getSmsmarketing().setEmpresa(new Empresa());
					smsmi.setStatuscandidato(new StatusCandidato());
					preencher(smsmi, rs);
					listaSMSMI.add(smsmi);
				}

				return listaSMSMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingInstituicao> listaPublicacaoSMSMarketingPorInstituicao(
			String instituicao, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmi.idsmsmarketinginstituicao, smsmi.idsmsmarketing, " +
						"smsmi.idinstituicao, smsm.assunto, i.instituicao, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmi.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketinginstituicao smsmi " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmi.idsmsmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=smsmi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmi.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND i.instituicao like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+instituicao+"%");
				
				
				List<SMSMarketingInstituicao> listaSMSMI = new ArrayList<SMSMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingInstituicao smsmi = new SMSMarketingInstituicao();
					smsmi.setSmsmarketing(new SMSMarketing());
					smsmi.setInstituicao(new Instituicao());
					smsmi.getSmsmarketing().setEmpresa(new Empresa());
					smsmi.setStatuscandidato(new StatusCandidato());
					preencher(smsmi, rs);
					listaSMSMI.add(smsmi);
				}

				return listaSMSMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingInstituicao> listaPublicacaoSMSMarketingPorAssunto(
			String assuntoSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmi.idsmsmarketinginstituicao, smsmi.idsmsmarketing, " +
						"smsmi.idinstituicao, smsm.assunto, i.instituicao, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmi.data as datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketinginstituicao smsmi " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmi.idsmsmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=smsmi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmi.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND smsm.assunto like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+assuntoSMS+"%");
				
				
				List<SMSMarketingInstituicao> listaSMSMI = new ArrayList<SMSMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingInstituicao smsmi = new SMSMarketingInstituicao();
					smsmi.setSmsmarketing(new SMSMarketing());
					smsmi.setInstituicao(new Instituicao());
					smsmi.getSmsmarketing().setEmpresa(new Empresa());
					smsmi.setStatuscandidato(new StatusCandidato());
					preencher(smsmi, rs);
					listaSMSMI.add(smsmi);
				}

				return listaSMSMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
