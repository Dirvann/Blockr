package domain;

import domain.block.Block;
import domain.game_world.GameWorld;

public interface FacadeGameController {
	
	public GameController makeGameController();
	public GameController makeGameController(GameWorld gameWorld);
	
	public void setGameWorld(GameController gameController, GameWorld gameWorld);
	public GameWorld getGameWorld(GameController gameController);
	public ProgramArea getProgramArea(GameController gameController);
	
	public void addTopLevelBlock(GameController gameController,Block block);
	public void removeTopLevelBlock(GameController gameController,Block block);
	public void execute(GameController gameController) throws Exception;
	public void stopExecution(GameController gameController);
	public Block getNextBlockToExecute(GameController gameController);
	
}
