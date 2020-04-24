package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import game_world.api.FacadeGameWorld;

public class ActionBlockCommand extends ExecutionCommand {
	ImplementationGameController GCF = new ImplementationGameController();
	ImplementationBlock BF = new ImplementationBlock();

	/**
	 * Makes an actionBlock command that includes all the information to undo and
	 * redo the action command.
	 * 
	 * @param previouslyExecuted The block that has been executed just before.
	 * @param currentlyExecuted  The block that is currently being executed.
	 * @param nextToExecute      The next block to execute.
	 * @param GC                 The gameController where the blocks are stored in.
	 * @Post previouslyExecuted, currentlyexecuted, nextToExecute and GC will be
	 *       saved in this command, so it can be used later to undo and redo this
	 *       command.
	 */
	public ActionBlockCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute,
			GameController GC) {
		super(previouslyExecuted, currentlyExecuted, nextToExecute, GC);

	}

	@Override
	public void undo() {

		FacadeGameWorld GWF = GCF.getGameWorldImplementation(GC);
		GWF.undoAction(BF.getName(currentlyExecuted));
		super.undo();
	}

}
