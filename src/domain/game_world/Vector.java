package domain.game_world;

public final class Vector {

	final private int x;
	final private int y;
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
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
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
