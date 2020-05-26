package domain;

import java.util.ArrayList;

import command.Command;
import command.DraggingCommand;
import domain.block.ImplementationBlock;
import game_world.api.Vector;
import presentation.block.PresentationBlock;

/**
 * This class is specially made for the creation of the block structure in programArea. 
 * This means dragging, connecting, disconnecting, making and deleting blocks. 
 * If a block is disconnected, dragged and again connected, this counts as one execution step.
 * It has a stack of the commands, the cursor and number of the undone commands.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class CommandProcessor {
	GameController gameController;
	ImplementationBlock BF = new ImplementationBlock();
	int cursor;
	ArrayList<Command> undoStack = new ArrayList<>();
	int nbCommandsUndone;

	/**
	 * This function undoes the next command to undo. An undone Command can always
	 * be redone, except when a new change has been made when this command has been
	 * undone.
	 * 
	 * @post Undoes all the changed made by the last command in the stack and adjust
	 *       the current index of the undoStack.
	 */
	public void undo() {
		if (undoStack.size() > nbCommandsUndone)
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
	}

	/**
	 * This function redoes the most recent undone command.
	 * 
	 * @post Re-does the most recent undone command.
	 */
	public void redo() {
		if (nbCommandsUndone > 0)
			undoStack.get(undoStack.size() - nbCommandsUndone--).execute();
	}

	/**
	 * When a block has been dragged, a new draggingCommand has been made. This
	 * function makes sure that command is added to the undostack so this command
	 * can be undone in the future.
	 * 
	 * @param command 
	 * 		  The new command that HAS BEEN executed.
	 * @post  The command will be added to the undoStack. If there were commands
	 *        undone, they will be removed from the undoStack. command will be the
	 *        top element of the undoStack.
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
	 * This function is used to make a new draggingCommand and store it in the
	 * undoStack.
	 * 
	 * @param oldPos
	 * 		  The previous position of the mouse.
	 * @param newPos
	 * 		  The current position of the mouse.
	 * @param block
	 * 		  The block that is bein dragged
	 * @param preCommand  
	 * 		  The command that has been executed before the block has
	 *        been dragged. This can be making a block or disconnecting.
	 * @param postCommand 
	 * 		  The command that has been executed after the block is
	 *        released. This can be deleting or connecting a block.
	 * @post  A new command will be made and added to the undoStack. This command
	 *        will hold the information of the preCommand, draggingCommand and
	 *        postCommand.
	 */
	public void dragCommand(Vector oldPos, Vector newPos, PresentationBlock<?> block, Command preCommand,
			Command postCommand) {

		executed(new DraggingCommand(oldPos, newPos, block, preCommand, postCommand));
	}
}
