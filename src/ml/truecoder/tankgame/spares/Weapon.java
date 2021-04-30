package ml.truecoder.tankgame.spares;

import ml.truecoder.tankgame.nativecode.Graphic;
import ml.truecoder.tankgame.nogui.Utilities;

public class Weapon {
//	private int damage;
	private int speed=1;
	private int xCord=-1, yCord=-1;
	private Graphic sprite;
	
	public Weapon(String imagePath) {
		sprite=Utilities.getAssestGraphic("Spares/"+imagePath);
	}
	
	public Graphic getSprite() {
		return sprite;
	}
	
	public int getSpeed() {
		return speed;
	}

	public int getXcord() {
		return xCord;
	}
	public int getYcord() {
		return yCord;
	}
	public void setXcord(int xCord) {
		this.xCord=xCord;
	}
	public void setYcord(int yCord) {
		this.yCord=yCord;
	}
	public void reset() {
		xCord=-1;
		yCord=-1;
	}
}
