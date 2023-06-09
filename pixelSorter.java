/*
 * Author: Leif Forrest
 * Date: May 31st 2023
 * Description:	 A java program that reads an image and sorts its pixels by
 * the sum of each pixels RGB values. It then writes this now sorted image
 * to a new file. 
 * 
 */
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Collections;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class pixelSorter {
	int BLACK = -16777216;
	public static void main(String args[])
		throws IOException
	{	
		String fileName = "coolpic.jpg";
		// String filePath = "/home/nokken/templ/JavaLearning/PixelSorting/images/" + fileName;
		pixelSorter l = new pixelSorter();
		// l.imageToBnW(Filename, 25);
		// l.imgToColum(fileName, l, 70);
		// l.imgToRow(fileName, l, 70);
	  l.imgToRowInterval(fileName, l, 40);

	} // main() ends here

	
	public void imgToRowInterval(String Filename, pixelSorter l, int threshhold){
		BufferedImage img = null, imgBW = null, masktwo = null;
		File f = null, fBW = null, mask2 = null;

		l.imageToBnW(Filename, threshhold);

		//Open files

		// BW mask
		try{
			fBW = new File("/home/nokken/templ/JavaLearning/PixelSorting/BW_images/BW_"+ Filename);
			imgBW = ImageIO.read(fBW);
		}catch(IOException e){
			System.out.println(e);
		}
		// pattered mask
		try{
			mask2 = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/intervals.png");
			masktwo = ImageIO.read(mask2);
		}catch(IOException e){
			System.out.println(e);
		}
		// Origenal image
		try{
			f = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/" + Filename);
			img = ImageIO.read(f);
		}catch(IOException e){
			System.out.println(e);
		}
		// System.out.println(imgBW.getRGB(50,235) + " black val");

		int[] imgRGB = new int[img.getWidth()];
		// Vector<Integer> imgRGB = new Vector<Integer>();
		int rowIdx;
		int changedPixels = 0;

		boolean startPosSet = false, endPosSet = false;
		int startPos = 0, endPos;
		int imgHeight = img.getHeight(), imgWidth = img.getWidth();
		for(int j = 0; j < imgHeight; j++){
			// System.out.println(j + " for loop");
			rowIdx = 0;
			while(rowIdx < imgWidth ){
				imgRGB[rowIdx] = (img.getRGB(rowIdx, j));
				if(imgBW.getRGB(rowIdx,j) != BLACK && startPosSet == false ){//|| masktwo.getRGB(i,j) != 0xFF000000){
					startPos = rowIdx;
					startPosSet = true;
				}
				else if(imgBW.getRGB(rowIdx,j) == BLACK && startPosSet == true ){
					endPos = rowIdx;
					// System.out.println("start pos True startPos = " + startPos +" endpos =" +endPos);

					Arrays.sort(imgRGB, startPos, endPos);
					startPosSet = false;

				}
				else if(rowIdx == imgWidth -1){
					endPos = rowIdx;
					System.out.println("start pos True startPos = " + startPos +" endpos =" +endPos + " j= " + j);
					System.out.println("rowIdx= " + rowIdx + " imgWidth= " + imgWidth);

					Arrays.sort(imgRGB, startPos, endPos);
					startPosSet = false;
				}		
				rowIdx++;
			}
			for(int i = 0; i < img.getWidth(); i++){
				img.setRGB(i, j, imgRGB[i]);
			}
		}
		System.out.println(changedPixels + " changed pixels");

		// output image
		try{
			f = new File("/home/nokken/templ/JavaLearning/PixelSorting/outputs/Output_Row_Interval_"+ Filename);
			ImageIO.write(img, "jpg", f);
		  }catch(IOException e){
			System.out.println(e);
		  }

	}
	public void imgToRow(String Filename, pixelSorter l, int threshhold){
		BufferedImage img = null, imgBW = null, masktwo = null;
		File f = null, fBW = null, mask2 = null;

		l.imageToBnW(Filename, threshhold);

		//Open files

		// BW mask
		try{
			fBW = new File("/home/nokken/templ/JavaLearning/PixelSorting/BW_images/BW_"+ Filename);
			imgBW = ImageIO.read(fBW);
		}catch(IOException e){
			System.out.println(e);
		}
		// pattered mask
		try{
			mask2 = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/intervals.png");
			masktwo = ImageIO.read(mask2);
		}catch(IOException e){
			System.out.println(e);
		}
		// Origenal image
		try{
			f = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/" + Filename);
			img = ImageIO.read(f);
		}catch(IOException e){
			System.out.println(e);
		}


		int[] imgRGB = new int[img.getWidth()];

		for(int j = 0; j < img.getHeight(); j++){
			for(int i = 0; i < img.getWidth(); i++){
				if(imgBW.getRGB(i,j) != 0xFF000000 ){//|| masktwo.getRGB(i,j) != 0xFF000000){
					imgRGB[i] = img.getRGB(i, j);
				}
			}
			sortINT(imgRGB);
			for(int i = 0; i < img.getWidth(); i ++){
				if(imgBW.getRGB(i, j) != 0xFF000000 ){//|| masktwo.getRGB(i,j) != 0xFF000000){
					img.setRGB(i, j, imgRGB[i]);
				}
			}
		}
		// output image
		try{
			f = new File("/home/nokken/templ/JavaLearning/PixelSorting/outputs/Output_Row_"+ Filename);
			ImageIO.write(img, "jpg", f);
		  }catch(IOException e){
			System.out.println(e);
		  }

	}
	public void imgToColum(String Filename, pixelSorter l, int threshhold){
		BufferedImage img = null, imgBW = null, masktwo = null;
		File f = null, fBW = null, mask2 = null;

		l.imageToBnW(Filename, threshhold);

		//Open files

		// BW mask
		try{
			fBW = new File("/home/nokken/templ/JavaLearning/PixelSorting/BW_images/BW_"+ Filename);
			imgBW = ImageIO.read(fBW);
		}catch(IOException e){
			System.out.println(e);
		}
		// pattered mask
		try{
			mask2 = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/intervals.png");
			masktwo = ImageIO.read(mask2);
		}catch(IOException e){
			System.out.println(e);
		}
		// Origenal image
		try{
			f = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/" + Filename);
			img = ImageIO.read(f);
		}catch(IOException e){
			System.out.println(e);
		}


		int[] imgRGB = new int[img.getHeight()];
		System.out.println(imgRGB.length);
		for(int j = 0; j < img.getWidth(); j++){
			for(int i = 0; i < img.getHeight(); i++){
				if(imgBW.getRGB(j,i) != 0xFF000000){// || masktwo.getRGB(i,j) != 0xFF000000){
					imgRGB[i] = img.getRGB(j, i);
				}
			}
			sortINT(imgRGB);
			for(int i = 0; i < img.getHeight(); i ++){
				if(imgBW.getRGB(j, i) != 0xFF000000 ){//|| masktwo.getRGB(i,j) != 0xFF000000){
					img.setRGB(j, i, imgRGB[i]);
				}
			}
		}
		// output image
		try{
			f = new File("/home/nokken/templ/JavaLearning/PixelSorting/outputs/Output_Col_"+ Filename);
			ImageIO.write(img, "jpg", f);
		  }catch(IOException e){
			System.out.println(e);
		  }

	}
	
	public void sortINT(int arr[])
    {
        int n = arr.length;
 
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapifyINT(arr, n, i);
 
        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
 
            // call max heapify on the reduced heap
            heapifyINT(arr, i, 0);
        }
    }
 
    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    void heapifyINT(int arr[], int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2
 
        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;
 
        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;
 
        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
 
            // Recursively heapify the affected sub-tree
            heapifyINT(arr, n, largest);
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
	public void imageToBnW(String Filename, int threshold){
		try {

            File input = new File("/home/nokken/templ/JavaLearning/PixelSorting/images/" + Filename);
            BufferedImage image = ImageIO.read(input);
			System.out.println(input.getAbsolutePath());
            File output = new File("/home/nokken/templ/JavaLearning/PixelSorting/BW_images/BW_"+ Filename);
			
            ImageIO.write(thresholdImage(image, threshold), "png", output);

        }  catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void bubbleSortImg(BufferedImage imgBW, BufferedImage img, int height, int width){
		for(int i = 0; i < height; i++){
			for(int k = 0; k < width - 1; k++){
				for(int j = 0; j < width - 1; j++){
					System.out.println(img.getRGB(j, i));
					if(img.getRGB(j, i) < img.getRGB(j+1, i) && imgBW.getRGB(j,i) != 0xFF000000){
						int tempPixel = img.getRGB(j, i);
						img.setRGB(j,i,img.getRGB(j + 1, i));
						img.setRGB(j+1, i, tempPixel);
					}
				}
			}
		}	
	}
	public void insertionSort(BufferedImage img, int width, int height){
		int key, i, j;
		for(int k = 0; k < height; k++){
			for(i = 1; i < width; i++){
				key = img.getRGB(i, k);
				j = i-1;
				while(j >=0 && img.getRGB(j,k) > key){
					int tempPixel = img.getRGB(j, k);
					img.setRGB(j+1, k, tempPixel);
					j--;
				}
				img.setRGB(j+1, k, key);
			}
		}

	}
	/*
	 * heapsort and heapify adapted from: 
	 * https://www.geeksforgeeks.org/java-program-for-heap-sort/
	 */
	public void heapSort(BufferedImage img)
	{
		int n = img.getWidth();

		for(int y = 0; y < img.getHeight(); y++){
			// Build heap (rearrange array)
			for (int i = n / 2 - 1; i >= 0; i--){
	
				heapify(img, n, i, y);
			}
			// One by one extract an element from heap
			for (int i = n - 1; i >= 0; i--) {
				// Move current root to end
				int temp = img.getRGB(0, y);
				img.setRGB(0, y, img.getRGB(i, y));
				img.setRGB(i, y, temp);

				// call max heapify on the reduced heap
				heapify(img, i, 0, y);
			}
		}

	}

	// To heapify a subtree rooted with node i which is
	// an index in arr[]. n is size of heap
	void heapify(BufferedImage img, int n, int i, int height)
	{
		int largest = i; // Initialize largest as root
		int l = 2 * i + 1; // left = 2*i + 1
		int r = 2 * i + 2; // right = 2*i + 2

		// If left child is larger than root
		if (l < n && img.getRGB(l, height) > img.getRGB(largest,height))
			largest = l;

		// If right child is larger than largest so far
		if (r < n && img.getRGB(r, height) > img.getRGB(largest, height))
			largest = r;

		// If largest is not root
		if (largest != i) {
			int swap = img.getRGB(i, height);
			img.setRGB(i,height,img.getRGB(largest, height));
			img.setRGB(largest, height, swap);

			// Recursively heapify the affected sub-tree
			heapify(img, n, largest, height);
		}
	}
} 
