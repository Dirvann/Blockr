package game_world.api;

/**
 * 
 * GameWorldListener allows the controller to 'listen' for changes to the GameWorld.
 * The implementing GameWorld should execute gameWorldChanged()
 * for every listener whenever the GameWorld changes.
 */
public interface GameWorldListener {
	void gameWorldChanged();
}
