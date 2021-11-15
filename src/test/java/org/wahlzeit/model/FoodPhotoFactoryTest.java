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
		FoodPhotoFactory foodPhotoFactory = FoodPhotoFactory.getInstance();
        
        assertNotNull(foodPhotoFactory);
        assert foodPhotoFactory instanceof PhotoFactory;
	}
    
    @Test
	public void testReturnsSingleton() {
		FoodPhotoFactory foodPhotoFactory = FoodPhotoFactory.getInstance();
        FoodPhotoFactory fPF2 = foodPhotoFactory.getInstance();
        
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
