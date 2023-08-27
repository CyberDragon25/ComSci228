package edu.iastate.cs228.hw2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuickSorterTest extends AbstractSorterTest {

	@BeforeEach
	void setUp() {
		super.setUp();
		sorter = new QuickSorter(pts);
	}


}