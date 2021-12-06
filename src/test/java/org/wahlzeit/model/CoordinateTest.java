package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test cases for the Coordinate implementing class.
 */
public class CoordinateTest {

	@Test
	public void testCoordinateConstructor() {
		CartesianCoordinate coordinate = new CartesianCoordinate(1, 2, 3);
        
        assertEquals(coordinate.getX(), 1, 0);
        assertEquals(coordinate.getY(), 2, 0);
        assertEquals(coordinate.getZ(), 3, 0);
	}

    @Test
	public void testSettingCoordinates() {
		CartesianCoordinate coordinate = new CartesianCoordinate(1, 2, 3);
        
        coordinate.setX(4);
        coordinate.setY(5);
        coordinate.setZ(6);
        
        assertEquals(coordinate.getX(), 4, 0);
        assertEquals(coordinate.getY(), 5, 0);
        assertEquals(coordinate.getZ(), 6, 0);
	}

    @Test
	public void testCoordinateIsEqual() {
		CartesianCoordinate coordinate = new CartesianCoordinate(1, 2, 3);

        assertTrue(coordinate.isEqual(coordinate));
        assertTrue(coordinate.isEqual(new CartesianCoordinate(1, 2, 3)));
	}

    @Test
	public void testCoordinateShouldNotBeEqual() {
		CartesianCoordinate coordinate = new CartesianCoordinate(1, 2, 3);

        assertFalse(coordinate.isEqual(new CartesianCoordinate(2, 2, 2)));
        assertFalse(coordinate.isEqual(new CartesianCoordinate(1, 2, 2)));
        assertFalse(coordinate.isEqual(new CartesianCoordinate(2, 2, 3)));
        assertFalse(coordinate.isEqual(new CartesianCoordinate(3, 2, 1)));
        assertFalse(coordinate.isEqual(new CartesianCoordinate(0, 0, 0)));
	}

    @Test
	public void testCoordinateGetDistance() {
		CartesianCoordinate coordinate = new CartesianCoordinate(0, 0, 0);
        CartesianCoordinate otherCoordinate = new CartesianCoordinate(123, 45, 6789);
        
        double dist1 = coordinate.getCartesianDistance(otherCoordinate);
        double dist2 = otherCoordinate.getCartesianDistance(coordinate);
        double dist3 = otherCoordinate.getCartesianDistance(new CartesianCoordinate(123, 45, 6789));
        double dist4 = coordinate.getCartesianDistance(new CartesianCoordinate(Double.MIN_VALUE, 0, 0));
        double delta = 1e-5;

        assertEquals(dist1, dist2, 0);
        assertEquals(dist1, 6790.26325, delta);
        assertEquals(dist3, 0, 0);
        assertEquals(dist4, 0, 0);
	}

    @Test
	public void testConvertingBack() {
		CartesianCoordinate coordinate = new CartesianCoordinate(0.0012345, 12345678, 1234.5678);
        CartesianCoordinate convertedBack = coordinate.asSphericCoordinate().asCartesianCoordinate();
        for(int i = 0; i < 10000; i++){
            convertedBack = convertedBack.asSphericCoordinate().asCartesianCoordinate();
        }
        double delta = 0.0001;
        assertEquals(convertedBack.getX(), coordinate.getX(), delta);
        assertEquals(convertedBack.getY(), coordinate.getY(), delta);
        assertEquals(convertedBack.getZ(), coordinate.getZ(), delta);
	}

    @Test
    public void testCartesianToSpheric() {
		CartesianCoordinate Cartcoordinate = new CartesianCoordinate(3, 2, 1);
        SphericCoordinate AsSpheric = Cartcoordinate.asSphericCoordinate(); 
        double delta = 0.0001;
        assertEquals(AsSpheric.getPhi(), 1.3002465638163, delta);
        assertEquals(AsSpheric.getTheta(), 0.58800260354757, delta);
        assertEquals(AsSpheric.getRadius(), 3.7416573867739, delta);
    }

    @Test
    public void testSphericToCartesian() {
		SphericCoordinate SpherCoordinate = new SphericCoordinate(2, 3, 4);
        CartesianCoordinate AsCartesian = SpherCoordinate.asCartesianCoordinate(); 
        double delta = 0.0001;
        assertEquals(AsCartesian.getX(), -3.600790519, delta);
        assertEquals(AsCartesian.getY(), 0.5132802408, delta);
        assertEquals(AsCartesian.getZ(), -1.664587346, delta);
    }

    @Test
    public void testCentralAngleCalculation() {
		SphericCoordinate SpherCoordinate = new SphericCoordinate(1, 2, 3);
        SphericCoordinate otherSpherCoordinate = new SphericCoordinate(4, 5, 6); 
        double delta = 0.0001;
        assertEquals(otherSpherCoordinate.getCentralAngle(SpherCoordinate), 106.690188, delta);
        assertEquals(SpherCoordinate.getCentralAngle(otherSpherCoordinate), 106.690188, delta);
    }

}