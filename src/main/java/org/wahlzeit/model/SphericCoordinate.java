package org.wahlzeit.model;

import java.sql.*;

/**
 * A coordinate represents the x,y,z coordinate of a location.
 */
public class SphericCoordinate extends AbstractCoordinate{

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
		assertClassInvariants();
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
		assertClassInvariants();
        double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
        double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
        double z = this.radius * Math.cos(this.phi);
        CartesianCoordinate cartesianCoord = new CartesianCoordinate(x, y, z);
		assertClassInvariants();
        return cartesianCoord;
    }

	/**
	 * return coordinate as spheric coordinate
	 * @methodtype conversion
	 */
    public SphericCoordinate asSphericCoordinate(){
		assertClassInvariants();
        return this;
    }

	/**
	 * 
	 * @methodtype command
	 */
	public void readFrom(ResultSet rset) throws SQLException{
		assertClassInvariants();
        double x = rset.getDouble("coordinate_x");
		double y = rset.getDouble("coordinate_y");
		double z = rset.getDouble("coordinate_z");

        SphericCoordinate Spheric = new CartesianCoordinate(x, y, z).asSphericCoordinate();
        this.phi = Spheric.getPhi();
        this.theta = Spheric.getTheta();
        this.radius = Spheric.getRadius();
		assertClassInvariants();
	}

	/**
	 * 
	 * @methodtype assert
	 */
	@Override
    public void assertClassInvariants(){
		if(Double.isNaN(this.phi) || Double.isNaN(this.radius) || Double.isNaN(this.theta)){
			throw new NumberFormatException("Spheric Coordinate not valid (NaN)");			
		}
		if(this.radius < 0){
			throw new IllegalArgumentException("Spheric Coordinate radius not in valid range");			
		}
	}
	
}
