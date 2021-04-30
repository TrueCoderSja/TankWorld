package ml.truecoder.tankgame.nativecode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import ml.truecoder.tankgame.nogui.Utilities;

public class Speaker {
	private static boolean muted=false;
	private static HashMap<String, ByteArrayOutputStream> audios=new HashMap<String, ByteArrayOutputStream>();
	
	public static void init(String audioListPath) {
		audios.clear();
		Scanner entryFetcher=new Scanner(Utilities.getAsset(audioListPath));
		while(entryFetcher.hasNextLine()) {
			String entry=entryFetcher.nextLine();
			InputStream input=Utilities.getAsset("audio/"+entry);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			
			byte[] buffer = new byte[1024];
			int len;
			try {
				while ((len = input.read(buffer)) > -1 ) {
				    baos.write(buffer, 0, len);
				}
				baos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			audios.put(entry, baos);
		}
	}
	
	public static void play(String audioName) {
		if(!muted) {
			AudioPlayer player=new AudioPlayer(audioName);
			new Thread(player).start();
		}
	}
	static class AudioPlayer implements Runnable {
		AudioInputStream audio;
		
		AudioPlayer(String audioName){
			InputStream input=new ByteArrayInputStream(audios.get(audioName).toByteArray());
			try {
				audio=AudioSystem.getAudioInputStream(input);
			} catch (UnsupportedAudioFileException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run() {
			try {
				Clip clip=AudioSystem.getClip();
				clip.open(audio);
				clip.start();
			} catch (LineUnavailableException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	
	}
}