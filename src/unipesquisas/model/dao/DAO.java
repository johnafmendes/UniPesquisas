package unipesquisas.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
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
	//@Resource(name = "jdbc/unipesquisads")
	//private DataSource ds;
	
	/**
	 * Objeto que representa a conexão com o banco de dados
	 */
	private Connection conn;

	/**
	 * Retorna a conexão aberta com o banco de dados
	 * @return Conexão
	 * @throws DAOException
	 * @throws ClassNotFoundException 
	 */
	protected Connection getConnection() throws DAOException {
		try {
			if (conn == null) {
				//conn = ds.getConnection();
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/unipesquisas","root","root");
				if (conn != null) {
	                System.out.println("Connected to the database!");
	            } else {
	                System.out.println("Failed to make connection!");
	            }
			}
			return conn;
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
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
