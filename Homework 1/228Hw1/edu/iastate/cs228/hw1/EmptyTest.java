package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Subham Bhattacharya
 * Test for Empty Towncell
 */

class EmptyTest {

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
        assertEquals(a.grid[1][1].who().equals(State.EMPTY), true);
        assertEquals(a.grid[0][2].who().equals(State.EMPTY), false);

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
        assertEquals(a.grid[1][1].next(a).who(), State.CASUAL);

    }
}