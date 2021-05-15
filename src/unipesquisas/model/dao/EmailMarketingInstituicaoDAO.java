package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailMarketingInstituicao;
import unipesquisas.model.entity.Instituicao;
import unipesquisas.model.entity.StatusCandidato;

public class EmailMarketingInstituicaoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (EmailMarketingInstituicao emi) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO emailmarketinginstituicao (idemailmarketing, idinstituicao, data, " +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?)", new String[] { "idemailmarketinginstituicao" });
				stmt.setInt(1, emi.getEmailmarketing().getIdemailmarketing());
				stmt.setInt(2, emi.getInstituicao().getIdinstituicao());
				stmt.setDate(3, convertDate(emi.getData()));
				if(emi.getStatuscandidato().getIdstatuscandidato() == 0){
					stmt.setNull(4, java.sql.Types.INTEGER);
				}else{
					stmt.setInt(4, emi.getStatuscandidato().getIdstatuscandidato());
				}

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				emi.setIdemailmarketinginstituicao(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(EmailMarketingInstituicao emi, ResultSet rs) throws DAOException {
		try {
			emi.setIdemailmarketinginstituicao(rs.getInt("idemailmarketinginstituicao"));
			emi.getEmailmarketing().setIdemailmarketing(rs.getInt("idemailmarketing"));
			emi.getEmailmarketing().setAssunto(rs.getString("assunto"));
			emi.getEmailmarketing().setMensagem(rs.getString("mensagem"));
			emi.getEmailmarketing().setData(rs.getDate("data"));
			emi.getEmailmarketing().setStatus(rs.getInt("status"));
			emi.getEmailmarketing().setIdempresa(rs.getInt("idempresa"));
			emi.getInstituicao().setIdinstituicao(rs.getInt("idinstituicao"));
			emi.getInstituicao().setInstituicao(rs.getString("instituicao"));
			emi.setData(rs.getDate("datapublicacao"));
			if(rs.getString("statuscandidato") != null){
				emi.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				emi.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public EmailMarketingInstituicao carregar(Integer idEmailMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emi.idemailmarketinginstituicao, emi.idemailmarketing, emi.idinstituicao, " +
						"em.assunto, i.instituicao, em.idemailmarketing, dm.mensagem, em.data, em.idempresa, em.status, " +
						"emi.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketinginstituicao emi " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emi.idemailmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=emi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emi.idstatuscandidato " +
						"WHERE emi.idemailmarketing = ?");
				stmt.setInt(1, idEmailMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				EmailMarketingInstituicao emi = new EmailMarketingInstituicao();
				emi.setInstituicao(new Instituicao());
				emi.setEmailmarketing(new EmailMarketing());
				emi.setStatuscandidato(new StatusCandidato());
				preencher(emi, rs);

				return emi;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingInstituicao> listaEmailMarketingInstituicao(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emi.idemailmarketinginstituicao, emi.idemailmarketing, " +
						"emi.idinstituicao, em.assunto, i.instituicao, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emi.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketinginstituicao emi " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emi.idemailmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=emi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emi.idstatuscandidato " +
						"WHERE em.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<EmailMarketingInstituicao> listaEMI = new ArrayList<EmailMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingInstituicao emi = new EmailMarketingInstituicao();
					emi.setEmailmarketing(new EmailMarketing());
					emi.setInstituicao(new Instituicao());
					emi.setStatuscandidato(new StatusCandidato());
					preencher(emi, rs);
					listaEMI.add(emi);
				}

				return listaEMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean emailPublicado(Integer idEmailMarketing, Integer idInstituicao) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT * " +
						"FROM emailmarketinginstituicao " +
						"WHERE idemailmarketing = ? AND idinstituicao = ?");
				stmt.setInt(1, idEmailMarketing);
				stmt.setInt(2, idInstituicao);
				
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

	public List<EmailMarketingInstituicao> listaPublicacaoEmailMarketingPorIdEmail(
			int idEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emi.idemailmarketinginstituicao, emi.idemailmarketing, " +
						"emi.idinstituicao, em.assunto, i.instituicao, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emi.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketinginstituicao emi " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emi.idemailmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=emi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emi.idstatuscandidato " +
						"WHERE em.idempresa = ? AND em.idemailmarketing = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmail);
				
				
				List<EmailMarketingInstituicao> listaEMI = new ArrayList<EmailMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingInstituicao emi = new EmailMarketingInstituicao();
					emi.setEmailmarketing(new EmailMarketing());
					emi.setInstituicao(new Instituicao());
					emi.setStatuscandidato(new StatusCandidato());
					preencher(emi, rs);
					listaEMI.add(emi);
				}

				return listaEMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingInstituicao> listaPublicacaoEmailMarketingPorIdInstituicao(
			int idInstituicao, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emi.idemailmarketinginstituicao, emi.idemailmarketing, " +
						"emi.idinstituicao, em.assunto, i.instituicao, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emi.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketinginstituicao emi " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emi.idemailmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=emi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emi.idstatuscandidato " +
						"WHERE em.idempresa = ? AND i.idinstituicao = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idInstituicao);
				
				
				List<EmailMarketingInstituicao> listaEMI = new ArrayList<EmailMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingInstituicao emi = new EmailMarketingInstituicao();
					emi.setEmailmarketing(new EmailMarketing());
					emi.setInstituicao(new Instituicao());
					emi.setStatuscandidato(new StatusCandidato());
					preencher(emi, rs);
					listaEMI.add(emi);
				}

				return listaEMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingInstituicao> listaPublicacaoEmailMarketingPorInstituicao(
			String instituicao, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emi.idemailmarketinginstituicao, emi.idemailmarketing, " +
						"emi.idinstituicao, em.assunto, i.instituicao, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emi.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketinginstituicao emi " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emi.idemailmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=emi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emi.idstatuscandidato " +
						"WHERE em.idempresa = ? AND i.instituicao like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+instituicao+"%");
				
				
				List<EmailMarketingInstituicao> listaEMI = new ArrayList<EmailMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingInstituicao emi = new EmailMarketingInstituicao();
					emi.setEmailmarketing(new EmailMarketing());
					emi.setInstituicao(new Instituicao());
					emi.setStatuscandidato(new StatusCandidato());
					preencher(emi, rs);
					listaEMI.add(emi);
				}

				return listaEMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingInstituicao> listaPublicacaoEmailMarketingPorAssunto(
			String assuntoEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emi.idemailmarketinginstituicao, emi.idemailmarketing, " +
						"emi.idinstituicao, em.assunto, i.instituicao, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emi.data as datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketinginstituicao emi " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emi.idemailmarketing " +
						"INNER JOIN instituicao i ON i.idinstituicao=emi.idinstituicao " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emi.idstatuscandidato " +
						"WHERE em.idempresa = ? AND em.assunto like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+assuntoEmail+"%");
				
				
				List<EmailMarketingInstituicao> listaEMI = new ArrayList<EmailMarketingInstituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingInstituicao emi = new EmailMarketingInstituicao();
					emi.setEmailmarketing(new EmailMarketing());
					emi.setInstituicao(new Instituicao());
					emi.setStatuscandidato(new StatusCandidato());
					preencher(emi, rs);
					listaEMI.add(emi);
				}

				return listaEMI;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
