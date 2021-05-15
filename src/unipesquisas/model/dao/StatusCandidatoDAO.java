package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.StatusCandidato;

public class StatusCandidatoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L;

	public List<StatusCandidato> listarStatus(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idstatuscandidato, status, idempresa " +
						"FROM statuscandidato " +
						"WHERE idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				List<StatusCandidato> listaStatus = new ArrayList<StatusCandidato>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					StatusCandidato sc = new StatusCandidato();
					sc.setEmpresa(new Empresa());
					preencher(sc, rs);
					listaStatus.add(sc);
				}

				return listaStatus;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	} 

	private void preencher(StatusCandidato sc, ResultSet rs) throws DAOException {
		try {
			sc.setIdstatuscandidato(rs.getInt("idstatuscandidato"));
			sc.setStatus(rs.getString("status"));
			sc.getEmpresa().setIdempresa(rs.getInt("idempresa"));
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public void salvar(StatusCandidato sc) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO statuscandidato (status, idempresa) VALUES (?, ?)", new String[] { "idstatuscandidato" });
				stmt.setString(1, sc.getStatus());
				stmt.setInt(2, sc.getEmpresa().getIdempresa());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				sc.setIdstatuscandidato(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(StatusCandidato sc) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE statuscandidato SET status = ? " +
						"WHERE idstatuscandidato = ?");
				stmt.setString(1, sc.getStatus());
				stmt.setInt(2, sc.getIdstatuscandidato());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar statuscandidato = " + sc.getIdstatuscandidato());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public StatusCandidato carregar(Integer idStatusCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idstatuscandidato, status, idempresa " +
						"FROM statuscandidato " +
						"WHERE idstatuscandidato = ?");
				stmt.setInt(1, idStatusCandidato);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				StatusCandidato sc = new StatusCandidato();
				sc.setEmpresa(new Empresa());
				preencher(sc, rs);
				return sc;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean excluir(Integer idStatusCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM statuscandidato WHERE idstatuscandidato = ?");
				stmt.setInt(1, idStatusCandidato);

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

	public List<StatusCandidato> listarResumosStatus(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT sc.idstatuscandidato, sc.status, count(c.idcandidato) as total " +
						"FROM statuscandidato sc " +
						"INNER JOIN candidatoempresa ce ON ce.idstatuscandidato=sc.idstatuscandidato " +
						"INNER JOIN candidato c ON c.idcandidato=ce.idcandidato " +
						"WHERE sc.idempresa = ? AND ce.idempresa = ? " +
						"GROUP BY sc.status " +
						"ORDER BY sc.status");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmpresa);
				
				List<StatusCandidato> listaSC = new ArrayList<StatusCandidato>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					StatusCandidato sc = new StatusCandidato();
					sc.setIdstatuscandidato(rs.getInt("idstatuscandidato"));
					sc.setStatus(rs.getString("status"));
					sc.setTotal(rs.getInt("total"));
					listaSC.add(sc);
				}
				return listaSC;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Integer getTotalCandidatosSemStatus(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT count(*) as total " +
						"FROM candidatoempresa ce " + 
						"WHERE ce.idempresa = ? AND (ce.idstatuscandidato is NULL OR ce.idstatuscandidato = 0)");
				stmt.setInt(1, idEmpresa);
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					return rs.getInt("total");
				}
				return 0;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
