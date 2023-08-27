package edu.iastate.cs228.hw2;

/**
 * 
 * @author Subham Bhattacharya
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts .length== 0) {
			throw new IllegalArgumentException();
		}
		this.sortingAlgorithm = algo;

		this.points = new Point[pts.length];

		for (int i = 0; i < points.length; i++) {
			points[i] = pts[i];
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{

		this.sortingAlgorithm = algo;
		try {
			File file = new File(inputFileName);
			Scanner sc = new Scanner(file);
			int fileNum = 0;
			
			while(sc.hasNextInt()) {
				sc.nextInt();
				fileNum++;
			}
			sc.close();

			if (fileNum % 2 != 0) {
				throw new InputMismatchException();
			}

			points = new Point[fileNum/2];
			Scanner sc2 = new Scanner(file);
			for (int i = 0; i < fileNum/2; i++) {
				points[i] = new Point(sc2.nextInt(), sc2.nextInt());
			}

			sc2.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}

	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter = null;
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 


		//create an object to be referenced by aSorter according to sortingAlgorithm.
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		}
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		}
		if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		}
		if (sortingAlgorithm == Algorithm.QuickSort){
			aSorter = new QuickSorter(points);
		}

		long startTime, endTime;
		//  a) call setComparator() with an argument 0.
		//  b) call sort().
		// tracing time

		aSorter.setComparator(0);
		startTime = System.nanoTime();
		aSorter.sort();
		endTime = System.nanoTime();
		// setting up scan time
		scanTime += endTime - startTime;

		int xMedian = aSorter.getMedian().getX();

		//  a) call setComparator() with an argument  1.
		//  b) call sort().
		// tracing time

		aSorter.setComparator(1);
		startTime = System.nanoTime();
		aSorter.sort();
		endTime = System.nanoTime();
		// setting up scan time
		scanTime += endTime - startTime;

		int yMedian = aSorter.getMedian().getY();

		//		POINT A = NEW POINT(X, Y);

		//     MEDIANCOORDINATEPOINT = A;

		//   c) use a new Point object to store the coordinates of the medianCoordinatePoin
		medianCoordinatePoint = new Point(xMedian, yMedian);

		// deep copying
//		for (int i = 0; i < aSorter.points.length; i++) {
//			points[i] = new Point(aSorter.points[i]);
//		}

		try {
			writeMCPToFile();
		} catch (FileNotFoundException e) {
			System.out.println("unable to write to file");
		}

	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
			return String.format("%-18s%-6s%-10s ", sortingAlgorithm.toString(), points.length, scanTime);
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX() + " " + medianCoordinatePoint.getY() + ")";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		File file = new File("outputFileName.txt");
		PrintWriter printWriter = new PrintWriter(file);
		printWriter.write(toString());
		printWriter.close();
	}	

	

		
}
