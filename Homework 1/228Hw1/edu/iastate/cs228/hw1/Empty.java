package edu.iastate.cs228.hw1;

/**
 * @author Subham Bhattacharya
 * extension of the towncell class
 */
public class Empty extends TownCell{
    public Empty(Town p, int r, int c) {
        super(p, r, c);
    }

    @Override
    public State who() {
        return State.EMPTY;
    }


    @Override
    public TownCell next(Town tNew) {
        census(nCensus);
        if (nCensus[EMPTY] + nCensus[OUTAGE] <= 1) {
            return new Reseller(tNew, row, col);
        }
        return new Casual(tNew, row, col);
    }
}
