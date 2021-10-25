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

	public Coordinate coordinate;

	public Location(Coordinate coordinate){
		this.coordinate = coordinate;
	}

	public void writeOn(ResultSet rset) throws SQLException {
		coordinate.writeOn(rset);
	}

}
