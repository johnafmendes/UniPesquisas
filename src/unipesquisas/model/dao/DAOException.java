package unipesquisas.model.dao;

/**
 * Exce��o lan�ada pelas classes de DAO da aplica��o
 */
public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8308006229788310417L;

	public DAOException() {
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
