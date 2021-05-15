package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.mail.EmailException;

import unipesquisas.model.entity.EmailPadrao;

public class EmailPadraoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public EmailPadrao getEmailBoasVindas() throws DAOException, EmailException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT assuntoboasvindas, mensagemboasvindas " +
						"FROM emailpadrao ");
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailPadrao ep = new EmailPadrao();
					ep.setAssuntoboasvindas(rs.getString("assuntoboasvindas"));
					ep.setMensagemboasvindas(rs.getString("mensagemboasvindas"));
					return ep;
				}
				
				return null;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void salvar(EmailPadrao ep) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE emailpadrao SET assuntoboasvindas = ?, mensagemboasvindas = ?, " +
						"assuntolembretepesquisa = ?, mensagemlembretepesquisa = ? " +
						"WHERE idemailpadrao = 1");
				stmt.setString(1, ep.getAssuntoboasvindas());
				stmt.setString(2, ep.getMensagemboasvindas());
				stmt.setString(3, ep.getAssuntolembretepesquisa());
				stmt.setString(4, ep.getMensagemlembretepesquisa());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar Email Padrao ");
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public EmailPadrao carregar() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT assuntoboasvindas, mensagemboasvindas, assuntolembretepesquisa, " +
						"mensagemlembretepesquisa " +
						"FROM emailpadrao ");
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				EmailPadrao ep = new EmailPadrao();
				ep.setAssuntoboasvindas(rs.getString("assuntoboasvindas"));
				ep.setMensagemboasvindas(rs.getString("mensagemboasvindas"));
				ep.setAssuntolembretepesquisa(rs.getString("assuntolembretepesquisa"));
				ep.setMensagemlembretepesquisa(rs.getString("mensagemlembretepesquisa"));
				return ep;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
