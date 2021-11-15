package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Test cases for the FoodPhotoManager class.
 */
public class FoodPhotoManagerTest {

    @Test
	public void testCreateInstance() {
		FoodPhotoManager foodPhotoManager = FoodPhotoManager.getInstance();
        
        assertNotNull(foodPhotoManager);
        assert foodPhotoManager instanceof PhotoManager;
	}
    
    @Test
	public void testGetInstanceReturnsSingleton() {
		FoodPhotoManager foodPhotoManager = FoodPhotoManager.getInstance();
        FoodPhotoManager fPM2 = foodPhotoManager.getInstance();
        
        assertEquals(fPM2, foodPhotoManager);
	}
}
