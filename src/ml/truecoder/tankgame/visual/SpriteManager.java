package ml.truecoder.tankgame.visual;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.exceptions.NoSpriteWithIDException;
import ml.truecoder.tankgame.nogui.Utilities;

public class SpriteManager implements GameData {
	private static HashMap<Integer, Spritesheet> spritesheets=new HashMap<Integer, Spritesheet>();
	
	public static Sprite getSprite(int id, int positionIndex) throws NoSpriteWithIDException {
		Spritesheet sheet=spritesheets.get(id);
		if(sheet==null)
			throw new NoSpriteWithIDException(id);
		switch(positionIndex) {
		case UP:
			return sheet.getUpSprite();
		case DOWN:
			return sheet.getDownSprite();
		case LEFT:
			return sheet.getLeftSprite();
		case RIGHT:
			return sheet.getRightSprite();
		default:
			return null;
		}
	}
	
	public static int getSpriteWidth(int id) {
		Spritesheet sheet=spritesheets.get(id);
		if(sheet==null)
			throw new NoSpriteWithIDException(id);
		return sheet.getWidth();
	}
	
	public static int getSpriteHeight(int id) {
		Spritesheet sheet=spritesheets.get(id);
		if(sheet==null)
			throw new NoSpriteWithIDException(id);
		return sheet.getHeight();
	}
	
	public static void init() {
		//Map Data
		ArrayList<String[]> mappedData=new ArrayList<String[]>();
		Scanner dataFile=new Scanner(Utilities.getAsset(spritesheetInfoFile));
		String line, values[], tmp[];
		while(dataFile.hasNextLine()) {
			tmp=new String[8];
			line=dataFile.nextLine();
			values=line.split(",");
			int i=0;
			for(String val:values) {
				tmp[i++]=val.trim();
			}
			mappedData.add(tmp);
		}
		
		for(String entry[]:mappedData) {
			int id=Integer.parseInt(entry[0]);
			int spriteWidth=Integer.parseInt(entry[1]);
			int spriteHeight=Integer.parseInt(entry[2]);
			int[] positionIndexes= {Integer.parseInt(entry[3]), Integer.parseInt(entry[4]), Integer.parseInt(entry[5]), Integer.parseInt(entry[6])};
			Spritesheet sheet=new Spritesheet("Spritesheets"+File.separator+entry[7], id, spriteWidth, spriteHeight, positionIndexes);
			spritesheets.put(id, sheet);
		}
	}
}
