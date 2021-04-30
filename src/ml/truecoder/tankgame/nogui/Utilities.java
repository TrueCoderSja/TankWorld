package ml.truecoder.tankgame.nogui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import ml.truecoder.tankgame.nativecode.Graphic;

import java.util.HashMap;

public class Utilities {
	public static Graphic getAssestGraphic(String path) {
		try {
			return new Graphic(Utilities.class.getClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static Graphic getAssestGraphic(String path, int mode) {
		try {
			return new Graphic(Utilities.class.getClassLoader().getResourceAsStream(path), mode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static InputStream getAsset(String path) {
		return Utilities.class.getClassLoader().getResourceAsStream(path);
	}
	public static String getTextContent(String filePath) {
		Scanner scanner=new Scanner(getAsset(filePath));
		scanner.useDelimiter("\\A");
		String content=scanner.hasNext()?scanner.next():"";
		scanner.close();
		return content;
	}
	public static HashMap<String, String> parseEQS(String filePath) {
		Scanner sc=new Scanner(getAsset(filePath));
		HashMap<String, String> eqs_data=new HashMap<String, String>();
		String tmp[];
		while(sc.hasNextLine()) {
			tmp=sc.nextLine().split("=");
			if(tmp.length!=0)
				eqs_data.put(tmp[0], tmp[1]);
		}
		sc.close();
		return eqs_data;
	}
}
