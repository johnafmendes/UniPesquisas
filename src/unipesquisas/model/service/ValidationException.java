package unipesquisas.model.service;

/**
 * Exceção lançada quando ocorre uma validação de negócio
 */
public class ValidationException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3694559559111330388L;

	public ValidationException() {
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
