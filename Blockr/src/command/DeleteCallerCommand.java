package command;

import domain.GameController;
import domain.block.FunctionCall;
import domain.block.FunctionDefinition;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
import presentation.block.PresentationBlock;

/**
 * A class that holds all the information about the action where a block is
 * deleted. This information consists of the objects block and
 * GameController. The class also specifies what must happen to undo and execute this
 * command.
 * 
 * @version 3.0
 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
 *
 */
public class DeleteCallerCommand implements Command {
	GameController GC;
	FunctionCall caller;
	ImplementationBlock BF = new ImplementationBlock();
	SequenceBlock previous;
	SequenceBlock next;
	SurroundingBlock surroundingBlock;
	FunctionDefinition function;

	/**
	 * Makes a delete block Commmand. This Command includes all of the info needed
	 * to undo and redo a block deletion Command.
	 * 
	 * @param GC    the gamecontroller where the block is deleted.
	 * @param block the block that gets deleted.
	 * 
	 * @Post The objects block and GC are stored in this command for later use.
	 */
	public DeleteCallerCommand(GameController GC, FunctionCall caller) {
		this.GC = GC;
		this.caller = caller;
		this.previous = (SequenceBlock) BF.getPreviousBlock(caller);
		this.next = (SequenceBlock) BF.getNextBlock(caller);
		this.surroundingBlock = BF.getSurroundingBlock(caller);
		this.function = BF.getFunctionBlock(caller);
	}

	@Override
	public void execute() {
		BF.deleteFunctionCall(caller, GC);
	}

	@Override
	public void undo() {
		if (previous != null) {
			GC.connect(previous, caller);
		}
		else if (surroundingBlock != null) {
			GC.setBody(surroundingBlock, caller);
		}
		else if (function != null) {
			GC.setBody(function, caller);
		}
		else {
			GC.addBlockToProgramArea(BF.getPresentationBlock(caller));
			GC.connect(caller, next);
		}

	}

}