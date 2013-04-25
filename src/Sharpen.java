
import java.awt.Color;
import java.awt.image.*;

import javax.vecmath.Color3f;

import com.jhlabs.image.*;

public class Sharpen{
	private BufferedImage image;
	private float sharpness;
	private float oldSharp = 0.0f;
	
	public Sharpen(BufferedImage i){
		image = i;
	}
	
	public BufferedImage sharpen(float sharpAmount , BufferedImage i  ){
		sharpness = sharpAmount;		
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());
		int width = i.getWidth();
		int height = i.getHeight();
		float s = sharpness;
        long heapSize = Runtime.getRuntime().totalMemory();
         
        System.out.println("Heap Size = " + heapSize);
		
		int[] pixels = new int[width * height];
		//int[] result = new int[width * height];
		float[] matrix =new float[] { 0, -s ,0 ,  -s,(1+4*s),-s ,  0 ,-s,0  }; 

		i.getRGB(0, 0, width, height, pixels, 0, width);
		/*
		ConvolveFilter conv = new ConvolveFilter(matrix);
		
		ConvolveFilter.convolve(conv.getKernel() , pixels , result, width,height, 255);
		
		dst.setRGB(0, 0, width, height, result, 0, width);
		*/
		//image = dst;
		
		for(int k = 0; k < width*height ; k++){
			Color center = new Color(pixels[k]);
			Color3f top,bottom,left,right;
			Color3f centerColor = new Color3f(center.getRed(), center.getGreen() , center.getBlue());
			Color temp;
			
			centerColor.scale(matrix[4]);
			
			if(k < width){
				temp = new Color(pixels[k]);
				top =  new Color3f(temp.getRed(), temp.getGreen() , temp.getBlue());
			}else{
				temp = new Color(pixels[k-width]);
				top =  new Color3f(temp.getRed(), temp.getGreen() , temp.getBlue());
				top.scale(matrix[1]);
			}
			
			if(k > (width*height - 1 -width)){
				temp = new Color(pixels[k]);
				bottom =  new Color3f(temp.getRed(), temp.getGreen() , temp.getBlue());
			}else{
				temp = new Color(pixels[k+width]);
				bottom =  new Color3f(temp.getRed(), temp.getGreen() , temp.getBlue());
				bottom.scale(matrix[7]);
			}
			
			if(k %width == width-1){
				temp = new Color(pixels[k]);
				right =  new Color3f(temp.getRed(), temp.getGreen() , temp.getBlue());
			}else{
				temp = new Color(pixels[k+1]);
				right =  new Color3f(temp.getRed(), temp.getGreen() , temp.getBlue());
				right.scale(matrix[5]);
			}
			
			if(k%width == 0){
				temp = new Color(pixels[k]);
				left =  new Color3f(temp.getRed(), temp.getGreen() , temp.getBlue());
			}else{
				temp = new Color(pixels[k-1]);
				left =  new Color3f(temp.getRed(), temp.getGreen() , temp.getBlue());
				left.scale(matrix[3]);
			}
			
			int r = PixelUtils.clamp((int) (top.x + bottom.x + left.x + right.x + centerColor.x));
			int g = PixelUtils.clamp((int) (top.y + bottom.y + left.y + right.y + centerColor.y));
			int b = PixelUtils.clamp((int) (top.z + bottom.z + left.z + right.z + centerColor.z));
			int a = PixelUtils.clamp(center.getAlpha());
			
			
			pixels[k] = new Color(r, g, b, a).getRGB();

			
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);

		
		return dst;
		
	}
	
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
