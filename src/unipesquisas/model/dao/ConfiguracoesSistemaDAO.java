package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unipesquisas.model.entity.ConfiguracoesSistema;

public class ConfiguracoesSistemaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2551571499004957685L;

	public ConfiguracoesSistema getConfiguracaoEmail() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emailsmtp, emailfrom, emaillogin, emailsenha, emailsmtpporta, emailssl, " +
						"emailtls, emailautenticacaosmtp " +
						"FROM configuracoessistema " +
						"WHERE idconfiguracoessistema = 1");
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				ConfiguracoesSistema cs = new ConfiguracoesSistema();
				
				cs.setEmailsmtp(rs.getString("emailsmtp"));
				cs.setEmailfrom(rs.getString("emailfrom"));
				cs.setEmaillogin(rs.getString("emaillogin"));
				cs.setEmailsenha(rs.getString("emailsenha"));
				cs.setEmailsmtpporta(rs.getString("emailsmtpporta"));
				cs.setEmailssl(rs.getInt("emailssl"));
				cs.setEmailtls(rs.getInt("emailtls"));
				cs.setEmailautenticacaosmtp(rs.getInt("emailautenticacaosmtp"));
				return cs;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void salvar(ConfiguracoesSistema cs) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE configuracoessistema SET emailsmtp = ?, emailfrom = ?, " +
						"emaillogin = ?, emailsenha = ?, emailsmtpporta = ?, status = ?, emailssl = ?, emailtls = ?, " +
						"emailautenticacaosmtp = ? " +
						"WHERE idconfiguracoessistema = 1");
				stmt.setString(1, cs.getEmailsmtp());
				stmt.setString(2, cs.getEmailfrom());
				stmt.setString(3, cs.getEmaillogin());
				stmt.setString(4, cs.getEmailsenha());
				stmt.setString(5, cs.getEmailsmtpporta());
				stmt.setInt(6, cs.getStatus());
				stmt.setInt(7, cs.getEmailssl());
				stmt.setInt(8, cs.getEmailtls());
				stmt.setInt(9, cs.getEmailautenticacaosmtp());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar Configurações do Sistema ");
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public ConfiguracoesSistema carregar() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emailsmtp, emailfrom, emaillogin, emailsenha, emailsmtpporta, status, " +
						"emailautenticacaosmtp, emailssl, emailtls " +
						"FROM configuracoessistema ");
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				ConfiguracoesSistema cs = new ConfiguracoesSistema();
				cs.setEmailsmtp(rs.getString("emailsmtp"));
				cs.setEmailfrom(rs.getString("emailfrom"));
				cs.setEmaillogin(rs.getString("emaillogin"));
				cs.setEmailsenha(rs.getString("emailsenha"));
				cs.setEmailsmtpporta(rs.getString("emailsmtpporta"));
				cs.setEmailssl(rs.getInt("emailssl"));
				cs.setEmailtls(rs.getInt("emailtls"));
				cs.setStatus(rs.getInt("status"));
				cs.setEmailautenticacaosmtp(rs.getInt("emailautenticacaosmtp"));
				return cs;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean sistemaOnLine() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT status " +
						"FROM configuracoessistema " +
						"WHERE idconfiguracoessistema = 1 AND status = 1");
				
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
	
}
