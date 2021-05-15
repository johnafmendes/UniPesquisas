package unipesquisas.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

/**
 * Superclasse das classes de DAO (Data Access Object) da aplicação
 */
@RequestScoped
public abstract class DAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2661226472533359112L;

	/**
	 * Referência à data source
	 */
	@Resource(name = "jdbc/unipesquisads")
	private DataSource ds;
	
	/**
	 * Objeto que representa a conexão com o banco de dados
	 */
	private Connection conn;

	/**
	 * Retorna a conexão aberta com o banco de dados
	 * @return Conexão
	 * @throws DAOException
	 */
	protected Connection getConnection() throws DAOException {
		try {
			if (conn == null) {
				conn = ds.getConnection();
			}
			return conn;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	/**
	 * Fecha um PreparedStatement aberto
	 * @param stmt PreparedStatement a ser fechado
	 * @throws DAOException
	 */
	protected void closeStatement(Statement stmt) throws DAOException {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	/**
	 * Fecha a conexão com o banco de dados. Este método é chamado automaticamente pelo contêiner caso o 
	 * CDI bean seja destruído.
	 * @throws DAOException
	 */
	@PreDestroy
	protected void closeConnection() throws DAOException {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	/**
	 * Converte um objeto java.util.Data para um objeto java.sql.Date
	 */
	protected java.sql.Date convertDate(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}
}
