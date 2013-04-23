import java.awt.Color;
import java.awt.image.*;
import java.util.LinkedList;

import com.jhlabs.image.*;

public class Contrast {
	private BufferedImage image;
	private float contrast;
	
	public Contrast(BufferedImage i){	
		image = i; 
	}
	public BufferedImage contr(float con , BufferedImage i , LinkedList<int[]> bounds){
		contrast = con;
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());

		int width = i.getWidth();
		int height = i.getHeight();

		int[] pixels = new int[width * height];
		i.getRGB(0, 0, width, height, pixels, 0, width);
		
		
		int a, r, g, b;
		
		for(int x = 0; x <height*width; x++){
			
			int tempX = x % width;
			int tempY = (int) Math.ceil((double)(x/width));
			int minX=0,minY=0,maxX=0,maxY=0;
			int centerX = 0;
			int centerY = 0;
			float largestD = 0;
			float currentD = 0;
			boolean inBound = false;
			float mult = 1.0f;
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
						largestD = (float) Math.sqrt( (Math.abs(centerX - minX))*(Math.abs(centerX - minX)) + Math.abs((centerY - maxY))*Math.abs((centerY - maxY)) );
						currentD = (float) Math.sqrt( (Math.abs(centerX - tempX))*(Math.abs(centerX - tempX)) + Math.abs((centerY - tempY))*Math.abs((centerY - tempY)) );
					}
				}
			}else{
				inBound = true;
			}
			Color rgb = new Color(pixels[x]);
			
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			if(inBound == true){

				if(centerX != 0 || centerY !=0){
					 mult = 1.0f - ( (currentD/largestD) );
					 mult = mult * mult * mult;
				}
				if(contrast >= 1.0f){
					r = PixelUtils.clamp((int)((float)r * (1.0f + ((contrast)-1.0f)*mult)) );
					g = PixelUtils.clamp((int)((float)g * (1.0f + ((contrast)-1.0f)*mult)) );
					b = PixelUtils.clamp((int)((float)b * (1.0f + ((contrast)-1.0f)*mult)) );
				}else{
					r = PixelUtils.clamp((int)((float)r * (1.0f - (1.0f - contrast)*mult)) );
					g = PixelUtils.clamp((int)((float)g * (1.0f - (1.0f - contrast)*mult)) );
					b = PixelUtils.clamp((int)((float)b * (1.0f - (1.0f - contrast)*mult)) );
					
				}
			}
			pixels[x] = new Color(r, g, b, a).getRGB();
		
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		
		return dst;
	}
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
