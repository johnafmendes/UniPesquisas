package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.SMSMarketing;
import unipesquisas.model.entity.Usuario;

public class SMSMarketingDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (SMSMarketing smsm) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO smsmarketing (assunto, mensagem, data, idusuario, idempresa, status) " +
						"VALUES (?, ?, ?, ?, ?, ?)", new String[] { "idemailmarketing" });
				stmt.setString(1, smsm.getAssunto());
				stmt.setString(2, smsm.getMensagem());
				stmt.setDate(3, convertDate(smsm.getData()));
				stmt.setInt(4, smsm.getUsuario().getIdusuario());
				stmt.setInt(5, smsm.getEmpresa().getIdempresa());
				stmt.setInt(6, smsm.getStatus());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				smsm.setIdsmsmarketing(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(SMSMarketing smsm) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE smsmarketing SET assunto = ?, mensagem = ?, data = ?, idusuario = ?, " +
						"idempresa = ?, status = ? " +
						"WHERE idsmsmarketing = ?");
				stmt.setString(1, smsm.getAssunto());
				stmt.setString(2, smsm.getMensagem());
				stmt.setDate(3, convertDate(smsm.getData()));
				stmt.setInt(4, smsm.getUsuario().getIdusuario());
				stmt.setInt(5, smsm.getEmpresa().getIdempresa());
				stmt.setInt(6, smsm.getStatus());
				stmt.setInt(7, smsm.getIdsmsmarketing());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar smsmarketing = " + smsm.getIdsmsmarketing());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idSMSMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM smsmarketing WHERE idsmsmarketing = ?");
				stmt.setInt(1, idSMSMarketing);

				if (stmt.executeUpdate() != 1) {
//					throw new DAOException("Erro ao excluir candidato ID = " + idCandidato);
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

	public List<SMSMarketing> listarSMSMarketing(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT s.idsmsmarketing, s.assunto, s.mensagem, s.data, s.idempresa, " +
						"s.idusuario, s.status, u.nome " +
						"FROM smsmarketing s " +
						"INNER JOIN usuario u ON s.idusuario=u.idusuario " +
						"WHERE s.idempresa = ? " +
						"ORDER BY s.data desc");
				stmt.setInt(1, idEmpresa);
				
				List<SMSMarketing> listaSMSMarketing = new ArrayList<SMSMarketing>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketing smsm = new SMSMarketing();
					smsm.setUsuario(new Usuario());
					smsm.setEmpresa(new Empresa());
					preencher(smsm, rs);
					listaSMSMarketing.add(smsm);
				}

				return listaSMSMarketing;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(SMSMarketing smsm, ResultSet rs) throws DAOException {
		try {
			smsm.setIdsmsmarketing(rs.getInt("idsmsmarketing"));
			smsm.setAssunto(rs.getString("assunto"));
			smsm.setMensagem(rs.getString("mensagem"));
			smsm.setData(rs.getDate("data"));
			smsm.getUsuario().setIdusuario(rs.getInt("idusuario"));
			smsm.getUsuario().setNome(rs.getString("nome"));
			smsm.getEmpresa().setIdempresa(rs.getInt("idempresa"));
			smsm.setStatus(rs.getInt("status"));
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public SMSMarketing carregar(Integer idSMSMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT s.idsmsmarketing, s.assunto, s.mensagem, s.data, s.idempresa, " +
						"s.idusuario, s.status, u.nome " +
						"FROM smsmarketing s " +
						"INNER JOIN usuario u ON s.idusuario=u.idusuario " +
						"WHERE s.idsmsmarketing = ?");
				stmt.setInt(1, idSMSMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				SMSMarketing smsm = new SMSMarketing();
				smsm.setUsuario(new Usuario());
				smsm.setEmpresa(new Empresa());
				preencher(smsm, rs);
				return smsm;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketing> listarSMSMarketingPorAssunto(String assuntoSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT s.idsmsmarketing, s.assunto, s.mensagem, s.data, s.idempresa, " +
						"s.idusuario, s.status, u.nome " +
						"FROM smsmarketing s " +
						"INNER JOIN usuario u ON s.idusuario=u.idusuario " +
						"WHERE s.assunto like ? AND s.idempresa = ? " +
						"ORDER BY s.assunto");
				stmt.setString(1, "%"+assuntoSMS+"%");
				stmt.setInt(2, idEmpresa);
				
				List<SMSMarketing> listaSMS = new ArrayList<SMSMarketing>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketing smsm = new SMSMarketing();
					smsm.setUsuario(new Usuario());
					smsm.setEmpresa(new Empresa());
					preencher(smsm, rs);
					listaSMS.add(smsm);
				}

				return listaSMS;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<SMSMarketing> listarSMSMarketingPorId(int idSMS, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT s.idsmsmarketing, s.assunto, s.mensagem, s.data, s.idempresa, " +
						"s.idusuario, s.status, u.nome " +
						"FROM smsmarketing s " +
						"INNER JOIN usuario u ON s.idusuario=u.idusuario " +
						"WHERE s.idsmsmarketing = ? AND s.idempresa = ? " +
						"ORDER BY s.assunto");
				stmt.setInt(1, idSMS);
				stmt.setInt(2, idEmpresa);
				
				List<SMSMarketing> listaSMS = new ArrayList<SMSMarketing>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					SMSMarketing smsm = new SMSMarketing();
					smsm.setUsuario(new Usuario());
					smsm.setEmpresa(new Empresa());
					preencher(smsm, rs);
					listaSMS.add(smsm);
				}

				return listaSMS;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}	
}
