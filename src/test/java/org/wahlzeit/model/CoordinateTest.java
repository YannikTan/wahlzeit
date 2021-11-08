package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test cases for the Coordinate class.
 */
public class CoordinateTest {

	@Test
	public void testCoordinateConstructor() {
		Coordinate coordinate = new Coordinate(1, 2, 3);
        
        assertEquals(coordinate.getX(), 1, 0);
        assertEquals(coordinate.getY(), 2, 0);
        assertEquals(coordinate.getZ(), 3, 0);
	}

    @Test
	public void testSettingCoordinates() {
		Coordinate coordinate = new Coordinate(1, 2, 3);
        
        coordinate.setX(4);
        coordinate.setY(5);
        coordinate.setZ(6);
        
        assertEquals(coordinate.getX(), 4, 0);
        assertEquals(coordinate.getY(), 5, 0);
        assertEquals(coordinate.getZ(), 6, 0);
	}

    @Test
	public void testCoordinateIsEqual() {
		Coordinate coordinate = new Coordinate(1, 2, 3);

        assertTrue(coordinate.isEqual(coordinate));
        assertTrue(coordinate.isEqual(new Coordinate(1, 2, 3)));
	}

    @Test
	public void testCoordinateShouldNotBeEqual() {
		Coordinate coordinate = new Coordinate(1, 2, 3);

        assertFalse(coordinate.isEqual(null));
        assertFalse(coordinate.isEqual(new Coordinate(2, 2, 2)));
        assertFalse(coordinate.isEqual(new Coordinate(1, 2, 2)));
        assertFalse(coordinate.isEqual(new Coordinate(2, 2, 3)));
        assertFalse(coordinate.isEqual(new Coordinate(3, 2, 1)));
        assertFalse(coordinate.isEqual(new Coordinate(0, 0, 0)));
	}

    @Test
	public void testCoordinateGetDistance() {
		Coordinate coordinate = new Coordinate(0, 0, 0);
        Coordinate otherCoordinate = new Coordinate(123, 45, 6789);
        
        double dist1 = coordinate.getDistance(otherCoordinate);
        double dist2 = otherCoordinate.getDistance(coordinate);
        double dist3 = otherCoordinate.getDistance(new Coordinate(123, 45, 6789));
        double dist4 = coordinate.getDistance(new Coordinate(Double.MIN_VALUE, 0, 0));
        double delta = 1e-5;

        assertEquals(dist1, dist2, 0);
        assertEquals(dist1, 6790.26325, delta);
        assertEquals(dist3, 0, 0);
        assertEquals(dist4, 0, 0);
	}

}