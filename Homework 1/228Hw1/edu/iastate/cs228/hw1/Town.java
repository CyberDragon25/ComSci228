package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author Subham Bhattacharya
 *
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		/**
		 * 1.) open the file, and assing to a variable
		 * 2.) Check if the file exists or not, if not FileNotFoundException error
		 * 3.) else, if file is present, read the first line of file and create a grid with that length and width
		 * 4.) Fill the grid with the values present in the file
		 */
		File file = new File(inputFileName);
		Scanner sc = new Scanner((file)); // scanner to scan each line of file
		//Getting the length and width of the grid
		int length = sc.nextInt();
		int width = sc.nextInt();
				//just copying the constructor to create a new grid based on the data of the file
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
		sc.nextLine();
		for (int row = 0; row < length; row++) {
			String[] line = sc.nextLine().split(" ");
			for (int col = 0; col < line.length; col++) {
//				System.out.print(line[col]);
				if (line[col].equals("C") || line[col].equals("C\t")) {
					Casual c = new Casual(this, row, col);
					grid[row][col] = c;
				} else if (line[col].equals("S")|| line[col].equals("S\t")) {
					Streamer s = new Streamer(this, row, col);
					grid[row][col] = s;
				} else if (line[col].equals("O")|| line[col].equals("O\t")) {
					Outage o = new Outage(this, row, col);
					grid[row][col] = o;
				} else if (line[col].equals("E")|| line[col].equals("E\t")) {
					Empty e = new Empty(this, row, col);
					grid[row][col] = e;
				} else if (line[col].equals("R")|| line[col].equals("R\t")) {
					Reseller r = new Reseller(this, row, col);
					grid[row][col] = r;
				} else {

				}
			}
		}
	sc.close();

	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	//Test it
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		//TODO: Write your code here.
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				int newRandomValue = rand.nextInt(5);
				if (newRandomValue == TownCell.RESELLER) {
					Reseller r = new Reseller(this, row, col);
					grid[row][col] = r;
				} else if (newRandomValue == TownCell.EMPTY) {
					Empty e = new Empty(this, row, col);
					grid[row][col] = e;
				} else if (newRandomValue == TownCell.CASUAL) {
					Casual c = new Casual(this, row, col);
					grid[row][col] = c;
				} else if (newRandomValue == TownCell.OUTAGE) {
					Outage o = new Outage(this, row, col);
					grid[row][col] = o;
				} else if (newRandomValue == TownCell.STREAMER) {
					Streamer s = new Streamer(this, row, col);
					grid[row][col] = s;
				}
			}
		}
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	//Test it
	@Override
	public String toString() {
		String s = "";
		//TODO: Write your code here.
		for (int row = 0; row < length; row++) {
			if (row == 0) {
				s += s;
			} else {
				s = s + "\n";
			}
			for (int col = 0; col < width; col++) {
				if (grid[row][col].who().equals(State.RESELLER)) {
					if(col != width - 1) {
						s += "R ";
					} else {
						s += "R";
					}
				} else if (grid[row][col].who().equals(State.EMPTY)) {
					if(col != width - 1) {
						s += "E ";
					} else {
						s += "E";
					}
				} else if (grid[row][col].who().equals(State.CASUAL)) {
					if(col != width - 1) {
						s += "C ";
					} else {
						s += "C";
					}
				} else if (grid[row][col].who().equals(State.OUTAGE)) {
					if(col != width - 1) {
						s +="O ";
					} else {
						s += "O";
					}
				} else if (grid[row][col].who().equals(State.STREAMER)) {
					if(col != width - 1) {
						s +="S ";
					} else {
						s += "S";
					}
				}
			}
		}
		return s;
	}

//	protected TownCell getCellAt(int row, int col) {
//		return grid[row][col];
//	}

}
