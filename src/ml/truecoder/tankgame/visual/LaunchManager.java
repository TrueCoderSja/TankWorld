package ml.truecoder.tankgame.visual;

import java.util.ArrayList;
import java.util.List;

public class LaunchManager {
	private static List<Launch> launches=new ArrayList<Launch>();
	private static int currentIndex;
	
	public static void register(int x, int y, int direction, Tank tank) {
		launches.add(new Launch(x, y, direction, tank));
	}
	
	public static Launch getLaunch() {
		if(currentIndex>=launches.size()) {
			currentIndex=0;
			return null;
		}
		return launches.get(currentIndex++);
	}
	
	public static void unregister(Launch launch) {
		int index=launches.indexOf(launch);
		if(index==-1)
			return;
		launches.remove(index);
	}
}
