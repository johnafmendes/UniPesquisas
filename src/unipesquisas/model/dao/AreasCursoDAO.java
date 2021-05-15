package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.AreasCurso;


public class AreasCursoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (AreasCurso areas) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO areacurso (area, idempresa) VALUES (?, ?)", new String[] { "idareacurso" });
				stmt.setString(1, areas.getArea());
				stmt.setInt(2, areas.getIdempresa());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				areas.setIdareacurso(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(AreasCurso areas) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE areacurso SET area = ? WHERE idareacurso = ?");
				stmt.setString(1, areas.getArea());
				stmt.setInt(2, areas.getIdareacurso());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar usuario = " + areas.getIdareacurso());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idAreaCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM areacurso WHERE idareacurso = ?");
				stmt.setInt(1, idAreaCurso);

				if (stmt.executeUpdate() != 1) {
//					throw new DAOException("Erro ao excluir area ID = " + idAreasCurso);
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

	public List<AreasCurso> listarAreas(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idareacurso, area, idempresa " +
						"FROM areacurso " +
						"WHERE idempresa = ? " +
						"ORDER BY area");
				stmt.setInt(1, idEmpresa);
				
				List<AreasCurso> listaAreas = new ArrayList<AreasCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					AreasCurso areas = new AreasCurso();
					preencher(areas, rs);
					listaAreas.add(areas);
				}

				return listaAreas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(AreasCurso areas, ResultSet rs) throws DAOException {
		try {
			areas.setIdareacurso(rs.getInt("idareacurso"));
			areas.setArea(rs.getString("area"));

		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public AreasCurso carregar(Integer idAreaCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idareacurso, area FROM areacurso WHERE idareacurso = ?");
				stmt.setInt(1, idAreaCurso);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				AreasCurso areas = new AreasCurso();
				preencher(areas, rs);
				return areas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<AreasCurso> listarResumoAreas(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ac.idareacurso, ac.area, count(c.idcandidato) as total " +
						"FROM areacurso ac " +
						"INNER JOIN curso cu ON cu.idarea=ac.idareacurso " +
						"INNER JOIN candidatocurso cc ON cc.idcurso=cu.idcurso " +
						"INNER JOIN candidato c ON c.idcandidato=cc.idcandidato " +
						"WHERE ac.idempresa = ? " +
						"GROUP BY ac.area " +
						"ORDER BY ac.area");
				stmt.setInt(1, idEmpresa);
				
				List<AreasCurso> listaAreas = new ArrayList<AreasCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					AreasCurso areas = new AreasCurso();
					areas.setIdareacurso(rs.getInt("idareacurso"));
					areas.setArea(rs.getString("area"));
					areas.setTotal(rs.getInt("total"));
					listaAreas.add(areas);
				}

				return listaAreas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
