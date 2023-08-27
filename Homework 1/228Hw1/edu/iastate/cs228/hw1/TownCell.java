package edu.iastate.cs228.hw1;

/**
 * 
 * @author Subham Bhattacharya
 * Represent a generic cell, which different types of cell, in which internet is provided
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param //counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 

		//TODO: Write your code here.
		int nextRow = row + 1;
		int nextCol = col + 1;
		int prevRow = row - 1;
		int prevCol = col - 1;

		if (nextRow < plain.getLength()){
			State t = plain.grid[nextRow][col].who();
			nCensus[t.ordinal()]++;
		}
		if (nextCol < plain.getWidth()) {
			State t = plain.grid[row][nextCol].who();
			nCensus[t.ordinal()]++;
		}
		if (nextRow < plain.getLength() && nextCol < plain.getWidth()) {
			State t = plain.grid[nextRow][nextCol].who();
			nCensus[t.ordinal()]++;
		}
		if (prevRow >= 0){
			State t = plain.grid[prevRow][col].who();
			nCensus[t.ordinal()]++;
		}
		if (prevCol >= 0){
			State t = plain.grid[row][prevCol].who();
			nCensus[t.ordinal()]++;
		}
		if (prevRow >= 0 && prevCol >= 0){
			State t = plain.grid[prevRow][prevCol].who();
			nCensus[t.ordinal()]++;
		}
		if (nextRow < plain.getLength() && prevCol >= 0){
			State t = plain.grid[nextRow][prevCol].who();
			nCensus[t.ordinal()]++;
		}
		if (prevRow >= 0 && nextCol < plain.getWidth()){
			State t = plain.grid[prevRow][nextCol].who();
			nCensus[t.ordinal()]++;
		}

	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);



}
