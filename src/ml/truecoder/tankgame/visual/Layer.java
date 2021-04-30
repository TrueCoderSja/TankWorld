package ml.truecoder.tankgame.visual;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.exceptions.CoordinatesNotSetException;
import ml.truecoder.tankgame.exceptions.CoordinatesOutOfScreenRangeException;
import ml.truecoder.tankgame.exceptions.TileMapLengthsUnequalException;
import ml.truecoder.tankgame.nativecode.GraphicsFactory;

public class Layer implements GameData {
	private int tileWidth, tileHeight;
	private boolean isCollidable;
	private int xTiles, yTiles;
	
	//TileMap Fields
	private int[][] rawMap;
	private int[] center, min, max;
	public int[] centerCoord;
	private int x, y;
	private boolean xODD, yODD;
	private boolean coordCalled;
	
	//Constructors
	
	protected Layer()
	{}	//To be used only by PlayerLayer
	
	public Layer(int tileWidth, int tileHeight) {
		if(tileWidth>0 && tileHeight>0) {
			this.tileWidth=tileWidth;
			this.tileHeight=tileHeight;
		}
	}
	
	public Layer(int tileWidth, int tileHeight, int[][] tileMap) {
		if(tileWidth>0 && tileHeight>0) {
			this.tileWidth=tileWidth;
			this.tileHeight=tileHeight;
			setTileMap(tileMap);
		}
	}
	
	public Layer(int tileWidth, int tileHeight, InputStream plainLyrFile) {
		if(tileWidth>0 && tileHeight>0) {
			this.tileWidth=tileWidth;
			this.tileHeight=tileHeight;
			setTileMap(cookLyr(plainLyrFile));
		}
	}
	
	//House Methods
	public int[][] cookLyr(InputStream lyr) throws TileMapLengthsUnequalException {
		Scanner lineReader=new Scanner(lyr);
		Scanner idReader;
		ArrayList<ArrayList<Integer>> tileMap=new ArrayList<ArrayList<Integer>>();
		int x=0, y;
		while(lineReader.hasNextLine()) {
			ArrayList<Integer> tmp=new ArrayList<Integer>();
			y=0;
			String line=lineReader.nextLine();
			idReader=new Scanner(line);
			while(idReader.hasNextInt()) {
				tmp.add(idReader.nextInt());
				y++;
			}
			idReader.close();
			if(yTiles!=y && yTiles!=0)
			{
				lineReader.close();
				throw new TileMapLengthsUnequalException();
			}
			yTiles=y;
			x++;
			tileMap.add(tmp);
		}
		xTiles=x;
		lineReader.close();
		int[][] rawMap=new int[xTiles][yTiles];
		int i=0;
		for(ArrayList<Integer> tmp:tileMap) {
			rawMap[i++]=tmp.stream().mapToInt(j->j).toArray();;
		}
		return rawMap;
	}
	
	public void setCoord(int xCoord, int yCoord) {
		if(!coordCalled) {
			centerCoord=new int[2];
			coordCalled=true;
		}
		centerCoord[0]=xCoord-world.X()*tileWidth;
		centerCoord[1]=yCoord+world.Y()*tileHeight;
	}
	
	private int getAbsX(int x) {
		return center[0]+x;
	}
	
	private int getAbsY(int y) {
		return center[1]-y;
	}
	
	/**
	 * This method returns the tile id in the given referenced co-ordinates
	 */
	public int getTileId(int x, int y) {
		if((x<min[0] || x>max[0]) || (y<min[1] || y>max[1]))
		{
			return -1;
			//TODO throw new exception here
		} 
		else {
			int xC=getAbsX(x);
			int yC=getAbsY(y);
			return rawMap[yC][xC];
		}
	}
	
	//Setters
	public boolean setTileMap(int[][] tileMap) {
		if(tileMap.length>0 && tileMap[0].length>0) {
			center=new int[2];
			max=new int[2];
			min=new int[2];
			xTiles=tileMap.length;
			yTiles=tileMap[0].length;
			this.rawMap=tileMap;
			x=rawMap[0].length;
			y=rawMap.length;
			xODD=!(x%2==0);
			yODD=!(y%2==0);
			
			//Initialize x elements  |||| 0 subscript refers x part
			if(xODD) {
				center[0]=(x-1)/2;
				max[0]=center[0];
				min[0]=-center[0];
			}
			else {
				center[0]=(x-1)/2;
				max[0]=center[0]+1;
				min[0]=-(center[0]);
			}
			
			//Initialize y elements |||| 1 subscript refers to y part
			if(yODD) {
				center[1]=(y-1)/2;
				max[1]=center[1];
				min[1]=-center[1];
			}
			else {
				center[1]=(y-1)/2;
				max[1]=center[1]+1;
				min[1]=-(center[1]);
				
				//Tricky play; we find max in min by assuming center of y as center of x
				//Now initialise actual center
				center[1]=(y-1)/2+1;
			}
			
			
			return true;
		}
		else
			return false;
	}
	
	public void removeTile(int x, int y) {
		int xC=getAbsX(x);
		int yC=getAbsY(y);
		rawMap[yC][xC]=0;
	}
	
	public void setCollidable(boolean isCollidable) {
		if(isCollidable==true && this.isCollidable==false)
			world.addCollidable(this);
		else if(isCollidable==false && this.isCollidable==true)
			world.removeCollidabe(this);
		this.isCollidable=isCollidable;
	}
	
	//Getters
	public int[][] getTileMap() {
		return rawMap;
	}
	public boolean isCollidable() {
		return isCollidable;
	}
	public int[] getDimensions() {
		int[] dim={tileWidth, tileHeight};
		return dim;
	}
	public int getTileWidth() {
		return tileWidth;
	}
	public int getTileHeight() {
		return tileHeight;
	}
	public int getWidth() {
		return tileWidth*xTiles;
	}
	public int getHeight() {
		return tileHeight*yTiles;
	}
	public int getLowerX() {
		return min[0];
	}
	public int getUpperX() {
		if(getXtiles()%2==0)
			return max[0]-2;
		else
			return max[0];
	}
	public int getLowerY() {
		return min[1];
	}
	public int getUpperY() {
		return max[1];
	}
	
	public int getXpix(int xTileIndex) throws CoordinatesOutOfScreenRangeException {
		if(!coordCalled)
			throw new CoordinatesNotSetException();
		int xPix=centerCoord[0]+(xTileIndex*tileWidth);
		if(xPix==GraphicsFactory.getStartingX()) {
			int nextXPix=centerCoord[0]+((xTileIndex+1)*tileWidth);
			if(nextXPix==xPix)
				throw new CoordinatesOutOfScreenRangeException();
		}
		else if(xPix==GraphicsFactory.getEndingX()) {
			int prevXPix=centerCoord[0]+((xTileIndex-1)*tileWidth);
			if(prevXPix==xPix)
				throw new CoordinatesOutOfScreenRangeException();
		}
		else if(xPix>GraphicsFactory.getEndingX() || xPix<GraphicsFactory.getStartingX())
			throw new CoordinatesOutOfScreenRangeException();
		return xPix;
	}
	
	public int getYpix(int yTileIndex) throws CoordinatesOutOfScreenRangeException {
		if(!coordCalled)
			throw new CoordinatesNotSetException();
		int yPix=centerCoord[1]-(yTileIndex*tileHeight);
		if(yPix==GraphicsFactory.getStartingY()) {
			int nextYPix=centerCoord[1]-((yTileIndex-1)*tileHeight);
			if(yPix==nextYPix)
				throw new CoordinatesOutOfScreenRangeException();
		}
		else if(yPix==GraphicsFactory.getEndingY()) {
			int prevYPix=centerCoord[1]-((yTileIndex+1)*tileHeight);
			if(yPix==prevYPix)
				throw new CoordinatesOutOfScreenRangeException();
		}
		else if(yPix<GraphicsFactory.getStartingY() || yPix>GraphicsFactory.getEndingY())
			throw new CoordinatesOutOfScreenRangeException();
		return yPix;
	}
	
	/**
	 * @return no. of columns of tiles that can be fitted on screen
	 */
	public int getXtiles() {
		return BASE_RESOLUTION[0]/tileWidth;
	}
	/**
	 * @return no. of rows of tiles that can be fitted on screen
	 */
	public int getYtiles() {
		return BASE_RESOLUTION[1]/tileHeight;
	}
}