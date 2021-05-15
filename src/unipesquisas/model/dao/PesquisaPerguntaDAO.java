package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.PesquisaPergunta;
import unipesquisas.model.entity.Pergunta.Tipo;

public class PesquisaPerguntaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (PesquisaPergunta pp) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO pesquisapergunta (idpesquisa, idpergunta) VALUES (?, ?)", new String[] { "idpesquisapergunta" });
				stmt.setInt(1, pp.getPesquisa().getIdpesquisa());
				stmt.setInt(2, pp.getPergunta().getIdpergunta());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				pp.setIdpesquisapergunta(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean excluir(Integer idPP) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM pesquisapergunta WHERE idpesquisapergunta = ?");
				stmt.setInt(1, idPP);

				if (stmt.executeUpdate() != 1) {
//					throw new DAOException("Erro ao excluir pesquisapergunta ID = " + idpp);
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

	public List<PesquisaPergunta> listarPPs(Integer idPesquisa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pp.idpesquisapergunta, pp.idpesquisa, pp.idpergunta, pesq.titulo, " +
						"per.pergunta, per.tipo " +
						"FROM pesquisapergunta pp " +
						"INNER JOIN pesquisa pesq ON pp.idpesquisa=pesq.idpesquisa " +
						"INNER JOIN pergunta per ON pp.idpergunta=per.idpergunta " +
						"WHERE pp.idpesquisa = ?");
				stmt.setInt(1, idPesquisa);
				
				List<PesquisaPergunta> listaPPs = new ArrayList<PesquisaPergunta>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaPergunta pp = new PesquisaPergunta();
					pp.setPesquisa(new Pesquisa());
					pp.setPergunta(new Pergunta());
					preencher(pp, rs);
					listaPPs.add(pp);
				}

				return listaPPs;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(PesquisaPergunta pp, ResultSet rs) throws DAOException {
		try {
			pp.setIdpesquisapergunta(rs.getInt("idpesquisapergunta"));
			pp.getPesquisa().setIdpesquisa(rs.getInt("idpesquisa"));
			pp.getPesquisa().setTitulo(rs.getString("titulo"));
			pp.getPergunta().setIdpergunta(rs.getInt("idpergunta"));
			pp.getPergunta().setPergunta(rs.getString("pergunta"));
			pp.getPergunta().setTipo(Tipo.valueOf(rs.getString("tipo")));
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public boolean existePP(Integer idPesquisa, Integer idPergunta) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idpesquisa, idpergunta " +
						"FROM pesquisapergunta " +
						"WHERE idpesquisa = ? AND idpergunta = ?");
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idPergunta);
				
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

	public PesquisaPergunta carregar(Integer idPesquisa, Integer idPergunta) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pp.idpesquisapergunta, pp.idpesquisa, pp.idpergunta, " +
						"pesq.titulo, per.pergunta, per.tipo " +
						"FROM pesquisapergunta pp " +
						"INNER JOIN pesquisa pesq ON pp.idpesquisa=pesq.idpesquisa " +
						"INNER JOIN pergunta per ON pp.idpergunta=per.idpergunta " +
						"WHERE pp.idpesquisa = ? AND pp.idpergunta = ?");
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idPergunta);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				PesquisaPergunta pp = new PesquisaPergunta();
				pp.setPesquisa(new Pesquisa());
				pp.setPergunta(new Pergunta());
				preencher(pp, rs);

				return pp;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public int numeroPerguntasNaPesquisa(Integer idPesquisa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT count(pp.idpergunta) as total " +
						"FROM pesquisapergunta pp " +
						"INNER JOIN pesquisa pesq ON pp.idpesquisa=pesq.idpesquisa " +
						"INNER JOIN pergunta per ON pp.idpergunta=per.idpergunta " +
						"WHERE pp.idpesquisa = ?");
				stmt.setInt(1, idPesquisa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return 0;
				}
				
				return rs.getInt("total");

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
