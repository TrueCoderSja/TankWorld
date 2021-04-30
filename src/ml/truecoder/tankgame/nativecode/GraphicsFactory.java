package ml.truecoder.tankgame.nativecode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.Timer;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.exceptions.NoSpriteWithIDException;
import ml.truecoder.tankgame.nogui.Utilities;
import ml.truecoder.tankgame.visual.Layer;
import ml.truecoder.tankgame.visual.Actor;
import ml.truecoder.tankgame.visual.AutoDeleteActor;
import ml.truecoder.tankgame.visual.Launch;
import ml.truecoder.tankgame.visual.LaunchManager;
import ml.truecoder.tankgame.visual.PlayerLayer;
import ml.truecoder.tankgame.visual.Sprite;
import ml.truecoder.tankgame.visual.SpriteManager;
import ml.truecoder.tankgame.visual.TileManager;

public class GraphicsFactory extends JPanel implements MouseListener, MouseMotionListener, GameData {
	private int[][] optionCoordinates= {{6, 23}, {270, 290}, {246, 266}, {222, 242}};
	private Image options;
	private int optionsX;
	private boolean drawn;
	private Timer refresher;
	private static int xOffset, yOffset;
	private static JPanel self;
	private HashMap<Actor, Integer> frameCounts=new HashMap<Actor, Integer>();
	
	public GraphicsFactory() {
		self=this;
		setBackground(Color.BLACK);
		addMouseListener(this);
		addMouseMotionListener(this);
		refresher=new Timer(REFRESH_RATE, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		refresher.start();
	}
	private static final long serialVersionUID = 1L;
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 int centerXpix=0, centerYpix=0;
		 double[] percentDifference=new double[2];
		 g.setFont(new Font("Arial", Font.BOLD, 16));
		 g.setColor(Color.BLACK);
		 int[] resolution=getResolution();
		 int xGBox=(getWidth()-resolution[0])/2;
		 int yGBox=(getHeight()-resolution[1])/2;
		 g.fillRect(xGBox, yGBox, resolution[0], resolution[1]);
		 
		 //Calculates percent difference
		 percentDifference[0]=((double)resolution[0]/BASE_RESOLUTION[0])*100.0;
		 percentDifference[1]=((double)resolution[1]/BASE_RESOLUTION[1])*100.0;
		 
		 //Game Part Here
		 world.reset();
		 while(true){
			 Layer layer=world.getLayer();
			 if(layer==null)
				 break;
			 if(!(layer instanceof PlayerLayer)) {
				 int tileHeight=layer.getTileHeight(), tileWidth=layer.getTileWidth();
				 int xTiles=BASE_RESOLUTION[0]/tileWidth, yTiles=BASE_RESOLUTION[1]/tileHeight;
				 xOffset=(BASE_RESOLUTION[0]-(xTiles*tileWidth))/2;
				 yOffset=(BASE_RESOLUTION[1]-(yTiles*tileHeight))/2;
				 int xBackCount=(xTiles%2==0)?xTiles/2-1:xTiles/2, yBackCount=yTiles/2;
				 int xPix=0, yPix=0;
				 int yTileIndex=0, xTileIndex = 0;
				 int tileID;
				 
				 //Get new width and image adapted to current resolution
				 tileWidth=(int)((percentDifference[0]*tileWidth)/100);
				 tileHeight=(int)((percentDifference[1]*tileHeight)/100);
				 
				 for(int yTileCount=yTiles-1;yTileCount>=0;yTileCount--) {
					 yPix=yGBox+yOffset+(tileHeight*yTileCount);
					 for(int xTileCount=0;xTileCount<xTiles;xTileCount++) {
						xPix=xGBox+xOffset+(tileWidth*xTileCount);
						xTileIndex=world.X()+xTileCount-xBackCount;
						yTileIndex=world.Y()-yTileCount+yBackCount;
						tileID=layer.getTileId(xTileIndex, yTileIndex);
						if(xTileIndex==0 && yTileIndex==0) 
							g.drawRect(xPix, yPix, tileWidth, tileHeight);
						if(xTileIndex-world.X()==0 && yTileIndex-world.Y()==0) {
							centerXpix=(tileWidth%2==1)?xPix+tileWidth/2+1:xPix+tileWidth/2;
							centerYpix=(tileHeight%2==1)?yPix+tileHeight/2+1:yPix+tileHeight/2;
							Layer xLargestLayer=world.getXlargestLayer();
							if(layer==xLargestLayer) {
								layer.setCoord(centerXpix, centerYpix);
							}
						}
						if(tileID>0) {
							g.drawImage(TileManager.getTile(tileID).tile.val, xPix, yPix, tileWidth, tileHeight, null);
							//g.drawRect(xPix, yPix, tileWidth, tileHeight);
						}
						else if(tileID==-1) {
							g.setColor(Color.BLACK);
							g.drawRect(xPix, yPix, tileWidth, tileHeight);
						}
					
//						//TODO Remove at ASAWD
						if(xTileIndex==0) {
							g.setColor(Color.RED);
							g.drawString(yTileIndex+"", xPix, yPix+tileHeight);
							g.setColor(Color.BLUE);
							g.drawRect(xPix, yPix, tileWidth, tileHeight);
						}
						if(yTileIndex==0) {
							g.setColor(Color.RED);
							g.drawString(xTileIndex+"", xPix, yPix+tileHeight);
							g.setColor(Color.BLUE);
							g.drawRect(xPix, yPix, tileWidth, tileHeight);
						}
					 }
				 }
			 }
			 else if(layer.getClass()==PlayerLayer.class) {
				 PlayerLayer playerlayer=(PlayerLayer)layer;
				 while(true) {
					 Object[] playerData=playerlayer.getPlayerData();
					 if(playerData[0]==null || playerData[1]==null)
						 break;
					 try {
						 Actor actor=(Actor)playerData[0];
						 Sprite actorSprite=SpriteManager.getSprite(actor.getSpriteID(), actor.getDirection());
						 int frameCount;
						 if(frameCounts.get(actor)==null) {
							 frameCounts.put(actor, 1);
							 frameCount=0;
						 }
						 else {
							 Integer val=frameCounts.get(actor);
							 frameCount=val;
							 if(val==actorSprite.getFramesLength()) {
								 frameCount=0;
								 val=0;
								 if(actor instanceof AutoDeleteActor) {
									 playerlayer.removePlayer(actor);
									 continue;
								 }
							 }
							 else
								 val++;
							 frameCounts.put(actor, val);
						 }
					 	 int[] tileCoords=(int[])playerData[1];
							 Layer referenceLayer=world.getXlargestLayer();
							 int xPix=referenceLayer.getXpix(tileCoords[0]);
							 int yPix=referenceLayer.getYpix(tileCoords[1]);
							 Graphic frame=actorSprite.getFrame(frameCount);
							 int width=frame.getWidth(), height=frame.getHeight();
							 width=(int)((percentDifference[0]*width)/100);
							 height=(int)((percentDifference[1]*height)/100);
							 xPix=(width%2==1)?xPix-width/2+1:xPix-width/2;
							 yPix=(height%2==1)?yPix-height/2+1:yPix-height/2;
							 g.drawImage(frame.val, xPix, yPix, width, height, null);
					 } catch (NoSpriteWithIDException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 }
				 }
			 }			 
		 }
		 
		 //Launch code here
		 while(true) {
			 Launch launch=LaunchManager.getLaunch();
			 if(launch==null)
				 break;
			 g.drawImage(launch.getSprite().val, launch.getXcoord(), launch.getYcoord(), null);
		 }
		 
//		 int[] coords=world.getXlargestLayer().centerCoord;
//		 g.setColor(Color.GREEN);
//		 g.drawRect(coords[0], coords[1], 32, 32);
		 
		 //Tank drawing code here
//		try {
//			 Sprite tankSprite=SpriteManager.getSprite(world.getTank().getSpriteID(), world.getTank().getDirection());
//			 int width=tankSprite.getWidth(), height=tankSprite.getHeight();
//			 width=(int)((percentDifference[0]*width)/100);
//			 height=(int)((percentDifference[1]*height)/100);
//			 int xPix=centerXpix-width/2+1;
//			 int yPix=centerYpix-height/2+1;
//			 g.drawImage(tankSprite.getFrame().val, xPix, yPix, width, height, null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 
	}
	private static int[] getResolution() {
		int[] windowRatio= {self.getWidth(), self.getHeight()};
		int calXLength, calYLength;
		if((windowRatio[1]%ASPECT_RATIO[1])!=0) {
			windowRatio[1]-=windowRatio[1]%ASPECT_RATIO[1];
		}
		int[] windowResolution=windowRatio.clone();
		int d1, d2, gcd=1;
		d1=windowRatio[0]<windowRatio[1]?windowRatio[0]:windowRatio[1];
		d2=windowRatio[0]>windowRatio[1]?windowRatio[0]:windowRatio[1];
		while(d2%d1!=0) {
			gcd=d2%d1;
			d2=d1; 
			d1=gcd;
		}
		windowRatio[0]/=gcd;
		windowRatio[1]/=gcd;
		calXLength=(ASPECT_RATIO[0]*windowResolution[1])/ASPECT_RATIO[1];
		calYLength=(ASPECT_RATIO[1]*windowResolution[0])/ASPECT_RATIO[0];
		if(calXLength>windowResolution[0])
			windowResolution[1]=calYLength;
		else
			windowResolution[0]=calXLength;
		return windowResolution;
	}
	public void drawWindowOptions(Graphics g) {
		options=Utilities.getAssestGraphic("icons/menu.png").val;
		optionsX=(getWidth()-options.getWidth(null))/2;
		g.drawImage(options, optionsX, 0, null);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int x=e.getX();
		int y=e.getY();
		if(y>=optionCoordinates[0][0] && y<=optionCoordinates[0][1]) {
			if(x>=optionCoordinates[1][0]+optionsX && x<=optionCoordinates[1][1]+optionsX)
				Window.close();
			else if(x>=optionCoordinates[2][0]+optionsX && x<=optionCoordinates[2][1]+optionsX)
				Window.restore();
			else if(x>=optionCoordinates[3][0]+optionsX && x<=optionCoordinates[3][1]+optionsX)
				Window.minimise();
		}
		
	}
	
	public static int getStartingX() {
		return xOffset;
	}	
	public static int getStartingY() {
		return yOffset;
	}
	
	public static int getEndingX() {
		return getStartingX()+getResolution()[0];
	}
	public static int getEndingY() {
		return getStartingY()+getResolution()[1];
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(Window.self.isUndecorated()) {
			if(e.getY()<=optionCoordinates[0][1]+100) {
				drawWindowOptions(getGraphics());
				drawn=true;
			}
			else if(drawn) {
				repaint();
				drawn=false;
			}
		}		
	}
}
