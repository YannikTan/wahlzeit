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
        FoodPhotoManager.initialize();
		FoodPhotoManager foodPhotoManager = (FoodPhotoManager) PhotoManager.getInstance();
        
        assertNotNull(foodPhotoManager);
        assert foodPhotoManager instanceof PhotoManager;
	}
    
    @Test
	public void testGetInstanceReturnsSingleton() {
        FoodPhotoManager.initialize();
		FoodPhotoManager foodPhotoManager = (FoodPhotoManager) PhotoManager.getInstance();
        FoodPhotoManager fPM2 = (FoodPhotoManager) PhotoManager.getInstance();
        
        assertEquals(fPM2, foodPhotoManager);
	}
}
