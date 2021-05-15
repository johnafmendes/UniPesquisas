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
 * Superclasse das classes de DAO (Data Access Object) da aplica��o
 */
@RequestScoped
public abstract class DAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2661226472533359112L;

	/**
	 * Refer�ncia � data source
	 */
	@Resource(name = "jdbc/unipesquisads")
	private DataSource ds;
	
	/**
	 * Objeto que representa a conex�o com o banco de dados
	 */
	private Connection conn;

	/**
	 * Retorna a conex�o aberta com o banco de dados
	 * @return Conex�o
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
	 * Fecha a conex�o com o banco de dados. Este m�todo � chamado automaticamente pelo cont�iner caso o 
	 * CDI bean seja destru�do.
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
