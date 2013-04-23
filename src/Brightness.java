import java.awt.Color;
import java.awt.image.*;
import java.util.LinkedList;

import com.jhlabs.image.*;

public class Brightness {
	private BufferedImage image;
	private float brightness;
	
	public Brightness(BufferedImage i ){
		image = i;
	}
	
	public BufferedImage brighten(float bright , BufferedImage i , LinkedList<int[]> bounds){
		brightness = bright;

		
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());

		int width = i.getWidth();
		int height = i.getHeight();

		// a buffer that stores the destination image pixels
		int[] pixels = new int[width * height];
	
		// get the pixels of the source image	
		i.getRGB(0, 0, width, height, pixels, 0, width);
		int a, r, g, b;
		float mult = 1.0f;

		for(int k = 0; k < width * height; k ++) {
			Color rgb = new Color(pixels[k]);
			// a color is represented as an integer (4 bytes); 
			// each of the alpha, red, green, blue channels is stored in one byte in order;
			// you can use the Color class to extract the value of each individual channel
			// or composite a new integer color from the separated components
			int tempX = k % width;
			int tempY = (int) Math.ceil((double)(k/width));
			int minX=0,minY=0,maxX=0,maxY=0;
			int centerX = 0;
			int centerY = 0;
			float largestD = 0;
			float currentD = 0;
			boolean inBound = false;
			if(bounds.size()!=0){
				for(int[] currentBound: bounds){
					minX = currentBound[0];
					minY = currentBound[1];
					maxX = currentBound[2];
					maxY = currentBound[3];
					
					if(tempX > minX && tempX < maxX && tempY > minY && tempY < maxY){
						inBound = true;
						centerX = (int) (minX + (maxX - minX)/2.0);
						centerY = (int) (minY + (maxY - minY)/2.0);
						//System.out.println(centerX);
						largestD = (float) Math.sqrt( (Math.abs(centerX - minX))*(Math.abs(centerX - minX)) + Math.abs((centerY - minY))*Math.abs((centerY - minY)) );
						currentD = (float) Math.sqrt( (Math.abs(centerX - tempX))*(Math.abs(centerX - tempX)) + Math.abs((centerY - tempY))*Math.abs((centerY - tempY)) );
					}
				
				
				
				}
			}else{
				inBound = true;
			}
			
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
		
			if(inBound == true){
				if(centerX != 0 || centerY !=0){
					 mult = 1.0f - ( (currentD/largestD) );
					 mult = mult * mult * mult;
				}
				if(brightness >= 1.0f){
					r = PixelUtils.clamp((int)((float)r * (1.0f + ((brightness)-1.0f)*mult)) );
					g = PixelUtils.clamp((int)((float)g * (1.0f + ((brightness)-1.0f)*mult)) );
					b = PixelUtils.clamp((int)((float)b * (1.0f + ((brightness)-1.0f)*mult)) );
				}else{
					r = PixelUtils.clamp((int)((float)r * (1.0f - (1.0f - brightness)*mult)) );
					g = PixelUtils.clamp((int)((float)g * (1.0f - (1.0f - brightness)*mult)) );
					b = PixelUtils.clamp((int)((float)b * (1.0f - (1.0f - brightness)*mult)) );
					
				}

			}
			pixels[k] = new Color(r, g, b, a).getRGB();

		}

		// write pixel values to the destination image
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		
		return dst;
		
	}
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
