package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

/**
 * @author Subham Bhattacharya
 * Test for Casual Towncell
 */

import static org.junit.jupiter.api.Assertions.*;

class CasualTest {

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
        assertEquals(a.grid[1][2].who().equals(State.CASUAL), true);
        assertEquals(a.grid[2][3].who().equals(State.CASUAL), false);

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
        assertEquals(a.grid[1][2].next(a).who(), State.OUTAGE);

    }
}