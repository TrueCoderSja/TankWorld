package ml.truecoder.tankgame;

import java.util.Arrays;

import ml.truecoder.tankgame.nogui.Utilities;
import ml.truecoder.tankgame.visual.World;

public interface GameData {
	public static final String dataFile="versionSpecificData";
	public static final String tilsetInfoFile=Utilities.parseEQS(dataFile).get("tileSetInfoFile");
	public static final String spritesheetInfoFile=Utilities.parseEQS(dataFile).get("tankSpritesheetInfoFile");
	public static final int[] ASPECT_RATIO=Arrays.stream(Utilities.parseEQS(dataFile).get("stage_ratio").split(",")).mapToInt(Integer::parseInt).toArray();
	public static final int[] MINIMUM_RESOLUTION=Arrays.stream(Utilities.parseEQS(dataFile).get("minimumWindowResolution").split(",")).mapToInt(Integer::parseInt).toArray();
	public static final int[] BASE_RESOLUTION=Arrays.stream(Utilities.parseEQS(dataFile).get("baseResolution").split(",")).mapToInt(Integer::parseInt).toArray();
	public static final int UP=0;
	public static final int RIGHT=1;
	public static final int DOWN=2;
	public static final int LEFT=3;
	public static final int REFRESH_RATE=100;
	public static World world=new World();
}
