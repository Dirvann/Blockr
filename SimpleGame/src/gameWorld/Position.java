package gameWorld;

public class Position {

	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	
	public Position translate(int x, int y) {
		return new Position(this.getX() + x, this.getY() + y);
	}
	

	public boolean equals(Position other) {
		return (this.getX() == other.getX() && this.getY() == other.getY());
	}
	
}
