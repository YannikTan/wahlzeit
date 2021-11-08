package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the Location class.
 */
public class LocationTest {

	@Test
	public void testLocationConstructor() {
        Coordinate coordinate = new Coordinate(1, 2, 3);
		Location location = new Location(coordinate);

        assertEquals(location.getCoordinate(), coordinate);
	}

	@Test
	public void testSetCoordinate() {
        Coordinate coordinate = new Coordinate(1, 2, 3);
		Location location = new Location(new Coordinate(0, 0, 0));
        location.setCoordinate(coordinate);

        assertEquals(location.getCoordinate(), coordinate);
	}

}