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

	public Coordinate(double x, double y, double z) {
		this.x = x;
        this.y = y;
        this.z = z;
	}

	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

	public double getZ(){
		return z;
	}

	public void setX(double x){
		this.x = x;
	}

	public void setY(double y){
		this.y = y;
	}

	public void setZ(double z){
		this.z = z;
	}

	public void writeOn(ResultSet rset) throws SQLException{
		rset.updateDouble("coordinate_x", x);
		rset.updateDouble("coordinate_y", y);
		rset.updateDouble("coordinate_z", z);
	}
}
