package ml.truecoder.tankgame.nativecode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import ml.truecoder.tankgame.visual.Controller;

public class NativeController extends KeyAdapter  {
	public void keyPressed(KeyEvent e) {
		int keyCode=e.getKeyCode();
		switch(keyCode) {
		case KeyEvent.VK_NUMPAD8:
			Controller.upKey();
		break;
		case KeyEvent.VK_NUMPAD2:
			Controller.downKey();
			break;
		case KeyEvent.VK_NUMPAD4:
			Controller.leftKey();
			break;
		case KeyEvent.VK_NUMPAD6:
			Controller.rightKey();
			break;
		case KeyEvent.VK_NUMPAD5:
			Controller.fireKey();
		}
	}
}
