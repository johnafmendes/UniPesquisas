package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unipesquisas.model.entity.ConfiguracoesEmpresa;
import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.StatusCandidato;

public class ConfiguracoesEmpresaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2551571499004957685L;

	public void salvar(ConfiguracoesEmpresa ce) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE configuracaoempresa SET idstatuscandidatopadrao = ? " +
						"WHERE idempresa = ?");
				stmt.setInt(1, ce.getStatuscandidato().getIdstatuscandidato());
				stmt.setInt(2, ce.getEmpresa().getIdempresa());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar Configurações do Sistema ");
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public ConfiguracoesEmpresa carregar(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idstatuscandidatopadrao, idempresa " +
						"FROM configuracaoempresa " +
						"WHERE idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				ConfiguracoesEmpresa ce = new ConfiguracoesEmpresa();
				ce.setStatuscandidato(new StatusCandidato());
				ce.getStatuscandidato().setIdstatuscandidato(rs.getInt("idstatuscandidatopadrao"));
				ce.setEmpresa(new Empresa());
				ce.getEmpresa().setIdempresa(rs.getInt("idempresa"));
				return ce;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void incluirConfiguracoes(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO configuracaoempresa (idempresa) VALUES (?)");
				stmt.setInt(1, idEmpresa);

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
