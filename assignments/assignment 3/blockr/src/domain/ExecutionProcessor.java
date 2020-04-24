package domain;

import java.util.ArrayList;

import command.ExecutionCommand;

/**
 * A class that is specially made to control the commands during the execution.
 * It has a stack of the commands, the cursor and number of the undone commands.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class ExecutionProcessor {

	ImplementationGameController GCF = new ImplementationGameController();
	int cursor;
	ArrayList<ExecutionCommand> undoStack = new ArrayList<>();
	int nbCommandsUndone;

	/**
	 * A function to undo the most recent command that hasn't been undone yet.
	 * 
	 * @post Undoes all the changed made by the last command in the stack that
	 *       hasn't been undone.
	 */
	public void undo() {
		if (undoStack.size() > nbCommandsUndone)
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
	}

	/**
	 * A function that redoes the most recent undone command in the stack
	 * 
	 * @post Redoes the most recent undone command and changes the current index of the stack (nbOfCommandsUndone)
	 */
	public void redo() {
		if (nbCommandsUndone > 0)
			undoStack.get(undoStack.size() - nbCommandsUndone--).execute();
	}

	/**
	 * A function that is used to add an element to the stack.
	 * 
	 * @param command The new command that HAS BEEN executed.
	 * @post The command will be added to the undoStack. If there were commands
	 *       undone, they will be removed from the undoStack. command will then be the
	 *       top element of the undoStack.
	 */
	void executed(ExecutionCommand command) {
		for (; nbCommandsUndone > 0; nbCommandsUndone--)
			undoStack.remove(undoStack.size() - 1);
		undoStack.add(command);
		// already executed in game;
		// command.execute();
	}

	/**
	 * This function adds an executionCommand to the stack.
	 * 
	 * @param exeCmd The execution command that has been executed.
	 * @post if exeCmd is not null, it will be added to undoStack and removes all of
	 *       the undone changes.
	 */
	public void addExecutionStep(ExecutionCommand exeCmd) {
		if (exeCmd != null)
			this.executed(exeCmd);
	}
}
