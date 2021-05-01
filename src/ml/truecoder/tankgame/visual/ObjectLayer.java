package ml.truecoder.tankgame.visual;

import java.io.InputStream;

import ml.truecoder.tankgame.nativecode.Speaker;

public class ObjectLayer extends Layer {
	int explodeSpriteID;
	
	public ObjectLayer(int tileWidth, int tileHeight, int explodeSpriteID, InputStream plainLyrFile) {
		super(tileWidth, tileHeight, plainLyrFile);
		setCollidable(true);
		this.explodeSpriteID=explodeSpriteID;
	}
	
	public void destroy(int xTileIndex, int yTileIndex) {
		int upperX=xTileIndex, lowerX=xTileIndex, upperY=yTileIndex, lowerY=yTileIndex;
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
		int x=lowerX+(upperX-lowerX)/2;
		x=((upperX-lowerX)%2==0)?x:x+1;
		int y=lowerY+(upperY-lowerY)/2;
		y=((upperY-lowerY)%2==0)?y:y+1;
		Speaker.play("explosion.wav");
		world.getPrimaryActorLayer().addPlayer(new AutoDeleteActor(explodeSpriteID, new int[] {x, y}));
	}
}
