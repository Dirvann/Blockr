package game_world;

/**
 * A class of the Robot, containing the location and the direction of the Robot.
 *
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 */
public class Robot {
	
	private Vector location;
	private Direction direction;
	/**
	 * Initialize a new Robot on the given location and let him look in the given direction
	 * 
	 * @param location
	 * 		  The location of the Robot.
	 * @param direction
	 *		  The direction of the Robot.
	 * @post  The location of this new Robot is equal to the given location.
	 * 		  |new.getLocation() = location
	 * @post  The direction of this new Robot is equal to the given direction.
	 * 		  |new.getDirection() = direction
	 */
	protected Robot(Vector location, Direction direction) {
		this.setLocation(location);
		this.setDirection(direction);
	}
	/**
	 * Initialize a new Robot on the given location and let him look up.
	 * 
	 * @param location
	 * 		  The location of the Robot.
	 * @post  The location of this new Robot is equal to the given location.
	 * 		  |new.getLocation() = location
	 * @post  The direction of this new Robot is UP.
	 * 		  |new.getDirection() = Direction.UP
	 */
	protected Robot(Vector location) {
		this(location, Direction.UP);
	}
	/**
	 * Initialize a new Robot on the same place as the given Robot
	 * and let him look in the same direction.
	 * 
	 * @param other
	 * 		  The given Robot with the location and direction for this Robot.
	 * @post  The location of this new Robot is the same as the given Robot.
	 * 		  |new.getLocation() = other.getLocation()
	 * @post  The direction of this new Robot is the same as the given direction.
	 * 		  |new.getDirection() = other.getDirection()
	 */
	protected Robot(Robot other) {
		this(other.getLocation(), other.getDirection());
	}
	/**
	 * Initialize the Robot with a given x and y coordinate looking up.
	 * 
	 * @param x
	 * 		  The x coordinate of the location of this Robot.
	 * @param y
	 * 		  The y coordinate of the location of this Robot.
	 * @post  The x coordinate of this new Robot is equal to the given x coordinate.
	 * 		  |new.getLocation().getX() = x
	 * @post  The y coordinate of this new Robot is equal to the given y coordinate.
	 * 		  |new.getLocation().getY() = y
	 * @post  The direction of this new Robot is UP.
	 * 		  |new.getDirection() = Direction.UP
	 */
	protected Robot(int x, int y) {
		this(new Vector(x, y));
	}
	
	/**
	 * The location of the Robot.
	 * 
	 * @return the location of the Robot.
	 */
	public Vector getLocation() {
		return location;
	}
	/**
	 * Set the location of the Robot to the given location.
	 * 
	 * @param location
	 * 		  The location for this Robot.
	 * @post  The location of this Robot is equal to the given location.
	 * 		  |new.getLocation() = location
	 */
	protected void setLocation(Vector location) {
		this.location = location;
	}
	/**
	 * Return the direction of the Robot.
	 * 
	 * @return direction of the Robot.
	 */
	public Direction getDirection() {
		return direction;
	}
	/**
	 * Set the direction of the Robot to the given location.
	 * 
	 * @param direction
	 * 		  The direction for this Robot.
	 * @post  The direction of the Robot is equal to the given direcion.
	 * 		  |new.getDirection() = direction
	 */
	protected void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * Returns the location of cell in front of the Robot.
	 * 
	 * @return location of the cell in front of the Robot.
	 */
	protected Vector getPositionInFront() {
		switch (this.direction) {
			case UP:
				return new Vector(location.getX(),location.getY()-1);
			case DOWN:
				return new Vector(location.getX(),location.getY()+1);
			case LEFT:
				return new Vector(location.getX()-1,location.getY());
			case RIGHT:
				return new Vector(location.getX()+1,location.getY());
			default:
				return null;
		}

	}
	
	/**
	 * Sets the location of the Robot to the location in front of the Robot.
	 * 
	 * @post The location of the Robot is the location in front of the previous location.
	 * 		 | new.getLocation = getPositionInFront()
	 */
	protected void stepForwards() {
		Vector positionInFront = getPositionInFront();
		setLocation(positionInFront);
		
	}
	/**
	 * Set the direction of the Robot to the direction to the left of the current direction.
	 * 
	 * @post The direction of the Robot is the direction to the left of the previous direction.
	 */
	protected void turnLeft() {
		switch (getDirection()) {
			case DOWN:
				setDirection(Direction.RIGHT);
				break;
				
			case LEFT:
				setDirection(Direction.DOWN);
				break;
	
			case RIGHT:
				setDirection(Direction.UP);
				break;
				
			case UP:
				setDirection(Direction.LEFT);
				break;
				
			default:
				break;
		}
	}
	/**
	 * Set the direction of the Robot to the direction to the right of the current direction.
	 * 
	 * @post The direction of the Robot is the direction to the left of the previous direction.
	 */
	protected void turnRight() {
		switch (getDirection()) {
			case DOWN:
				setDirection(Direction.LEFT);
				break;
				
			case LEFT:
				setDirection(Direction.UP);
				break;
				
			case RIGHT:
				setDirection(Direction.DOWN);
				break;
				
			case UP:
				setDirection(Direction.RIGHT);
				break;
				
			default:
				break;
		}
	}
	
}
