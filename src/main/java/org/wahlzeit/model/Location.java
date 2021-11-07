/*
 * 
 * 
 */

package org.wahlzeit.model;

import java.sql.*;

/**
 * A location represents the location of a photo.
 */
public class Location {

	private Coordinate coordinate;

	/**
	 * 
	 * @methodtype constructor
	 */
	public Location(Coordinate coordinate){
		this.coordinate = coordinate;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public Coordinate getCoordinate(){
		return coordinate;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setCoordinate(Coordinate coordinate){
		this.coordinate = coordinate;
	}

	/**
	 * 
	 * @methodtype command
	 */
	public void writeOn(ResultSet rset) throws SQLException {
		coordinate.writeOn(rset);
	}

}
