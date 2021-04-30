package ml.truecoder.tankgame.visual;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.exceptions.WrongPositionIndexException;
import ml.truecoder.tankgame.nativecode.Graphic;

public class Sprite implements GameData {
	private int spriteWidth, spriteHeight;
	private Graphic[] sprites;
	private int frames;
	
	public Sprite(Spritesheet sheet, int spriteWidth, int spriteHeight, int index) {
		this.spriteWidth=spriteWidth;
		this.spriteHeight=spriteHeight;
		frames=sheet.getXlen();
		sprites=new Graphic[frames];
		int y=spriteHeight*(index-1);
		for(int i=0;i<frames;i++) {
			int x=spriteWidth*i;
			sprites[i]=sheet.extractSpriteFrame(x, y, spriteWidth, spriteHeight);
		}
	}
	
	public Sprite(Spritesheet sheet, int spriteWidth, int spriteHeight, int index, int position) {
		frames=sheet.getXlen();
		sprites=new Graphic[frames];
		int y=0;
		if(position<0 || position>3)
			throw new WrongPositionIndexException(position);
		int angle=(position-index)*90;
		for(int i=0;i<frames;i++) {
			int x=spriteWidth*i;
			sprites[i]=sheet.extractSpriteFrame(x, y, spriteWidth, spriteHeight).rotateImage(angle);
		}
		this.spriteWidth=sprites[0].getWidth();
		this.spriteHeight=sprites[0].getHeight();
	}
	
	public int getWidth() {
		return spriteWidth;
	}
	
	public int getHeight() {
		return spriteHeight;
	}
	
	public Graphic getFrame(int frameCount) {
		Graphic frame=sprites[frameCount];
		spriteWidth=frame.getWidth();
		spriteHeight=frame.getHeight();
		return frame;
	}
	
	public int getFramesLength() {
		return frames;
	}
}
