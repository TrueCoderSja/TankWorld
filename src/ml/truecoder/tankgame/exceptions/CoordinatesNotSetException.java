package ml.truecoder.tankgame.exceptions;

public class CoordinatesNotSetException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CoordinatesNotSetException() {
		super("Cannot get coordinates without setting any coordinates");
	}
}
