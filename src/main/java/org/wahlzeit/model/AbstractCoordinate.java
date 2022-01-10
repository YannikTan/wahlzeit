package org.wahlzeit.model;

import java.sql.*;
import java.util.Objects;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.PatternInstance;

@PatternInstance(
	patternName = "Template Method",
	participants = {
		"AbstractClass"
	}
)
public abstract class AbstractCoordinate implements Coordinate{

    /**
	 * return cartesian distance between this and other coordinate
	 * @methodtype get
	 */
    public double getCartesianDistance(Coordinate coordinate){
        assertClassInvariants();
        double distance = 0;
        try{
            assertObjectNotNull(coordinate);
        
            //convert coordinates to Cartesian and calculate distance d = sqrt((x_1 - x_2)^2 + (y_1 - y_2)^2 + (z_1 - z_2)^2)
            CartesianCoordinate currentAsCartesian = this.asCartesianCoordinate();
            CartesianCoordinate otherAsCartesian = coordinate.asCartesianCoordinate();
            distance = Math.sqrt(
                Math.pow(currentAsCartesian.getX() - otherAsCartesian.getX(), 2) +
                Math.pow(currentAsCartesian.getY() - otherAsCartesian.getY(), 2) +
                Math.pow(currentAsCartesian.getZ() - otherAsCartesian.getZ(), 2)
            );
        
            assertValueNotNaN(distance);
            if(distance < 0){
                throw new IllegalArgumentException("Distance must not be smaller than 0");
            }
        }catch(RuntimeException e){
            SysLog.logThrowable(e);
        }
        assertClassInvariants();
        return distance;
    }

    /**
	 * return central angle between this and other coordinate
	 * @methodtype get
	 */
    public double getCentralAngle(Coordinate coordinate){
        assertClassInvariants();
        double angle = 0;
        try{
            assertObjectNotNull(coordinate);
		
            SphericCoordinate thisAsSpheric = this.asSphericCoordinate();
            SphericCoordinate otherAsSpheric = coordinate.asSphericCoordinate();
        
            double phi_1 = otherAsSpheric.getPhi();
            double phi_2 = thisAsSpheric.getPhi();
            double delta = Math.abs(otherAsSpheric.getTheta() - thisAsSpheric.getTheta());

            angle = Math.toDegrees(
                Math.acos(
                    Math.sin(phi_1) * Math.sin(phi_2) + 
                    Math.cos(phi_1) * Math.cos(phi_2) * Math.cos(delta)
                )
            );
        
            assertValueNotNaN(angle);
            if(angle < 0 || angle > 2*Math.PI){
                throw new IllegalArgumentException("Central angle is not in valid range");
            }
        }catch(RuntimeException e){
            SysLog.logThrowable(e);
        }
        assertClassInvariants();
        return angle;
    }

    /**
	 * check whether x,y and z coordinate is equal to other coordinate
	 * @methodtype comparison
	 */
	public boolean isEqual(Coordinate otherCoordinate){
        assertClassInvariants();
		assertObjectNotNull(otherCoordinate);
        
        CartesianCoordinate currentAsCartesian = this.asCartesianCoordinate();
        CartesianCoordinate otherAsCartesian = otherCoordinate.asCartesianCoordinate();
        
        double epsilon = 0.001;
        boolean eqX = Math.abs(otherAsCartesian.getX() - currentAsCartesian.getX()) <= epsilon;
        boolean eqY = Math.abs(otherAsCartesian.getY() - currentAsCartesian.getY()) <= epsilon;
        boolean eqZ = Math.abs(otherAsCartesian.getZ() - currentAsCartesian.getZ()) <= epsilon;
        
        assertClassInvariants();
		return eqX && eqY && eqZ;
	}

	/**
	 * 
	 * @methodtype comparison
	 */
    @Override
	public boolean equals(Object object){
		if(object == this){
			return true;
        }
        if(!(object instanceof Coordinate)){
            return false;
        }

        Coordinate other = (Coordinate) object;
		return isEqual(other);
	}


    public int hashcode(){
        assertClassInvariants();
        CartesianCoordinate thisAsCartesian = this.asCartesianCoordinate();
        assertClassInvariants();
        return Objects.hash(thisAsCartesian.getX(), thisAsCartesian.getY(), thisAsCartesian.getZ());
    }

    /**
	 * write coordinate values (as cartesian) to database
	 * @methodtype command
	 */
	public void writeOn(ResultSet rset) throws SQLException{
        assertClassInvariants();
        CartesianCoordinate thisAsCartesian = this.asCartesianCoordinate();
		rset.updateDouble("coordinate_x", thisAsCartesian.getX());
		rset.updateDouble("coordinate_y", thisAsCartesian.getY());
		rset.updateDouble("coordinate_z", thisAsCartesian.getZ());
        assertClassInvariants();
	}

    /**
	 * check that Object o is not null
	 * @methodtype assert
	 */
    public void assertObjectNotNull(Object o){
        if(o == null){
            throw new NullPointerException("Object may not be null");
        }
    }

    /**
	 * check that value is not NaN
	 * @methodtype assert
	 */
    public void assertValueNotNaN(double value){
        if(Double.isNaN(value)){
            throw new NumberFormatException("Value may not be NaN");
        }
    }

}
