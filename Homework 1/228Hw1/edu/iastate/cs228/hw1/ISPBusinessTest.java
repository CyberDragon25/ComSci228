package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Subham Bhattacharya
 * Test for ISPBusiness class
 */
class ISPBusinessTest {

    @Test
    void updatePlain() {
        Town a = new Town(4,4);
        a.randomInit(10);
        Town b = ISPBusiness.updatePlain(a);
        assertEquals(b.toString(), "E E E E\n" +
                "C C O E\n" +
                "C O E O\n" +
                "C E E E"
         );

    }

    @Test
    void getProfit() {
        Town a = new Town(4,4);
        a.randomInit(10);
        assertEquals(ISPBusiness.getProfit(a), 1);
    }

    @Test
    void main() {
        //no need to test
    }
}