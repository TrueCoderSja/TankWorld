package ml.truecoder.tankgame.exceptions;

public class NoSpriteWithIDException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NoSpriteWithIDException(int id) {
		super("No sprite with ID: "+id);
	}
}
