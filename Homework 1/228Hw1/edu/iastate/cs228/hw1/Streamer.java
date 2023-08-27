package edu.iastate.cs228.hw1;

/**
 * @author Subham Bhattacharya
 * extension of the towncell class
 */

public class Streamer extends TownCell{
    public Streamer(Town p, int r, int c){
        super(p, r, c);
    }

    @Override
    public State who() {
        return State.STREAMER;
    }

    @Override
    public TownCell next(Town tNew) {
        census(nCensus);
        if (nCensus[EMPTY] + nCensus[OUTAGE] <= 1) {
            return new Reseller(tNew, row, col);
        } else if (nCensus[RESELLER] > 0) {
            return new Outage(tNew, row, col);
        } else if (nCensus[OUTAGE] > 0) {
            return new Empty(tNew, row, col);
        } else if (nCensus[CASUAL] >= 5) {
            return new Streamer(tNew, row, col);
        }
        return new Streamer(tNew, row, col);
    }
}
