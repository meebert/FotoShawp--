
import java.awt.image.*;
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
		
		
		System.out.println(sharpness);
		
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());
		int width = i.getWidth();
		int height = i.getHeight();
		float s = sharpness;
		int[] pixels = new int[width * height];
		int[] result = new int[width * height];
		float[] matrix =new float[] { 0, -s ,0 ,  -s,(1+4*s),-s ,  0 ,-s,0  }; 

		i.getRGB(0, 0, width, height, pixels, 0, width);
		
		ConvolveFilter conv = new ConvolveFilter(matrix);
		
		ConvolveFilter.convolve(conv.getKernel() , pixels , result, width,height, 255);
		
		dst.setRGB(0, 0, width, height, result, 0, width);
		
		//image = dst;
		
		return dst;
		
	}
	
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
