package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Subham Bhattacharya
 * Test for Reseller Towncell
 */
class ResellerTest {

    @Test
    void who() {
        //grid printed by random in this testcase
        /**
         * O R O R
         * E E C O
         * E S O S
         * E O R R
         */
        Town a = new Town(4, 4);
        a.randomInit(10);
        System.out.println(a.toString());
        assertEquals(a.grid[0][1].who().equals(State.RESELLER), true);
        assertEquals(a.grid[0][0].who().equals(State.RESELLER), false);

    }

    @Test
    void next() {
        //grid printed by random in this testcase
        /**
         * O R O R
         * E E C O
         * E S O S
         * E O R R
         */
        Town a = new Town(4, 4);
        a.randomInit(10);
        System.out.println(a.toString());
        assertEquals(a.grid[0][1].next(a).who(), State.EMPTY);

    }
}