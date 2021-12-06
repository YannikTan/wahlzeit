/*
 * 
 * 
 */

package org.wahlzeit.model;

import java.sql.*;

/**
 * A coordinate represents the x,y,z coordinate of a location.
 */
public class CartesianCoordinate extends AbstractCoordinate{

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
		assertClassInvariants();
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
		assertClassInvariants();
        return this;
    }
    

	/**
	 * return coordinate as spheric coordinate
	 * @methodtype conversion
	 */
    public SphericCoordinate asSphericCoordinate(){
		assertClassInvariants();
        double radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		double phi;
		if(radius == 0){
			return new SphericCoordinate(0, 0, 0); 
		}
		phi = Math.acos(z / radius);
		double theta;
		
		if(x > 0){
			theta = Math.atan(y / x);
		}else if(x < 0){
			theta = Math.atan(y / x) + Math.PI;
		}else{
			theta = Math.PI / 2;
		}
		assertClassInvariants();
		return new SphericCoordinate(phi, theta, radius);
    }

	/**
	 * 
	 * @methodtype command
	 */
	public void readFrom(ResultSet rset) throws SQLException{
		assertClassInvariants();
		this.x = rset.getDouble("coordinate_x");
		this.y = rset.getDouble("coordinate_y");
		this.z = rset.getDouble("coordinate_z");
		assertClassInvariants();
	}

	/**
	 * 
	 * @methodtype assert
	 */
	@Override
    public void assertClassInvariants(){
		if(Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isNaN(this.z)){
			throw new NumberFormatException("Cartesian Coordinate not valid (NaN)");			
		}
	}
	
}
