package org.wahlzeit.model;

import java.sql.*;
import java.util.Objects;

/**
 * A coordinate represents the x,y,z coordinate of a location.
 */
public class SphericCoordinate implements Coordinate{

    private double phi;
    private double theta;
    private double radius;

	/**
	 * 
	 * @methodtype constructor
	 */
	public SphericCoordinate(double phi, double theta, double radius) {
		this.phi = phi;
        this.theta = theta;
        this.radius = radius;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getPhi(){
		return phi;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public double getTheta(){
		return theta;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getRadius(){
		return radius;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setPhi(double phi){
		this.phi = phi;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setTheta(double theta){
		this.theta = theta;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setRadius(double radius){
		this.radius = radius;
	}

	/**
	 * return coordinate as cartesian coordinate
	 * @methodtype conversion
	 */
    public CartesianCoordinate asCartesianCoordinate(){
        double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
        double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
        double z = this.radius * Math.cos(this.phi);
        CartesianCoordinate cartesianCoord = new CartesianCoordinate(x, y, z);
        return cartesianCoord;
    }
    
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
	 * return coordinate as spheric coordinate
	 * @methodtype conversion
	 */
    public SphericCoordinate asSphericCoordinate(){
        return this;
    }

    /**
	 * return central angle between this and other coordinate
	 * @methodtype get
	 */
    public double getCentralAngle(Coordinate coordinate){
        if(coordinate == null){
            throw new NullPointerException("Given coordinate may not be null");
        }
        SphericCoordinate otherAsSpheric = coordinate.asSphericCoordinate();
        
        double phi_1 = otherAsSpheric.getPhi();
        double phi_2 = this.getPhi();
        double delta = Math.abs(otherAsSpheric.getTheta() - this.getTheta());

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
        CartesianCoordinate thisAsCartesian = this.asCartesianCoordinate();
		rset.updateDouble("coordinate_x", thisAsCartesian.getX());
		rset.updateDouble("coordinate_y", thisAsCartesian.getY());
		rset.updateDouble("coordinate_z", thisAsCartesian.getZ());
	}

	/**
	 * 
	 * @methodtype command
	 */
	public void readFrom(ResultSet rset) throws SQLException{
        double x = rset.getDouble("coordinate_x");
		double y = rset.getDouble("coordinate_y");
		double z = rset.getDouble("coordinate_z");

        SphericCoordinate Spheric = new CartesianCoordinate(x, y, z).asSphericCoordinate();
        this.phi = Spheric.getPhi();
        this.theta = Spheric.getTheta();
        this.radius = Spheric.getRadius();
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
