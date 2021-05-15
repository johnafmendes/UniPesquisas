package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.entity.Pergunta.Tipo;

public class PerguntaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (Pergunta pergunta) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO pergunta (pergunta, alta, altb, altc, altd, " +
						"alte, idempresa, tipo) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "idpergunta" });
				stmt.setString(1, pergunta.getPergunta());
				stmt.setString(2, pergunta.getAlta());
				stmt.setString(3, pergunta.getAltb());
				stmt.setString(4, pergunta.getAltc());
				stmt.setString(5, pergunta.getAltd());
				stmt.setString(6, pergunta.getAlte());
				stmt.setInt(7, pergunta.getIdempresa());
				stmt.setString(8, pergunta.getTipo().toString());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				pergunta.setIdpergunta(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Pergunta pergunta) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE pergunta SET pergunta = ?, alta = ?, altb = ?, altc = ?, " +
						"altd = ?, alte = ?, idempresa = ?, tipo = ? " +
						"WHERE idpergunta = ?");
				stmt.setString(1, pergunta.getPergunta());
				stmt.setString(2, pergunta.getAlta());
				stmt.setString(3, pergunta.getAltb());
				stmt.setString(4, pergunta.getAltc());
				stmt.setString(5, pergunta.getAltd());
				stmt.setString(6, pergunta.getAlte());
				stmt.setInt(7, pergunta.getIdempresa());
				stmt.setString(8, pergunta.getTipo().toString());
				stmt.setInt(9, pergunta.getIdpergunta());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar pergunta = " + pergunta.getIdpergunta());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idPergunta) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM pergunta WHERE idpergunta = ?");
				stmt.setInt(1, idPergunta);

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

	public List<Pergunta> listarPerguntas(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idpergunta, pergunta, alta, altb, altc, altd, alte, " +
						"idempresa, tipo " +
						"FROM pergunta " +
						"WHERE idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pergunta pergunta = new Pergunta();
					preencher(pergunta, rs);
					listaPerguntas.add(pergunta);
				}

				return listaPerguntas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Pergunta pergunta, ResultSet rs) throws DAOException {
		try {
			pergunta.setIdpergunta(rs.getInt("idpergunta"));
			pergunta.setPergunta(rs.getString("pergunta"));
			pergunta.setAlta(rs.getString("alta"));
			pergunta.setAltb(rs.getString("altb"));
			pergunta.setAltc(rs.getString("altc"));
			pergunta.setAltd(rs.getString("altd"));
			pergunta.setAlte(rs.getString("alte"));
			pergunta.setIdempresa(rs.getInt("idempresa"));
			pergunta.setTipo(Tipo.valueOf(rs.getString("tipo")));
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Pergunta carregar(Integer idPergunta) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idpergunta, pergunta, alta, altb, altc, altd, " +
						"alte, idempresa, tipo " +
						"FROM pergunta " +
						"WHERE idpergunta = ?");
				stmt.setInt(1, idPergunta);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Pergunta pergunta = new Pergunta();
				preencher(pergunta, rs);
				return pergunta;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pergunta> listarPesquisaPerguntas(String tituloPergunta, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idpergunta, pergunta, alta, altb, altc, altd, " +
						"alte, idempresa, tipo " +
						"FROM pergunta " +
						"WHERE pergunta like ? AND idempresa = ?");
				stmt.setString(1, "%"+tituloPergunta+"%");
				stmt.setInt(2, idEmpresa);
				
				List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pergunta pergunta = new Pergunta();
					preencher(pergunta, rs);
					listaPerguntas.add(pergunta);
				}

				return listaPerguntas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorPesquisaEscolaridade(Integer idPesquisa, Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpergunta, p.pergunta, p.alta, p.altb, p.altc, p.altd, p.alte, " +
						"p.idempresa, p.tipo " +
						"FROM pergunta p " +
						"INNER JOIN pesquisapergunta pp ON pp.idpergunta=p.idpergunta " +
						"WHERE pp.idpesquisa = ? " +
						"AND pp.idpesquisapergunta " +
						"NOT IN (SELECT rpe.idpesquisapergunta " +
						"FROM respostapesquisaescolaridade rpe " +
						"INNER JOIN pesquisapergunta pp2 ON pp2.idpesquisapergunta=rpe.idpesquisapergunta " +
						"WHERE pp2.idpesquisa = ? AND rpe.idcandidato = ?)");
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idPesquisa);
				stmt.setInt(3, idCandidato);
				
				List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pergunta pergunta = new Pergunta();
					preencher(pergunta, rs);
					listaPerguntas.add(pergunta);
				}

				return listaPerguntas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorPesquisaInstituicao(
			Integer idPesquisa, Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpergunta, p.pergunta, p.alta, p.altb, p.altc, p.altd, p.alte, " +
						"p.idempresa, p.tipo " +
						"FROM pergunta p " +
						"INNER JOIN pesquisapergunta pp ON pp.idpergunta=p.idpergunta " +
						"WHERE pp.idpesquisa = ? " +
						"AND pp.idpesquisapergunta " +
						"NOT IN (SELECT rpi.idpesquisapergunta " +
						"FROM respostapesquisainstituicao rpi " +
						"INNER JOIN pesquisapergunta pp2 ON pp2.idpesquisapergunta=rpi.idpesquisapergunta " +
						"WHERE pp2.idpesquisa = ? AND rpi.idcandidato = ?)");
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idPesquisa);
				stmt.setInt(3, idCandidato);
				
				List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pergunta pergunta = new Pergunta();
					preencher(pergunta, rs);
					listaPerguntas.add(pergunta);
				}

				return listaPerguntas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorPesquisaCurso(Integer idPesquisa, Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpergunta, p.pergunta, p.alta, p.altb, p.altc, p.altd, p.alte, " +
						"p.idempresa, p.tipo " +
						"FROM pergunta p " +
						"INNER JOIN pesquisapergunta pp ON pp.idpergunta=p.idpergunta " +
						"WHERE pp.idpesquisa = ? " +
						"AND pp.idpesquisapergunta " +
						"NOT IN (SELECT rpc.idpesquisapergunta " +
						"FROM respostapesquisacurso rpc " +
						"INNER JOIN pesquisapergunta pp2 ON pp2.idpesquisapergunta=rpc.idpesquisapergunta " +
						"WHERE pp2.idpesquisa = ? AND rpc.idcandidato = ?)");
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idPesquisa);
				stmt.setInt(3, idCandidato);
				
				List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pergunta pergunta = new Pergunta();
					preencher(pergunta, rs);
					listaPerguntas.add(pergunta);
				}

				return listaPerguntas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorPesquisa(Integer idPesquisa,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpergunta, p.pergunta, p.alta, p.altb, p.altc, p.altd, p.alte, " +
						"p.idempresa, p.tipo, pp.idpesquisa " +
						"FROM pergunta p " +
						"INNER JOIN pesquisapergunta pp ON pp.idpergunta=p.idpergunta " +
						"WHERE pp.idpesquisa = ? AND p.idempresa = ?");
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idEmpresa);
				
				List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pergunta pergunta = new Pergunta();
					preencher(pergunta, rs);
					listaPerguntas.add(pergunta);
				}

				return listaPerguntas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pergunta> listarPerguntasPorId(int idPergunta, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpergunta, p.pergunta, p.alta, p.altb, p.altc, p.altd, p.alte, " +
						"p.idempresa, p.tipo " +
						"FROM pergunta p " +
						"WHERE p.idpergunta = ? AND p.idempresa = ?");
				stmt.setInt(1, idPergunta);
				stmt.setInt(2, idEmpresa);
				
				List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pergunta pergunta = new Pergunta();
					preencher(pergunta, rs);
					listaPerguntas.add(pergunta);
				}

				return listaPerguntas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
