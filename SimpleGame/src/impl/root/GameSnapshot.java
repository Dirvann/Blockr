package impl.root;

import game_world.SimpleGameController;
import game_world.api.Snapshot;

public class GameSnapshot implements Snapshot {
	private SimpleGameController gameState;
	GameSnapshot(SimpleGameController gameState) {
		this.gameState = gameState;
	}
	
	SimpleGameController getState() {
		return this.gameState;
	}
}
