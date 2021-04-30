package ml.truecoder.tankgame.nativecode;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

import ml.truecoder.tankgame.GameData;
public class Window extends JFrame implements WindowStateListener, GameData {
	public static Window self;
	public static final long serialVersionUID=1L;
	Window() {
		self=this;
		add(new GraphicsFactory());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		addWindowStateListener(this);
		addKeyListener(new NativeController());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets=getInsets();
		int decoWidth=insets.left+insets.right, decoHeight=insets.top+insets.bottom;
		setMinimumSize(new Dimension(GameData.MINIMUM_RESOLUTION[0]+decoWidth, GameData.MINIMUM_RESOLUTION[1]+decoHeight));
	}
	public static void minimise() {
		self.setState(ICONIFIED);
	}
	public static void restore() {
		self.dispose();
		self.setUndecorated(false);
		self.setVisible(true);
		self.setExtendedState(NORMAL);
	}
	public static void close() {
		System.exit(0);
	}
	public void windowStateChanged(WindowEvent e) {
		if(e.getNewState()==MAXIMIZED_BOTH && !isUndecorated()) {
			dispose();
			setUndecorated(true);
			setVisible(true);
		}
		else if(e.getNewState()==NORMAL) {
			setSize(new Dimension(GameData.MINIMUM_RESOLUTION[0], GameData.MINIMUM_RESOLUTION[1]));
		}
	}
}
