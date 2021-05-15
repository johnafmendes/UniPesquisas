package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.CandidatoPesquisaInstituicao;

public class CandidatoPesquisaInstituicaoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void atualizar(CandidatoPesquisaInstituicao cpi) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO candidatopesquisainstituicao (idcandidato, idpesquisainstituicao) " +
						"VALUES (?, ?)", new String[] { "idcandidatopesquisainstituicao" });
				stmt.setInt(1, cpi.getCandidato().getIdcandidato());
				stmt.setInt(2, cpi.getPesquisainstituicao().getIdpesquisainstituicao());

				stmt.executeUpdate();

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public void publicar(Integer idpesquisainstituicao,
			List<Candidato> listaCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				for(int i = 0; i < listaCandidato.size(); i++){
					conn = getConnection();
					stmt = conn.prepareStatement("INSERT INTO candidatopesquisainstituicao (idcandidato, " +
							"idpesquisainstituicao, status) " +
							"VALUES (?, ?, 0)", new String[] { "idcandidatopesquisainstituicao" });
					stmt.setInt(1, listaCandidato.get(i).getIdcandidato());
					stmt.setInt(2, idpesquisainstituicao);
	
					stmt.executeUpdate();
	
					// Obtém o ID gerado pelo banco de dados e atribui ao objeto
//					ResultSet rs = stmt.getGeneratedKeys();
//					rs.next();
//					candidato.setIdcandidato(rs.getInt(1));
				}

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

}