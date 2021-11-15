package org.wahlzeit.model;

import java.sql.*;

import org.wahlzeit.services.*;

/**
 * A Factory for creating Food photos and related objects.
 */
public class FoodPhotoFactory extends PhotoFactory{
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static FoodPhotoFactory instance = null;
	
    private FoodPhotoFactory() {
		// do nothing
	}

	/**
	 * Public singleton access method.
	 */
	public static synchronized FoodPhotoFactory getInstance() {
		if (instance == null) {
			SysLog.logSysInfo("setting FoodPhotoFactory");
			setInstance(new FoodPhotoFactory());
		}
		
		return instance;
	}
	
	/**
	 * Method to set the singleton instance of PhotoFactory.
	 */
	protected static synchronized void setInstance(FoodPhotoFactory FoodPhotoFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initialize FoodPhotoFactory twice");
		}
		
		instance = FoodPhotoFactory;
	}
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}

	/**
	 * @methodtype factory
	 */
    @Override
	public FoodPhoto createPhoto() {
		return new FoodPhoto();
	}
	
	/**
	 * 
	 */
    @Override
	public FoodPhoto createPhoto(PhotoId id) {
		return new FoodPhoto(id);
	}
	
	/**
	 * 
	 */
    @Override
	public FoodPhoto createPhoto(ResultSet rs) throws SQLException {
		return new FoodPhoto(rs);
	}

}
