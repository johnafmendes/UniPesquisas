package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.Usuario;

public class PesquisaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (Pesquisa pesquisa) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO pesquisa (titulo, descricao, data, idusuario, idempresa, status) " +
						"VALUES (?, ?, ?, ?, ?, ?)", new String[] { "idpesquisa" });
				stmt.setString(1, pesquisa.getTitulo());
				stmt.setString(2, pesquisa.getDescricao());
				stmt.setDate(3, convertDate(pesquisa.getData()));
				stmt.setInt(4, pesquisa.getUsuario().getIdusuario());
				stmt.setInt(5, pesquisa.getIdempresa());
				stmt.setInt(6, pesquisa.getStatus());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				pesquisa.setIdpesquisa(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Pesquisa pesquisa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE pesquisa SET titulo = ?, descricao = ?, data = ?, idusuario = ?, " +
						"idempresa = ?, status = ? " +
						"WHERE idpesquisa = ?");
				stmt.setString(1, pesquisa.getTitulo());
				stmt.setString(2, pesquisa.getDescricao());
				stmt.setDate(3, convertDate(pesquisa.getData()));
				stmt.setInt(4, pesquisa.getUsuario().getIdusuario());
				stmt.setInt(5, pesquisa.getIdempresa());
				stmt.setInt(6, pesquisa.getStatus());
				stmt.setInt(7, pesquisa.getIdpesquisa());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar pesquisa = " + pesquisa.getIdpesquisa());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idPesquisa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM pesquisa WHERE idpesquisa = ?");
				stmt.setInt(1, idPesquisa);

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

	public List<Pesquisa> listarPesquisas(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.idusuario, p.status, u.nome " +
						"FROM pesquisa p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"WHERE p.idempresa = ? " +
						"ORDER BY p.data desc");
				stmt.setInt(1, idEmpresa);
				
				List<Pesquisa> listaPesquisas = new ArrayList<Pesquisa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pesquisa pesquisa = new Pesquisa();
					pesquisa.setUsuario(new Usuario());
					preencher(pesquisa, rs);
					listaPesquisas.add(pesquisa);
				}

				return listaPesquisas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Pesquisa pesquisa, ResultSet rs) throws DAOException {
		try {
			pesquisa.setIdpesquisa(rs.getInt("idpesquisa"));
			pesquisa.setTitulo(rs.getString("titulo"));
			pesquisa.setDescricao(rs.getString("descricao"));
			pesquisa.setData(rs.getDate("data"));
			pesquisa.getUsuario().setIdusuario(rs.getInt("idusuario"));
			pesquisa.getUsuario().setNome(rs.getString("nome"));
			pesquisa.setIdempresa(rs.getInt("idempresa"));
			pesquisa.setStatus(rs.getInt("status"));
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Pesquisa carregar(Integer idPesquisa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.idusuario, p.status, u.nome " +
						"FROM pesquisa p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"WHERE p.idpesquisa = ?");
				stmt.setInt(1, idPesquisa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Pesquisa pesquisa = new Pesquisa();
				pesquisa.setUsuario(new Usuario());
				preencher(pesquisa, rs);
				return pesquisa;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorTitulo(String tituloPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.idusuario, p.status, u.nome " +
						"FROM pesquisa p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"WHERE p.titulo like ? AND p.idempresa = ? " +
						"ORDER BY p.titulo");
				stmt.setString(1, "%"+tituloPesquisa+"%");
				stmt.setInt(2, idEmpresa);
				
				List<Pesquisa> listaPesquisas = new ArrayList<Pesquisa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pesquisa pesquisa = new Pesquisa();
					pesquisa.setUsuario(new Usuario());
					preencher(pesquisa, rs);
					listaPesquisas.add(pesquisa);
				}

				return listaPesquisas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorEscolaridade(Integer idEscolaridade,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.idusuario, p.status, u.nome " +
						"FROM pesquisa p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"INNER JOIN pesquisaescolaridade pe ON pe.idpesquisa=p.idpesquisa " +
						"WHERE pe.idescolaridade = ? AND p.idempresa = ? " +
						"ORDER BY p.titulo");
				stmt.setInt(1, idEscolaridade);
				stmt.setInt(2, idEmpresa);
				
				List<Pesquisa> listaPesquisas = new ArrayList<Pesquisa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pesquisa pesquisa = new Pesquisa();
					pesquisa.setUsuario(new Usuario());
					preencher(pesquisa, rs);
					listaPesquisas.add(pesquisa);
				}

				return listaPesquisas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorInstituicao(Integer idInstituicao,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.idusuario, p.status, u.nome " +
						"FROM pesquisa p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"INNER JOIN pesquisainstituicao pi ON pi.idpesquisa=p.idpesquisa " +
						"WHERE pi.idinstituicao = ? AND p.idempresa = ? " +
						"ORDER BY p.titulo");
				stmt.setInt(1, idInstituicao);
				stmt.setInt(2, idEmpresa);
				
				List<Pesquisa> listaPesquisas = new ArrayList<Pesquisa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pesquisa pesquisa = new Pesquisa();
					pesquisa.setUsuario(new Usuario());
					preencher(pesquisa, rs);
					listaPesquisas.add(pesquisa);
				}

				return listaPesquisas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorCurso(Integer idCurso,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.idusuario, p.status, u.nome " +
						"FROM pesquisa p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"INNER JOIN pesquisacurso pc ON pc.idpesquisa=p.idpesquisa " +
						"WHERE pc.idcurso = ? AND p.idempresa = ? " +
						"ORDER BY p.titulo");
				stmt.setInt(1, idCurso);
				stmt.setInt(2, idEmpresa);
				
				List<Pesquisa> listaPesquisas = new ArrayList<Pesquisa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pesquisa pesquisa = new Pesquisa();
					pesquisa.setUsuario(new Usuario());
					preencher(pesquisa, rs);
					listaPesquisas.add(pesquisa);
				}

				return listaPesquisas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Pesquisa> listarPesquisaPorID(int idPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
						"p.idusuario, p.status, u.nome " +
						"FROM pesquisa p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"WHERE p.idpesquisa = ? AND p.idempresa = ?");
				stmt.setInt(1, idPesquisa);
				stmt.setInt(2, idEmpresa);
				
				List<Pesquisa> listaPesquisas = new ArrayList<Pesquisa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Pesquisa pesquisa = new Pesquisa();
					pesquisa.setUsuario(new Usuario());
					preencher(pesquisa, rs);
					listaPesquisas.add(pesquisa);
				}

				return listaPesquisas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

//	public List<Pesquisa> listarPesquisasDisponiveis(Integer idCandidato) throws DAOException {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//
//		try {
//			try {
//				conn = getConnection();
//				stmt = conn.prepareStatement("SELECT p.idpesquisa, p.titulo, p.descricao, p.data, p.idempresa, " +
//						"p.idusuario, p.status, u.nome " +
//						"FROM pesquisa p " +
//						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
//						"INNER JOIN pesquisaescolaridade pe ON pe.idpesquisa=p.idpesquisa " +
//						"INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade " +
//						"INNER JOIN candidato c ON c.idescolaridade=e.idescolaridade " +
//						"INNER JOIN candidatopesquisaescolaridade cpe ON cpe.idpesquisaescolaridade=pe.idpesquisaescolaridade " +
//						"WHERE c.idcandidato = ? AND p.status = 1 AND cpe.status = 0 " +
//						"ORDER BY p.data");
//				stmt.setInt(1, idCandidato);
//				
//				List<Pesquisa> listaPesquisas = new ArrayList<Pesquisa>();
//
//				ResultSet rs = stmt.executeQuery();
//
//				while (rs.next()) {
//					Pesquisa pesquisa = new Pesquisa();
//					pesquisa.setUsuario(new Usuario());
//					preencher(pesquisa, rs);
//					listaPesquisas.add(pesquisa);
//				}
//
//				return listaPesquisas;
//
//			} finally {
//				closeStatement(stmt);
//				closeConnection();
//			}
//		} catch (SQLException e) {
//			throw new DAOException(e);
//		}
//	}
	
}
