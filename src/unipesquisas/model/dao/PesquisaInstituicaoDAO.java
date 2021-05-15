package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.PesquisaInstituicao;
import unipesquisas.model.entity.StatusCandidato;

public class PesquisaInstituicaoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (PesquisaInstituicao pi) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO pesquisainstituicao (idpesquisa, idinstituicao, idstatuscandidato) " +
						"VALUES (?, ?, ?)", new String[] { "idpesquisainstituicao" });
				stmt.setInt(1, pi.getPesquisa().getIdpesquisa());
				stmt.setInt(2, pi.getInstituicao().getIdinstituicao());
				if(pi.getStatuscandidato().getIdstatuscandidato()==0){
					stmt.setNull(3, java.sql.Types.INTEGER);
				}else{
					stmt.setInt(3, pi.getStatuscandidato().getIdstatuscandidato());
				}

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				pi.setIdpesquisainstituicao(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(PesquisaInstituicao pi, ResultSet rs) throws DAOException {
		try {
			pi.setIdpesquisainstituicao(rs.getInt("idpesquisainstituicao"));
			pi.getPesquisa().setIdpesquisa(rs.getInt("idpesquisa"));
			pi.getPesquisa().setTitulo(rs.getString("titulo"));
			pi.getPesquisa().setDescricao(rs.getString("descricao"));
			pi.getPesquisa().setData(rs.getDate("data"));
			pi.getPesquisa().setStatus(rs.getInt("status"));
			pi.getPesquisa().setIdempresa(rs.getInt("idempresa"));
			pi.getInstituicao().setIdinstituicao(rs.getInt("idinstituicao"));
			pi.getInstituicao().setInstituicao(rs.getString("instituicao"));
			if(rs.getString("statuscandidato") != null){
				pi.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				pi.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public PesquisaInstituicao carregar(Integer idPesquisa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pi.idpesquisainstituicao, pi.idpesquisa, pi.idinstituicao, " +
						"p.titulo, i.instituicao, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisainstituicao pi " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pi.idpesquisa " +
						"INNER JOIN instituicao i ON i.idinstituicao=pi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pi.idstatuscandidato " +
						"WHERE pi.idpesquisa = ?");
				stmt.setInt(1, idPesquisa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				PesquisaInstituicao pi = new PesquisaInstituicao();
				pi.setInstituicao(new Instituicao());
				pi.setPesquisa(new Pesquisa());
				pi.setStatuscandidato(new StatusCandidato());
				preencher(pi, rs);

				return pi;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaInstituicao> listaPesquisaInstituicao(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pi.idpesquisainstituicao, pi.idpesquisa, pi.idinstituicao, " +
						"p.titulo, i.instituicao, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisainstituicao pi " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pi.idpesquisa " +
						"INNER JOIN instituicao i ON i.idinstituicao=pi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pi.idstatuscandidato " +
						"WHERE p.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<PesquisaInstituicao> listaPI = new ArrayList<PesquisaInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaInstituicao pi = new PesquisaInstituicao();
					pi.setPesquisa(new Pesquisa());
					pi.setInstituicao(new Instituicao());
					pi.setStatuscandidato(new StatusCandidato());
					preencher(pi, rs);
					listaPI.add(pi);
				}

				return listaPI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean pesquisaPublicada(Integer idPesquisa, Integer idInstituicao,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				if(idStatusCandidato == 0){
					stmt = conn.prepareStatement("SELECT * " +
							"FROM pesquisainstituicao " +
							"WHERE idpesquisa = ? AND idinstituicao = ? AND idstatuscandidato is null");
				}else{
					stmt = conn.prepareStatement("SELECT * " +
							"FROM pesquisainstituicao " +
							"WHERE idpesquisa = ? AND idinstituicao = ? AND idstatuscandidato = ?");
				}
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idInstituicao);
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

	public List<PesquisaInstituicao> listarPesquisaInstituicaoDisponiveis(
			Integer idCandidato, Integer idEmpresa, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.status, pi.idpesquisainstituicao, pi.idinstituicao, i.instituicao, sc.status as statuscandidato " +
						"FROM pesquisainstituicao pi " +
						"INNER JOIN pesquisa p ON pi.idpesquisa=p.idpesquisa " +
						"INNER JOIN instituicao i ON i.idinstituicao=pi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pi.idstatuscandidato " +
						"WHERE p.status = 1 AND (sc.idstatuscandidato = ? OR sc.idstatuscandidato is null) " +
						"AND pi.idpesquisainstituicao NOT IN " +
						"(SELECT idpesquisainstituicao " +
						"FROM candidatopesquisainstituicao " +
						"WHERE idcandidato = ?) AND p.idempresa = ? AND i.idinstituicao IN (" +
						"SELECT ci.idinstituicao " +
						"FROM candidatoinstituicao ci " +
						"INNER JOIN instituicao i ON ci.idinstituicao=i.idinstituicao " +
						"WHERE ci.idcandidato = ? AND i.idempresa = ?) " +
						"ORDER BY p.data");
				stmt.setInt(1, idStatusCandidato);
				stmt.setInt(2, idCandidato);
				stmt.setInt(3, idEmpresa);
				stmt.setInt(4, idCandidato);
				stmt.setInt(5, idEmpresa);
				
				List<PesquisaInstituicao> listaPI = new ArrayList<PesquisaInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaInstituicao pi = new PesquisaInstituicao();
					pi.setPesquisa(new Pesquisa());
					pi.setInstituicao(new Instituicao());
					pi.setStatuscandidato(new StatusCandidato());
					preencher(pi, rs);
					listaPI.add(pi);
				}

				return listaPI;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaInstituicao> listarPublicacaoInstituicaoPorIdPesquisa(
			int idPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pi.idpesquisainstituicao, pi.idpesquisa, pi.idinstituicao, " +
						"p.titulo, i.instituicao, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisainstituicao pi " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pi.idpesquisa " +
						"INNER JOIN instituicao i ON i.idinstituicao=pi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pi.idstatuscandidato " +
						"WHERE p.idempresa = ? AND p.idpesquisa = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idPesquisa);
				
				
				List<PesquisaInstituicao> listaPI = new ArrayList<PesquisaInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaInstituicao pi = new PesquisaInstituicao();
					pi.setPesquisa(new Pesquisa());
					pi.setInstituicao(new Instituicao());
					pi.setStatuscandidato(new StatusCandidato());
					preencher(pi, rs);
					listaPI.add(pi);
				}

				return listaPI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaInstituicao> listarPublicacaoInstituicaoPorIdInstituicao(
			int idInstituicao, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pi.idpesquisainstituicao, pi.idpesquisa, pi.idinstituicao, " +
						"p.titulo, i.instituicao, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisainstituicao pi " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pi.idpesquisa " +
						"INNER JOIN instituicao i ON i.idinstituicao=pi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pi.idstatuscandidato " +
						"WHERE p.idempresa = ? AND i.idinstituicao = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idInstituicao);
				
				
				List<PesquisaInstituicao> listaPI = new ArrayList<PesquisaInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaInstituicao pi = new PesquisaInstituicao();
					pi.setPesquisa(new Pesquisa());
					pi.setInstituicao(new Instituicao());
					pi.setStatuscandidato(new StatusCandidato());
					preencher(pi, rs);
					listaPI.add(pi);
				}

				return listaPI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaInstituicao> listarPublicacaoInstituicaoPorInstituicao(
			String instituicao, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pi.idpesquisainstituicao, pi.idpesquisa, pi.idinstituicao, " +
						"p.titulo, i.instituicao, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisainstituicao pi " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pi.idpesquisa " +
						"INNER JOIN instituicao i ON i.idinstituicao=pi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pi.idstatuscandidato " +
						"WHERE p.idempresa = ? AND i.instituicao like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+instituicao+"%");
				
				
				List<PesquisaInstituicao> listaPI = new ArrayList<PesquisaInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaInstituicao pi = new PesquisaInstituicao();
					pi.setPesquisa(new Pesquisa());
					pi.setInstituicao(new Instituicao());
					pi.setStatuscandidato(new StatusCandidato());
					preencher(pi, rs);
					listaPI.add(pi);
				}

				return listaPI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaInstituicao> listarPublicacaoInstituicaoPorPesquisa(
			String tituloPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pi.idpesquisainstituicao, pi.idpesquisa, pi.idinstituicao, " +
						"p.titulo, i.instituicao, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisainstituicao pi " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pi.idpesquisa " +
						"INNER JOIN instituicao i ON i.idinstituicao=pi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pi.idstatuscandidato " +
						"WHERE p.idempresa = ? AND p.titulo like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+tituloPesquisa+"%");
				
				
				List<PesquisaInstituicao> listaPI = new ArrayList<PesquisaInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaInstituicao pi = new PesquisaInstituicao();
					pi.setPesquisa(new Pesquisa());
					pi.setInstituicao(new Instituicao());
					pi.setStatuscandidato(new StatusCandidato());
					preencher(pi, rs);
					listaPI.add(pi);
				}

				return listaPI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
