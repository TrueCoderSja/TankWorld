package ml.truecoder.tankgame.visual;

import java.io.InputStream;

public class ObjectLayer extends Layer {
	int explodeSpriteID;
	
	public ObjectLayer(int tileWidth, int tileHeight, int explodeSpriteID, InputStream plainLyrFile) {
		super(tileWidth, tileHeight, plainLyrFile);
		setCollidable(true);
		this.explodeSpriteID=explodeSpriteID;
	}
	
	public void destroy(int xTileIndex, int yTileIndex) {
		int upperX=0, lowerX=xTileIndex, upperY=yTileIndex, lowerY=yTileIndex;
		upperScanner:
		for(int y=yTileIndex;;y++) {
			boolean started=false;
			int newX=xTileIndex;
			for(int x=xTileIndex;;x++) {
				int tileID=getTileId(x,y);
				if(tileID<1 && started==false)
					break upperScanner;
				else if(tileID<1)
					break;
				else
					newX++;
				started=true;
			}
			upperX=(--newX>upperX)?newX:upperX;
			upperY++;
		}
		upperY--;
		
		//Scan tiles below currentY
		lowerScanner:
		for(int y=yTileIndex;;y--) {
			boolean started=false;
			int newX=xTileIndex;
			for(int x=xTileIndex;;x++) {
				int tileID=getTileId(x,y);
				if(tileID<1 && started==false)
					break lowerScanner;
				else if(tileID<1)
					break;
				else
					newX++;
				started=true;
			}
			upperX=(--newX>upperX)?newX:upperX;
			lowerY--;
		}
		lowerY++;
		
		for(int y=lowerY;y<=upperY;y++) {
			int newX=upperX;
			for(int x=upperX;;x--) {
				int tileID=getTileId(x,y);
				if(tileID<1)
					break;
				else
					newX--;
			}
			lowerX=(++newX<lowerX)?newX:lowerX;
		}
		
		for(int y=lowerY;y<=upperY;y++) {
			for(int x=lowerX;x<=upperX;x++) {
				removeTile(x, y);
			}
		}
		world.getPrimaryActorLayer().addPlayer(new Actor(explodeSpriteID, new int[] {lowerX, upperY}));
	}
}
