import java.awt.Color;
import java.awt.image.*;

import com.jhlabs.image.*;

public class FaceDetect {

	public FaceDetect() {

	}

	public BufferedImage skinQuantize(BufferedImage i) {

		int width = i.getWidth();
		int height = i.getHeight();
		int[] pixels = new int[width * height];
		BufferedImage dst = new BufferedImage(i.getWidth(), i.getHeight(),
				i.getType());
		i.getRGB(0, 0, width, height, pixels, 0, width);

		float I = 0.0f;

		float f1 = 0.0f, f2 = 0.0f, R = 0.0f, G = 0.0f, B = 0.0f, w = 0.0f , theta = 0.0f, top = 0.0f , bot = 0.0f , H =0.0f;

		int r, b, g, a;
		float rf ,bf,gf;
		for (int x = 0; x < width * height; x++) {
			Color rgb = new Color(pixels[x]);
			
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			a = rgb.getAlpha();
			rf = (float)r;
			gf = (float)g;
			bf = (float)b;

			if (rf != 0 && gf != 0 && bf != 0) {

			//SKIN
				R = (float) (rf / (rf + bf + gf));
				G = (float) (gf / (rf + gf + bf));
				B = (float) (bf / (rf + gf + bf));

				f1 = (float) (-1.376f * R * R + 1.0743f * R + 0.2f);
				f2 = (float) (-0.776f * R * R + 0.5601f * R + 0.18f);
				w = (float)((R - 0.33f) * (R - 0.33f) + (G - 0.33f) * (G - 0.33f));
			//	System.out.println(B+ " " + R + " " + G);
				I = (1.0f/3.0f)*(r+g+b);

			
				
				
				top = 0.5f*( ( R-G) + (R-B) );
				bot = (float) Math.sqrt( (R-G)*(R-G) + (R-B)*(G-B));
				theta = (float) Math.acos(top / bot);
			
				if(B <= G){
					H = theta;
				}else{
					H = theta -360.0f;
				}
				
				///System.out.println(I);

				if((I<80 && ((B-G <15)||B-R<15 )) || (H <=40 && H>20)){
					//pixels[x] = new Color(0, 0, 0, a).getRGB();

				}else{
					//pixels[x] = new Color(0, 0, 0, a).getRGB();

				}
				
				
				//System.out.println( Math.acos(Math.sqrt( (r-g)*(r-g) + (r-b)*(g-b))));
				if ((G < f1) && (G > f2) && (w > 0.001f) && ((H>240 || H < 20)) ) {
					pixels[x] = new Color(r, g, b, a).getRGB();

				} else {
					pixels[x] = new Color(0, 0, 0, a).getRGB();

				}
			}else{
				pixels[x] = new Color(r, g, b, a).getRGB();

			}
			 //r =PixelUtils.clamp( (int) ((r*sat)+(l*(1-sat))));
			 //g =PixelUtils.clamp( (int) ((g*sat)+(l*(1-sat))));
			// b =PixelUtils.clamp( (int) ((b*sat)+(l*(1-sat))));

		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		return dst;

	}

}
