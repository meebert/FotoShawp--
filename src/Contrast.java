import java.awt.Color;
import java.awt.image.*;

import com.jhlabs.image.*;


public class Contrast {
	private BufferedImage image;
	private float contrast;
	
	public Contrast(BufferedImage i){	
		image = i; 
	}
	public BufferedImage contr(float con , BufferedImage i){
		contrast = con;
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());

		int width = i.getWidth();
		int height = i.getHeight();

		int[] pixels = new int[width * height];
		i.getRGB(0, 0, width, height, pixels, 0, width);
		
		
		int a, r, g, b;
		
		for(int x = 0; x <height*width; x++){
			
			Color rgb = new Color(pixels[x]);
			
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			
			r = PixelUtils.clamp((int) ( (contrast*(float)r) + ((1-contrast)*(float)127)));
			g = PixelUtils.clamp((int) ( (contrast*(float)g) + ((1-contrast)*(float)127)));
			b = PixelUtils.clamp((int) ( (contrast*(float)b) + ((1-contrast)*(float)127)));

			pixels[x] = new Color(r, g, b, a).getRGB();
		
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		
		return dst;
	}
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
