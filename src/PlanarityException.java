/**
 * 
 */

/**
 * PlanarityException is an Exception that is thrown in the planarity-testing algorithm.
 * 
 * @author Trevor Mack		(tmm9274@rit.edu)
 * @author David Sweeney	(dts7079@rit.edu)
 *
 */
public class PlanarityException extends Exception {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param message
	 */
	public PlanarityException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PlanarityException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PlanarityException(String message, Throwable cause) {
		super(message, cause);
	}

}
