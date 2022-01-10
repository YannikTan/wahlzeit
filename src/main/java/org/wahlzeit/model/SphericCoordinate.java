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
public class SphericCoordinate extends AbstractCoordinate{

    private final double phi;
    private final double theta;
    private final double radius;
    private static final ConcurrentHashMap<Integer, SphericCoordinate> sphericHashMap = new ConcurrentHashMap<>();

	/**
	 *
	 * @methodtype constructor
	 */
	private SphericCoordinate(final double phi, final double theta, final double radius) {
		this.phi = phi;
        this.theta = theta;
        this.radius = radius;
		assertClassInvariants();
	}

	public static SphericCoordinate doGetCoordinate(final double phi, final double theta, final double radius) {
        SphericCoordinate coord = new SphericCoordinate(phi, theta, radius);
        int hash = coord.hashCode();
        synchronized (sphericHashMap) {
            if (sphericHashMap.get(hash) == null) {
            	sphericHashMap.put(hash, coord);
            }else {
            	coord = sphericHashMap.get(hash);
            }
            return coord;
        }
    }

	/**
	 *
	 * @methodtype get
	 */
	public double getPhi(){
		return phi;
	}

	/**
	 *
	 * @methodtype get
	 */
	public double getTheta(){
		return theta;
	}

	/**
	 *
	 * @methodtype get
	 */
	public double getRadius(){
		return radius;
	}

	/**
	 * return coordinate as cartesian coordinate
	 * @methodtype conversion
	 */
    public CartesianCoordinate asCartesianCoordinate(){
		assertClassInvariants();

		final double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
        final double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
        final double z = this.radius * Math.cos(this.phi);
        CartesianCoordinate cartesianCoord = CartesianCoordinate.doGetCoordinate(x, y, z);

		assertClassInvariants();
        return cartesianCoord;
    }

	/**
	 * return coordinate as spheric coordinate
	 * @methodtype conversion
	 */
    public SphericCoordinate asSphericCoordinate(){
		assertClassInvariants();
        return this;
    }

	/**
	 * set coordinate according to database entries
	 * @methodtype command
	 */
	public void readFrom(ResultSet rset) throws SQLException{
		assertClassInvariants();
        // get cartesian coordinate
		double x = rset.getDouble("coordinate_x");
		double y = rset.getDouble("coordinate_y");
		double z = rset.getDouble("coordinate_z");

		//convert to Spheric ccordinate
        SphericCoordinate Spheric = CartesianCoordinate.doGetCoordinate(x, y, z).asSphericCoordinate();
        final double phi = Spheric.getPhi();
        final double theta = Spheric.getTheta();
        final double radius = Spheric.getRadius();
		doGetCoordinate(phi, theta, radius);
		assertClassInvariants();
	}

	/**
	 * Assert that all values are not NaN and radius is not smaller than 0
	 * @methodtype assert
	 */
	@Override
    public void assertClassInvariants(){
		if(Double.isNaN(this.phi) || Double.isNaN(this.radius) || Double.isNaN(this.theta)){
			throw new NumberFormatException("Spheric Coordinate not valid (NaN)");
		}
		if(this.radius < 0){
			throw new IllegalArgumentException("Spheric Coordinate radius not in valid range");
		}
	}

}