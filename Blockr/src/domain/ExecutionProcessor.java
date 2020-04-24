package domain;

import java.util.ArrayList;

import command.ExecutionCommand;


//This class is specially made to control the commands during the execution. 
public class ExecutionProcessor {

	ImplementationGameController GCF = new ImplementationGameController();
	int cursor;
	ArrayList<ExecutionCommand> undoStack = new ArrayList<>();
	int nbCommandsUndone;

	/**
	 * @post Undoes all the changed made by the last command in the stack.
	 */
	public void undo() {
		if (undoStack.size() > nbCommandsUndone)
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
	}

	/**
	 * @post Redoes the most recent undone command.
	 */
	public void redo() {
		if (nbCommandsUndone > 0)
			undoStack.get(undoStack.size() - nbCommandsUndone--).execute();
	}

	/**
	 * 
	 * @param command The new command that HAS BEEN executed.
	 * @post The command will be added to the undoStack. If there were commands
	 *       undone, they will be removed from the undoStack. command will be the
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
