package domain.game_world;

public class Robot {
	
	private Vector location;
	private Direction direction;
	
	public Robot(Vector location, Direction direction) {
		this.setLocation(location);
		this.setDirection(direction);
	}
	
	public Robot(Vector location) {
		this(location, Direction.UP);
	}
	
	public Robot(Robot other) {
		this(other.getLocation(), other.getDirection());
	}
	
	public Robot(int x, int y) {
		this(new Vector(x, y));
	}
	

	public Vector getLocation() {
		return location;
	}

	public void setLocation(Vector location) {
		this.location = location;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	//given position may be out of bound!!
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
	public void stepForwards() {
		try {
			Vector positionInFront = getPositionInFront();
			setLocation(positionInFront);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
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
