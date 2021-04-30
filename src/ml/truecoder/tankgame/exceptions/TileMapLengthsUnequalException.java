package ml.truecoder.tankgame.exceptions;

public class TileMapLengthsUnequalException extends RuntimeException {
	private static final long serialVersionUID=1L;
	public TileMapLengthsUnequalException() {
		super("TileMap Lengths are not matching");
	}
}
