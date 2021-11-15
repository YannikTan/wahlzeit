package org.wahlzeit.model;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.wahlzeit.main.*;
import org.wahlzeit.services.*;

/**
 * A photo manager provides access to and manages Food photos.
 */
public class FoodPhotoManager extends PhotoManager {
	
	/**
	 * 
	 */
	protected static final FoodPhotoManager instance = new FoodPhotoManager();

    public static FoodPhotoManager getInstance() {
		return instance;
	}

	/**
	 * @methodtype command
	 */
    @Override
	protected FoodPhoto createObject(ResultSet rset) throws SQLException {
		return FoodPhotoFactory.getInstance().createPhoto(rset);
	}
    
    /**
	 * @methodtype command
	 */
    @Override
	public FoodPhoto createPhoto(File file) throws Exception {
		PhotoId id = PhotoId.getNextId();
		FoodPhoto result = (FoodPhoto) PhotoUtil.createPhoto(file, id);
		addPhoto(result);
		return result;
	}
	
}
