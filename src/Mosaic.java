import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jhlabs.image.PixelUtils;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class Mosaic {

	public Mosaic(){
		
	}
	

	public BufferedImage mosaicIt(BufferedImage image , String type){
		
		BufferedImage dst = new BufferedImage(image.getWidth() , image.getHeight(), image.getType());

		int width = image.getWidth();
		int height = image.getHeight();

		// load all mosaic images from the specified subfolder
		String mosaicfolder = "";
		
		if(type.equals("music")){
			mosaicfolder = "./music";
		}else if(type.equals("paint")){
			mosaicfolder = " ";
		}
		
		File folder = new File(mosaicfolder);
		File files[] = folder.listFiles();

		int i;
		int w = 0, h = 0;
		int num = files.length;

		// mpixels stores the pixels of each mosaic image read from a disk file
		int[][] mpixels = new int[num][];

		for (i = 0; i < files.length; i ++) {
			if (!files[i].isFile()) continue;
			BufferedImage mosaic = null;
			try {
				mosaic = ImageIO.read(files[i]);
			} catch (IOException e) {
			}
			if (w == 0) {
				w = mosaic.getWidth();
				h = mosaic.getHeight();
			} else {
				if (mosaic.getWidth() != w || mosaic.getHeight() != h) {
					System.out.println("mosaic images must be of the same size.");
					System.exit(1);
				}
			}
			mpixels[i] = new int[w*h];

			// get pixels from the buffered image
			mosaic.getRGB(0, 0, w, h, mpixels[i], 0, w);
		}
		System.out.println("" + num + " mosaic images (" + w + "," + h + ") loaded.");

		/* === YOUR WORK HERE === */
		// complete the remaining of the code
	
		float minVal = 0;
		float currVal = 0;
		int rM, bM , gM, a, rB, bB , gB;
		
		int blockRows = (int) Math.floor(width / w);
		int blockCol = (int) Math.floor(height / h);
		int numBlocks = blockRows*blockCol;
		int[] currentBlock = new int[w*h];
		int minImage = 0;
		int[] selectedImage;
		float alphaR =0 , alphaB=0, alphaG=0; 

		
		//two Loops below loop through each block in image looking to make mosaic out of
		//One in each direction
		for(int blockR = 0; blockR < blockRows ; blockR++){
			for(int blockC = 0; blockC <  blockCol; blockC++){
				
				BufferedImage blockPic = new BufferedImage(w,h,2);
				Graphics2D g2d =  blockPic.createGraphics();
				g2d.drawImage(image, -blockR*w , -blockC*h,null);
				blockPic.getRGB(0,0,w,h,currentBlock,0,w);
			
				//for each block in the origional image, this loop runs 
				//through all the candidate images, looking for mathces
				for(int imgNum = 0 ; imgNum < num; imgNum++){
					int[] currentImg = mpixels[imgNum];
					
					float bmr=0,bmb=0,bmg=0,mmr=0,mmb=0,mmg = 0;
					float d = 0;
					float dr,db,dg;
					int x;
					
					//runs through each pixel in both image block and candidate image
					for( x = 0; x < w*h; x++){
					
						Color rgbM = new Color(currentImg[x]);
						rM = rgbM.getRed();
						gM = rgbM.getGreen();
						bM = rgbM.getBlue();
						a = rgbM.getAlpha();
					
						Color rgbB = new Color(currentBlock[x]);
						rB = rgbB.getRed();
						gB = rgbB.getGreen();
						bB = rgbB.getBlue();
						a = rgbB.getAlpha();
						
						bmr = bmr + rM*rB;
						bmb = bmb + bM*bB;
						bmg = bmg + gM*gB;
						
						mmr = mmr + rM*rM;
						mmb = mmb + bM*bM;
						mmg = mmg + gM*gM;
						
					}
					 dr = (-(bmr * bmr)) /(mmr);
					 db = (-(bmb * bmb)) /(mmb);
					 dg = (-(bmg * bmg)) /(mmg);
					 
					 d = dr + db + dg;
					 
					 double noise = (Math.random()+ 1);
					 d = (float) (d * noise); 
					
					 //looking to see for new minimum
					if(d < minVal){
						alphaR = bmr / mmr;
						alphaB = bmb / mmb;
						alphaG = bmg / mmg;
						minVal = d;
						minImage = imgNum;
					}
					 bmr = 0;
					 bmb = 0;
					 bmg = 0;
					 mmr = 0;
					 mmb = 0;
					 mmg = 0;
					 d = 0;
				}//loop through all candidates
				
				selectedImage = mpixels[minImage];
				int r,b,g;
				//multiplies the replacement match by alpha
				for( int x = 0; x < w*h; x++){
					
						Color rgb = new Color(selectedImage[x]);
						r = rgb.getRed();
						g = rgb.getGreen();
						b = rgb.getBlue();
						a = rgb.getAlpha();
						
						r = PixelUtils.clamp( (int) (r*alphaR));
						b = PixelUtils.clamp( (int) (b*alphaB));
					    g = PixelUtils.clamp( (int) (g*alphaG));	
					    
						selectedImage[x] = new Color(r, g, b, a).getRGB();

				}
			dst.setRGB(blockR*w,blockC*h,w,h,mpixels[minImage],0,w);
			
			
			minImage = 0 ;
			minVal = 0;
			}
		}
		return dst;
	}
}
