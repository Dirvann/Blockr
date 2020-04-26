package simpleui;


/**
 * A class of vectors involving an x value and a y value.
 *
 */
public final class Vector {

	final private int x;
	final private int y;
	/**
	 * Initialize a new Vector with the given x value as its x coordinate 
	 * and the given y value as its y coordinate.
	 * 
	 * @param x
	 * 		  The given x coordinate for this Vector.
	 * @param y
	 * 		  The given y coordinate for this Vector.
	 * @post  The x coordinate of this vector is equal to the given x value.
	 * 		  | new.getX() == x
	 * @post  The y coordinate of this vector is equal to the given y value.
	 * 		  | new.getY() == y
	 */
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Initialize a copy of a given vector.
	 * 
	 * @param other
	 * 		  The given vector with the coordinates for this vector.
	 * @post  The x and y coordinates are equal to those of the given vector.
	 * 		  | new.getX() == other.getX()
	 * 		  | new.getY() == other.getY()
	 */
	public Vector(Vector other) {
		this.x = other.getX();
		this.y = other.getY();
	}
	/**
	 * Return True if the given x and y coordinates of the vectors are equal.
	 * 
	 * @return True if the x and y coordinates of the given vector are equal to this vector.
	 * 		  | result == (getX() == other.getX()) && (getY() == other.getY())	
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Vector)) {
			return false;
		}
		Vector obj_ = (Vector) obj;
		if (obj_.getX() == this.getX() &&
			obj_.getY() == this.getY()) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * The x coordinate of the Vector.
	 * 
	 * @return the x coordinate of this Vector.
	 */
	public int getX() {
		return x;
	}
	/**
	 * The y coordinate of the Vector.
	 * 
	 * @return the y coordinate of this Vector.
	 */
	public int getY() {
		return y;
	}
	/**
	 * The sum of the given Vectors.
	 * 
	 * @param  other
	 * 		   The Vector to add to the current vector.
	 * @return A new Vector which x and y coordinates 
	 * 		   are the sum of the given Vector and this Vector.
	 * 		   | Vector v with v.getX() == getX() + other.getX()
	 * 		   |           and v.getY() == getY() + other.getY()
	 */
	public Vector add(Vector other) {
		if (other != null) {
			return new Vector(this.getX() + other.getX(), this.getY() + other.getY());
		}
		return null;
	}
}
