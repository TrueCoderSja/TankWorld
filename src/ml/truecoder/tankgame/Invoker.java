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
		Layer l1=new Layer(32, 32, Utilities.getAsset("worlds/test/baseLyr.stg"));
		Layer l2=new Layer(32, 32, Utilities.getAsset("worlds/test/natLyr.stg"));
		Layer n2=new Layer(32, 32, Utilities.getAsset("worlds/test/natLyr2.stg"));
		ObjectLayer l3=new ObjectLayer(32, 32, 3, Utilities.getAsset("worlds/test/objLyr.stg"));
		world.addLayer(l1);
		world.addLayer(l2);
		world.addLayer(n2);
		world.addLayer(l3);
		n2.setCollidable(true);
		Tank tank=new Tank(2, new int[2], 41);
		world.setTank(tank);
		PlayerLayer l4=new PlayerLayer();
		l4.addPlayer(tank);
		world.setPrimaryActorLayer(l4);
		Starter.start();
	}
}
