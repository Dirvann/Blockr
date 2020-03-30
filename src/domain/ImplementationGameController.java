package domain;

import domain.block.Block;
import domain.game_world.GameWorld;

public class ImplementationGameController implements FacadeGameController{

	public ImplementationGameController() {};

	@Override
	public GameController makeGameController() {
		return new GameController();
	}

	@Override
	public GameController makeGameController(GameWorld gameWorld) {
		return new GameController(gameWorld);
	}

	@Override
	public void setGameWorld(GameController gameController,GameWorld gameWorld) {
		gameController.setGameWorld(gameWorld);
	}

	@Override
	public GameWorld getGameWorld(GameController gameController) {
		return gameController.getGameWorld();
	}

	@Override
	public ProgramArea getProgramArea(GameController gameController) {
		return gameController.getProgramArea();
	}
	
	@Override
	public void addTopLevelBlock(GameController gameController, Block block) {
		gameController.getProgramArea().addTopLevelBlock(block);
		
	}

	@Override
	public void removeTopLevelBlock(GameController gameController, Block block) {
		gameController.getProgramArea().removeTopLevelBlock(block);
		
	}

	@Override
	public void execute(GameController gameController) throws Exception {
		gameController.execute();
	}

	@Override
	public void stopExecution(GameController gameController) {
		gameController.stopExecution();
	}

	@Override
	public Block getNextBlockToExecute(GameController gameController) {
		return gameController.getNextBlockToExecute();
	}
}
