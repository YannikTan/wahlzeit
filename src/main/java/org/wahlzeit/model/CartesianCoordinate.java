/*
 *
 *
 */

package org.wahlzeit.model;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;
import org.wahlzeit.utils.PatternInstance;

/**
 * A coordinate represents the x,y,z coordinate of a location.
 */
@PatternInstance(
	patternName = "Template Method",
	participants = {
		"ConcreteClass"
	}
)
public final class CartesianCoordinate extends AbstractCoordinate{

    /**
	 * cartesian x, y, z coordinates
	 */
    private final double x;
    private final double y;
    private final double z;
    private static final ConcurrentHashMap<Integer, CartesianCoordinate> cartesianHashMap = new ConcurrentHashMap<>();

	/**
	 *
	 * @methodtype constructor
	 */
	private CartesianCoordinate(final double x, final double y, final double z) {
		this.x = x;
        this.y = y;
        this.z = z;
		assertClassInvariants();
	}

	public static CartesianCoordinate doGetCoordinate(final double x, final double y, final double z) {
        CartesianCoordinate coord = new CartesianCoordinate(x, y, z);
        int hash = coord.hashCode();
        synchronized (cartesianHashMap) {
            if (cartesianHashMap.get(hash) == null) {
            	cartesianHashMap.put(hash, coord);
            }else {
            	coord = cartesianHashMap.get(hash);
            }
            return coord;
        }
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
	 * return coordinate as cartesian coordinate
	 * @methodtype conversion
	 */
    public CartesianCoordinate asCartesianCoordinate(){
		assertClassInvariants();
        return this;
    }


	/**
	 * return coordinate as spheric coordinate
	 * @methodtype conversion
	 */
    public SphericCoordinate asSphericCoordinate(){
		assertClassInvariants();

		double radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		double phi;
		if(radius == 0){
			return SphericCoordinate.doGetCoordinate(0, 0, 0);
		}
		phi = Math.acos(z / radius);
		double theta;

		if(x > 0){
			theta = Math.atan(y / x);
		}else if(x < 0){
			theta = Math.atan(y / x) + Math.PI;
		}else{
			theta = Math.PI / 2;
		}

		assertClassInvariants();
		return SphericCoordinate.doGetCoordinate(phi, theta, radius);
    }

	/**
	 * set x, y, and z coordinates according to database entries
	 * @methodtype command
	 */
	public void readFrom(ResultSet rset) throws SQLException{
		assertClassInvariants();
		final double x = rset.getDouble("coordinate_x");
		final double y = rset.getDouble("coordinate_y");
		final double z = rset.getDouble("coordinate_z");
		doGetCoordinate(x, y, z);
		assertClassInvariants();
	}

	/**
	 * assert that all values are not NaN
	 * @methodtype assert
	 */
	@Override
    public void assertClassInvariants(){
		if(Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isNaN(this.z)){
			throw new NumberFormatException("Cartesian Coordinate not valid (NaN)");
		}
	}

}