package command;

import domain.GameController;
import domain.block.ImplementationBlock;
import presentation.block.PresentationBlock;

/**
 * A class that holds all the information about the action where a block is
 * deleted. This information consists of the objects block and
 * GameController. The class also specifies what must happen to undo and execute this
 * command.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class DeleteBlockCommand implements Command {
	GameController GC;
	PresentationBlock<?> block;
	ImplementationBlock BF = new ImplementationBlock();

	/**
	 * Makes a delete block Commmand. This Command includes all of the info needed
	 * to undo and redo a block deletion Command.
	 * 
	 * @param GC    
	 * 		  The GameController where the block is deleted.
	 * @param block 
	 * 		  The block that gets deleted.
	 * @post  The objects block and GC are stored in this command for later use.
	 * 		  | new.GC == GC
	 * 		  | new.block == block
	 */
	public DeleteBlockCommand(GameController GC, PresentationBlock<?> block) {
		this.GC = GC;
		this.block = block;
	}

	@Override
	public void execute() {
		GC.removeBlockFromProgramArea(block);

	}

	@Override
	public void undo() {
		GC.addBlockToProgramArea(block);

	}

}
