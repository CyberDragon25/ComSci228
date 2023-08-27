package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Subham Bhattacharya
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for (int row = 0; row < tOld.getLength(); row++) {
			for (int col = 0; col < tOld.getWidth(); col++) {
				tNew.grid[row][col] = tOld.grid[row][col].next(tNew);
			}
		}
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		int profit = 0;
		//looping through the matrix through each cell
		//if cell is casual $1 profit is made
		for (int row = 0; row < town.getLength(); row++) {
			for (int col = 0; col < town.getWidth(); col++) {
				if (town.grid[row][col].who().equals(State.CASUAL)) {
					profit++;
				}
			}
		}
		return profit;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		//TODO: Write your code here.
		Town t;
		Scanner sc = new Scanner(System.in);
		System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
		int populateGrid = sc.nextInt();
		if (populateGrid == 1) {
			System.out.println("Please enter file path:");
			String fileName = sc.next();
			try {
				t = new Town(fileName);
//				System.out.println(t.toString());
			} catch (FileNotFoundException e) {
				//Get it checked
				System.out.println("Invalid input");
				return;
			}
			// first way done
		} else if (populateGrid == 2) {
			System.out.println("Provide rows, cols and seed integer separated by spaces:");
			int row = sc.nextInt();
			int col = sc.nextInt();
			int seed = sc.nextInt();
			t = new Town(row, col);
			t.randomInit(seed);
			// second way done
//			System.out.println(t.toString());
		} else {
			System.out.println("Invalid input");
			return;
		}
		int profit = 0;
		for (int month = 0; month < 11; month++) {
			profit += getProfit(t);
			t = updatePlain(t);
//			System.out.println(t.toString());
		}

		int gridArea = (t.getLength() * t.getWidth()) * 12;
		double ans = (double) profit / gridArea * 100;
		System.out.printf("%.2f", ans);
		System.out.print("%");
	}
}
