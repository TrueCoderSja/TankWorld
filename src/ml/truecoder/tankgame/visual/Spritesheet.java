package ml.truecoder.tankgame.visual;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.nativecode.Graphic;
import ml.truecoder.tankgame.nogui.Utilities;

public class Spritesheet implements GameData {
	private Graphic sheetImage;
	public final int id;
	private int width, height;
	private int spriteWidth, spriteHeight;
	private int xLen, yLen;
	private int defaultPos;
	private int[] positionIndexes;
	
	public Spritesheet(String sheetImagePath, int id, int spriteWidth, int spriteHeight, int[] positionIndexes) {
		this.sheetImage=Utilities.getAssestGraphic(sheetImagePath);
		this.id=id;
		width=sheetImage.getWidth();
		height=sheetImage.getHeight();
		this.spriteWidth=spriteWidth;
		this.spriteHeight=spriteHeight;
		xLen=width/spriteWidth;
		yLen=height/spriteHeight;
		this.positionIndexes=positionIndexes;
		for(int i=0;i<positionIndexes.length;i++) {
			int pos=positionIndexes[i];
			if(pos>0) {
				defaultPos=i;
				break;
			}
		}
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getXlen() {
		return xLen;
	}
	public int getYlen() {
		return yLen;
	}
	public Graphic extractSpriteFrame(int x, int y, int width, int height) {
		return sheetImage.getSubimage(x, y, width, height);
	}
	public Sprite getUpSprite() {
		if(positionIndexes[UP]>=0)
			return new Sprite(this, spriteWidth, spriteHeight, positionIndexes[UP]);
		else
			return new Sprite(this, spriteWidth, spriteHeight, defaultPos, UP);
	}
	public Sprite getDownSprite() {
		if(positionIndexes[DOWN]>=0)
			return new Sprite(this, spriteWidth, spriteHeight, positionIndexes[DOWN]);
		else
			return new Sprite(this, spriteWidth, spriteHeight, defaultPos, DOWN);
	}
	public Sprite getLeftSprite() {
		if(positionIndexes[LEFT]>=0)
			return new Sprite(this, spriteWidth, spriteHeight, positionIndexes[LEFT]);
		else
			return new Sprite(this, spriteWidth, spriteHeight, defaultPos, LEFT);
	}
	public Sprite getRightSprite() {
		if(positionIndexes[RIGHT]>=0)
			return new Sprite(this, spriteWidth, spriteHeight, positionIndexes[RIGHT]);
		else
			return new Sprite(this, spriteWidth, spriteHeight, defaultPos, RIGHT);
	}
}
