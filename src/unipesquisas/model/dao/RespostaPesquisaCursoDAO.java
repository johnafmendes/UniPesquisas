package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unipesquisas.model.entity.RespostaPesquisaCurso;

public class RespostaPesquisaCursoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (RespostaPesquisaCurso rpc) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO respostapesquisacurso " +
						"(idcandidato, idpesquisacurso, idpesquisapergunta, resposta, data, " +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?, ?, ?)", new String[] { "idrespostapesquisacurso" });
				stmt.setInt(1, rpc.getCandidato().getIdcandidato());
				stmt.setInt(2, rpc.getPesquisacurso().getIdpesquisacurso());
				stmt.setInt(3, rpc.getPesquisapergunta().getIdpesquisapergunta());
				stmt.setString(4, rpc.getResposta());
				stmt.setDate(5, convertDate(rpc.getData()));
				stmt.setInt(6, rpc.getStatuscandidato().getIdstatuscandidato());
				
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				rpc.setIdrespostapesquisacurso(rs.getInt(1));

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
				stmt = conn.prepareStatement("UPDATE respostapesquisacurso rpc " +
						"INNER JOIN pesquisacurso pc ON pc.idpesquisacurso=rpc.idpesquisacurso " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pc.idpesquisa " +
						"SET rpc.idstatuscandidato = ? " +
						"WHERE rpc.idcandidato = ? AND p.idempresa = ?");
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
