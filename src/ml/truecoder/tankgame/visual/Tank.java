package ml.truecoder.tankgame.visual;

import java.util.ArrayList;
import java.util.List;

import ml.truecoder.tankgame.nativecode.Speaker;
import ml.truecoder.tankgame.spares.Weapon;

public class Tank extends Actor {
	List<Weapon> weapons=new ArrayList<Weapon>();
	private int barrell_Length;
	
	public Tank(int spriteID, int[] coords, int barrell_Length) {
		super(spriteID, coords);
		weapons.add(new Weapon("bullet.png"));
		this.barrell_Length=barrell_Length;
	}

	public void upPressed() {
		if(direction!=UP)
			direction=UP;
		else {
			world.moveDown(speed);
		}
	}

	public void downPressed() {
		if(direction!=DOWN)
			direction=DOWN;
		else {
			world.moveUp(speed);
		}
	}

	public void leftPressed() {
		if(direction!=LEFT)	
			direction=LEFT;
		else {
			world.moveRight(speed);
		}
	}

	public void rightPressed() {
		if(direction!=RIGHT)
			direction=RIGHT;
		else {
			world.moveLeft(speed);
		}
	}
	
	public void firePressed() {
		LaunchManager.register(getCoords()[0], getCoords()[1], direction, this);
		Speaker.play("launch.wav");
	}
	
	public Weapon getWeapon() {
		return weapons.get(0);
	}
	
	public int getBarrellLength() {
		return barrell_Length;
	}
}
