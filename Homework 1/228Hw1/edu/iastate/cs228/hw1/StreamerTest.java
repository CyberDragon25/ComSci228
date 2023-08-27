package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Subham Bhattacharya
 * Test for Streamer Towncell
 */
class StreamerTest {

    @Test
    void who() {
        //grid printed by randome in this testcase
        /**
         * O R O R
         * E E C O
         * E S O S
         * E O R R
         */
        Town a = new Town(4, 4);
        a.randomInit(10);
        System.out.println(a.toString());
        assertEquals(a.grid[2][1].who().equals(State.STREAMER), true);
        assertEquals(a.grid[2][2].who().equals(State.STREAMER), false);

    }

    @Test
    void next() {
        //grid printed by randome in this testcase
        /**
         * O R O R
         * E E C O
         * E S O S
         * E O R R
         */
        Town a = new Town(4, 4);
        a.randomInit(10);
        System.out.println(a.toString());
        assertEquals(a.grid[2][1].next(a).who(), State.OUTAGE);

    }
}