
import java.awt.image.*;
import com.jhlabs.image.*;

public class Sharpen{
	private BufferedImage image;
	private float sharpness;

	
	public Sharpen(BufferedImage i){
		image = i;
	}
	
	public BufferedImage sharpen(float sharpAmount ){
		sharpness = sharpAmount;

		BufferedImage dst = new BufferedImage(image.getWidth() , image.getHeight(), image.getType());
		int width = image.getWidth();
		int height = image.getHeight();
		float s = sharpness;
		int[] pixels = new int[width * height];
		int[] result = new int[width * height];
		float[] matrix =new float[] { 0, -s ,0 ,  -s,(1+4*s),-s ,  0 ,-s,0  }; 

		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		ConvolveFilter conv = new ConvolveFilter(matrix);
		
		ConvolveFilter.convolve(conv.getKernel() , pixels , result, width,height, 255);
		
		dst.setRGB(0, 0, width, height, result, 0, width);
		
		return dst;
		
	}
	
	public void setImage(BufferedImage i){
		image = i;
	}
	
}
