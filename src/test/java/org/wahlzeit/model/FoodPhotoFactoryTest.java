package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.lang.reflect.*;

/**
 * Test cases for the FoodPhotoFactory class.
 */
public class FoodPhotoFactoryTest {

    @Test
	public void testCreateInstance() {
        FoodPhotoFactory foodPhotoFactory = null;
        try {
            foodPhotoFactory = FoodPhotoFactory.getInstance();    
        } catch (IllegalStateException e) {
            
        }
        
        assertNotNull(foodPhotoFactory);
        assert foodPhotoFactory instanceof PhotoFactory;
	}
    
    @Test
	public void testReturnsSingleton() {
        FoodPhotoFactory foodPhotoFactory = null;
        FoodPhotoFactory fPF2 = null;
        try {
            foodPhotoFactory = FoodPhotoFactory.getInstance();    
        } catch (IllegalStateException e) {
            
        }
		try {
            fPF2 = foodPhotoFactory.getInstance();    
        } catch (IllegalStateException e) {
            
        }
        
        assertEquals(fPF2, foodPhotoFactory);
	}

    @Test
	public void testHasSinglePrivateConstructor() {
        Class c = FoodPhotoFactory.class;
		Constructor[] constructors = c.getDeclaredConstructors();
        assert constructors.length == 1;
        assert constructors[0].getModifiers() == Modifier.PRIVATE;  
	}
}
