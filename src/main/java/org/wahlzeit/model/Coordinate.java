package org.wahlzeit.model;

import java.sql.*;

public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate();
    double getCartesianDistance(Coordinate coordinate);
    SphericCoordinate asSphericCoordinate();
    double getCentralAngle(Coordinate coordinate);
    boolean isEqual(Coordinate coordinate);
    int hashcode();
    boolean equals(Object object);
    void writeOn(ResultSet rset) throws SQLException;
    void readFrom(ResultSet rset) throws SQLException;
    void assertClassInvariants();
}
