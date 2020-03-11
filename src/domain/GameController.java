package domain;

import domain.block.*;
import domain.block.abstract_classes.ActionBlock;
import domain.block.abstract_classes.SurroundingBlock;
import domain.block.block_types.Block;
import domain.block.block_types.ConditionBlock;
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
		Block blockToExecute = programArea.getNextBlockToExecute();
		
		if (blockToExecute instanceof SurroundingBlock) {
			ConditionBlock condition = ((SurroundingBlock) blockToExecute).getConditionBlock();
			boolean evaluationResult = conditionEvaluation(condition);
			// TODO: inject result of evaluation into surroundingBlock
		}
		
		if (blockToExecute instanceof ActionBlock) {
			executeAction((ActionBlock) blockToExecute);
		}
		
		
		programArea.finishBlockExecution();
	}
	
	private void executeAction(ActionBlock actionBlock) {
		if (actionBlock instanceof MoveForward) {
			gameWorld.robotStepForwards();
		}
		if (actionBlock instanceof TurnLeft) {
			gameWorld.robotTurnLeft();
		}
		if (actionBlock instanceof TurnRight) {
			gameWorld.robotTurnRight();
		}
	}
	
	private boolean conditionEvaluation(ConditionBlock conditionBlock) {
		if (conditionBlock instanceof NotBlock) {
			return !conditionEvaluation(((NotBlock) conditionBlock).getNextCondition());
		}
		if (conditionBlock instanceof WallInFront) {
			return gameWorld.robotWallInFront();
		}
		
		return false;
	}
	
}
