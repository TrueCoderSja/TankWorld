package ml.truecoder.tankgame.visual;

import java.util.List;
import java.util.ArrayList;

/**
 * @author TrueCoder
 *This class is valid for all the changes taking place
 */
public class World {
	
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
		boolean moveAllowed=true;
		int newX=x-speed;
		if(newX>lowerX) {
//		for(int index:collidableLayerIndexes) {
//			Layer layer=layers.get(index);
//			int width=tank.getWidth(), height=tank.getHeight();
//			int tileHeight=xLargestLayer.getTileHeight(), tileWidth=xLargestLayer.getTileWidth();
//			int offsetY=(height%2==0)?height/2:height/2+1;
//			int y=this.y+(offsetY/tileHeight);
//			y=(offsetY%tileHeight==0)?y:y+1;
//			int nY=height/tileHeight;
//			nY=(height%tileHeight==0)?nY:nY+1;
//			int offsetX=(width%2==0)?width/2:width/2+1;
//			int x=newX-(offsetX/tileWidth);
//			for(int i=y;i>y-nY;i--) {
//				int tileID=layer.getTileId(x, i);
//				moveAllowed=(tileID>0)?false:moveAllowed;
//			}				
//		}
			if(moveAllowed) {
				x=newX;
				matchTankCoords();
			}
		}
	}
	public void moveLeft(int speed) {
		boolean moveAllowed=true;
		int newX=x+speed;
		if(newX<upperX) {
//			for(int index:collidableLayerIndexes) {
//				Layer layer=layers.get(index);
//				int width=tank.getWidth(), height=tank.getHeight();
//				int tileHeight=xLargestLayer.getTileHeight(), tileWidth=xLargestLayer.getTileWidth();
//				int offsetY=(height%2==0)?height/2:height/2+1;
//				int y=this.y+(offsetY/tileHeight);
//				y=(offsetY%tileHeight==0)?y:y+1;
//				int nY=height/tileHeight;
//				nY=(height%tileHeight==0)?nY:nY+1;
//				int offsetX=(width%2==0)?width/2:width/2+1;
//				int x=newX+(offsetX/tileWidth);
//				for(int i=y;i>y-nY;i--) {
//					int tileID=layer.getTileId(x, i);
//					moveAllowed=(tileID>0)?false:moveAllowed;
//				}				
//			}
			if(moveAllowed) {
				x=newX;
				matchTankCoords();
			}
		}		
	}
	public void moveDown(int speed) {
		boolean moveAllowed=true;
		int newY=y+speed;
		if(newY<upperY) {
//			for(int index:collidableLayerIndexes) {
//				Layer layer=layers.get(index);
//				int width=tank.getWidth(), height=tank.getHeight();
//				int tileHeight=xLargestLayer.getTileHeight(), tileWidth=xLargestLayer.getTileWidth();
//				int offsetX=(width%2==0)?width/2:width/2+1;
//				int x=this.x+(offsetX/tileWidth);
//				x=(offsetX%tileWidth==0)?x:x+1;
//				int nX=width/tileWidth;
//				nX=(width%tileWidth==0)?nX:nX+1;
//				int offsetY=(height%2==0)?height/2:height/2+1;
//				int y=newY+(offsetY/tileHeight);
//				for(int i=x;i>x-nX;i--) {
//					int tileID=layer.getTileId(i, y);
//					moveAllowed=(tileID>0)?false:moveAllowed;
//				}				
//			}
			if(moveAllowed) {
				y=newY;
				matchTankCoords();
			}
		}
	}
	public void moveUp(int speed) {
		boolean moveAllowed=true;
		int newY=y-speed;
		if(newY>lowerY) {
//			for(int index:collidableLayerIndexes) {
//				Layer layer=layers.get(index);
//				int width=tank.getWidth(), height=tank.getHeight();
//				int tileHeight=xLargestLayer.getTileHeight(), tileWidth=xLargestLayer.getTileWidth();
//				int offsetX=(width%2==0)?width/2:width/2+1;
//				int x=this.x+(offsetX/tileWidth);
//				x=(offsetX%tileWidth==0)?x:x+1;
//				int nX=width/tileWidth;
//				nX=(width%tileWidth==0)?nX:nX+1;
//				int offsetY=(height%2==0)?height/2:height/2+1;
//				int y=newY-(offsetY/tileHeight);
//				for(int i=x;i>x-nX;i--) {
//					int tileID=layer.getTileId(i, y);
//					moveAllowed=(tileID>0)?false:moveAllowed;
//				}	
//			}
			if(moveAllowed) {
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
}
