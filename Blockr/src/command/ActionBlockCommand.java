package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import game_world.ImplementationGameWorld;

public class ActionBlockCommand extends ExecutionCommand {
	ImplementationGameController GCF = new ImplementationGameController();
	ImplementationBlock BF = new ImplementationBlock();

	/**
	 * 
	 * @param previouslyExecuted The block that has been executed just before.
	 * @param currentlyExecuted The block that is currently being executed.
	 * @param nextToExecute The next block to execute.
	 * @param GC The gameController where the blocks are stored in.
	 */
	public ActionBlockCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute, GameController GC) {
		super(previouslyExecuted, currentlyExecuted, nextToExecute, GC);

	}

	@Override
	public void undo() {

		ImplementationGameWorld GWF = GCF.getGameWorldImplementation(GC);
		GWF.undoAction(BF.getName(currentlyExecuted));
		super.undo();
	}

}
