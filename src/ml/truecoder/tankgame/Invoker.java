package ml.truecoder.tankgame;

import ml.truecoder.tankgame.nativecode.Speaker;
import ml.truecoder.tankgame.nativecode.Starter;
import ml.truecoder.tankgame.nogui.Utilities;
import ml.truecoder.tankgame.visual.Layer;
import ml.truecoder.tankgame.visual.ObjectLayer;
import ml.truecoder.tankgame.visual.PlayerLayer;
import ml.truecoder.tankgame.visual.SpriteManager;
import ml.truecoder.tankgame.visual.Tank;
import ml.truecoder.tankgame.visual.TileManager;

public class Invoker implements GameData {
	public static void main(String args[]) {
		TileManager.init();
		SpriteManager.init();
		Speaker.init("audio/info.txt");
		Layer l1=new Layer(32, 32, Utilities.getAsset("worlds/base.stg"));
		Layer l2=new Layer(32, 32, Utilities.getAsset("worlds/natLyr.stg"));
		ObjectLayer l3=new ObjectLayer(32, 32, 3, Utilities.getAsset("worlds/artLyr.stg"));
		world.addLayer(l1);
		world.addLayer(l2);
		world.addLayer(l3);
		//l2.setCollidable(true);
		Tank tank=new Tank(1, new int[2], 41);
		world.setTank(tank);
		PlayerLayer l4=new PlayerLayer();
		l4.addPlayer(tank);
		world.setPrimaryActorLayer(l4);
		Starter.start();
	}
}
