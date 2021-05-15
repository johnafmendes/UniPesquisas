package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.SMSMarketingCurso;
import unipesquisas.model.entity.StatusCandidato;

public class SMSMarketingCursoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (SMSMarketingCurso smsmc) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO smsmarketingcurso (idsmsmarketing, idcurso, data," +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?)", new String[] { "idsmsmarketingcurso" });
				stmt.setInt(1, smsmc.getSmsmarketing().getIdsmsmarketing());
				stmt.setInt(2, smsmc.getCurso().getIdcurso());
				stmt.setDate(3, convertDate(smsmc.getData()));
				//if(smsmc.getStatuscandidato().getIdstatuscandidato() == 0){
				//	stmt.setNull(4, java.sql.Types.INTEGER);
				//}else{
					stmt.setInt(4, smsmc.getStatuscandidato().getIdstatuscandidato());
				//}
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				smsmc.setIdsmsmarketingcurso(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(SMSMarketingCurso smsmc, ResultSet rs) throws DAOException {
		try {
			smsmc.setIdsmsmarketingcurso(rs.getInt("idsmsmarketingcurso"));
			smsmc.getSmsmarketing().setIdsmsmarketing(rs.getInt("idsmsmarketing"));
			smsmc.getSmsmarketing().setAssunto(rs.getString("assunto"));
			smsmc.getSmsmarketing().setMensagem(rs.getString("mensagem"));
			smsmc.getSmsmarketing().setData(rs.getDate("data"));
			smsmc.getSmsmarketing().setStatus(rs.getInt("status"));
			smsmc.getSmsmarketing().getEmpresa().setIdempresa(rs.getInt("idempresa"));
			smsmc.getCurso().setIdcurso(rs.getInt("idcurso"));
			smsmc.getCurso().setCurso(rs.getString("curso"));
			smsmc.setData(rs.getDate("datapublicacao"));
			if(rs.getString("statuscandidato") != null){
				smsmc.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				smsmc.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public SMSMarketingCurso carregar(Integer idSMSMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmc.idsmsmarketingcurso, smsmc.idsmsmarketing, smsmc.idcurso, " +
						"smsm.assunto, c.curso, smsm.idsmsmarketing, dm.mensagem, smsm.data, smsm.idempresa, smsm.status, " +
						"smsmc.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM smsmarketingcurso smsmc " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmc.idsmsmarketing " +
						"INNER JOIN curso c ON c.idcurso=smsmc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmc.idstatuscandidato " +
						"WHERE smsmc.idsmsmarketing = ?");
				stmt.setInt(1, idSMSMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				SMSMarketingCurso smsmc = new SMSMarketingCurso();
				smsmc.setCurso(new Curso());
				smsmc.setSmsmarketing(new SMSMarketing());
				smsmc.getSmsmarketing().setEmpresa(new Empresa());
				smsmc.setStatuscandidato(new StatusCandidato());
				preencher(smsmc, rs);

				return smsmc;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingCurso> listaSMSMarketingCurso(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmc.idsmsmarketingcurso, smsmc.idsmsmarketing, " +
						"smsmc.idcurso, smsm.assunto, c.curso, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmc.data AS datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingcurso smsmc " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmc.idsmsmarketing " +
						"INNER JOIN curso c ON c.idcurso=smsmc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmc.idstatuscandidato " +
						"WHERE smsm.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<SMSMarketingCurso> listaSMSMC = new ArrayList<SMSMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingCurso smsmc = new SMSMarketingCurso();
					smsmc.setSmsmarketing(new SMSMarketing());
					smsmc.setCurso(new Curso());
					smsmc.getSmsmarketing().setEmpresa(new Empresa());
					smsmc.setStatuscandidato(new StatusCandidato());
					preencher(smsmc, rs);
					listaSMSMC.add(smsmc);
				}

				return listaSMSMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean smsPublicado(Integer idSMSMarketing, Integer idCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT * " +
						"FROM smsmarketingcurso " +
						"WHERE idsmsmarketing = ? AND idcurso = ?");
				stmt.setInt(1, idSMSMarketing);
				stmt.setInt(2, idCurso);
				
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

	public List<SMSMarketingCurso> listaPublicacaoSMSMarketingPorIdSMS(
			int idSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmc.idsmsmarketingcurso, smsmc.idsmsmarketing, " +
						"smsmc.idcurso, smsm.assunto, c.curso, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmc.data AS datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingcurso smsmc " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmc.idsmsmarketing " +
						"INNER JOIN curso c ON c.idcurso=smsmc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmc.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND smsm.idsmsmarketing = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idSMS);
				
				
				List<SMSMarketingCurso> listaSMSMC = new ArrayList<SMSMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingCurso smsmc = new SMSMarketingCurso();
					smsmc.setSmsmarketing(new SMSMarketing());
					smsmc.setCurso(new Curso());
					smsmc.getSmsmarketing().setEmpresa(new Empresa());
					smsmc.setStatuscandidato(new StatusCandidato());
					preencher(smsmc, rs);
					listaSMSMC.add(smsmc);
				}

				return listaSMSMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingCurso> listaPublicacaoSMSMarketingPorIdCurso(
			int idCurso, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmc.idsmsmarketingcurso, smsmc.idsmsmarketing, " +
						"smsmc.idcurso, smsm.assunto, c.curso, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmc.data AS datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingcurso smsmc " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmc.idsmsmarketing " +
						"INNER JOIN curso c ON c.idcurso=smsmc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmc.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND c.idcurso = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idCurso);
				
				
				List<SMSMarketingCurso> listaSMSMC = new ArrayList<SMSMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingCurso smsmc = new SMSMarketingCurso();
					smsmc.setSmsmarketing(new SMSMarketing());
					smsmc.setCurso(new Curso());
					smsmc.getSmsmarketing().setEmpresa(new Empresa());
					smsmc.setStatuscandidato(new StatusCandidato());
					preencher(smsmc, rs);
					listaSMSMC.add(smsmc);
				}

				return listaSMSMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingCurso> listaPublicacaoSMSMarketingPorCurso(
			String curso, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmc.idsmsmarketingcurso, smsmc.idsmsmarketing, " +
						"smsmc.idcurso, smsm.assunto, c.curso, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmc.data AS datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingcurso smsmc " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmc.idsmsmarketing " +
						"INNER JOIN curso c ON c.idcurso=smsmc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmc.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND c.curso like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+curso+"%");
				
				
				List<SMSMarketingCurso> listaSMSMC = new ArrayList<SMSMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingCurso smsmc = new SMSMarketingCurso();
					smsmc.setSmsmarketing(new SMSMarketing());
					smsmc.setCurso(new Curso());
					smsmc.getSmsmarketing().setEmpresa(new Empresa());
					smsmc.setStatuscandidato(new StatusCandidato());
					preencher(smsmc, rs);
					listaSMSMC.add(smsmc);
				}

				return listaSMSMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketingCurso> listaPublicacaoSMSMarketingPorAssunto(
			String assuntoSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT smsmc.idsmsmarketingcurso, smsmc.idsmsmarketing, " +
						"smsmc.idcurso, smsm.assunto, c.curso, smsm.idsmsmarketing, smsm.mensagem, " +
						"smsm.data, smsm.idempresa, smsm.status, smsmc.data AS datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM smsmarketingcurso smsmc " +
						"INNER JOIN smsmarketing smsm ON smsm.idsmsmarketing=smsmc.idsmsmarketing " +
						"INNER JOIN curso c ON c.idcurso=smsmc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=smsmc.idstatuscandidato " +
						"WHERE smsm.idempresa = ? AND smsm.assunto like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+assuntoSMS+"%");
				
				
				List<SMSMarketingCurso> listaSMSMC = new ArrayList<SMSMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketingCurso smsmc = new SMSMarketingCurso();
					smsmc.setSmsmarketing(new SMSMarketing());
					smsmc.setCurso(new Curso());
					smsmc.getSmsmarketing().setEmpresa(new Empresa());
					smsmc.setStatuscandidato(new StatusCandidato());
					preencher(smsmc, rs);
					listaSMSMC.add(smsmc);
				}

				return listaSMSMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
