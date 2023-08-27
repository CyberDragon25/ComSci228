package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;


/**
 *  
 * @author Subham Bhattacharya
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		//breaking the recursion
		if (pts.length <= 1){
			return;
		}

		int mid = pts.length / 2;
		//sorting them seperately
		Point[] left = (Arrays.copyOfRange(pts, 0, mid));
		Point[] right = (Arrays.copyOfRange(pts, mid, pts.length));

		mergeSortRec(left);
		mergeSortRec(right);


		/**
		 * mixing arrays
		 */
		//Point[] mix = new Point[left.length + right.length];
		int i = 0; // pointer for first array
		int j = 0; // pointer for second array
		int k = 0; // pointer for mix array

		while (i < left.length && j < right.length) {
			if (pointComparator.compare(left[i], right[j]) < 0) {
				pts[k] = left[i];
				i++;
			} else {
				pts[k] = right[j];
				j++;
			}
			k++;
		}

		//It may be possible one of array is not complete
		//copy the remaining elements
		while (i < left.length) {
			pts[k] = left[i];
			i++;
			k++;
		}

		while (j < right.length) {
			pts[k] = right[j];
			j++;
			k++;
		}

	}

	
	// Other private methods if needed ...

}
