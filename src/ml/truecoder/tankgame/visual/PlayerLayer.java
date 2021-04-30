package ml.truecoder.tankgame.visual;

import java.util.ArrayList;
import java.util.List;

public class PlayerLayer extends Layer {
	
	private List<Actor> actors=new ArrayList<Actor>();
	private int len;
	private int currentIndex;
	
	public void addPlayer(Actor actor) {
		actors.add(actor);
		len++;
	}
	
	public Object[] getPlayerData() {
		if(currentIndex>=len) {
			currentIndex=0;
			Object[] pair= {null, null};
			return pair;
		}
		return new Object[] {actors.get(currentIndex), actors.get(currentIndex++).getCoords()};
	}
}
