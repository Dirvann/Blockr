package command;

import domain.GameController;
import domain.block.Block;
/**
 * A class that holds all the information about the execution by a block
 * (currently executed) that is not an actionBlock. This information consists of the objects
 * previouslyExecuted, currentlyExecuted and nextToExecute. The class also
 * specifies what must happen to undo and execute this command.
 * 
 * @version 3.0
 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
 *
 */
public class ExecutionCommand {
	protected Block previouslyExecuted;
	protected Block currentlyExecuted;
	protected Block nextToExecute;
	protected GameController GC;

	/**
	 * ExecutionCommand is a command that is able to hold all of the information to
	 * undo and redo the execution of this command.
	 * 
	 * @param previouslyExecuted The block that was executed before this one.
	 * @param currentlyExecuted  The block that is executed now.
	 * @param nextToExecute      The next block that will be executed.
	 * @param GC                 The GameController that includes the blocks.
	 * 
	 * @post The objects previouslyExecuted, currentlyExecuted, nextToExecute and GC
	 *       will be stored in this Command for later use.
	 */
	public ExecutionCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute, GameController GC) {
		this.previouslyExecuted = previouslyExecuted;
		this.currentlyExecuted = currentlyExecuted;
		this.nextToExecute = nextToExecute;
		this.GC = GC;

	}

	/**
	 * This function can be used to undo an execution command.
	 * 
	 * @Post all of the changes made by executing this command will be undone.
	 */
	public void undo() {
		GC.setNewExecution(previouslyExecuted, currentlyExecuted);
	}

	/**
	 * This function can be used to execute this command.
	 * 
	 * @Post all of the changes needed to execute this command will be made.
	 */
	public void execute() {
		try {
			GC.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Set the previously executed block of this object.
	 * 
	 * @param block The block that is executed before this command.
	 * @post this.prevouslyExecuted is set to block
	 */
	public void setPrevious(Block block) {
		this.previouslyExecuted = block;
	}

	/**
	 * Set the currently executing block of this object.
	 * 
	 * @param block The block that is executed in this command.
	 * @post this.currentlyExecuted is set to block
	 */
	public void setCurrent(Block block) {
		this.currentlyExecuted = block;
	}

	/**
	 * Set the 'next to execute' block of this object.
	 * 
	 * @param block The block that should be executed after this command.
	 * @post this.nextToExecuted is set to block
	 */
	public void setNext(Block block) {
		this.nextToExecute = block;
	}
}
