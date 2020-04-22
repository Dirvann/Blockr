package domain;

import java.util.ArrayList;

import command.Command;
import command.DraggingCommand;
import domain.block.ImplementationBlock;
import game_world.Vector;
import presentation.block.PresentationBlock;

// This class is specially made for the creation of the block structure in programArea. 
//This means dragging, connecting, disconnecting, making and deleting blocks. 
//If a block is disconnected, dragged and again connected, this counts as one execution step.
public class CommandProcessor {
	GameController gameController;
	ImplementationBlock BF = new ImplementationBlock();
	int cursor;
	ArrayList<Command> undoStack = new ArrayList<>();
	int nbCommandsUndone;

	/**
	 * @post Undoes all the changed made by the last command in the stack.
	 */
	public void undo() {
		if (undoStack.size() > nbCommandsUndone)
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
	}

	/**
	 * @post Re-does the most recent undone command.
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
	void executed(Command command) {
		for (; nbCommandsUndone > 0; nbCommandsUndone--)
			undoStack.remove(undoStack.size() - 1);
		undoStack.add(command);
		// first execution happens in program itself, this is because dragging needs 3
		// commands (disconnect, drag and connect for example)
		// command.execute();
	}

	/**
	 * 
	 * @param oldPos
	 * @param newPos
	 * @param block
	 * @param preCommand  The command that has been executed before the block has
	 *                    been dragged. This can be making a block or disconnecting.
	 * @param postCommand The command that has been executed after the block is
	 *                    released. This can be deleting or connecting a block.
	 * @post a new command will be made and added to the undoStack. This command
	 *       will hold the information of the preCommand, draggingCommand and
	 *       postCommand.
	 */
	public void dragCommand(Vector oldPos, Vector newPos, PresentationBlock<?> block, Command preCommand,
			Command postCommand) {

		executed(new DraggingCommand(oldPos, newPos, block, preCommand, postCommand));
	}
}
