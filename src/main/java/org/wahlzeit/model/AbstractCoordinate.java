package org.wahlzeit.model;

import java.sql.*;
import java.util.Objects;

public abstract class AbstractCoordinate implements Coordinate{

    /**
	 * return cartesian distance between this and other coordinate
	 * @methodtype get
	 */
    public double getCartesianDistance(Coordinate coordinate){
        if(coordinate == null){
            throw new NullPointerException("Given coordinate may not be null");
        }
        CartesianCoordinate currentAsCartesian = this.asCartesianCoordinate();
        CartesianCoordinate otherAsCartesian = coordinate.asCartesianCoordinate();
        double distance = Math.sqrt(
            Math.pow(currentAsCartesian.getX() - otherAsCartesian.getX(), 2) +
            Math.pow(currentAsCartesian.getY() - otherAsCartesian.getY(), 2) +
            Math.pow(currentAsCartesian.getZ() - otherAsCartesian.getZ(), 2)
        );
        if(Double.isNaN(distance)){
            throw new NumberFormatException("calculated distance is not a number");
        }
        return distance;
    }

    /**
	 * return central angle between this and other coordinate
	 * @methodtype get
	 */
    public double getCentralAngle(Coordinate coordinate){
        if(coordinate == null){
            throw new NullPointerException("Given coordinate may not be null");
        }
		SphericCoordinate thisAsSpheric = this.asSphericCoordinate();
        SphericCoordinate otherAsSpheric = coordinate.asSphericCoordinate();
        
        double phi_1 = otherAsSpheric.getPhi();
        double phi_2 = thisAsSpheric.getPhi();
        double delta = Math.abs(otherAsSpheric.getTheta() - thisAsSpheric.getTheta());

        double angle = Math.toDegrees(
            Math.acos(
                Math.sin(phi_1) * Math.sin(phi_2) + 
                Math.cos(phi_1) * Math.cos(phi_2) * Math.cos(delta)
            )
        );
        if(Double.isNaN(angle)){
            throw new NumberFormatException("calculated angle is not a number");
        }
        return angle;
    }

    /**
	 * check whether x,y and z coordinate is equal to other coordinate
	 * @methodtype comparison
	 */
	public boolean isEqual(Coordinate otherCoordinate){
		if(otherCoordinate == null){
			return false;
		}
        CartesianCoordinate currentAsCartesian = this.asCartesianCoordinate();
        CartesianCoordinate otherAsCartesian = otherCoordinate.asCartesianCoordinate();
        double epsilon = 0.001;
        boolean eqX = Math.abs(otherAsCartesian.getX() - currentAsCartesian.getX()) <= epsilon;
        boolean eqY = Math.abs(otherAsCartesian.getY() - currentAsCartesian.getY()) <= epsilon;
        boolean eqZ = Math.abs(otherAsCartesian.getZ() - currentAsCartesian.getZ()) <= epsilon;

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
        CartesianCoordinate thisAsCartesian = this.asCartesianCoordinate();
        return Objects.hash(thisAsCartesian.getX(), thisAsCartesian.getY(), thisAsCartesian.getZ());
    }

    /**
	 * 
	 * @methodtype command
	 */
	public void writeOn(ResultSet rset) throws SQLException{
        CartesianCoordinate thisAsCartesian = this.asCartesianCoordinate();
		rset.updateDouble("coordinate_x", thisAsCartesian.getX());
		rset.updateDouble("coordinate_y", thisAsCartesian.getY());
		rset.updateDouble("coordinate_z", thisAsCartesian.getZ());
	}
}
