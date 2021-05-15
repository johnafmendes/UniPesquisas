package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unipesquisas.model.entity.CandidatoEmpresa;
import unipesquisas.model.entity.StatusCandidato;

public class CandidatoEmpresaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (CandidatoEmpresa ce, Integer admin) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				//if(admin == 1){
					stmt = conn.prepareStatement("INSERT INTO candidatoempresa (idcandidato, idempresa, receberemail, " +
							"recebersms, idstatuscandidato) VALUES (?, ?, ?, ?, ?)");
				//}else{
				//	stmt = conn.prepareStatement("INSERT INTO candidatoempresa (idcandidato, idempresa, receberemail, " +
				//			"recebersms) VALUES (?, ?, ?, ?)");
				//}
				stmt.setInt(1, ce.getIdcandidato());
				stmt.setInt(2, ce.getIdempresa());
				stmt.setInt(3, ce.getReceberemail());
				stmt.setInt(4, ce.getRecebersms());
				//if(admin == 1){
					stmt.setInt(5, ce.getStatuscandidato().getIdstatuscandidato());
				//}

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				//ResultSet rs = stmt.getGeneratedKeys();
				//rs.next();
				//ce.setIdcandidatoempresa(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean excluir(Integer idCandidato, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM candidatoempresa WHERE idcandidato = ? AND idempresa ?");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idEmpresa);

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

	public boolean existeCandidatoEmpresa(CandidatoEmpresa ce) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idcandidato, idempresa FROM candidatoempresa " +
						"WHERE idcandidato = ? AND idempresa = ?");
				stmt.setInt(1, ce.getIdcandidato());
				stmt.setInt(2, ce.getIdempresa());
				
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

	public void atualizar(CandidatoEmpresa ce, Integer admin) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				//if(admin == 1){
					stmt = conn.prepareStatement("UPDATE candidatoempresa SET codigo = ?, " +
							"receberemail = ?, recebersms = ?, idstatuscandidato = ? " +
							"WHERE idcandidato = ? AND idempresa = ? ");
					stmt.setString(1, ce.getCodigo());
					stmt.setInt(2, ce.getReceberemail());
					stmt.setInt(3, ce.getRecebersms());
					stmt.setInt(4, ce.getStatuscandidato().getIdstatuscandidato());
					stmt.setInt(5, ce.getIdcandidato());
					stmt.setInt(6, ce.getIdempresa());
				/*}else{
					stmt = conn.prepareStatement("UPDATE candidatoempresa SET codigo = ?, " +
							"receberemail = ?, recebersms = ? " +
							"WHERE idcandidato = ? AND idempresa = ? ");
					stmt.setString(1, ce.getCodigo());
					stmt.setInt(2, ce.getReceberemail());
					stmt.setInt(3, ce.getRecebersms());
					stmt.setInt(4, ce.getIdcandidato());
					stmt.setInt(5, ce.getIdempresa());
				}*/

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar candidatoempresa = " + ce.getIdcandidatoempresa());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public CandidatoEmpresa carregar(Integer idCandidato, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idcandidatoempresa, idcandidato, " +
						"idempresa, codigo, receberemail, recebersms, idstatuscandidato " +
						"FROM candidatoempresa " +
						"WHERE idcandidato = ? AND idempresa = ?");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idEmpresa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				CandidatoEmpresa ce = new CandidatoEmpresa();
				ce.setStatuscandidato(new StatusCandidato());
				preencher(ce, rs);
				return ce;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(CandidatoEmpresa ce, ResultSet rs) throws DAOException {
		try {
			ce.setIdcandidatoempresa(rs.getInt("idcandidatoempresa"));
			ce.setIdcandidato(rs.getInt("idcandidato"));
			ce.setIdempresa(rs.getInt("idempresa"));
			ce.setCodigo(rs.getString("codigo"));
			ce.setReceberemail(rs.getInt("receberemail"));
			ce.setRecebersms(rs.getInt("recebersms"));
			ce.getStatuscandidato().setIdstatuscandidato(rs.getInt("idstatuscandidato"));
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}
}
