package ml.truecoder.tankgame.visual;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.exceptions.NoSpriteWithIDException;
import ml.truecoder.tankgame.nogui.Utilities;

public class SpriteManager implements GameData {
	private static List<Spritesheet> spritesheets=new ArrayList<Spritesheet>();
	
	public static Sprite getSprite(int id, int positionIndex) throws NoSpriteWithIDException {
		for(Spritesheet sheet:spritesheets) {
			if(sheet.id==id) {
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
		}
		throw new NoSpriteWithIDException(id);
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
			spritesheets.add(sheet);
		}
	}
}
