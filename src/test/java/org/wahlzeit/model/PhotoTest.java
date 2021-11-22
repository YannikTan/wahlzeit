package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test cases for the Photo class.
 */
public class PhotoTest {

	@Test
	public void testSetLocation() {
		Photo photo = new Photo();
        Location location = new Location(new CartesianCoordinate(1, 2, 3));

        photo.setLocation(location);
        assertEquals(photo.getLocation(), location);
	}

    @Test
	public void testLocationInitiallyNull() {
		Photo photo = new Photo();
        assertNull(photo.getLocation());
	}

}