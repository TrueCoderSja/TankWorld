package ml.truecoder.tankgame.visual;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.nogui.ControllerListener;

public class Actor implements GameData, ControllerListener {
	private int spriteID;
	protected int speed;
	private int x, y;
	protected int direction;
	
	public Actor(int spriteID, int[] coords) {
		this.spriteID=spriteID;
		x=coords[0];
		y=coords[1];
		speed=1;
		Controller.addListener(this);
	}
	
	//Setters
	public void setCoords(int[] coords) {
		x=coords[0];
		y=coords[1];
	}
	
	//Getters
	public int[] getCoords() {
		return new int[] {x, y};
	}
	
	public int getSpriteID() {
		return spriteID;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getWidth() {
		return SpriteManager.getSprite(spriteID, direction).getWidth();
	}
	
	public int getHalfXtileCoverage() {
		int coverage=(getWidth()%2==0)?getWidth()/2:getWidth()/2+1;
		return coverage;
	}
	
	public int getHeight() {
		return SpriteManager.getSprite(spriteID, direction).getHeight();
	}
	
	public int getHalfYtileCoverage() {
		int coverage=(getHeight()%2==0)?getHeight()/2:getHeight()/2+1;
		return coverage;
	}
	
	public void moveUp() {
		if(y-speed>=world.getLowerY())
			y-=speed;
	}
	public void moveDown() {
		if(y+speed<=world.getUpperY())
			y+=speed;
	}
	public void moveLeft() {
		if(x-speed>=world.getLowerX())
			x-=speed;
	}
	public void moveRight() {
		if(x-speed<=world.getUpperX())
			x+=speed;
	}
	

	//To be overwritten in Derived class
	public void upPressed()
	{}
	public void downPressed()
	{}
	public void leftPressed()
	{}
	public void rightPressed() 
	{}
	public void firePressed()
	{}

}
