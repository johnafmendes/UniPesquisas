package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailMarketingAlternativa;
import unipesquisas.model.entity.Pergunta;
import unipesquisas.model.entity.Pesquisa;
import unipesquisas.model.entity.StatusCandidato;

public class EmailMarketingAlternativaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (EmailMarketingAlternativa ema) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO emailmarketingalternativa (idemailmarketing, idpergunta, " +
						"alternativa, idpesquisa, data, idstatuscandidato) " +
						"VALUES (?, ?, ?, ?, ?, ?)", new String[] { "idemailmarketingalternativa" });
				stmt.setInt(1, ema.getEmailmarketing().getIdemailmarketing());
				stmt.setInt(2, ema.getPergunta().getIdpergunta());
				stmt.setString(3, ema.getAlternativa());
				stmt.setInt(4, ema.getPesquisa().getIdpesquisa());
				stmt.setDate(5, convertDate(ema.getData()));
				if(ema.getStatuscandidato().getIdstatuscandidato() == 0){
					stmt.setNull(6, java.sql.Types.INTEGER);
				}else{
					stmt.setInt(6, ema.getStatuscandidato().getIdstatuscandidato());
				}
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				ema.setIdemailmarketingalternativa(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(EmailMarketingAlternativa ema, ResultSet rs) throws DAOException {
		try {
			ema.setIdemailmarketingalternativa(rs.getInt("idemailmarketingalternativa"));
			ema.getEmailmarketing().setIdemailmarketing(rs.getInt("idemailmarketing"));
			ema.getEmailmarketing().setAssunto(rs.getString("assunto"));
			ema.getEmailmarketing().setMensagem(rs.getString("mensagem"));
			ema.getEmailmarketing().setData(rs.getDate("data"));
			ema.getEmailmarketing().setStatus(rs.getInt("status"));
			ema.getEmailmarketing().setIdempresa(rs.getInt("idempresa"));
			ema.getPergunta().setIdpergunta(rs.getInt("idpergunta"));
			ema.getPergunta().setPergunta(rs.getString("pergunta"));
			ema.getPesquisa().setIdpesquisa(rs.getInt("idpesquisa"));
			ema.getPesquisa().setTitulo(rs.getString("titulo"));
			ema.setAlternativa(rs.getString("alternativa"));
			ema.setData(rs.getDate("datapublicacao"));
			if(rs.getString("statuscandidato") != null){
				ema.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				ema.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public EmailMarketingAlternativa carregar(Integer idEmailMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ema.idemailmarketingalternativa, ema.idemailmarketing, ema.idpergunta, " +
						"em.assunto, p.pergunta, em.idemailmarketing, dm.mensagem, em.data, em.idempresa, em.status, " +
						"ema.alternativa, pes.idpesquisa, pes.titulo, ema.data AS datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM emailmarketingalternativa ema " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=ema.idemailmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=ema.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=ema.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=ema.idstatuscandidato " +
						"WHERE ema.idemailmarketing = ?");
				stmt.setInt(1, idEmailMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				EmailMarketingAlternativa ema = new EmailMarketingAlternativa();
				ema.setPergunta(new Pergunta());
				ema.setEmailmarketing(new EmailMarketing());
				ema.setPesquisa(new Pesquisa());
				ema.setStatuscandidato(new StatusCandidato());
				preencher(ema, rs);

				return ema;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingAlternativa> listaEmailMarketingAlternativa(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ema.idemailmarketingalternativa, ema.idemailmarketing, " +
						"ema.idpergunta, em.assunto, p.pergunta, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, ema.alternativa, pes.idpesquisa, pes.titulo, " +
						"ema.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingalternativa ema " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=ema.idemailmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=ema.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=ema.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=ema.idstatuscandidato " +
						"WHERE em.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<EmailMarketingAlternativa> listaEMA = new ArrayList<EmailMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingAlternativa ema = new EmailMarketingAlternativa();
					ema.setEmailmarketing(new EmailMarketing());
					ema.setPergunta(new Pergunta());
					ema.setPesquisa(new Pesquisa());
					ema.setStatuscandidato(new StatusCandidato());
					preencher(ema, rs);
					listaEMA.add(ema);
				}

				return listaEMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean emailPublicado(Integer idEmailMarketing, Integer idPergunta, 
			Integer idPesquisa, String alternativa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT * " +
						"FROM emailmarketingalternativa " +
						"WHERE idemailmarketing = ? AND idpergunta = ? AND idpesquisa = ? AND alternativa = ?");
				stmt.setInt(1, idEmailMarketing);
				stmt.setInt(2, idPergunta);
				stmt.setInt(3, idPesquisa);
				stmt.setString(4, alternativa);
				
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

	public List<EmailMarketingAlternativa> listaPublicacaoEmailMarketingPorIdEmail(
			int idEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ema.idemailmarketingalternativa, ema.idemailmarketing, " +
						"ema.idpergunta, em.assunto, p.pergunta, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, ema.alternativa, pes.idpesquisa, pes.titulo, " +
						"ema.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingalternativa ema " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=ema.idemailmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=ema.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=ema.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=ema.idstatuscandidato " +
						"WHERE em.idempresa = ? AND em.idemailmarketing = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmail);
				
				
				List<EmailMarketingAlternativa> listaEMA = new ArrayList<EmailMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingAlternativa ema = new EmailMarketingAlternativa();
					ema.setEmailmarketing(new EmailMarketing());
					ema.setPergunta(new Pergunta());
					ema.setPesquisa(new Pesquisa());
					ema.setStatuscandidato(new StatusCandidato());
					preencher(ema, rs);
					listaEMA.add(ema);
				}

				return listaEMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingAlternativa> listaPublicacaoEmailMarketingPorIdPergunta(
			int idPergunta, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ema.idemailmarketingalternativa, ema.idemailmarketing, " +
						"ema.idpergunta, em.assunto, p.pergunta, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, ema.alternativa, pes.idpesquisa, pes.titulo, " +
						"ema.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingalternativa ema " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=ema.idemailmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=ema.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=ema.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=ema.idstatuscandidato " +
						"WHERE em.idempresa = ? AND p.idpergunta = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idPergunta);
				
				
				List<EmailMarketingAlternativa> listaEMA = new ArrayList<EmailMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingAlternativa ema = new EmailMarketingAlternativa();
					ema.setEmailmarketing(new EmailMarketing());
					ema.setPergunta(new Pergunta());
					ema.setPesquisa(new Pesquisa());
					ema.setStatuscandidato(new StatusCandidato());
					preencher(ema, rs);
					listaEMA.add(ema);
				}

				return listaEMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingAlternativa> listaPublicacaoEmailMarketingPorPergunta(
			String pergunta, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ema.idemailmarketingalternativa, ema.idemailmarketing, " +
						"ema.idpergunta, em.assunto, p.pergunta, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, ema.alternativa, pes.idpesquisa, pes.titulo, " +
						"ema.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingalternativa ema " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=ema.idemailmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=ema.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=ema.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=ema.idstatuscandidato " +
						"WHERE em.idempresa = ? AND p.pergunta like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+pergunta+"%");
				
				
				List<EmailMarketingAlternativa> listaEMA = new ArrayList<EmailMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingAlternativa ema = new EmailMarketingAlternativa();
					ema.setEmailmarketing(new EmailMarketing());
					ema.setPergunta(new Pergunta());
					ema.setPesquisa(new Pesquisa());
					ema.setStatuscandidato(new StatusCandidato());
					preencher(ema, rs);
					listaEMA.add(ema);
				}

				return listaEMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingAlternativa> listaPublicacaoEmailMarketingPorAssunto(
			String assuntoEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ema.idemailmarketingalternativa, ema.idemailmarketing, " +
						"ema.idpergunta, em.assunto, p.pergunta, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, ema.alternativa, pes.idpesquisa, pes.titulo, " +
						"ema.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingalternativa ema " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=ema.idemailmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=ema.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=ema.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=ema.idstatuscandidato " +
						"WHERE em.idempresa = ? AND em.assunto like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+assuntoEmail+"%");
				
				
				List<EmailMarketingAlternativa> listaEMA = new ArrayList<EmailMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingAlternativa ema = new EmailMarketingAlternativa();
					ema.setEmailmarketing(new EmailMarketing());
					ema.setPergunta(new Pergunta());
					ema.setPesquisa(new Pesquisa());
					ema.setStatuscandidato(new StatusCandidato());
					preencher(ema, rs);
					listaEMA.add(ema);
				}

				return listaEMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingAlternativa> listaPublicacaoEmailMarketingPorIdPesquisa(
			int idPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ema.idemailmarketingalternativa, ema.idemailmarketing, " +
						"ema.idpergunta, em.assunto, p.pergunta, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, ema.alternativa, pes.idpesquisa, pes.titulo, " +
						"ema.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingalternativa ema " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=ema.idemailmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=ema.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=ema.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=ema.idstatuscandidato " +
						"WHERE em.idempresa = ? AND pes.idpesquisa = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idPesquisa);
				
				
				List<EmailMarketingAlternativa> listaEMA = new ArrayList<EmailMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingAlternativa ema = new EmailMarketingAlternativa();
					ema.setEmailmarketing(new EmailMarketing());
					ema.setPergunta(new Pergunta());
					ema.setPesquisa(new Pesquisa());
					ema.setStatuscandidato(new StatusCandidato());
					preencher(ema, rs);
					listaEMA.add(ema);
				}

				return listaEMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingAlternativa> listaPublicacaoEmailMarketingPorPesquisa(
			String tituloPesquisa, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT ema.idemailmarketingalternativa, ema.idemailmarketing, " +
						"ema.idpergunta, em.assunto, p.pergunta, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, ema.alternativa, pes.idpesquisa, pes.titulo, " +
						"ema.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingalternativa ema " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=ema.idemailmarketing " +
						"INNER JOIN pergunta p ON p.idpergunta=ema.idpergunta " +
						"INNER JOIN pesquisa pes ON pes.idpesquisa=ema.idpesquisa " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=ema.idstatuscandidato " +
						"WHERE em.idempresa = ? AND pes.titulo like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+tituloPesquisa+"%");
				
				
				List<EmailMarketingAlternativa> listaEMA = new ArrayList<EmailMarketingAlternativa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingAlternativa ema = new EmailMarketingAlternativa();
					ema.setEmailmarketing(new EmailMarketing());
					ema.setPergunta(new Pergunta());
					ema.setPesquisa(new Pesquisa());
					ema.setStatuscandidato(new StatusCandidato());
					preencher(ema, rs);
					listaEMA.add(ema);
				}

				return listaEMA;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
