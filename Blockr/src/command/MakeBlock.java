package command;

import domain.GameController;
import domain.block.ImplementationBlock;
import presentation.block.PresentationBlock;
/**
 * A class that holds all the information about the action where a block is
 * made. This information consists of the objects block and
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
public class MakeBlock implements Command {
	GameController GC;
	PresentationBlock<?> block;
	ImplementationBlock BF = new ImplementationBlock();

	/**
	 * Makes a Command for the creation of a block. This holds the info to undo and
	 * redo this action.
	 * 
	 * @param blockToConnectTo 
	 * 	      Block before group of blocks connected.
	 * @param blockToConnect   
	 * 		  First block of group of blocks that gets connected.
	 * @post  The objects GC and block will be stored in this Command for later use.
	 * 		  | new.GC == GC
	 * 		  | new.block == block
	 */
	public MakeBlock(GameController GC, PresentationBlock<?> block) {
		this.GC = GC;
		this.block = block;
	}

	@Override
	public void execute() {
		GC.addBlockToProgramArea(block);

	}

	@Override
	public void undo() {
		GC.removeBlockFromProgramArea(block);

	}

}
