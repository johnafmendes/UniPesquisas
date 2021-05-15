package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.Usuario;

public class AutenticacaoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2551571499004957685L;

	public Usuario validarLogin(Usuario usuario) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT u.idusuario, u.nome, u.idempresa " +
						"FROM usuario u " +
						"INNER JOIN empresa e ON e.idempresa=u.idempresa " +
						"WHERE u.email = ? AND u.password = ? AND u.status = 1 AND e.status = 1");
				stmt.setString(1, usuario.getEmail());
				stmt.setString(2, usuario.getPassword());
				//stmt.setInt(3, usuario.getIdempresa());
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				usuario.setIdusuario(rs.getInt("idusuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setIdempresa(rs.getInt("idempresa"));
				
				return usuario;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Candidato validarLoginCandidato(Candidato candidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idcandidato, nome, idescolaridade " +
						"FROM candidato " +
						"WHERE email = ? and password = ?");
				stmt.setString(1, candidato.getEmail());
				stmt.setString(2, candidato.getPassword());
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				candidato.setIdcandidato(rs.getInt("idcandidato"));
				candidato.setNome(rs.getString("nome"));
				candidato.setEscolaridade(new Escolaridade());
				candidato.getEscolaridade().setIdescolaridade(rs.getInt("idescolaridade"));
				
				return candidato;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
