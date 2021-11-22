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
	
    public static FoodPhotoManager getInstance() {
		return (FoodPhotoManager) PhotoManager.getInstance();
	}

	public static void initialize(){
		SysLog.logSysInfo("setting FoodPhotoManager");
		PhotoManager.instance = new FoodPhotoManager();
	}

	/**
	 * In-memory cache for photos
	 */
	protected Map<PhotoId, FoodPhoto> photoCache = new HashMap<PhotoId, FoodPhoto>();
	
	
	/**
	 * 
	 */
	public static boolean hasPhoto(String id) {
		return hasPhoto(PhotoId.getIdFromString(id));
	}
	
	/**
	 * 
	 */
	public static boolean hasPhoto(PhotoId id) {
		return getPhoto(id) != null;
	}
	
	/**
	 * 
	 */
	public static FoodPhoto getPhoto(String id) {
		return getPhoto(PhotoId.getIdFromString(id));
	}
	
	/**
	 * 
	 */
	public static FoodPhoto getPhoto(PhotoId id) {
		return getInstance().getPhotoFromId(id);
	}
	
	/**
	 * @methodtype boolean-query
	 * @methodproperties primitive
	 */
	protected boolean doHasPhoto(PhotoId id) {
		return photoCache.containsKey(id);
	}
	
	/**
	 * 
	 */
	@Override
	public FoodPhoto getPhotoFromId(PhotoId id) {
		if (id.isNullId()) {
			return null;
		}

		FoodPhoto result = doGetPhotoFromId(id);
		
		if (result == null) {
			try {
				PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE id = ?");
				result = (FoodPhoto) readObject(stmt, id.asInt());
			} catch (SQLException sex) {
				SysLog.logThrowable(sex);
			}
			if (result != null) {
				doAddPhoto(result);
			}
		}
		
		return result;
	}
		
	/**
	 * @methodtype get
	 * @methodproperties primitive
	 */
	protected FoodPhoto doGetPhotoFromId(PhotoId id) {
		return photoCache.get(id);
	}
	
	/**
	 * 
	 */
	@Override
	protected FoodPhoto createObject(ResultSet rset) throws SQLException {
		return FoodPhotoFactory.getInstance().createPhoto(rset);
	}
	
	/**
	 * @methodtype command
	 *
	 * Load all persisted photos. Executed when Wahlzeit is restarted.
	 */
	public void addPhoto(FoodPhoto photo) {
		PhotoId id = photo.getId();
		assertIsNewPhoto(id);
		doAddPhoto(photo);

		try {
			PreparedStatement stmt = getReadingStatement("INSERT INTO photos(id) VALUES(?)");
			createObject(photo, stmt, id.asInt());
			ServiceMain.getInstance().saveGlobals();
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	
	/**
	 * @methodtype command
	 * @methodproperties primitive
	 */
	protected void doAddPhoto(FoodPhoto myPhoto) {
		super.doAddPhoto(myPhoto);
	}

	
	
	/**
	 * 
	 */
	@Override
	public void savePhoto(Photo photo) {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM photos WHERE id = ?");
			updateObject(photo, stmt);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void savePhotos() {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM photos WHERE id = ?");
			updateObjects(photoCache.values(), stmt);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	
	/**
	 * @methodtype command
	 *
	 * Persists all available sizes of the Photo. If one size exceeds the limit of the persistence layer, e.g. > 1MB for
	 * the Datastore, it is simply not persisted.
	 */
	@Override
	public Set<Photo> findPhotosByOwner(String ownerName) {
		Set<Photo> result = new HashSet<Photo>();
		try {
			PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE owner_name = ?");
			readObjects(result, stmt, ownerName);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
		
		for (Iterator<Photo> i = result.iterator(); i.hasNext(); ) {
			doAddPhoto(i.next());
		}

		return result;
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
