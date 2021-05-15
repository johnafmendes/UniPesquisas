package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Escolaridade;


public class EscolaridadeDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (Escolaridade escolaridade) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO escolaridade (escolaridade) VALUES (?)", new String[] { "idescolaridade" });
				stmt.setString(1, escolaridade.getEscolaridade());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				escolaridade.setIdescolaridade(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Escolaridade escolaridade) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE escolaridade SET escolaridade = ? WHERE idescolaridade = ?");
				stmt.setString(1, escolaridade.getEscolaridade());
				stmt.setInt(2, escolaridade.getIdescolaridade());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar usuario = " + escolaridade.getIdescolaridade());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idEscolaridade) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM escolaridade WHERE idescolaridade = ?");
				stmt.setInt(1, idEscolaridade);

				if (stmt.executeUpdate() != 1) {
//					throw new DAOException("Erro ao excluir escolaridade ID = " + idEscolaridade);
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

	public List<Escolaridade> listarEscolaridades() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idescolaridade, escolaridade " +
						"FROM escolaridade " +
						"ORDER BY escolaridade");
				
				List<Escolaridade> listaEscolaridades = new ArrayList<Escolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Escolaridade escolaridade = new Escolaridade();
					preencher(escolaridade, rs);
					listaEscolaridades.add(escolaridade);
				}

				return listaEscolaridades;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Escolaridade escolaridade, ResultSet rs) throws DAOException {
		try {
			escolaridade.setIdescolaridade(rs.getInt("idescolaridade"));
			escolaridade.setEscolaridade(rs.getString("escolaridade"));

		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Escolaridade carregar(Integer idEscolaridade) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idescolaridade, escolaridade " +
						"FROM escolaridade " +
						"WHERE idescolaridade = ?");
				stmt.setInt(1, idEscolaridade);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Escolaridade escolaridade = new Escolaridade();
				preencher(escolaridade, rs);
				return escolaridade;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Escolaridade> listarPesquisaPorEscolaridades(
			String tituloEscolaridade) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idescolaridade, escolaridade " +
						"FROM escolaridade " +
						"WHERE escolaridade like ? " +
						"ORDER BY escolaridade");
				stmt.setString(1, "%"+tituloEscolaridade+"%");
				
				List<Escolaridade> listaEscolaridades = new ArrayList<Escolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Escolaridade escolaridade = new Escolaridade();
					preencher(escolaridade, rs);
					listaEscolaridades.add(escolaridade);
				}

				return listaEscolaridades;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Escolaridade> listarEscolaridadesPorPesquisa(Integer idPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT e.idescolaridade, e.escolaridade " +
						"FROM escolaridade e " +
						"INNER JOIN pesquisaescolaridade pe ON pe.idescolaridade=e.idescolaridade " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pe.idpesquisa " +
						"WHERE pe.idpesquisa = ? AND p.idempresa = ? " +
						"ORDER BY e.escolaridade");
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idEmpresa);
				
				List<Escolaridade> listaEscolaridades = new ArrayList<Escolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Escolaridade escolaridade = new Escolaridade();
					preencher(escolaridade, rs);
					listaEscolaridades.add(escolaridade);
				}

				return listaEscolaridades;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Escolaridade> listarEscolaridadesPorId(int idEscolaridade) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT e.idescolaridade, e.escolaridade " +
						"FROM escolaridade e " +
						"WHERE e.idescolaridade = ? " +
						"ORDER BY e.escolaridade");
				stmt.setInt(1, idEscolaridade);
				
				List<Escolaridade> listaEscolaridades = new ArrayList<Escolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Escolaridade escolaridade = new Escolaridade();
					preencher(escolaridade, rs);
					listaEscolaridades.add(escolaridade);
				}

				return listaEscolaridades;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Escolaridade> listarResummosEscolaridades(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT e.idescolaridade, e.escolaridade, count(c.idcandidato) as total " +
						"FROM escolaridade e " +
						"INNER JOIN candidato c ON c.idescolaridade=e.idescolaridade " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato " +
						"WHERE ce.idempresa = ? " +
						"GROUP BY e.escolaridade " +
						"ORDER BY e.escolaridade");
				stmt.setInt(1, idEmpresa);
				
				List<Escolaridade> listaEscolaridades = new ArrayList<Escolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Escolaridade escolaridade = new Escolaridade();
					escolaridade.setIdescolaridade(rs.getInt("idescolaridade"));
					escolaridade.setEscolaridade(rs.getString("escolaridade"));
					escolaridade.setTotal(rs.getInt("total"));
					listaEscolaridades.add(escolaridade);
				}

				return listaEscolaridades;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Escolaridade> listarResummosEscolaridades() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT e.idescolaridade, e.escolaridade, count(c.idcandidato) as total " +
						"FROM escolaridade e " +
						"INNER JOIN candidato c ON c.idescolaridade=e.idescolaridade " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato " +
						"GROUP BY e.escolaridade " +
						"ORDER BY e.escolaridade");
				
				List<Escolaridade> listaEscolaridades = new ArrayList<Escolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Escolaridade escolaridade = new Escolaridade();
					escolaridade.setIdescolaridade(rs.getInt("idescolaridade"));
					escolaridade.setEscolaridade(rs.getString("escolaridade"));
					escolaridade.setTotal(rs.getInt("total"));
					listaEscolaridades.add(escolaridade);
				}

				return listaEscolaridades;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
