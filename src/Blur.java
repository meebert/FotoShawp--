import java.awt.image.*;

import com.jhlabs.image.*;

public class Blur {

	public Blur(){
		
	}
	
	public BufferedImage blur(BufferedImage i , float blurAmount){
		BufferedImage dst = new BufferedImage(i.getWidth() , i.getHeight(), i.getType());
		GaussianFilter filter = new GaussianFilter();
		filter.setRadius(blurAmount);
		filter.filter(i, dst);
		
		return dst;
	}
	
}
