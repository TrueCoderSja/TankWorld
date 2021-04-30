package ml.truecoder.tankgame.visual;

import ml.truecoder.tankgame.nativecode.Graphic;
import ml.truecoder.tankgame.nogui.Utilities;

public class Tile {
	public Graphic tile;
	public Tile(Graphic tile) {
		this.tile=tile;
	}
	
	public Tile(String imagePath) {
		this.tile=Utilities.getAssestGraphic(imagePath);
//		BufferedImage newRGB = new BufferedImage(tile.getWidth(), tile.getHeight(), BufferedImage.TYPE_INT_RGB);
//	    newRGB .createGraphics().drawImage(tile, 0, 0, tile.getWidth(), tile.getHeight(), null);
//	    this.tile=newRGB;
	}
	
	public int getWidth() {
		return tile.getWidth();
	}
	
	public int getHeight() {
		return tile.getHeight();
	}
}
