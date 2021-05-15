package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unipesquisas.model.entity.RespostaPesquisa;

public class RespostaPesquisaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (RespostaPesquisa rp) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO respostapesquisa (idpesquisapergunta, idcandidato, resposta, data) " +
						"VALUES (?, ?, ?, ?)", new String[] { "idrespostapesquisa" });
				stmt.setInt(1, rp.getPesquisapergunta().getIdpesquisapergunta());
				stmt.setInt(2, rp.getCandidato().getIdcandidato());
				stmt.setString(3, rp.getResposta());
				stmt.setDate(4, convertDate(rp.getData()));

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				rp.setIdrespostapesquisa(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
