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

	public Location(Coordinate coordinate){
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate(){
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate){
		this.coordinate = coordinate;
	}

	public void writeOn(ResultSet rset) throws SQLException {
		coordinate.writeOn(rset);
	}

}
