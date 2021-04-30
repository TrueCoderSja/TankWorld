package ml.truecoder.tankgame.exceptions;

public class CoordinatesOutOfScreenRangeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CoordinatesOutOfScreenRangeException() {
		super("The given coordinates are out of range");
	}
}
