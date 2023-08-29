/*
 * Author: Leif Forrest
 * Date: August 29th 2023
 * Description:	 A java program that reads an image and sorts its pixels by
 * the sum of each pixels RGB values. It then writes this now sorted image
 * to a new file. 
 * 
 */
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class pixelSorter {
	int BLACK = -16777216;
	String filename;
	File f, fBnW;
	BufferedImage img = null;
	BufferedImage imgBnW = null;
	
	public pixelSorter(String File, int threshhold){
		filename = File;
		try{
		f = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/" + filename);
		img = ImageIO.read(f);
		}catch(IOException e){
			System.out.println(e);
		}
		imgBnW = thresholdImage(img, threshhold);
	}
	public static void main(String args[]) throws IOException
	{	
		// String fileName = "image.jpg";
		pixelSorter l = new pixelSorter("sitting.jpg", 50);
		// l.imageToBnW(Filename, 25);
		// l.imgToColum(fileName, l, 70);
		// l.imgToRow(fileName, l, 70);
	  	l.imgToRowInterval(l, "jpg");
		l.imgToRowIntervalCol(l, "jpg");

	} // main() ends here
	public void imgToRowInterval(pixelSorter l, String filetype){
		BufferedImage img = l.img, imgBW = l.imgBnW;

		int imgHeight = img.getHeight(), imgWidth = img.getWidth();
		int[] imgRGB = new int[imgWidth];
		int startPos = 0, endPos;
		int rowIdx;
		boolean startPosSet = false;

		
		for(int j = 0; j < imgHeight; j++){
			rowIdx = 0;
			while(rowIdx < imgWidth ){
				imgRGB[rowIdx] = (img.getRGB(rowIdx, j));
				if(imgBW.getRGB(rowIdx,j) != BLACK && startPosSet == false ){
					startPos = rowIdx;
					startPosSet = true;
				}
				else if(imgBW.getRGB(rowIdx,j) == BLACK && startPosSet == true && startPos != imgWidth - 1 ){
					endPos = rowIdx - 1;

					Arrays.sort(imgRGB, startPos, endPos);
					startPosSet = false;
					
				}
				// used for edge case when the end if the row is reached without setting an end position
				else if(rowIdx == imgWidth -1){
					endPos = rowIdx;
					Arrays.sort(imgRGB, startPos, endPos);
					startPosSet = false;
				}		
				rowIdx++;
			}
			// set image to the sorted row
			for(int i = 0; i < img.getWidth(); i++){
				img.setRGB(i, j, imgRGB[i]);
			}
		}

		// output image
		try{
			f = new File("/home/nokken/templ/JavaLearning/PixelSorting/outputs/Output_Row_Interval_TESTOBJ_"+ l.filename);
			ImageIO.write(img, filetype, f);
		  }catch(IOException e){
			System.out.println(e);
		  }

	}
	public void imgToRowIntervalCol(pixelSorter l, String filetype){
		BufferedImage img = l.img, imgBW = l.imgBnW;

		int imgHeight = img.getHeight(), imgWidth = img.getWidth();
		int[] imgRGB = new int[imgHeight];
		int startPos = 0, endPos;
		int colIdx;
		boolean startPosSet = false;

		
		for(int j = 0; j < imgWidth; j++){
			colIdx = 0;
			while(colIdx < imgHeight ){
				imgRGB[colIdx] = (img.getRGB(j, colIdx));				
		  		if(imgBW.getRGB(j,colIdx) != BLACK && startPosSet == false ){
					startPos = colIdx;
					startPosSet = true;
				}
				else if(imgBW.getRGB(j,colIdx) == BLACK && startPosSet == true && startPos != imgHeight - 1 ){
					endPos = colIdx - 1;

					Arrays.sort(imgRGB, startPos, endPos);
					startPosSet = false;
					
				}
				// used for edge case when the end if the row is reached without setting an end position
				else if(colIdx == imgHeight -1){
					endPos = colIdx;
					Arrays.sort(imgRGB, startPos, endPos);
					startPosSet = false;
				}		
				colIdx++;
			}
			// set image to the sorted row
			for(int i = 0; i < img.getHeight(); i++){
				img.setRGB(j, i, imgRGB[i]);
			}
		}

		// output image
		try{
			f = new File("/home/nokken/templ/JavaLearning/PixelSorting/outputs/Output_Interval_COL_"+ l.filename);
			ImageIO.write(img, filetype, f);
		  }catch(IOException e){
			System.out.println(e);
		  }

	}
	
/**
 * Converts an image to a binary one based on given threshold
 * @param image the image to convert. Remains untouched.
 * @param threshold the threshold in [0,255]
 * @return a new BufferedImage instance of TYPE_BYTE_GRAY with only 0'S and 255's
 */
	public static BufferedImage thresholdImage(BufferedImage image, int threshold) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		result.getGraphics().drawImage(image, 0, 0, null);
		WritableRaster raster = result.getRaster();
		int[] pixels = new int[image.getWidth()];
		for (int y = 0; y < image.getHeight(); y++) {
			raster.getPixels(0, y, image.getWidth(), 1, pixels);
			for (int i = 0; i < pixels.length; i++) {
				if (pixels[i] < threshold) pixels[i] = 0;
				else pixels[i] = 255;
			}
			raster.setPixels(0, y, image.getWidth(), 1, pixels);
		}
		return result;
	}
	public BufferedImage imageToBnW(String Filename, int threshold){
		try {

            File input = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/" + Filename);
            BufferedImage image = ImageIO.read(input);
			System.out.println(input.getAbsolutePath());
            File output = new File("/home/nokken/templ/JavaLearning/PixelSorting/BW_images/BW_"+ Filename);
            ImageIO.write(thresholdImage(image, threshold), "png", output);
			return image;

        }  catch (IOException e) {
            e.printStackTrace();
        }
		return null;
		
	}

}