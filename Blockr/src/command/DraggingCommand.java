package command;

import domain.Vector;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;
/**
 * A class that holds all the information about the action where a block gets
 * dragged from one place to another. This means also when a block gets
 * disconnected from one place and connected to another place, or made and
 * deleted. This information consists of the objects newPos, OldPos, PreCommand
 * and postComand. The class also specifies what must happen to undo and execute
 * this command.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class DraggingCommand implements Command {
	ImplementationPresentationBlock BPF = new ImplementationPresentationBlock();
	Command preCommand;
	Command postCommand;
	Vector oldPos;
	Vector newPos;
	PresentationBlock<?> block;

	/**
	 * This Command consists out of three parts. A preCommand is the command done
	 * before dragging the block, for example making a block or disconnecting one. A
	 * dragging Command is the movement from an old position to a new one. A
	 * postCommand is a Command like connecting or deleting a block.
	 * 
	 * @param oldPos
	 * 		  The old position of the block.
	 * @param newPos
	 * 		  The new position of the block.
	 * @param block
	 * 		  The block being dragged.
	 * @param preCommand  
	 * 		  The command that has been executed before the block has
	 *        been dragged. This can be making a block or disconnecting.
	 * @param postCommand 
	 * 		  The command that has been executed after the block is
	 *        released. This can be deleting or connecting a block.
	 * @post  This command will hold the information of the preCommand,
	 *        draggingCommand (The new position, old position and the block)
	 *        and postCommand together in one command.
	 * 		  | new.preCommand == preCommand
	 * 		  | new.postCommand == postCommand
	 *  	  | new.oldPos == oldPos
	 *   	  | new.newPos == newPos
	 *    	  | new.block == block
	 */
	public DraggingCommand(Vector oldPos, Vector newPos, PresentationBlock<?> block, Command preCommand,
			Command postCommand) {
		this.preCommand = preCommand;
		this.postCommand = postCommand;
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.block = block;
	}

	@Override
	public void execute() {
		if (preCommand != null) {
			preCommand.execute();
		}
		BPF.setPosition(block, newPos);
		if (postCommand != null) {
			postCommand.execute();
		}

	}

	@Override
	public void undo() {
		if (postCommand != null) {
			postCommand.undo();
		}
		BPF.setPosition(block, oldPos);
		if (preCommand != null) {
			preCommand.undo();
		}

	}

}
