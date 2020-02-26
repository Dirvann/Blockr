package domain.game_world;

public class GameWorld {
	
	private Grid gameGrid;
	
	public GameWorld(Grid grid) {
		this.setGameGrid(grid);
	}

	public Grid getGameGrid() {
		return gameGrid;
	}

	public void setGameGrid(Grid gameGrid) {
		this.gameGrid = gameGrid;
	}
}
