package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * @author Subham Bhattacharya
 * Test for Towncell class
 */
class TownCellTest {

//    protected void setUp() throws Exception
//    {
//
//    }




    @org.junit.jupiter.api.Test
    void census() {
        int[] census = new int[5];

        try{
            Town t = new Town("ISP4x4.txt");
            /**
             * 4 4
             * O R O R
             * E E C O
             * E S O S
             * E O R R
             */
            t.grid[0][0].census(census);
            // Test for top left corner of grid
            assertEquals(census[TownCell.RESELLER], 1);
            assertEquals(census[TownCell.EMPTY], 2);

            // Testing for grid[2][3]
            census = new int[5];
            t.grid[2][2].census(census);
            System.out.println(Arrays.toString(census));
            assertEquals(census[TownCell.RESELLER], 2);
            assertEquals(census[TownCell.EMPTY], 1);
            assertEquals(census[TownCell.STREAMER], 2);
            assertEquals(census[TownCell.OUTAGE], 2);
            assertEquals(census[TownCell.CASUAL], 1);
        } catch (FileNotFoundException e){
            System.out.println("File not found11111");
        }
    }



}