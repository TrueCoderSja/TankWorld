package ml.truecoder.tankgame.visual;

import java.util.List;

import ml.truecoder.tankgame.GameData;

import java.util.ArrayList;

/**
 * @author TrueCoder
 *This class is valid for all the changes taking place
 */
public class World implements GameData {
	
	private ArrayList<Layer> layers=new ArrayList<Layer>();
	private int layerCount=0;
	private int x, y;
	private int len;
	private Layer xLargestLayer, yLargestLayer;
	private PlayerLayer primaryActorLayer;
	private int lowerX, upperX, lowerY, upperY;
	private Tank tank;
	private List<Integer> collidableLayerIndexes=new ArrayList<Integer>();
	
	
	public void addLayer(Layer lyr) {
		layers.add(lyr);
		len++;
		if(lyr.isCollidable())
			collidableLayerIndexes.add(len-1);
		evaluateLimits(len-1);
		
	}
	public void addLayer(Layer lyr, int index) {
		layers.add(index, lyr);
		len++;
		evaluateLimits(index);
		if(lyr.isCollidable())
			collidableLayerIndexes.add(index);
	}
	
	public void addCollidable(Layer lyr) {
		int index=layers.indexOf(lyr);
		if(index==-1)
			return;
		collidableLayerIndexes.add(index);
	}
	public void removeCollidabe(Layer layer) {
		int index=layers.indexOf(layer);
		if(index==-1)
			return;
		collidableLayerIndexes.remove(index);
	}
	
	private void evaluateLimits(int index) {
		//Evaluate max and min
		Layer newLayer=layers.get(index);
		if((index+1)>1) {
			xLargestLayer=(newLayer.getWidth()>xLargestLayer.getWidth())?newLayer:xLargestLayer;
			yLargestLayer=(newLayer.getHeight()>yLargestLayer.getHeight())?newLayer:yLargestLayer;
		}
		else {
			xLargestLayer=newLayer;
			yLargestLayer=newLayer;
		}
		if(xLargestLayer.getXtiles()%2==1) {
			lowerX=xLargestLayer.getLowerX();
			upperX=xLargestLayer.getUpperX();
		}
		else {
			lowerX=xLargestLayer.getLowerX();
			upperX=xLargestLayer.getUpperX()+2;
		}
		if(yLargestLayer.getYtiles()%2==1) {
			lowerY=yLargestLayer.getLowerY();
			upperY=yLargestLayer.getUpperY();
		}
		else {
			lowerY=yLargestLayer.getLowerY()-1;
			upperY=yLargestLayer.getUpperY()-1;
		}
	}
	//Setters
	public void setPrimaryActorLayer(PlayerLayer lyr) {
		this.primaryActorLayer=lyr;
		if(layers.indexOf(lyr)==-1)
			addLayer(lyr);
	}
	
	public void setTank(Tank tank) {
		this.tank=tank;
	}
		
	//Getters
	public Tank getTank() {
		return tank;
	}
	
	public Layer getLayer() {
		if(layerCount<len)
			return layers.get(layerCount++);
		else
			return null;
	}
	
	public Layer getLayer(int index) {
		return layers.get(index);
	}
	
	public int getLength() {
		return len;
	}
	
	public Layer getXlargestLayer() {
		return xLargestLayer;
	}
	
	public int getUpperX() {
		return upperX;
	}
	public int getLowerX() {
		return lowerX;
	}
	public int getUpperY() {
		return upperY;
	}
	public int getLowerY() {
		return lowerY;
	}
	
	public void moveRight(int speed) {
		int newX=x-speed;
		if(newX>lowerX && isMoveAllowed(speed)) {
			x=newX;
			matchTankCoords();
		}
	}
	public void moveLeft(int speed) {
		int newX=x+speed;
		if(newX<upperX && isMoveAllowed(speed)) {
			x=newX;
			matchTankCoords();
		}
		else
			System.out.println();
	}
	public void moveDown(int speed) {
		int newY=y+speed;
		if(newY<upperY) {
			if(isMoveAllowed(speed)) {
				y=newY;
				matchTankCoords();
			}
		}
	}
	public void moveUp(int speed) {
		int newY=y-speed;
		if(newY>lowerY) {
			if(isMoveAllowed(speed)) {
				y=newY;
				matchTankCoords();
			}
		}
	}
	public void matchTankCoords() {
		int[] coords= {x, y};
		tank.setCoords(coords);
	}
	public int X() {
		return x;
	}
	public int Y() {
		return y;
	}
	/**
	 * Returns no. of layers
	 */
	public int len() {
		return len;
	}
	
	//House Methods
	public void reset() { 
		layerCount=0;
	}
	public Layer getYlargestLayer() {
		return yLargestLayer;
	}
	public PlayerLayer getPrimaryActorLayer() {
		return primaryActorLayer;
	}
	public boolean isMoveAllowed(int speed) {
		int direction=tank.getDirection();
		boolean moveAllowed=true;
		int newCoord=(direction==UP || direction==DOWN)?y:x;
		switch(direction) {
		case RIGHT:
			newCoord=x+speed;
			break;
		case LEFT:
			newCoord=x-speed;
			break;
		case UP:
			newCoord=y+speed;
			break;
		case DOWN:
			newCoord=y-speed;
			break;
		}
		for(int index:collidableLayerIndexes) {
			Layer layer=layers.get(index);
			int width=tank.getWidth(), height=tank.getHeight();
			int tileHeight=xLargestLayer.getTileHeight(), tileWidth=xLargestLayer.getTileWidth();
			int offset1 ,coord1, nT, offset2;
			if(direction==LEFT || direction==RIGHT) {
				offset1=(height%2==0)?height/2:height/2+1;
				coord1=this.y+(offset1/tileHeight);
				coord1=(offset1%tileHeight==0)?coord1:coord1+1;
				nT=height/tileHeight;
				nT=(height%tileHeight==0)?nT:nT+1;
				offset2=(width%2==0)?width/2:width/2+1;
			}
			else {
				offset1=(width%2==0)?width/2:width/2+1;
				coord1=this.x+(offset1/tileWidth);
				coord1=(offset1%tileWidth==0)?x:x+1;
				nT=width/tileWidth;
                nT=(width%tileWidth==0)?nT:nT+1;
                offset2=(height%2==0)?height/2:height/2+1;
			}
			int coord2=0;
			switch(direction) {
			case RIGHT:
				coord2=newCoord+(offset2/tileWidth);
				break;
			case LEFT:
				coord2=newCoord-(offset2/tileWidth);
				break;
			case UP:
				coord2=newCoord+(offset2/tileHeight);
				break;
			case DOWN:
				coord2=newCoord-(offset2/tileHeight);
				break;
			}
			for(int i=coord1;i>coord1-nT;i--) {
				int tileID;
				if(direction==LEFT || direction==RIGHT)
					tileID=layer.getTileId(coord2, i);
				else
					tileID=layer.getTileId(i, coord2);
				moveAllowed=(tileID>0)?false:moveAllowed;
			}			
		}
		return moveAllowed;
	}
}
