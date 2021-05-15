package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unipesquisas.model.entity.RespostaPesquisaEscolaridade;

public class RespostaPesquisaEscolaridadeDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (RespostaPesquisaEscolaridade rpe) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO respostapesquisaescolaridade " +
						"(idcandidato, idpesquisaescolaridade, idpesquisapergunta, resposta, data, " +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?, ?, ?)", new String[] { "idrespostapesquisaescolaridade" });
				stmt.setInt(1, rpe.getCandidato().getIdcandidato());
				stmt.setInt(2, rpe.getPesquisaescolaridade().getIdpesquisaescolaridade());
				stmt.setInt(3, rpe.getPesquisapergunta().getIdpesquisapergunta());
				stmt.setString(4, rpe.getResposta());
				stmt.setDate(5, convertDate(rpe.getData()));
				stmt.setInt(6, rpe.getStatuscandidato().getIdstatuscandidato());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				rpe.setIdrespostapesquisaescolaridade(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Integer idCandidato, Integer idStatusCandidato,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE respostapesquisaescolaridade rpe " +
						"INNER JOIN pesquisaescolaridade pe ON pe.idpesquisaescolaridade=rpe.idpesquisaescolaridade " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pe.idpesquisa " +
						"SET rpe.idstatuscandidato = ? " +
						"WHERE rpe.idcandidato = ? AND p.idempresa = ?");
				stmt.setInt(1, idStatusCandidato);
				stmt.setInt(2, idCandidato);
				stmt.setInt(3, idEmpresa);

				stmt.executeUpdate();
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
