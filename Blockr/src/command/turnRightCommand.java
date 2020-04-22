package command;

import domain.GameController;
import domain.block.Block;
import game_world.ImplementationGameWorld;

public class turnRightCommand extends ExecutionCommand{
	ImplementationGameWorld GWF = new ImplementationGameWorld();

	/**
	 * 
	 * @param previouslyExecuted The block that has been executed just before.
	 * @param currentlyExecuted The block that is currently being executed.
	 * @param nextToExecute The next block to execute.
	 * @param GC The gameController where the blocks are stored in.
	 */
	public turnRightCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute, GameController GC) {
		super(previouslyExecuted, currentlyExecuted, nextToExecute, GC);

	}

	@Override
	public void undo() {
		GWF.robotTurnLeft(GCF.getGameWorld(GC));
		super.undo();
	}
}
