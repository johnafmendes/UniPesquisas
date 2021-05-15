package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unipesquisas.model.entity.RespostaPesquisaInstituicao;

public class RespostaPesquisaInstituicaoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (RespostaPesquisaInstituicao rpi) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO respostapesquisainstituicao " +
						"(idcandidato, idpesquisainstituicao, idpesquisapergunta, resposta, data, " +
						"idstatuscandidato) " +
						"VALUES (?, ?, ?, ?, ?, ?)", new String[] { "idrespostapesquisainstituicao" });
				stmt.setInt(1, rpi.getCandidato().getIdcandidato());
				stmt.setInt(2, rpi.getPesquisainstituicao().getIdpesquisainstituicao());
				stmt.setInt(3, rpi.getPesquisapergunta().getIdpesquisapergunta());
				stmt.setString(4, rpi.getResposta());
				stmt.setDate(5, convertDate(rpi.getData()));
				stmt.setInt(6, rpi.getStatuscandidato().getIdstatuscandidato());
				
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				rpi.setIdrespostapesquisainstituicao(rs.getInt(1));

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
				stmt = conn.prepareStatement("UPDATE respostapesquisainstituicao rpi " +
						"INNER JOIN pesquisainstituicao pi ON pi.idpesquisainstituicao=rpi.idpesquisainstituicao " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pi.idpesquisa " +
						"SET rpi.idstatuscandidato = ? " +
						"WHERE rpi.idcandidato = ? AND p.idempresa = ?");
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
