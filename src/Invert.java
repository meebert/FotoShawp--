import java.awt.Color;
import java.awt.image.*;
<<<<<<< HEAD:src/BW.java
import java.util.LinkedList;

=======
>>>>>>> Sepia and Invert:src/Invert.java
import com.jhlabs.image.*;

public class Invert {
	private BufferedImage image;
	
	public Invert(BufferedImage i){	
		image = i; 
	}
<<<<<<< HEAD:src/BW.java
	public BufferedImage blackWhite(BufferedImage i, LinkedList<int[]> bounds){
=======
	public BufferedImage invert(BufferedImage i){

		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());

>>>>>>> Sepia and Invert:src/Invert.java
		int width = i.getWidth();
		int height = i.getHeight();

		int[] pixels = new int[width * height];
		i.getRGB(0, 0, width, height, pixels, 0, width);
		
		
		int a, r, g, b;
		
<<<<<<< HEAD:src/BW.java
		int r , b , g, a;
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
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			a = rgb.getAlpha();
			l = .3*r + .59*b + .11*g;
			
			if(inBound == true){
				if(centerX != 0 || centerY !=0){
					 mult = 1.0f - ( (currentD/largestD) );
					 mult = (float) Math.pow(mult, 0.75f);
				}
				sat = 1- mult;
				
				r =PixelUtils.clamp( (int) ((r*sat)+(l*(1-sat))));
				g =PixelUtils.clamp( (int) ((g*sat)+(l*(1-sat))));
				b =PixelUtils.clamp( (int) ((b*sat)+(l*(1-sat))));
			}else{
				sat = 1.0f;
				r =PixelUtils.clamp( (int) ((r*sat)+(l*(1-sat))));
				g =PixelUtils.clamp( (int) ((g*sat)+(l*(1-sat))));
				b =PixelUtils.clamp( (int) ((b*sat)+(l*(1-sat))));
			}
			pixels[k] = new Color(r, g, b, a).getRGB();
=======
		for(int x = 0; x <height*width; x++){
			
			Color rgb = new Color(pixels[x]);
			
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			
			r = PixelUtils.clamp(255 - r);
			g = PixelUtils.clamp(255 - g);
			b = PixelUtils.clamp(255 - b);
>>>>>>> Sepia and Invert:src/Invert.java

			pixels[x] = new Color(r, g, b, a).getRGB();
		
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		
		return dst;
	}
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
