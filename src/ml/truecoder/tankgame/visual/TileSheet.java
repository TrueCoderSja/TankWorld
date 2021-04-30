package ml.truecoder.tankgame.visual;

import ml.truecoder.tankgame.nativecode.Graphic;
import ml.truecoder.tankgame.nogui.Utilities;

public class TileSheet {
	private Graphic img;
	private int lowerLimit, upperLimit;
	private int tile_width, tile_height;
	private int xLen, yLen;
	public TileSheet(String imagePath, int lowerLimit, int upperLimit, int tile_width, int tile_height) {
		img=Utilities.getAssestGraphic(imagePath);
		this.upperLimit=upperLimit;
		this.lowerLimit=lowerLimit;
		this.tile_width=tile_width;
		this.tile_height=tile_height;
		xLen=img.getWidth()/tile_width;
		yLen=img.getHeight()/tile_height;
	}
	
	/**
	 * Returns cropped instance of tile
	 */
	public Tile extractTile(int x, int y, int width, int height) {
		Graphic croppedImage=img.getSubimage(x, y, width, height);
		return new Tile(croppedImage);
	}
	
	/**
	 * Returns width of tilesheet image
	 */
	public int getWidth() {
		return img.getWidth();
	}
	
	/**
	 * Returns height of tilesheet image
	 */
	public int getHeight() {
		return img.getHeight();
	}
	
	public int getLowerLimit() {
		return lowerLimit;
	}
	
	public int getUpperLimit() {
		return upperLimit;
	}
	
	public int getXLen() {
		return xLen;
	}
	
	public int getYLen() {
		return yLen;
	}
	
	public int getTileWidth() {
		return tile_width;
	}
	
	public int getTileHeight() {
		return tile_height;
	}
}
