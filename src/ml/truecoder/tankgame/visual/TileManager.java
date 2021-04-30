package ml.truecoder.tankgame.visual;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.nogui.Utilities;

public class TileManager implements GameData {
	
	private static int maxID;
	
	private static ArrayList<TileSheet> tileSheets=new ArrayList<TileSheet>();
	public static Tile getTile(int id) {
		for(TileSheet tileSheet:tileSheets) {
			if(id>=tileSheet.getLowerLimit() && id<=tileSheet.getUpperLimit()) {
				int xLen=tileSheet.getXLen();
				int x=id/xLen;
				x=(x*xLen==id)?x-1:x;
				int y=id-(xLen*x)-1;
				int tileHeight=tileSheet.getTileHeight();
				int tileWidth=tileSheet.getTileWidth();
				int xPix=y*tileWidth;
				int yPix=x*tileHeight;
				return tileSheet.extractTile(xPix, yPix, tileWidth, tileHeight);
			}
		}
		return null;
	}
	
	public static int getNoOfTiles() {
		return maxID;
	}
	public static void init() {
		//Map Data
		ArrayList<String[]> mappedData=new ArrayList<String[]>();
		Scanner dataFile=new Scanner(Utilities.getAsset(tilsetInfoFile));
		String line, values[], tmp[];
		while(dataFile.hasNextLine()) {
			tmp=new String[5];
			line=dataFile.nextLine();
			values=line.split(",");
			int i=0;
			for(String val:values) {
				tmp[i++]=val.trim();
			}
			mappedData.add(tmp);
		}
		
		for(String entry[]:mappedData) {
			int lowerLimit=Integer.parseInt(entry[0]);
			int upperLimit=Integer.parseInt(entry[1]);
			int tile_width=Integer.parseInt(entry[2]);
			int tile_height=Integer.parseInt(entry[3]);
			TileSheet sheet=new TileSheet("TileSheets"+File.separator+entry[4], lowerLimit, upperLimit, tile_width, tile_height);
			tileSheets.add(sheet);
			
			//Update max ID
			maxID=(upperLimit>maxID)?upperLimit:maxID;
		}
		
//		//Load tiles in ram
//		for(String[] entry:mappedData) {
//			try {
//				TileSheet sheet=new TileSheet(Utilities.getAssestImage("TileSheets"+File.separator+entry[4]));
//				int w=Integer.parseInt(entry[2]), h=Integer.parseInt(entry[3]);
//				for(int imgID=Integer.parseInt(entry[0]); imgID<=Integer.parseInt(entry[1]); imgID++) {
//					for(int y=0;y<sheet.getHeight();y+=h) {
//						for(int x=0;x<sheet.getWidth();x+=w) {
//							Tile tile=sheet.extractTile(x, y, w, h);
//							images.add(tile);
//						}
//					}
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
}
