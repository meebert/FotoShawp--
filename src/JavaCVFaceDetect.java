import java.awt.Color;
import java.awt.image.*;
import com.jhlabs.image.*;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_highgui.*;
import com.googlecode.javacv.cpp.opencv_objdetect.*;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;
import com.googlecode.javacpp.Loader;
import  com.googlecode.javacv.cpp.opencv_imgproc.*;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;

/*
 * JavaCV Face Detection Help Code
 * https://code.google.com/p/javacv/issues/attachmentText?id=116&aid=1160000000&name=FaceDetection.java&token=cf4d89a182a5b237096a3b81ba3288c4
 * 
 * Used idea from cited webpage and adapted it to fit our needs 
 *  
 */


public class JavaCVFaceDetect {
	IplImage image;
	CvSeq faces;
	BufferedImage returnImage;
	public static final String HAAR_FILE = "haarcascade_frontalface_default.xml";
	
	public JavaCVFaceDetect(BufferedImage i ){
		image = IplImage.createFrom(i);
	}
	
	public BufferedImage detectWBoxes(boolean drawbox){
		
	    IplImage gray = IplImage.create(image.width(),image.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(image, gray, CV_BGR2GRAY);
       
        // equalize the small grayscale
        IplImage equImg = IplImage.create(gray.width(),gray.height(), IPL_DEPTH_8U, 1);
        cvEqualizeHist(gray, equImg);
        
        // create temp storage, used during object detection
        CvMemStorage storage = CvMemStorage.create();

        // instantiate a classifier cascade for face detection
        CvHaarClassifierCascade cascade =new CvHaarClassifierCascade(cvLoad(HAAR_FILE));
        faces = cvHaarDetectObjects(equImg, cascade, storage,1.1, 3, CV_HAAR_DO_CANNY_PRUNING);
        cvClearMemStorage(storage);

        // draw thick yellow rectangles around all the faces
        int total = faces.total();

        if(drawbox){
        	for (int i = 0; i < total; i++) {

                CvRect r = new CvRect(cvGetSeqElem(faces, i));
                cvRectangle(image, cvPoint( r.x(), r.y() ),cvPoint( (r.x() + r.width()),(r.y() + r.height()) ),CvScalar.RED, 6, CV_AA, 0);

        	}
        }
        if (total > 0) {
        	returnImage = image.getBufferedImage();

        }
        
		return returnImage;
	}
	
	public LinkedList<int[]> setFaceBounds(){
		LinkedList<int[]> bounds = new LinkedList<int[]>();
		BufferedImage test = this.detectWBoxes(false);
	
        int total = faces.total();

	    for (int i = 0; i < total; i++) {

	        CvRect r = new CvRect(cvGetSeqElem(faces, i));
	        int[] temp = {r.x() , r.y(), r.x() + r.width() , r.y() + r.height()};
	        bounds.add(temp);

	     }
	        
		return bounds;
	}
	
	
	
}
