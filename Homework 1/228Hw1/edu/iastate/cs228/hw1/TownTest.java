package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Subham Bhattacharya
 * Test for Town class
 */

class TownTest {

    @Test
    void getWidth() {
        Town a = new Town(4, 4);
        a.randomInit(10);
        assertEquals(a.getWidth() == 4, true);
        assertEquals(a.getWidth() == 6, false);

    }

    @Test
    void getLength() {
        Town a = new Town(4, 4);
        a.randomInit(10);
        assertEquals(a.getLength() == 4, true);
        assertEquals(a.getWidth() == 90, false);
    }

    @Test
    void randomInit() {
        //Tests both randominit and toString
        Town a = new Town(4,4);
        a.randomInit(10);
        assertEquals(
                "O R O R\n" +
                "E E C O\n" +
                "E S O S\n" +
                "E O R R", a.toString());

    }

    @Test
    void testToString() {
        //Tested above in the randominit method
        //Same code is written
        Town a = new Town(4,4);
        a.randomInit(10);
        assertEquals(
                "O R O R\n" +
                        "E E C O\n" +
                        "E S O S\n" +
                        "E O R R", a.toString());

    }
}