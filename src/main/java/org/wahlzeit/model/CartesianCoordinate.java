/*
 * 
 * 
 */

package org.wahlzeit.model;

import java.sql.*;
import java.util.Objects;

/**
 * A coordinate represents the x,y,z coordinate of a location.
 */
public class CartesianCoordinate implements Coordinate{

    /**
	 * cartesian x, y, z coordinates
	 */
    private double x;
    private double y;
    private double z;

	/**
	 * 
	 * @methodtype constructor
	 */
	public CartesianCoordinate(double x, double y, double z) {
		this.x = x;
        this.y = y;
        this.z = z;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public double getY(){
		return y;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getZ(){
		return z;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setX(double x){
		this.x = x;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setY(double y){
		this.y = y;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setZ(double z){
		this.z = z;
	}

	/**
	 * return coordinate as cartesian coordinate
	 * @methodtype conversion
	 */
    public CartesianCoordinate asCartesianCoordinate(){
        return this;
    }
    
    /**
	 * return cartesian distance between this and other coordinate
	 * @methodtype get
	 */
    public double getCartesianDistance(Coordinate coordinate){
        if(coordinate == null){
            throw new NullPointerException("Given coordinate may not be null");
        }
        CartesianCoordinate otherAsCartesian = coordinate.asCartesianCoordinate();
        double distance = Math.sqrt(
            Math.pow(x - otherAsCartesian.getX(), 2) +
            Math.pow(y - otherAsCartesian.getY(), 2) +
            Math.pow(z - otherAsCartesian.getZ(), 2)
        );
        if(Double.isNaN(distance)){
            throw new NumberFormatException("calculated distance is not a number");
        }
        return distance;
    }

	/**
	 * return coordinate as spheric coordinate
	 * @methodtype conversion
	 */
    public SphericCoordinate asSphericCoordinate(){
        double radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		double phi = Math.acos(z / radius);
		double theta;
		
		if(x > 0){
			theta = Math.atan(y / x);
		}else if(x < 0){
			theta = Math.atan(y / x) + Math.PI;
		}else{
			theta = Math.PI / 2;
		}
		
		return new SphericCoordinate(phi, theta, radius);
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
	 * 
	 * @methodtype command
	 */
	public void writeOn(ResultSet rset) throws SQLException{
		rset.updateDouble("coordinate_x", x);
		rset.updateDouble("coordinate_y", y);
		rset.updateDouble("coordinate_z", z);
	}

	/**
	 * 
	 * @methodtype command
	 */
	public void readFrom(ResultSet rset) throws SQLException{
		this.x = rset.getDouble("coordinate_x");
		this.y = rset.getDouble("coordinate_y");
		this.z = rset.getDouble("coordinate_z");
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

	
}
