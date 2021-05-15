package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.PesquisaCurso;
import unipesquisas.model.entity.StatusCandidato;

public class PesquisaCursoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (PesquisaCurso pc) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO pesquisacurso (idpesquisa, idcurso, idstatuscandidato) " +
						"VALUES (?, ?, ?)", new String[] { "idpesquisacurso" });
				stmt.setInt(1, pc.getPesquisa().getIdpesquisa());
				stmt.setInt(2, pc.getCurso().getIdcurso());
				if(pc.getStatuscandidato().getIdstatuscandidato()==0){
					stmt.setNull(3, java.sql.Types.INTEGER);
				}else{
					stmt.setInt(3, pc.getStatuscandidato().getIdstatuscandidato());
				}
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				pc.setIdpesquisacurso(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(PesquisaCurso pc, ResultSet rs) throws DAOException {
		try {
			pc.setIdpesquisacurso(rs.getInt("idpesquisacurso"));
			pc.getPesquisa().setIdpesquisa(rs.getInt("idpesquisa"));
			pc.getPesquisa().setTitulo(rs.getString("titulo"));
			pc.getPesquisa().setDescricao(rs.getString("descricao"));
			pc.getPesquisa().setData(rs.getDate("data"));
			pc.getPesquisa().setStatus(rs.getInt("status"));
			pc.getPesquisa().setIdempresa(rs.getInt("idempresa"));
			pc.getCurso().setIdcurso(rs.getInt("idcurso"));
			pc.getCurso().setCurso(rs.getString("curso"));
			if(rs.getString("statuscandidato") != null){
				pc.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				pc.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public PesquisaCurso carregar(Integer idPesquisa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pc.idpesquisacurso, pc.idpesquisa, pc.idcurso, " +
						"p.titulo, c.curso, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisacurso pc " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pc.idpesquisa " +
						"INNER JOIN curso c ON c.idcurso=pc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pc.idstatuscandidato " +
						"WHERE pc.idpesquisa = ?");
				stmt.setInt(1, idPesquisa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				PesquisaCurso pc = new PesquisaCurso();
				pc.setCurso(new Curso());
				pc.setPesquisa(new Pesquisa());
				pc.setStatuscandidato(new StatusCandidato());
				preencher(pc, rs);

				return pc;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaCurso> listaPesquisaCurso(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pc.idpesquisacurso, pc.idpesquisa, pc.idcurso, " +
						"p.titulo, c.curso, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisacurso pc " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pc.idpesquisa " +
						"INNER JOIN curso c ON c.idcurso=pc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pc.idstatuscandidato " +
						"WHERE p.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<PesquisaCurso> listaPC = new ArrayList<PesquisaCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaCurso pc = new PesquisaCurso();
					pc.setPesquisa(new Pesquisa());
					pc.setCurso(new Curso());
					pc.setStatuscandidato(new StatusCandidato());
					preencher(pc, rs);
					listaPC.add(pc);
				}

				return listaPC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean pesquisaPublicada(Integer idPesquisa, Integer idCurso,
			Integer idStatusCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				if(idStatusCandidato == 0){
					stmt = conn.prepareStatement("SELECT * " +
							"FROM pesquisacurso " +
							"WHERE idpesquisa = ? AND idcurso = ? AND idstatuscandidato is null");
				}else{
					stmt = conn.prepareStatement("SELECT * " +
							"FROM pesquisacurso " +
							"WHERE idpesquisa = ? AND idcurso = ? AND idstatuscandidato = ?");
				}
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idCurso);
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

	public List<PesquisaCurso> listarPesquisaCursoDisponiveis(
			Integer idCandidato, Integer idEmpresa, Integer idStatusCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.status, pc.idpesquisacurso, pc.idcurso, c.curso, sc.status as statuscandidato " +
						"FROM pesquisacurso pc " +
						"INNER JOIN pesquisa p ON pc.idpesquisa=p.idpesquisa " +
						"INNER JOIN curso c ON c.idcurso=pc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pc.idstatuscandidato " +
						"WHERE p.status = 1 AND (sc.idstatuscandidato = ? OR sc.idstatuscandidato is null) " +
						"AND pc.idpesquisacurso NOT IN " +
						"(SELECT idpesquisacurso " +
						"FROM candidatopesquisacurso " +
						"WHERE idcandidato = ?) AND p.idempresa = ? AND c.idcurso IN " + 
						"(SELECT cc.idcurso " +
						"FROM candidatocurso cc " +
						"INNER JOIN curso c ON cc.idcurso=c.idcurso " +
						"INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao " +
						"WHERE cc.idcandidato = ? AND i.idempresa = ?) " +
						"ORDER BY p.data");
				stmt.setInt(1, idStatusCandidato);
				stmt.setInt(2, idCandidato);
				stmt.setInt(3, idEmpresa);
				stmt.setInt(4, idCandidato);
				stmt.setInt(5, idEmpresa);
				
				List<PesquisaCurso> listaPC = new ArrayList<PesquisaCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaCurso pc = new PesquisaCurso();
					pc.setPesquisa(new Pesquisa());
					pc.setCurso(new Curso());
					pc.setStatuscandidato(new StatusCandidato());
					preencher(pc, rs);
					listaPC.add(pc);
				}

				return listaPC;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaCurso> listarPublicacaoCursoPorIdPesquisa(
			int idPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pc.idpesquisacurso, pc.idpesquisa, pc.idcurso, " +
						"p.titulo, c.curso, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisacurso pc " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pc.idpesquisa " +
						"INNER JOIN curso c ON c.idcurso=pc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pc.idstatuscandidato " +
						"WHERE p.idempresa = ? AND p.idpesquisa = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idPesquisa);
				
				
				List<PesquisaCurso> listaPC = new ArrayList<PesquisaCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaCurso pc = new PesquisaCurso();
					pc.setPesquisa(new Pesquisa());
					pc.setCurso(new Curso());
					pc.setStatuscandidato(new StatusCandidato());
					preencher(pc, rs);
					listaPC.add(pc);
				}

				return listaPC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaCurso> listarPublicacaoCursoPorIdCurso(int idCurso,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pc.idpesquisacurso, pc.idpesquisa, pc.idcurso, " +
						"p.titulo, c.curso, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisacurso pc " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pc.idpesquisa " +
						"INNER JOIN curso c ON c.idcurso=pc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pc.idstatuscandidato " +
						"WHERE p.idempresa = ? AND c.idcurso = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idCurso);
				
				
				List<PesquisaCurso> listaPC = new ArrayList<PesquisaCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaCurso pc = new PesquisaCurso();
					pc.setPesquisa(new Pesquisa());
					pc.setCurso(new Curso());
					pc.setStatuscandidato(new StatusCandidato());
					preencher(pc, rs);
					listaPC.add(pc);
				}

				return listaPC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaCurso> listarPublicacaoCursoPorCurso(
			String tituloCurso, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pc.idpesquisacurso, pc.idpesquisa, pc.idcurso, " +
						"p.titulo, c.curso, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisacurso pc " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pc.idpesquisa " +
						"INNER JOIN curso c ON c.idcurso=pc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pc.idstatuscandidato " +
						"WHERE p.idempresa = ? AND c.curso like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+tituloCurso+"%");
				
				
				List<PesquisaCurso> listaPC = new ArrayList<PesquisaCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaCurso pc = new PesquisaCurso();
					pc.setPesquisa(new Pesquisa());
					pc.setCurso(new Curso());
					pc.setStatuscandidato(new StatusCandidato());
					preencher(pc, rs);
					listaPC.add(pc);
				}

				return listaPC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<PesquisaCurso> listarPublicacaoCursoPorPesquisa(
			String tituloPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT pc.idpesquisacurso, pc.idpesquisa, pc.idcurso, " +
						"p.titulo, c.curso, p.idpesquisa, p.descricao, p.data, p.idempresa, p.status, " +
						"sc.status as statuscandidato " +
						"FROM pesquisacurso pc " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pc.idpesquisa " +
						"INNER JOIN curso c ON c.idcurso=pc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=pc.idstatuscandidato " +
						"WHERE p.idempresa = ? AND p.titulo like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+tituloPesquisa+"%");
				
				
				List<PesquisaCurso> listaPC = new ArrayList<PesquisaCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					PesquisaCurso pc = new PesquisaCurso();
					pc.setPesquisa(new Pesquisa());
					pc.setCurso(new Curso());
					pc.setStatuscandidato(new StatusCandidato());
					preencher(pc, rs);
					listaPC.add(pc);
				}

				return listaPC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
