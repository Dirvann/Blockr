package impl.root;

import game_world.GameWorld;
import game_world.api.Snapshot;

public class GameWorldSnapshot implements Snapshot {
	private GameWorld gameState;
	GameWorldSnapshot(GameWorld gameState) {
		this.gameState = gameState;
	}
	
	GameWorld getState() {
		return this.gameState;
	}
}
