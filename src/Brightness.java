import java.awt.Color;
import java.awt.image.*;

import com.jhlabs.image.*;

public class Brightness {
	private BufferedImage image;
	private float brightness;
	
	public Brightness(BufferedImage i ){
		image = i;
	}
	
	public BufferedImage brighten(float bright){
		brightness = bright;

		
		BufferedImage dst = new BufferedImage(image.getWidth() , image.getHeight(), image.getType());

		int width = image.getWidth();
		int height = image.getHeight();

		// a buffer that stores the destination image pixels
		int[] pixels = new int[width * height];
	
		// get the pixels of the source image	
		image.getRGB(0, 0, width, height, pixels, 0, width);

		int i;
		int a, r, g, b;
		for(i = 0; i < width * height; i ++) {
			Color rgb = new Color(pixels[i]);
			// a color is represented as an integer (4 bytes); 
			// each of the alpha, red, green, blue channels is stored in one byte in order;
			// you can use the Color class to extract the value of each individual channel
			// or composite a new integer color from the separated components
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			r = PixelUtils.clamp((int)((float)r * brightness));
			g = PixelUtils.clamp((int)((float)g * brightness));
			b = PixelUtils.clamp((int)((float)b * brightness));

			pixels[i] = new Color(r, g, b, a).getRGB();
		}

		// write pixel values to the destination image
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		
		return dst;
		
	}
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
