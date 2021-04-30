package ml.truecoder.tankgame.visual;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.exceptions.CoordinatesOutOfScreenRangeException;
import ml.truecoder.tankgame.nativecode.Graphic;
import ml.truecoder.tankgame.nativecode.GraphicsFactory;
import ml.truecoder.tankgame.spares.Weapon;

public class Launch implements GameData{
	private int direction;
	private Weapon weapon;
	private int x, y;
	private int xCoord, yCoord;
	private int init_xCoord, init_yCoord;
	private Graphic sprite;
	private Tank tank;
	private int weaponWidth;
	private int weaponHeight;
	
	Launch(int xTileIndex, int yTileIndex, int direction, Tank tank) throws CoordinatesOutOfScreenRangeException {
		Layer layer=world.getXlargestLayer();
		int tileWidth=world.getXlargestLayer().getTileWidth(), tileHeight=world.getYlargestLayer().getTileHeight();
		int xOffset=(tank.getWidth()%2==0)?tank.getWidth()/2:tank.getWidth()/2+1;
		xOffset=(xOffset%tileWidth==0)?xOffset/tileWidth:xOffset/tileWidth+1;
		int yOffset=(tank.getHeight()%2==0)?tank.getHeight()/2:tank.getHeight()/2+1;
		yOffset=(yOffset%tileHeight==0)?yOffset/tileHeight:yOffset/tileHeight+1;
		xCoord=layer.getXpix(xTileIndex);
		yCoord=layer.getYpix(yTileIndex);
		init_xCoord=xCoord;
		init_yCoord=yCoord;
		this.direction=direction;
		this.tank=tank;
		this.weapon=tank.getWeapon();
		int angle=0;
		x=xTileIndex;
		y=yTileIndex;
		switch(direction) {
		case UP:
			angle=-90;
			y=yTileIndex+yOffset;
			break;
		case DOWN:
			angle=90;
			y=yTileIndex-yOffset;
			break;
		case LEFT:
			angle=180;
			x=xTileIndex-xOffset;
			break;
		case RIGHT:
			x=xTileIndex+xOffset;
			break;
		} //no case for right as angle there will be zero
		sprite=weapon.getSprite().rotateImage(angle);
		weaponWidth=sprite.getWidth();
		weaponHeight=sprite.getHeight();
	}
	
	public int getXcoord() {
		int xCoord=this.xCoord;
		switch(direction) {
		case LEFT:
			xCoord-=tank.getWidth()/2+weaponWidth;
			this.xCoord-=weaponWidth*weapon.getSpeed();
			break;
		case RIGHT:
			xCoord+=tank.getWidth()/2;
			this.xCoord+=weaponWidth*weapon.getSpeed();
			break;
		default:
			xCoord-=weaponWidth/2;	
		}
		if(xCoord>GraphicsFactory.getEndingX() || xCoord<GraphicsFactory.getStartingX()) 
			LaunchManager.unregister(this);
		
		if(direction==LEFT || direction==RIGHT) {
			for(int index=world.getLength()-1;index>=0;index--) {
				Layer layer=world.getLayer(index);
				if(layer instanceof PlayerLayer)
					continue;
				int newX=0;
				if(direction==LEFT) {
					int diff=xCoord-(init_xCoord-(tank.getHalfXtileCoverage()+weaponWidth));
					newX=x+(diff/weapon.getSprite().getWidth());
					newX-=2;
				}
				else {
					int diff=xCoord-(init_xCoord+(tank.getHalfXtileCoverage()));
					newX=x+(diff/weapon.getSprite().getWidth());
					newX+=2;
				}
				int id=layer.getTileId(newX, y);
				if(id>0 && layer.isCollidable() || id==-1) {
					LaunchManager.unregister(this);
					if(layer instanceof ObjectLayer)
						((ObjectLayer) layer).destroy(newX, y);
				}
			}
		}
		return xCoord;
	}
	
	public int getYcoord() {
		int yCoord=this.yCoord;
		int coverage=tank.getHalfYtileCoverage();
		switch(direction) {
		case UP:
			yCoord=yCoord-coverage-weaponHeight;//-weaponHeight;
			this.yCoord-=weaponHeight*weapon.getSpeed();
			break;
		case DOWN:
			yCoord+=coverage;//+weaponW;
			this.yCoord+=weaponHeight*weapon.getSpeed();
			break;
		default:
			yCoord-=weaponHeight/2+1;
		}
		if(yCoord>GraphicsFactory.getEndingY() || yCoord<GraphicsFactory.getStartingY())
			LaunchManager.unregister(this);
		if(direction==UP || direction==DOWN) {
			for(int index=world.getLength()-1;index>=0;index--) {
				Layer layer=world.getLayer(index);
				if(layer instanceof PlayerLayer)
					continue;
				int newY=0;
				if(direction==UP) {
					int diff=yCoord-(init_yCoord-coverage-weaponHeight);
					newY=y-(diff/weaponHeight);
					newY+=1;
				}
				else {
					int diff=yCoord-(init_yCoord+coverage);
					newY=y-(diff/weaponHeight);
					newY-=1;
				}
				int id=layer.getTileId(x, newY);
				if(id>0 && layer.isCollidable() || id==-1) {
					LaunchManager.unregister(this);
					if(layer instanceof ObjectLayer) {
						((ObjectLayer) layer).destroy(x, newY);
					}
				}
			}
		}
		return yCoord;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public Graphic getSprite() {
		return sprite;
	}

	public int getDirection() {
		return direction;
	}
}
