import java.awt.Color;
import java.awt.image.*;

import com.jhlabs.image.*;


public class FaceDetect {

	public FaceDetect(){
		
	}
	
	public BufferedImage skinQuantize(BufferedImage i ){
		
		int width = i.getWidth();
		int height = i.getHeight();
		int[] pixels = new int[width * height];
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());
		i.getRGB(0, 0, width, height, pixels, 0, width);
		
		
		double l = 0;
		
		float f1 = 0.0f, f2 = 0.0f, R = 0.0f , G = 0.0f ,w  = 0.0f ;
		
		int r , b , g, a;
		for(int x = 0; x < width*height ; x++ ){
			Color rgb = new Color(pixels[x]);
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			a = rgb.getAlpha();
			l = .3*r + .59*b + .11*g;
			
			R = r/ (r+b+g);
			G = g /(r+g+b);
			
			f1 = (float) (-1.376 *r * r + 1.0743*r+.2);
			f2 = (float) (-.776*r*r + .5601*r+.18);
			w = (r-0.33f)*(r-0.33f) +(g -0.33f)*(g -0.33f);
			
			
			if((G < f1) && (G>f2) && (w > 0.001f)){
				pixels[x] = new Color(255, 255, 255, a).getRGB();

			}else{
				pixels[x] = new Color(r, g, b, a).getRGB();

			}
			
		//	r =PixelUtils.clamp( (int) ((r*sat)+(l*(1-sat))));
		//	g =PixelUtils.clamp( (int) ((g*sat)+(l*(1-sat))));
		//	b =PixelUtils.clamp( (int) ((b*sat)+(l*(1-sat))));

			
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
	return dst;	
			
	}
	
	
}
