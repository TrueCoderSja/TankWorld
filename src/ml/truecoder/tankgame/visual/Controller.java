package ml.truecoder.tankgame.visual;

import java.util.ArrayList;
import java.util.List;

import ml.truecoder.tankgame.GameData;
import ml.truecoder.tankgame.nogui.ControllerListener;

public class Controller implements GameData {
	private static List<ControllerListener> listeners=new ArrayList<ControllerListener>();
	
	public static void addListener(ControllerListener listener) {
		listeners.add(listener);
	}
	
	public static void upKey() {
		for(ControllerListener listener:listeners)
			listener.upPressed();
	}
	
	public static void downKey() {
		for(ControllerListener listener:listeners)
			listener.downPressed();
	}
	
	public static void leftKey() {
		for(ControllerListener listener:listeners)
			listener.leftPressed();
	}
	
	public static void rightKey() {
		for(ControllerListener listener:listeners)
			listener.rightPressed();
	}
	
	public static void fireKey() {
		for(ControllerListener listener:listeners)
			listener.firePressed();
	}
}