package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.PesquisaEscolaridade;
import unipesquisas.model.entity.StatusCandidato;

public class PesquisaEscolaridadeDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (PesquisaEscolaridade pe) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO pesquisaescolaridade (idpesquisa, idescolaridade, idstatuscandidato) " +
						"VALUES (?, ?, ?)", new String[] { "idpesquisaescolaridade" });
				stmt.setInt(1, pe.getPesquisa().getIdpesquisa());
				stmt.setInt(2, pe.getEscolaridade().getIdescolaridade());
				if(pe.getStatuscandidato().getIdstatuscandidato() == 0){
					stmt.setNull(3, java.sql.Types.INTEGER);
				}else{
					stmt.setInt(3, pe.getStatuscandidato().getIdstatuscandidato());
				}
				
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				pe.setIdpesquisaescolaridade(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(PesquisaEscolaridade pe, ResultSet rs) throws DAOException {
		try {
			pe.setIdpesquisaescolaridade(rs.getInt("idpesquisaescolaridade"));
			pe.getPesquisa().setIdpesquisa(rs.getInt("idpesquisa"));
			pe.getPesquisa().setTitulo(rs.getString("titulo"));
			pe.getPesquisa().setDescricao(rs.getString("descricao"));
			pe.getPesquisa().setData(rs.getDate("data"));
			pe.getPesquisa().setStatus(rs.getInt("status"));
			pe.getPesquisa().setIdempresa(rs.getInt("idempresa"));
			pe.getEscolaridade().setIdescolaridade(rs.getInt("idescolaridade"));
			pe.getEscolaridade().setEscolaridade(rs.getString("escolaridade"));
			if(rs.getString("statuscandidato") != null){
				pe.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				pe.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public PesquisaEscolaridade carregar(Integer idPesquisa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pe.idpesquisaescolaridade, pe.idpesquisa, pe.idescolaridade, " +
						"p.titulo, e.escolaridade, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisaescolaridade pe " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pe.idpesquisa " +
						"INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pe.idstatuscandidato " +
						"WHERE pe.idpesquisa = ?");
				stmt.setInt(1, idPesquisa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				PesquisaEscolaridade pe = new PesquisaEscolaridade();
				pe.setEscolaridade(new Escolaridade());
				pe.setPesquisa(new Pesquisa());
				pe.setStatuscandidato(new StatusCandidato());
				preencher(pe, rs);

				return pe;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaEscolaridade> listaPesquisaEscolaridade(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pe.idpesquisaescolaridade, pe.idpesquisa, pe.idescolaridade, " +
						"p.titulo, e.escolaridade, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisaescolaridade pe " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pe.idpesquisa " +
						"INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pe.idstatuscandidato " +
						"WHERE p.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<PesquisaEscolaridade> listaPE = new ArrayList<PesquisaEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaEscolaridade pe = new PesquisaEscolaridade();
					pe.setPesquisa(new Pesquisa());
					pe.setEscolaridade(new Escolaridade());
					pe.setStatuscandidato(new StatusCandidato());
					preencher(pe, rs);
					listaPE.add(pe);
				}

				return listaPE;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean pesquisaPublicada(Integer idPesquisa, Integer idEscolaridade,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				if(idStatusCandidato == 0){
					stmt = conn.prepareStatement("SELECT * " +
							"FROM pesquisaescolaridade " +
							"WHERE idpesquisa = ? AND idescolaridade = ? AND idstatuscandidato is null");
				}else{
					stmt = conn.prepareStatement("SELECT * " +
							"FROM pesquisaescolaridade " +
							"WHERE idpesquisa = ? AND idescolaridade = ? AND idstatuscandidato = ?");
				}
						
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idEscolaridade);
				if(idStatusCandidato > 0){
					stmt.setInt(3, idStatusCandidato);
				}
				
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

	public List<PesquisaEscolaridade> listarPesquisaEscolaridadeDisponiveis(
			Integer idCandidato, Integer idEmpresa, Integer idEscolaridade,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.status, pe.idpesquisaescolaridade, pe.idescolaridade, e.escolaridade, sc.status as statuscandidato " +
						"FROM pesquisaescolaridade pe " +
						"INNER JOIN pesquisa p ON pe.idpesquisa=p.idpesquisa " +
						"INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pe.idstatuscandidato " +
						"WHERE p.status = 1 AND pe.idpesquisaescolaridade NOT IN (SELECT idpesquisaescolaridade " + 
						"FROM candidatopesquisaescolaridade WHERE idcandidato = ?) AND p.idempresa = ? " +
						"AND e.idescolaridade = ? AND (sc.idstatuscandidato = ? OR sc.idstatuscandidato is null) " +
						"ORDER BY p.data");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idEmpresa);
				stmt.setInt(3, idEscolaridade);
				stmt.setInt(4, idStatusCandidato);
				
				List<PesquisaEscolaridade> listaPE = new ArrayList<PesquisaEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaEscolaridade pe = new PesquisaEscolaridade();
					pe.setPesquisa(new Pesquisa());
					pe.setEscolaridade(new Escolaridade());
					pe.setStatuscandidato(new StatusCandidato());
					preencher(pe, rs);
					listaPE.add(pe);
				}

				return listaPE;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaEscolaridade> listarPublicacaoEscolaridadePorIdPesquisa(
			int idPesquisa, int idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pe.idpesquisaescolaridade, pe.idpesquisa, pe.idescolaridade, " +
						"p.titulo, e.escolaridade, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisaescolaridade pe " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pe.idpesquisa " +
						"INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pe.idstatuscandidato " +
						"WHERE p.idempresa = ? AND p.idpesquisa = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idPesquisa);
				
				
				List<PesquisaEscolaridade> listaPE = new ArrayList<PesquisaEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaEscolaridade pe = new PesquisaEscolaridade();
					pe.setPesquisa(new Pesquisa());
					pe.setEscolaridade(new Escolaridade());
					pe.setStatuscandidato(new StatusCandidato());
					preencher(pe, rs);
					listaPE.add(pe);
				}

				return listaPE;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaEscolaridade> listarPublicacaoEscolaridadePorIdEscolaridade(
			int idEscolaridade, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pe.idpesquisaescolaridade, pe.idpesquisa, pe.idescolaridade, " +
						"p.titulo, e.escolaridade, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisaescolaridade pe " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pe.idpesquisa " +
						"INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pe.idstatuscandidato " +
						"WHERE p.idempresa = ? AND e.idescolaridade = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEscolaridade);
				
				
				List<PesquisaEscolaridade> listaPE = new ArrayList<PesquisaEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaEscolaridade pe = new PesquisaEscolaridade();
					pe.setPesquisa(new Pesquisa());
					pe.setEscolaridade(new Escolaridade());
					pe.setStatuscandidato(new StatusCandidato());
					preencher(pe, rs);
					listaPE.add(pe);
				}

				return listaPE;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaEscolaridade> listarPublicacaoEscolaridadePorEscolaridade(
			String escolaridade, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pe.idpesquisaescolaridade, pe.idpesquisa, pe.idescolaridade, " +
						"p.titulo, e.escolaridade, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisaescolaridade pe " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pe.idpesquisa " +
						"INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pe.idstatuscandidato " +
						"WHERE p.idempresa = ? AND e.escolaridade like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+escolaridade+"%");
				
				
				List<PesquisaEscolaridade> listaPE = new ArrayList<PesquisaEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaEscolaridade pe = new PesquisaEscolaridade();
					pe.setPesquisa(new Pesquisa());
					pe.setEscolaridade(new Escolaridade());
					pe.setStatuscandidato(new StatusCandidato());
					preencher(pe, rs);
					listaPE.add(pe);
				}

				return listaPE;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaEscolaridade> listarPublicacaoEscolaridadePorPesquisa(
			String pesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pe.idpesquisaescolaridade, pe.idpesquisa, pe.idescolaridade, " +
						"p.titulo, e.escolaridade, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisaescolaridade pe " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pe.idpesquisa " +
						"INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pe.idstatuscandidato " +
						"WHERE p.idempresa = ? AND p.titulo like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+pesquisa+"%");
				
				
				List<PesquisaEscolaridade> listaPE = new ArrayList<PesquisaEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaEscolaridade pe = new PesquisaEscolaridade();
					pe.setPesquisa(new Pesquisa());
					pe.setEscolaridade(new Escolaridade());
					pe.setStatuscandidato(new StatusCandidato());
					preencher(pe, rs);
					listaPE.add(pe);
				}

				return listaPE;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
