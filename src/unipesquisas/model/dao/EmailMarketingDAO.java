package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.Usuario;

public class EmailMarketingDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (EmailMarketing em) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO emailmarketing (assunto, mensagem, data, idusuario, idempresa, status) " +
						"VALUES (?, ?, ?, ?, ?, ?)", new String[] { "idpesquisa" });
				stmt.setString(1, em.getAssunto());
				stmt.setString(2, em.getMensagem());
				stmt.setDate(3, convertDate(em.getData()));
				stmt.setInt(4, em.getUsuario().getIdusuario());
				stmt.setInt(5, em.getIdempresa());
				stmt.setInt(6, em.getStatus());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				em.setIdemailmarketing(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(EmailMarketing em) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE emailmarketing SET assunto = ?, mensagem = ?, data = ?, idusuario = ?, " +
						"idempresa = ?, status = ? " +
						"WHERE idemailmarketing = ?");
				stmt.setString(1, em.getAssunto());
				stmt.setString(2, em.getMensagem());
				stmt.setDate(3, convertDate(em.getData()));
				stmt.setInt(4, em.getUsuario().getIdusuario());
				stmt.setInt(5, em.getIdempresa());
				stmt.setInt(6, em.getStatus());
				stmt.setInt(7, em.getIdemailmarketing());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar pesquisa = " + em.getIdemailmarketing());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idEmailMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM emailmarketing WHERE idemailmarketing = ?");
				stmt.setInt(1, idEmailMarketing);

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

	public List<EmailMarketing> listarEmailMarketing(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT e.idemailmarketing, e.assunto, e.mensagem, e.data, e.idempresa, " +
						"e.idusuario, e.status, u.nome " +
						"FROM emailmarketing e " +
						"INNER JOIN usuario u ON e.idusuario=u.idusuario " +
						"WHERE e.idempresa = ? " +
						"ORDER BY e.data desc");
				stmt.setInt(1, idEmpresa);
				
				List<EmailMarketing> listaEmailMarketing = new ArrayList<EmailMarketing>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketing em = new EmailMarketing();
					em.setUsuario(new Usuario());
					preencher(em, rs);
					listaEmailMarketing.add(em);
				}

				return listaEmailMarketing;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(EmailMarketing em, ResultSet rs) throws DAOException {
		try {
			em.setIdemailmarketing(rs.getInt("idemailmarketing"));
			em.setAssunto(rs.getString("assunto"));
			em.setMensagem(rs.getString("mensagem"));
			em.setData(rs.getDate("data"));
			em.getUsuario().setIdusuario(rs.getInt("idusuario"));
			em.getUsuario().setNome(rs.getString("nome"));
			em.setIdempresa(rs.getInt("idempresa"));
			em.setStatus(rs.getInt("status"));
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public EmailMarketing carregar(Integer idEmailMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT e.idemailmarketing, e.assunto, e.mensagem, e.data, e.idempresa, " +
						"e.idusuario, e.status, u.nome " +
						"FROM emailmarketing e " +
						"INNER JOIN usuario u ON e.idusuario=u.idusuario " +
						"WHERE e.idemailmarketing = ?");
				stmt.setInt(1, idEmailMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				EmailMarketing em = new EmailMarketing();
				em.setUsuario(new Usuario());
				preencher(em, rs);
				return em;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketing> listarEmailMarketingPorAssunto(String assuntoEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT em.idemailmarketing, em.assunto, em.mensagem, em.data, em.idempresa, " +
						"em.idusuario, em.status, u.nome " +
						"FROM emailmarketing em " +
						"INNER JOIN usuario u ON em.idusuario=u.idusuario " +
						"WHERE em.assunto like ? AND em.idempresa = ? " +
						"ORDER BY em.assunto");
				stmt.setString(1, "%"+assuntoEmail+"%");
				stmt.setInt(2, idEmpresa);
				
				List<EmailMarketing> listaEM = new ArrayList<EmailMarketing>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketing em = new EmailMarketing();
					em.setUsuario(new Usuario());
					preencher(em, rs);
					listaEM.add(em);
				}

				return listaEM;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketing> listarEmailMarketingPorId(int idEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT em.idemailmarketing, em.assunto, em.mensagem, em.data, em.idempresa, " +
						"em.idusuario, em.status, u.nome " +
						"FROM emailmarketing em " +
						"INNER JOIN usuario u ON em.idusuario=u.idusuario " +
						"WHERE em.idemailmarketing = ? AND em.idempresa = ? " +
						"ORDER BY em.assunto");
				stmt.setInt(1, idEmail);
				stmt.setInt(2, idEmpresa);
				
				List<EmailMarketing> listaEM = new ArrayList<EmailMarketing>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketing em = new EmailMarketing();
					em.setUsuario(new Usuario());
					preencher(em, rs);
					listaEM.add(em);
				}

				return listaEM;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

//	public List<Pesquisa> listarPesquisaPorEscolaridade(Integer idEscolaridade,
//			Integer idEmpresa) throws DAOException {
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
//						"WHERE pe.idescolaridade = ? AND p.idempresa = ? " +
//						"ORDER BY p.titulo");
//				stmt.setInt(1, idEscolaridade);
//				stmt.setInt(2, idEmpresa);
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
//
//	public List<Pesquisa> listarPesquisaPorInstituicao(Integer idInstituicao,
//			Integer idEmpresa) throws DAOException {
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
//						"INNER JOIN pesquisainstituicao pi ON pi.idpesquisa=p.idpesquisa " +
//						"WHERE pi.idinstituicao = ? AND p.idempresa = ? " +
//						"ORDER BY p.titulo");
//				stmt.setInt(1, idInstituicao);
//				stmt.setInt(2, idEmpresa);
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
//
//	public List<Pesquisa> listarPesquisaPorCurso(Integer idCurso,
//			Integer idEmpresa) throws DAOException {
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
//						"INNER JOIN pesquisacurso pc ON pc.idpesquisa=p.idpesquisa " +
//						"WHERE pc.idcurso = ? AND p.idempresa = ? " +
//						"ORDER BY p.titulo");
//				stmt.setInt(1, idCurso);
//				stmt.setInt(2, idEmpresa);
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
