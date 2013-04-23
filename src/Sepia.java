import java.awt.Color;
import java.awt.image.*;
import com.jhlabs.image.*;

public class Sepia {
	
	private BufferedImage image;
	
	public Sepia(BufferedImage i){	
		image = i; 
	}
	public BufferedImage brownIt(BufferedImage i){

		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());

		int width = i.getWidth();
		int height = i.getHeight();

		int[] pixels = new int[width * height];
		i.getRGB(0, 0, width, height, pixels, 0, width);
		
		
		int a, r, g, b;
		int outR, outB, outG;
		int sepiaDepth = 20;
		
		for(int x = 0; x <height*width; x++){
			
			Color rgb = new Color(pixels[x]);
			
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			
			outR = PixelUtils.clamp((int) ((int)(r * .393) + (g *.769) + (b * .189))); //For Sepia
			outB = PixelUtils.clamp((int) ((int)(r * .349) + (g *.686) + (b * .168)));
			outG = PixelUtils.clamp((int) ((int)(r * .272) + (g *.534) + (b * .131)));
			
			//int gry = (r + g + b) / 3;
//			r = g = b = gry;
//			r = r + (sepiaDepth * 2);
//			g = g + sepiaDepth;
			
			if(outR > 255)
				outR = 255;
			if(outB > 255)
				outB = 255;
			if(outG > 255)
				outG = 255;
			
			//b -= 5;

			// normalize if out of bounds
//			if (b < 0) 
//				b=0;
//			if (b > 255) 
//				b=255;
			

			//pixels[x] = new Color(r, b, g, a).getRGB();
			pixels[x] = new Color(outR, outB, outG, a).getRGB();
		
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		
		return dst;
	}
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
