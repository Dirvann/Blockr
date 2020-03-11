package domain;

import domain.block.block_types.Block;
import domain.game_world.GameWorld;

public class GameController {

	private ProgramArea programArea;
	private GameWorld gameWorld;
	
	public GameController() {
		this.programArea = new ProgramArea();
	}
	
	public GameController(GameWorld gameWorld) {
		this.programArea = new ProgramArea();
		this.gameWorld = gameWorld;
	}

	
	public void execute() throws Exception {
		if (programArea.programInProgress()) {
			executeNextBlock();
		} else {
			programArea.startExecution();
		}
	}
	
	public Block getNextBlockToExecute() {
		return programArea.getNextBlockToExecute();
	}
	
	private void executeNextBlock() {
		programArea.executeNextBlock(this);
	}
	
	public GameWorld getGameWorld() {
		return this.gameWorld;
	}
	
}
