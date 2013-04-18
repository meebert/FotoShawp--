import java.awt.Color;
import java.awt.image.*;

import com.jhlabs.image.*;

public class BW {

	
	public BW(){
	
	}
	public BufferedImage blackWhite(BufferedImage i){
		int width = i.getWidth();
		int height = i.getHeight();
		int[] pixels = new int[width * height];
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());
		i.getRGB(0, 0, width, height, pixels, 0, width);
		
		
		double l = 0;
		float sat = 0.0f;
		
		int r , b , g, a;
		for(int x = 0; x < width*height ; x++ ){
			Color rgb = new Color(pixels[x]);
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			a = rgb.getAlpha();
			l = .3*r + .59*b + .11*g;
			
			
			r =PixelUtils.clamp( (int) ((r*sat)+(l*(1-sat))));
			g =PixelUtils.clamp( (int) ((g*sat)+(l*(1-sat))));
			b =PixelUtils.clamp( (int) ((b*sat)+(l*(1-sat))));
			pixels[x] = new Color(r, g, b, a).getRGB();

			
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
	return dst;	
		
		
	}
	
}
