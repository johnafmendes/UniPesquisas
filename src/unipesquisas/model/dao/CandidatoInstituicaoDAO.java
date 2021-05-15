package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.CandidatoInstituicao;
import unipesquisas.model.entity.Instituicao;

public class CandidatoInstituicaoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (CandidatoInstituicao ci) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO candidatoinstituicao (idcandidato, idinstituicao) " +
						"VALUES (?, ?)");
				stmt.setInt(1, ci.getCandidato().getIdcandidato());
				stmt.setInt(2, ci.getInstituicao().getIdinstituicao());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				//ResultSet rs = stmt.getGeneratedKeys();
				//rs.next();
				//ce.setIdcandidatoempresa(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<CandidatoInstituicao> listarInstituicoesPorCandidato(Integer idEmpresa,
			Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT i.idinstituicao, i.instituicao, ci.idcandidatoinstituicao " +
						"FROM instituicao i " +
						"INNER JOIN candidatoinstituicao ci ON ci.idinstituicao=i.idinstituicao " +
						"WHERE ci.idcandidato = ? AND i.idinstituicao IN " +
						"(SELECT ci.idinstituicao " +
						"FROM candidatoinstituicao ci " +
						"INNER JOIN instituicao i ON i.idinstituicao=ci.idinstituicao " +
						"WHERE ci.idcandidato = ? AND i.idempresa = ?) AND i.idempresa = ? " +
						"ORDER BY i.instituicao");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idCandidato);
				stmt.setInt(3, idEmpresa);
				stmt.setInt(4, idEmpresa);
				
				List<CandidatoInstituicao> listaCI = new ArrayList<CandidatoInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					CandidatoInstituicao ci = new CandidatoInstituicao();
					ci.setInstituicao(new Instituicao());
					ci.getInstituicao().setIdinstituicao(rs.getInt("idinstituicao"));
					ci.getInstituicao().setInstituicao(rs.getString("instituicao"));
					ci.setIdcandidatoinstituicao(rs.getInt("idcandidatoinstituicao"));
					listaCI.add(ci);
				}

				return listaCI;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Boolean excluirInstituicao(Integer idCandidato, Integer idInstituicao) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM candidatoinstituicao WHERE idcandidato = ? AND idinstituicao = ?");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idInstituicao);

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

	public boolean excluirInstituicao(Integer idCandidatoInstituicao) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM candidatoinstituicao WHERE idcandidatoinstituicao = ?");
				stmt.setInt(1, idCandidatoInstituicao);

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

	public int numeroInstituicoesSelecionados(Integer idCandidato, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT count(ci.idinstituicao) as total " +
						"FROM instituicao i " +
						"INNER JOIN candidatoinstituicao ci ON ci.idinstituicao=i.idinstituicao " +
						"INNER JOIN candidato can ON can.idcandidato=ci.idcandidato " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=can.idcandidato " +
						"WHERE ci.idcandidato = ? AND ce.idempresa = ? AND i.idempresa = ?");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idEmpresa);
				stmt.setInt(3, idEmpresa);
				
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
