package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the FoodPhoto class.
 */
public class FoodPhotoTest {

    @Test
	public void testCreateInstance() {
		FoodPhoto foodPhoto = new FoodPhoto();
        
        assertNotNull(foodPhoto);
        assert foodPhoto instanceof Photo;
	}   
}