import java.awt.Color;
import java.awt.image.*;
import java.util.Random;

import com.jhlabs.image.*;

public class Dither {

	public Dither() {
	}

	public BufferedImage ditherO(BufferedImage i) {
		final float[][] Bayers = {
				{ 15 / 16.f, 7 / 16.f, 13 / 16.f, 5 / 16.f },
				{ 3 / 16.f, 11 / 16.f, 1 / 16.f, 9 / 16.f },
				{ 12 / 16.f, 4 / 16.f, 14 / 16.f, 6 / 16.f },
				{ 0, 8 / 16.f, 2 / 16.f, 10 / 16.f } };

		
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());

		
		int width = i.getWidth();
		int height = i.getHeight();
		int[] pixels = new int[width * height];
		i.getRGB(0, 0, width, height, pixels, 0, width);

		int r, b, g, a;
		int m = 0, n = 0;
		double l;
		float e;

		for (int x = 0; x < width * height; x++) {

			Color rgb = new Color(pixels[x]);

			// 4x4 matrix requires %4 ?
			e = Bayers[m % 4][n % 4];
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			a = rgb.getAlpha();

			l = .3 * r + .59 * g + .11 * b;

			if ((l / 255) > e) {
				r = PixelUtils.clamp(255);
				b = PixelUtils.clamp(255);
				g = PixelUtils.clamp(255);
				pixels[x] = new Color(r, g, b, a).getRGB();
			} else {
				r = PixelUtils.clamp(0);
				b = PixelUtils.clamp(0);
				g = PixelUtils.clamp(0);
				pixels[x] = new Color(r, g, b, a).getRGB();
			}
			n++;

			if ((n) == (width)) {
				m++;
				n = 0;
			}
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		
		return dst;
	}

	public BufferedImage ditherR(BufferedImage i) {
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());
		Random rand = new Random();
		double thresh = Math.random()*100;
		int width = i.getWidth();
		int height = i.getHeight();
		int[] pixels = new int[width * height];

		i.getRGB(0, 0, width, height, pixels, 0, width);		
		int r,b,g, a;
		double l;
		
		for(int x = 0; x < width*height ; x++ ){
			
				Color rgb = new Color(pixels[x]);
				r = rgb.getRed();
				g = rgb.getGreen();
				b = rgb.getBlue();
				a = rgb.getAlpha();
				l = .3*r + .59*b + .11*g;
				
				thresh = rand.nextDouble();
				
				if((l/255) > thresh){
					

					r = PixelUtils.clamp( 255);
					b = PixelUtils.clamp( 255);
				    g = PixelUtils.clamp( 255);
					pixels[x] = new Color(r, g, b, a).getRGB();


				}else{
					
					r = PixelUtils.clamp( 0);
					b = PixelUtils.clamp( 0);
				    g = PixelUtils.clamp( 0);
					pixels[x] = new Color(r, g, b, a).getRGB();


				}
							
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		return dst;
	}

}
