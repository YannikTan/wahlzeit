/*
 * 
 * 
 */

package org.wahlzeit.model;

import java.sql.*;

/**
 * A coordinate represents the x,y,z coordinate of a location.
 */
public class Coordinate {

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
	public Coordinate(double x, double y, double z) {
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
	 * @methodtype get
	 */
	public double getDistance(Coordinate otherCoordinate){
		assert otherCoordinate != null;

		double sum = 0;
		sum += Math.pow(this.x - otherCoordinate.getX(), 2);
		sum += Math.pow(this.y - otherCoordinate.getY(), 2);
		sum += Math.pow(this.z - otherCoordinate.getZ(), 2);
		
		return Math.sqrt(sum);
	}

	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean isEqual(Coordinate otherCoordinate){
		if(otherCoordinate == null){
			return false;
		}

		return this.equals(otherCoordinate) || this.getDistance(otherCoordinate) == 0;
	}
}
