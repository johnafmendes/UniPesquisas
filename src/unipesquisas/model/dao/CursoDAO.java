package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.AreasCurso;
import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.Instituicao;

public class CursoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (Curso curso) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO curso (curso, idarea, idinstituicao) VALUES (?, ?, ?)", new String[] { "idcurso" });
				stmt.setString(1, curso.getCurso());
				stmt.setInt(2, curso.getAreacurso().getIdareacurso());
				stmt.setInt(3, curso.getInstituicao().getIdinstituicao());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				curso.setIdcurso(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Curso curso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE curso SET curso = ?, idarea = ?, idinstituicao = ? WHERE idcurso = ?");
				stmt.setString(1, curso.getCurso());
				stmt.setInt(2, curso.getAreacurso().getIdareacurso());
				stmt.setInt(3, curso.getInstituicao().getIdinstituicao());
				stmt.setInt(4, curso.getIdcurso());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar curso = " + curso.getIdcurso());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM curso WHERE idcurso = ?");
				stmt.setInt(1, idCurso);

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

	public List<Curso> listarCursos(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, c.idinstituicao, c.idarea, i.instituicao, a.area " +
						"FROM curso c " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"INNER JOIN areacurso a ON c.idarea=a.idareacurso " +
						"WHERE i.idempresa = ? AND a.idempresa = ? " +
						"ORDER BY c.curso");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmpresa);
				
				List<Curso> listaCursos = new ArrayList<Curso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Curso curso = new Curso();
					curso.setAreacurso(new AreasCurso());
					curso.setInstituicao(new Instituicao());
					preencher(curso, rs);
					listaCursos.add(curso);
				}

				return listaCursos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Curso curso, ResultSet rs) throws DAOException {
		try {
			curso.setIdcurso(rs.getInt("idcurso"));
			curso.setCurso(rs.getString("curso"));
			curso.getInstituicao().setIdinstituicao(rs.getInt("idinstituicao"));
			curso.getInstituicao().setInstituicao(rs.getString("instituicao"));
			curso.getAreacurso().setIdareacurso(rs.getInt("idarea"));
			curso.getAreacurso().setArea(rs.getString("area"));
			curso.setMatriculado(1);
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Curso carregar(Integer idCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, c.idinstituicao, c.idarea, i.instituicao, a.area " +
						"FROM curso c " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"INNER JOIN areacurso a ON c.idarea=a.idareacurso " +
						"WHERE c.idcurso = ?");
				stmt.setInt(1, idCurso);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Curso curso = new Curso();
				curso.setAreacurso(new AreasCurso());
				curso.setInstituicao(new Instituicao());
				preencher(curso, rs);
				return curso;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Curso> listarCursosEscolhidos(Integer idEmpresa,
			Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, c.idinstituicao, c.idarea, i.instituicao, a.area " +
						"FROM curso c " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"INNER JOIN areacurso a ON c.idarea=a.idareacurso " +
						"WHERE i.idempresa = ? AND a.idempresa = ? AND c.idcurso IN (SELECT idcurso " +
						"FROM candidatocurso WHERE idcandidato = ?)" +
						"ORDER BY c.curso");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmpresa);
				stmt.setInt(3, idCandidato);
				
				List<Curso> listaCursos = new ArrayList<Curso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Curso curso = new Curso();
					curso.setAreacurso(new AreasCurso());
					curso.setInstituicao(new Instituicao());
					preencher(curso, rs);
					listaCursos.add(curso);
				}

				return listaCursos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Curso> listarCursosDisponiveis(Integer idEmpresa,
			Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, c.idinstituicao, c.idarea, i.instituicao, a.area " +
						"FROM curso c " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"INNER JOIN areacurso a ON c.idarea=a.idareacurso " +
						"WHERE i.idempresa = ? AND a.idempresa = ? AND c.idcurso " +
						"NOT IN (SELECT cc.idcurso FROM candidatocurso cc " +
						"INNER JOIN curso c ON c.idcurso=cc.idcurso " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"WHERE cc.idcandidato = ? and i.idempresa = ?) " +
						"ORDER BY c.curso");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmpresa);
				stmt.setInt(3, idCandidato);
				stmt.setInt(4, idEmpresa);
				
				List<Curso> listaCursos = new ArrayList<Curso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Curso curso = new Curso();
					curso.setAreacurso(new AreasCurso());
					curso.setInstituicao(new Instituicao());
					preencher(curso, rs);
					listaCursos.add(curso);
				}

				return listaCursos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Curso> listarPesquisaPorCurso(String tituloCurso,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, c.idinstituicao, c.idarea, i.instituicao, a.area " +
						"FROM curso c " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"INNER JOIN areacurso a ON c.idarea=a.idareacurso " +
						"WHERE i.idempresa = ? AND a.idempresa = ? AND c.curso like ? " +
						"ORDER BY c.curso");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmpresa);
				stmt.setString(3, "%"+tituloCurso+"%");
				
				List<Curso> listaCursos = new ArrayList<Curso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Curso curso = new Curso();
					curso.setAreacurso(new AreasCurso());
					curso.setInstituicao(new Instituicao());
					preencher(curso, rs);
					listaCursos.add(curso);
				}

				return listaCursos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Curso> listarCursoPorPesquisa(Integer idPesquisa,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, c.idinstituicao, c.idarea, i.instituicao, a.area " +
						"FROM curso c " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"INNER JOIN areacurso a ON c.idarea=a.idareacurso " +
						"INNER JOIN pesquisacurso pc ON pc.idcurso=c.idcurso " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pc.idpesquisa " +
						"WHERE i.idempresa = ? AND a.idempresa = ? AND p.idempresa = ? AND pc.idpesquisa = ? " +
						"ORDER BY c.curso");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmpresa);
				stmt.setInt(3, idEmpresa);
				stmt.setInt(4, idPesquisa);
				
				List<Curso> listaCursos = new ArrayList<Curso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Curso curso = new Curso();
					curso.setAreacurso(new AreasCurso());
					curso.setInstituicao(new Instituicao());
					preencher(curso, rs);
					listaCursos.add(curso);
				}

				return listaCursos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Curso> listarCursoPorId(int idCurso, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcurso, c.curso, c.idinstituicao, c.idarea, i.instituicao, a.area " +
						"FROM curso c " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"INNER JOIN areacurso a ON c.idarea=a.idareacurso " +
						"WHERE i.idempresa = ? AND a.idempresa = ? AND c.idcurso = ? ");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmpresa);
				stmt.setInt(3, idCurso);
				
				List<Curso> listaCursos = new ArrayList<Curso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Curso curso = new Curso();
					curso.setAreacurso(new AreasCurso());
					curso.setInstituicao(new Instituicao());
					preencher(curso, rs);
					listaCursos.add(curso);
				}

				return listaCursos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
