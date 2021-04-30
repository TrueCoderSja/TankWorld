package ml.truecoder.tankgame.exceptions;

public class WrongPositionIndexException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public WrongPositionIndexException(int positionIndex) {
		super("The position index "+positionIndex+" is not valid");
	}
}
