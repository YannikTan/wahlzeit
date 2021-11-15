package org.wahlzeit.model;

import java.sql.*;
import java.net.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

/**
 * A photo represents a user-provided (uploaded) Food photo.
 */
public class FoodPhoto extends Photo {
	
	/**
	 * @methodtype constructor
	 */
	public FoodPhoto() {
		super();
	}
	
	/**
	 * 
	 * @methodtype constructor
	 */
	public FoodPhoto(PhotoId myId) {
		super(myId);
	}
	
	/**
	 * 
	 * @methodtype constructor
	 */
	public FoodPhoto(ResultSet rset) throws SQLException {
		super(rset);
	}

}	