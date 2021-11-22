package org.wahlzeit.model;

import java.sql.*;

import org.wahlzeit.services.*;

/**
 * A Factory for creating Food photos and related objects.
 */
public class FoodPhotoFactory extends PhotoFactory{
	
	private static Boolean isInitialized = false;
	
    private FoodPhotoFactory() {
		// do nothing
	}

	/**
	 * Public singleton access method.
	 */
	public static synchronized FoodPhotoFactory getInstance() {
		if (!isInitialized) {
			SysLog.logSysInfo("setting FoodPhotoFactory");
			PhotoFactory.setInstance(new FoodPhotoFactory()); //use PhotoFactory to store instance
			isInitialized = true;
		}
		
		return (FoodPhotoFactory) PhotoFactory.getInstance();
	}
	
	/**
	 * Method to set the singleton instance of PhotoFactory.
	 */
	protected static synchronized void setInstance(FoodPhotoFactory FoodPhotoFactory) {
		if (isInitialized) {
			throw new IllegalStateException("attempt to initialize FoodPhotoFactory twice");
		}
		
		PhotoFactory.setInstance(FoodPhotoFactory);
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
