package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailMarketingEscolaridade;
import unipesquisas.model.entity.Escolaridade;
import unipesquisas.model.entity.StatusCandidato;

public class EmailMarketingEscolaridadeDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (EmailMarketingEscolaridade eme) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO emailmarketingescolaridade (idemailmarketing, idescolaridade, data, " +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?)", new String[] { "idemailmarketingescolaridade" });
				stmt.setInt(1, eme.getEmailmarketing().getIdemailmarketing());
				stmt.setInt(2, eme.getEscolaridade().getIdescolaridade());
				stmt.setDate(3, convertDate(eme.getData()));
				if(eme.getStatuscandidato().getIdstatuscandidato() == 0){
					stmt.setNull(4, java.sql.Types.INTEGER);
				}else{
					stmt.setInt(4, eme.getStatuscandidato().getIdstatuscandidato());
				}

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				eme.setIdemailmarketingescolaridade(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(EmailMarketingEscolaridade eme, ResultSet rs) throws DAOException {
		try {
			eme.setIdemailmarketingescolaridade(rs.getInt("idemailmarketingescolaridade"));
			eme.getEmailmarketing().setIdemailmarketing(rs.getInt("idemailmarketing"));
			eme.getEmailmarketing().setAssunto(rs.getString("assunto"));
			eme.getEmailmarketing().setMensagem(rs.getString("mensagem"));
			eme.getEmailmarketing().setData(rs.getDate("data"));
			eme.getEmailmarketing().setStatus(rs.getInt("status"));
			eme.getEmailmarketing().setIdempresa(rs.getInt("idempresa"));
			eme.getEscolaridade().setIdescolaridade(rs.getInt("idescolaridade"));
			eme.getEscolaridade().setEscolaridade(rs.getString("escolaridade"));
			eme.setData(rs.getDate("datapublicacao"));
			if(rs.getString("statuscandidato") != null){
				eme.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				eme.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public EmailMarketingEscolaridade carregar(Integer idEmailMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT eme.idemailmarketingescolaridade, eme.idemailmarketing, eme.idescolaridade, " +
						"em.assunto, e.escolaridade, em.idemailmarketing, dm.mensagem, em.data, em.idempresa, em.status, " +
						"eme.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingescolaridade eme " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=eme.idemailmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=eme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=eme.idstatuscandidato " +
						"WHERE eme.idemailmarketing = ?");
				stmt.setInt(1, idEmailMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				EmailMarketingEscolaridade eme = new EmailMarketingEscolaridade();
				eme.setEscolaridade(new Escolaridade());
				eme.setEmailmarketing(new EmailMarketing());
				eme.setStatuscandidato(new StatusCandidato());
				preencher(eme, rs);

				return eme;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listaEmailMarketingEscolaridade(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT eme.idemailmarketingescolaridade, eme.idemailmarketing, " +
						"eme.idescolaridade, em.assunto, e.escolaridade, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, eme.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingescolaridade eme " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=eme.idemailmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=eme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=eme.idstatuscandidato " +
						"WHERE em.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<EmailMarketingEscolaridade> listaEME = new ArrayList<EmailMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingEscolaridade eme = new EmailMarketingEscolaridade();
					eme.setEmailmarketing(new EmailMarketing());
					eme.setEscolaridade(new Escolaridade());
					eme.setStatuscandidato(new StatusCandidato());
					preencher(eme, rs);
					listaEME.add(eme);
				}

				return listaEME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean emailPublicado(Integer idEmailMarketing, Integer idEscolaridade) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT * " +
						"FROM emailmarketingescolaridade " +
						"WHERE idemailmarketing = ? AND idescolaridade = ?");
				stmt.setInt(1, idEmailMarketing);
				stmt.setInt(2, idEscolaridade);
				
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

	public List<EmailMarketingEscolaridade> listaPublicacaoEmailMarketingPorIdEmail(
			int idEmail, int idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT eme.idemailmarketingescolaridade, eme.idemailmarketing, " +
						"eme.idescolaridade, em.assunto, e.escolaridade, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, eme.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingescolaridade eme " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=eme.idemailmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=eme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=eme.idstatuscandidato " +
						"WHERE em.idempresa = ? AND em.idemailmarketing = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmail);
				
				
				List<EmailMarketingEscolaridade> listaEME = new ArrayList<EmailMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingEscolaridade eme = new EmailMarketingEscolaridade();
					eme.setEmailmarketing(new EmailMarketing());
					eme.setEscolaridade(new Escolaridade());
					eme.setStatuscandidato(new StatusCandidato());
					preencher(eme, rs);
					listaEME.add(eme);
				}

				return listaEME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listaPublicacaoEmailMarketingPorIdEscolaridade(
			int idEscolaridade, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT eme.idemailmarketingescolaridade, eme.idemailmarketing, " +
						"eme.idescolaridade, em.assunto, e.escolaridade, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, eme.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingescolaridade eme " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=eme.idemailmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=eme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=eme.idstatuscandidato " +
						"WHERE em.idempresa = ? AND e.idescolaridade = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEscolaridade);
				
				
				List<EmailMarketingEscolaridade> listaEME = new ArrayList<EmailMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingEscolaridade eme = new EmailMarketingEscolaridade();
					eme.setEmailmarketing(new EmailMarketing());
					eme.setEscolaridade(new Escolaridade());
					eme.setStatuscandidato(new StatusCandidato());
					preencher(eme, rs);
					listaEME.add(eme);
				}

				return listaEME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listaPublicacaoEmailMarketingPorEscolaridade(
			String escolaridade, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT eme.idemailmarketingescolaridade, eme.idemailmarketing, " +
						"eme.idescolaridade, em.assunto, e.escolaridade, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, eme.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingescolaridade eme " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=eme.idemailmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=eme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=eme.idstatuscandidato " +
						"WHERE em.idempresa = ? AND e.escolaridade like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%" + escolaridade + "%");
				
				
				List<EmailMarketingEscolaridade> listaEME = new ArrayList<EmailMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingEscolaridade eme = new EmailMarketingEscolaridade();
					eme.setEmailmarketing(new EmailMarketing());
					eme.setEscolaridade(new Escolaridade());
					eme.setStatuscandidato(new StatusCandidato());
					preencher(eme, rs);
					listaEME.add(eme);
				}

				return listaEME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingEscolaridade> listaPublicacaoEmailMarketingPorAssunto(
			String assuntoEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT eme.idemailmarketingescolaridade, eme.idemailmarketing, " +
						"eme.idescolaridade, em.assunto, e.escolaridade, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, eme.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingescolaridade eme " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=eme.idemailmarketing " +
						"INNER JOIN escolaridade e ON e.idescolaridade=eme.idescolaridade " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=eme.idstatuscandidato " +
						"WHERE em.idempresa = ? AND em.assunto like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%" + assuntoEmail + "%");
				
				
				List<EmailMarketingEscolaridade> listaEME = new ArrayList<EmailMarketingEscolaridade>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingEscolaridade eme = new EmailMarketingEscolaridade();
					eme.setEmailmarketing(new EmailMarketing());
					eme.setEscolaridade(new Escolaridade());
					eme.setStatuscandidato(new StatusCandidato());
					preencher(eme, rs);
					listaEME.add(eme);
				}

				return listaEME;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
