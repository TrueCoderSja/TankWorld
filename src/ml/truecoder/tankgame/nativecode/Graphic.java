package ml.truecoder.tankgame.nativecode;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Graphic {
	BufferedImage val;
	public Graphic(BufferedImage img) {
		val=img;
	}
	
	public Graphic(InputStream inputstream) throws FileNotFoundException {
		try {
			val=ImageIO.read(inputstream);
		} catch (IOException e) {
			throw new FileNotFoundException("No image file found with this name!");
		}
	}
	
	public Graphic(InputStream inputstream, int mode) throws FileNotFoundException {
		try {
			val=ImageIO.read(inputstream);
			BufferedImage img=new BufferedImage(val.getWidth(), val.getHeight(), BufferedImage.TYPE_INT_RGB);
			img.createGraphics().drawImage(val, 0, 0, val.getWidth(), val.getHeight(), null);
			val=img;
		} catch (IOException e) {
			throw new FileNotFoundException("No image file found with this name!");
		}
	}
	
	public int getWidth() {
		return val.getWidth();
	}
	public int getHeight() {
		return val.getHeight();
	}
	public Graphic getSubimage(int x, int y, int w, int h) {
		return new Graphic(val.getSubimage(x, y, w, h));
	}
	
	public Graphic rotateImage(int angle) {
		double rads = Math.toRadians(angle);
	    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
	    int w = val.getWidth();
	    int h = val.getHeight();
	    int newWidth = (int) Math.floor(w * cos + h * sin);
	    int newHeight = (int) Math.floor(h * cos + w * sin);

	    BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = rotated.createGraphics();
	    AffineTransform at = new AffineTransform();
	    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

	    int x = w / 2;
	    int y = h / 2;

	    at.rotate(rads, x, y);
	    g2d.setTransform(at);
	    g2d.drawImage(val, 0, 0, null);
	   
	    g2d.dispose();

	    return new Graphic(rotated);
	}
}
