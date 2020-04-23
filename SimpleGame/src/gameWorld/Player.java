package gameWorld;

public class Player {

	private Position position;
	
	
	public Player(int x, int y) {
		this(new Position(x, y));
	}
	
	public Player(Position position) {
		this.position = position;
	}
	
	
	public Position getPosition() {
		return this.position;
	}
	
	public void moveLeft() {
		this.position = this.getPosition().translate(-1, 0);
	}
	
	public void moveRight() {
		this.position = this.getPosition().translate(1, 0);
	}
}
