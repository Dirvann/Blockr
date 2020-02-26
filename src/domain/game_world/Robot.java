package domain.game_world;

public class Robot {
	
	private Vector location;
	private Direction direction;
	
	public Robot(int x, int y) {
		this.setLocation(new Vector(x,y));
		this.setDirection(Direction.UP);
		
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
	public Vector positionInFront() throws Exception {
		switch (this.direction) {
			case UP:
				return new Vector(location.getX(),location.getY()-1);
			case DOWN:
				return new Vector(location.getX(),location.getY()+1);
			case LEFT:
				return new Vector(location.getX()-1,location.getY());
			case RIGHT:
				return new Vector(location.getX()+1,location.getY());
		}
		throw new Exception();
	}
}
