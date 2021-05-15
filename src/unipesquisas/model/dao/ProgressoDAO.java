package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.Progresso;
import unipesquisas.model.entity.Usuario;

public class ProgressoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (Progresso progresso) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO progresso (idcandidato, data, observacao, idusuario, idempresa) " +
						"VALUES (?, ?, ?, ?, ?)", new String[] { "idprogressodetalhado" });
				stmt.setInt(1, progresso.getCandidato().getIdcandidato());
				stmt.setDate(2, convertDate(progresso.getData()));
				stmt.setString(3, progresso.getObservacao());
				stmt.setInt(4, progresso.getUsuario().getIdusuario());
				stmt.setInt(5, progresso.getIdempresa());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				progresso.setIdprogressodetalhado(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Progresso progresso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE progresso SET idcandidato = ?, data = ?, observacao = ?, " +
						"idusuario = ?, idempresa = ? " +
						"WHERE idprogressodetalhado = ?");
				stmt.setInt(1, progresso.getCandidato().getIdcandidato());
				stmt.setDate(2, convertDate(progresso.getData()));
				stmt.setString(3, progresso.getObservacao());
				stmt.setInt(4, progresso.getUsuario().getIdusuario());
				stmt.setInt(5, progresso.getIdempresa());
				stmt.setInt(6, progresso.getIdprogressodetalhado());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar progresso = " + progresso.getIdprogressodetalhado());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idProgresso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM progresso WHERE idprogressodetalhado = ?");
				stmt.setInt(1, idProgresso);

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

	public List<Progresso> listarProgressos(Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idprogressodetalhado, p.observacao, p.data, p.idempresa, " +
						"p.idusuario, p.idcandidato, u.nome as nomeusuario, c.nome as nomecandidato " +
						"FROM progresso p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"INNER JOIN candidato c ON p.idcandidato=c.idcandidato " +
						"WHERE c.idcandidato = ? ORDER BY p.data desc;");
				stmt.setInt(1, idCandidato);
				
				List<Progresso> listaProgressos = new ArrayList<Progresso>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Progresso progresso = new Progresso();
					progresso.setUsuario(new Usuario());
					progresso.setCandidato(new Candidato());
					preencher(progresso, rs);
					listaProgressos.add(progresso);
				}

				return listaProgressos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Progresso progresso, ResultSet rs) throws DAOException {
		try {
			progresso.setIdprogressodetalhado(rs.getInt("idprogressodetalhado"));
			progresso.setObservacao(rs.getString("observacao"));
			progresso.setData(rs.getDate("data"));
			progresso.setIdempresa(rs.getInt("idempresa"));
			progresso.getUsuario().setIdusuario(rs.getInt("idusuario"));
			progresso.getUsuario().setNome(rs.getString("nomeusuario"));
			progresso.getCandidato().setIdcandidato(rs.getInt("idcandidato"));
			progresso.getCandidato().setNome(rs.getString("nomecandidato"));
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Progresso carregar(Integer idProgresso) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT p.idprogressodetalhado, p.idusuario, p.idcandidato, " +
						"p.observacao, p.data, p.idempresa, u.nome as nomeusuario, c.nome as nomecandidato " +
						"FROM progresso p " +
						"INNER JOIN usuario u ON p.idusuario=u.idusuario " +
						"INNER JOIN candidato c ON p.idcandidato=c.idcandidato " +
						"WHERE p.idprogressodetalhado = ?");
				stmt.setInt(1, idProgresso);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Progresso progresso = new Progresso();
				progresso.setCandidato(new Candidato());
				progresso.setUsuario(new Usuario());
				preencher(progresso, rs);
				return progresso;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
