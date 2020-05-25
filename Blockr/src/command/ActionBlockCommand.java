package command;

import domain.GameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import game_world.api.FacadeGameWorld;

/**
 * A class that holds all the information about the execution by a block
 * (currently executed). This information consists of the objects
 * previouslyExecuted, currentlyExecuted and nextToExecute. The class also
 * specifies what must happen to undo and execute this command.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class ActionBlockCommand extends ExecutionCommand {
	ImplementationBlock BF = new ImplementationBlock();

	/**
	 * Makes an actionBlock command that includes all the information to undo and
	 * redo the action command.
	 * 
	 * @param previouslyExecuted 
	 * 	      The block that has been executed just before.
	 * @param currentlyExecuted  
	 * 		  The block that is currently being executed.
	 * @param nextToExecute      
	 * 		  The next block to execute.
	 * @param GC                 
	 * 		  The GameController where the blocks are stored in.
	 * @post  previouslyExecuted, currentlyexecuted, nextToExecute and GC will be
	 *        saved in this command, so it can be used later to undo and redo this
	 *        command.
	 *        | new.previouslyExecuted == previouslyExecuted
	 *        | new.currentlyExecuted == currentlyExecuted
	 * 		  | new.nextToExecute == nextToExecute
	 * 		  | new.GC == GC
	 */
	public ActionBlockCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute,
			GameController GC) {
		super(previouslyExecuted, currentlyExecuted, nextToExecute, GC);

	}
}
