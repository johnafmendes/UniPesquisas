package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.EnvioEmailMarketing;


public class EnvioEmailMarketingDAO extends DAO {

	
	@Inject
	private Diversos diversos;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public List<EnvioEmailMarketing> listarEnvioEmailMarketing() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT data, sum(numeroemails) as totalemails, " +
						"sum(tempo) as totaltempo " +
						"FROM envioemailmarketing " +
						"GROUP BY data " +
						"ORDER BY data");
				
				List<EnvioEmailMarketing> listaEEM = new ArrayList<EnvioEmailMarketing>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					EnvioEmailMarketing eem = new EnvioEmailMarketing();
					eem.setData(rs.getDate("data"));
					eem.setNumeroemails(rs.getInt("totalemails"));
					eem.setTempo(diversos.formatIntoHHMMSS(rs.getInt("totaltempo")));
					listaEEM.add(eem);
				}

				return listaEEM;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}