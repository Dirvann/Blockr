package domain.game_world;
/**
 * A class of the Robot, containing the location and the direction of the robot.
 *
 */
public class Robot {
	
	private Vector location;
	private Direction direction;
	/**
	 * Initialize a new robot on the given location and let him look in the given direction
	 * 
	 * @param location
	 * @param direction
	 */
	public Robot(Vector location, Direction direction) {
		this.setLocation(location);
		this.setDirection(direction);
	}
	/**
	 * Initialize a new robot on the given location and let him look up.
	 * 
	 * @param location
	 * @param direction
	 */
	public Robot(Vector location) {
		this(location, Direction.UP);
	}
	/**
	 * Initialize a new robot on the same place as the given robot
	 * and let him look in the same direction.
	 * 
	 * @param location
	 * @param direction
	 */
	public Robot(Robot other) {
		this(other.getLocation(), other.getDirection());
	}
	/**
	 * Initialize the robot with a given x and y coordinate looking up.
	 * 
	 * @param x
	 * @param y
	 */
	public Robot(int x, int y) {
		this(new Vector(x, y));
	}
	
	/**
	 * Return the location of the robot.
	 * 
	 * @return location of the robot.
	 */
	public Vector getLocation() {
		return location;
	}
	/**
	 * Set the location of the robot to the given location.
	 * 
	 * @param location
	 */
	public void setLocation(Vector location) {
		this.location = location;
	}
	/**
	 * Return the direction of the robot.
	 * 
	 * @return direction of the robot.
	 */
	public Direction getDirection() {
		return direction;
	}
	/**
	 * Set the direction of the robot to the given location.
	 * 
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	//given position may be out of bound!!
	/**
	 * Returns the location of cell in front of the robot.
	 * 
	 * @return location of the cell in front of the robot.
	 * @throws Exception
	 * 		   If the given location is not in bounds.
	 */
	public Vector getPositionInFront() throws Exception {
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
				throw new Exception();
		}
	}
	
	// Actions
	/**
	 * Sets the location of the robot to the location in front of the robot,
	 * if the location in front of the robot is a wall or not inside of the gird,
	 * then the location is set to the current location.
	 */
	public void stepForwards() {
		try {
			Vector positionInFront = getPositionInFront();
			setLocation(positionInFront);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Set the direction of the robot to the direction to the left of the current direction.
	 */
	public void turnLeft() {
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
	 * Set the direction of the robot to the direction to the right of the current direction.
	 */
	public void turnRight() {
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
