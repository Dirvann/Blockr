package game_world;

public class FallingBlock {

		private Position position;
		
		
		public FallingBlock(int x, int y) {
			this(new Position(x, y));
		}
		
		public FallingBlock(Position position) {
			this.position = position;
		}
	
		
		public Position getPosition() {
			return this.position;
		}
		
		public void moveDown() {
			this.position = this.getPosition().translate(0, 1);
		}
}
