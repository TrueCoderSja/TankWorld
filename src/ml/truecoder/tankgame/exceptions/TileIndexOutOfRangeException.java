package ml.truecoder.tankgame.exceptions;

public class TileIndexOutOfRangeException extends Exception {
	private static final long serialVersionUID = 1L;

	TileIndexOutOfRangeException(int indexX, int indexY) {
		super("Tile Index Out Of Range For X: "+indexX+";\tY: "+indexY);
	}
}
