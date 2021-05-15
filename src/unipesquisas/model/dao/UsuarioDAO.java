package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Usuario;

public class UsuarioDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (Usuario usuario) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO usuario (nome, email, password, idempresa, status) " +
						"VALUES (?, ?, ?, ?, ?)", new String[] { "idusuario" });
				stmt.setString(1, usuario.getNome());
				stmt.setString(2, usuario.getEmail());
				stmt.setString(3, usuario.getPassword());
				stmt.setInt(4, usuario.getIdempresa());
				stmt.setInt(5, usuario.getStatus());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				usuario.setIdusuario(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Usuario usuario) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE usuario SET nome = ?, email = ?, password = ?, status = ? " +
						"WHERE idusuario = ?");
				stmt.setString(1, usuario.getNome());
				stmt.setString(2, usuario.getEmail());
				stmt.setString(3, usuario.getPassword());
				stmt.setInt(4, usuario.getStatus());
				stmt.setInt(5, usuario.getIdusuario());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar usuario = " + usuario.getIdusuario());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idUsuario) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM usuario WHERE idusuario = ?");
				stmt.setInt(1, idUsuario);

				if (stmt.executeUpdate() != 1) {
//					throw new DAOException("Erro ao excluir usuario ID = " + idUsuario);
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

	public List<Usuario> listarUsuarios(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idusuario, nome, email, password, status " +
						"FROM usuario " +
						"WHERE idempresa = ? ORDER BY nome");
				stmt.setInt(1, idEmpresa);
				
				List<Usuario> listaUsuarios = new ArrayList<Usuario>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Usuario usuario = new Usuario();
					preencher(usuario, rs);
					listaUsuarios.add(usuario);
				}

				return listaUsuarios;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Usuario usuario, ResultSet rs) throws DAOException {
		try {
			usuario.setIdusuario(rs.getInt("idusuario"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setPassword(rs.getString("password"));
			usuario.setStatus(rs.getInt("status"));

		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Usuario carregar(Integer idUsuario) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idusuario, nome, email, password, status " +
						"FROM usuario " +
						"WHERE idusuario = ?");
				stmt.setInt(1, idUsuario);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Usuario usuario = new Usuario();
				preencher(usuario, rs);
				return usuario;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public boolean existeEmail(String email) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT email FROM usuario WHERE email = ?");
				stmt.setString(1, email);
				
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

	public List<Usuario> listarUsuarioPorId(int idUsuario, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idusuario, nome, email, password, status " +
						"FROM usuario " +
						"WHERE idempresa = ? AND idusuario = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idUsuario);
				
				List<Usuario> listaUsuarios = new ArrayList<Usuario>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Usuario usuario = new Usuario();
					preencher(usuario, rs);
					listaUsuarios.add(usuario);
				}

				return listaUsuarios;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Usuario> listarPesquisarPorNome(String nomeUsuario,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idusuario, nome, email, password, status " +
						"FROM usuario " +
						"WHERE idempresa = ? AND nome like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+nomeUsuario+"%");
				
				List<Usuario> listaUsuarios = new ArrayList<Usuario>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Usuario usuario = new Usuario();
					preencher(usuario, rs);
					listaUsuarios.add(usuario);
				}

				return listaUsuarios;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
