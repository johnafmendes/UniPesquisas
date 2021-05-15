package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.CandidatoCurso;
import unipesquisas.model.entity.Curso;

public class CandidatoCursoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (CandidatoCurso cc) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO candidatocurso (idcandidato, idcurso, ordem, matriculado) " +
						"VALUES (?, ?, ?, ?)");
				stmt.setInt(1, cc.getCandidato().getIdcandidato());
				stmt.setInt(2, cc.getCurso().getIdcurso());
				stmt.setInt(3, cc.getOrdem());
				stmt.setInt(4, cc.getMatriculado());

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

	public boolean excluir(Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM candidatocurso WHERE idcandidato = ?");
				stmt.setInt(1, idCandidato);

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

	public List<CandidatoCurso> listarCursosPorCandidato(Integer idEmpresa,
			Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, cc.matriculado, cc.idcandidatocurso " +
						"FROM curso c " +
						"INNER JOIN candidatocurso cc ON cc.idcurso=c.idcurso " +
						"WHERE cc.idcandidato = ? AND c.idcurso IN " +
						"(SELECT cc.idcurso " +
						"FROM candidatocurso cc " +
						"INNER JOIN curso c ON c.idcurso=cc.idcurso " +
						"INNER JOIN instituicao i ON i.idinstituicao=c.idinstituicao " +
						"WHERE cc.idcandidato = ? AND i.idempresa = ?) " +
						"ORDER BY c.curso");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idCandidato);
				stmt.setInt(3, idEmpresa);
				
				List<CandidatoCurso> listaCC = new ArrayList<CandidatoCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					CandidatoCurso cc = new CandidatoCurso();
					cc.setCurso(new Curso());
					cc.getCurso().setIdcurso(rs.getInt("idcurso"));
					cc.getCurso().setCurso(rs.getString("curso"));
					cc.setMatriculado(rs.getInt("matriculado"));
					cc.setIdcandidatocurso(rs.getInt("idcandidatocurso"));
					listaCC.add(cc);
				}

				return listaCC;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Boolean excluirCurso(Integer idCandidato, Integer idCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM candidatocurso WHERE idcandidato = ? AND idcurso = ?");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idCurso);

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

	public CandidatoCurso carregar(Integer idCandidatoCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, cc.matriculado, can.idcandidato, can.nome, " +
						"cc.idcandidatocurso " +
						"FROM curso c " +
						"INNER JOIN candidatocurso cc ON cc.idcurso=c.idcurso " +
						"INNER JOIN candidato can ON can.idcandidato=cc.idcandidato " +
						"WHERE cc.idcandidatocurso = ?");
				stmt.setInt(1, idCandidatoCurso);
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					CandidatoCurso cc = new CandidatoCurso();
					cc.setCurso(new Curso());
					cc.setCandidato(new Candidato());
					cc.getCurso().setIdcurso(rs.getInt("idcurso"));
					cc.getCurso().setCurso(rs.getString("curso"));
					cc.getCandidato().setIdcandidato(rs.getInt("idcandidato"));
					cc.getCandidato().setNome(rs.getString("nome"));
					cc.setMatriculado(rs.getInt("matriculado"));
					cc.setIdcandidatocurso(rs.getInt("idcandidatocurso"));
					return cc;
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

	public boolean excluirCurso(Integer idCandidatoCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM candidatocurso WHERE idcandidatocurso = ?");
				stmt.setInt(1, idCandidatoCurso);

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

	public boolean atualizar(CandidatoCurso cc) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE candidatocurso SET matriculado = ? WHERE idcandidatocurso = ?");
				stmt.setInt(1, cc.getMatriculado());
				stmt.setInt(2, cc.getIdcandidatocurso());

				if (stmt.executeUpdate() != 1) {
					//throw new DAOException("Erro ao atualizar curso = " + curso.getIdcurso());
					return false;
				}
				
				return true;

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

	public int numeroCursosSelecionados(Integer idCandidato, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT count(cc.idcurso) as total " +
						"FROM curso c " +
						"INNER JOIN candidatocurso cc ON cc.idcurso=c.idcurso " +
						"INNER JOIN candidato can ON can.idcandidato=cc.idcandidato " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=can.idcandidato " +
						"INNER JOIN instituicao i ON i.idinstituicao=c.idinstituicao " +
						"WHERE cc.idcandidato = ? AND ce.idempresa = ? AND i.idempresa = ?");
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
