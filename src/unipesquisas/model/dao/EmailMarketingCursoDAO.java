package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Curso;
import unipesquisas.model.entity.EmailMarketing;
import unipesquisas.model.entity.EmailMarketingCurso;
import unipesquisas.model.entity.StatusCandidato;

public class EmailMarketingCursoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (EmailMarketingCurso emc) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO emailmarketingcurso (idemailmarketing, idcurso, data, " +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?)", new String[] { "idemailmarketingcurso" });
				stmt.setInt(1, emc.getEmailmarketing().getIdemailmarketing());
				stmt.setInt(2, emc.getCurso().getIdcurso());
				stmt.setDate(3, convertDate(emc.getData()));
				if(emc.getStatuscandidato().getIdstatuscandidato() == 0){
					stmt.setNull(4, java.sql.Types.INTEGER);
				}else{
					stmt.setInt(4, emc.getStatuscandidato().getIdstatuscandidato());
				}
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				emc.setIdemailmarketingcurso(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private void preencher(EmailMarketingCurso emc, ResultSet rs) throws DAOException {
		try {
			emc.setIdemailmarketingcurso(rs.getInt("idemailmarketingcurso"));
			emc.getEmailmarketing().setIdemailmarketing(rs.getInt("idemailmarketing"));
			emc.getEmailmarketing().setAssunto(rs.getString("assunto"));
			emc.getEmailmarketing().setMensagem(rs.getString("mensagem"));
			emc.getEmailmarketing().setData(rs.getDate("data"));
			emc.getEmailmarketing().setStatus(rs.getInt("status"));
			emc.getEmailmarketing().setIdempresa(rs.getInt("idempresa"));
			emc.getCurso().setIdcurso(rs.getInt("idcurso"));
			emc.getCurso().setCurso(rs.getString("curso"));
			emc.setData(rs.getDate("datapublicacao"));
			if(rs.getString("statuscandidato") != null){
				emc.getStatuscandidato().setStatus(rs.getString("statuscandidato"));
			}else{
				emc.getStatuscandidato().setStatus("Todos");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public EmailMarketingCurso carregar(Integer idEmailMarketing) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emc.idemailmarketingcurso, emc.idemailmarketing, emc.idcurso, " +
						"em.assunto, c.curso, em.idemailmarketing, dm.mensagem, em.data, em.idempresa, em.status, " +
						"sc.status as statuscandidato " +
						"emc.data AS datapublicacao " +
						"FROM emailmarketingcurso emc " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emc.idemailmarketing " +
						"INNER JOIN curso c ON c.idcurso=emc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emc.idstatuscandidato " +
						"WHERE emc.idemailmarketing = ?");
				stmt.setInt(1, idEmailMarketing);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				EmailMarketingCurso emc = new EmailMarketingCurso();
				emc.setCurso(new Curso());
				emc.setEmailmarketing(new EmailMarketing());
				emc.setStatuscandidato(new StatusCandidato());
				preencher(emc, rs);

				return emc;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingCurso> listaEmailMarketingCurso(
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emc.idemailmarketingcurso, emc.idemailmarketing, " +
						"emc.idcurso, em.assunto, c.curso, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emc.data AS datapublicacao, " +
						"sc.status as statuscandidato " +
						"FROM emailmarketingcurso emc " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emc.idemailmarketing " +
						"INNER JOIN curso c ON c.idcurso=emc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emc.idstatuscandidato " +
						"WHERE em.idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				
				List<EmailMarketingCurso> listaEMC = new ArrayList<EmailMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingCurso emc = new EmailMarketingCurso();
					emc.setEmailmarketing(new EmailMarketing());
					emc.setCurso(new Curso());
					emc.setStatuscandidato(new StatusCandidato());
					preencher(emc, rs);
					listaEMC.add(emc);
				}

				return listaEMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean emailPublicado(Integer idEmailMarketing, Integer idCurso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT * " +
						"FROM emailmarketingcurso " +
						"WHERE idemailmarketing = ? AND idcurso = ?");
				stmt.setInt(1, idEmailMarketing);
				stmt.setInt(2, idCurso);
				
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

	public List<EmailMarketingCurso> listaPublicacaoEmailMarketingPorIdEmail(
			int idEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emc.idemailmarketingcurso, emc.idemailmarketing, " +
						"emc.idcurso, em.assunto, c.curso, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emc.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingcurso emc " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emc.idemailmarketing " +
						"INNER JOIN curso c ON c.idcurso=emc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emc.idstatuscandidato " +
						"WHERE em.idempresa = ? AND em.idemailmarketing = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEmail);
				
				
				List<EmailMarketingCurso> listaEMC = new ArrayList<EmailMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingCurso emc = new EmailMarketingCurso();
					emc.setEmailmarketing(new EmailMarketing());
					emc.setCurso(new Curso());
					emc.setStatuscandidato(new StatusCandidato());
					preencher(emc, rs);
					listaEMC.add(emc);
				}

				return listaEMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingCurso> listaPublicacaoEmailMarketingPorIdCurso(
			int idCurso, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emc.idemailmarketingcurso, emc.idemailmarketing, " +
						"emc.idcurso, em.assunto, c.curso, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emc.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingcurso emc " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emc.idemailmarketing " +
						"INNER JOIN curso c ON c.idcurso=emc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emc.idstatuscandidato " +
						"WHERE em.idempresa = ? AND c.idcurso = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idCurso);
				
				
				List<EmailMarketingCurso> listaEMC = new ArrayList<EmailMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingCurso emc = new EmailMarketingCurso();
					emc.setEmailmarketing(new EmailMarketing());
					emc.setCurso(new Curso());
					emc.setStatuscandidato(new StatusCandidato());
					preencher(emc, rs);
					listaEMC.add(emc);
				}

				return listaEMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingCurso> listaPublicacaoEmailMarketingPorCurso(
			String curso, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emc.idemailmarketingcurso, emc.idemailmarketing, " +
						"emc.idcurso, em.assunto, c.curso, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emc.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingcurso emc " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emc.idemailmarketing " +
						"INNER JOIN curso c ON c.idcurso=emc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emc.idstatuscandidato " +
						"WHERE em.idempresa = ? AND c.curso like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+curso+"%");
				
				
				List<EmailMarketingCurso> listaEMC = new ArrayList<EmailMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingCurso emc = new EmailMarketingCurso();
					emc.setEmailmarketing(new EmailMarketing());
					emc.setCurso(new Curso());
					emc.setStatuscandidato(new StatusCandidato());
					preencher(emc, rs);
					listaEMC.add(emc);
				}

				return listaEMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<EmailMarketingCurso> listaPublicacaoEmailMarketingPorAssunto(
			String assuntoEmail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT emc.idemailmarketingcurso, emc.idemailmarketing, " +
						"emc.idcurso, em.assunto, c.curso, em.idemailmarketing, em.mensagem, " +
						"em.data, em.idempresa, em.status, emc.data AS datapublicacao, sc.status as statuscandidato " +
						"FROM emailmarketingcurso emc " +
						"INNER JOIN emailmarketing em ON em.idemailmarketing=emc.idemailmarketing " +
						"INNER JOIN curso c ON c.idcurso=emc.idcurso " +
						"LEFT JOIN statuscandidato sc ON sc.idstatuscandidato=emc.idstatuscandidato " +
						"WHERE em.idempresa = ? AND em.assunto like ?");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+assuntoEmail+"%");
				
				
				List<EmailMarketingCurso> listaEMC = new ArrayList<EmailMarketingCurso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EmailMarketingCurso emc = new EmailMarketingCurso();
					emc.setEmailmarketing(new EmailMarketing());
					emc.setCurso(new Curso());
					emc.setStatuscandidato(new StatusCandidato());
					preencher(emc, rs);
					listaEMC.add(emc);
				}

				return listaEMC;
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
