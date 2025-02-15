package command;

import domain.GameController;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
/**
 * A class that holds all the information about the action where a block got
 * connected as a Body to a surroundingBlock. This information consists of
 * the objects blockToConnect, surroundingBlock and nextBlock (the block that
 * was previously connected as a body). The class also specifies what must
 * happen to undo and execute this command.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class AddToBodyCommand implements Command {

	SequenceBlock blockToConnect;
	SurroundingBlock surroundingBlock;
	SequenceBlock nextBlock;
	GameController GC;

	/**
	 * Makes a command for when a block is added to the body surrounding block. It
	 * includes all the info to undo and redo this action.
	 * 
	 * @param blockToConnectTo B
	 * 		  Block before group of blocks connected.
	 * @param blockToConnect   
	 * 		  First block of group of blocks that gets connected.
	 * @param nextBlock        
	 * 		  Block after group of blocks connected.
	 * @param GC
	 * 		  The given GameController.
	 * @post  surroundingBlock, blockToConnect, NextBlock and GC objects will be
	 *        saved in this object for later use.
	 *        | new.blockToConnect == blockToConnect
	 *        | new.surroundingBlock == surroundingBlock
	 * 		  | new.nextBlock == nextBlock
	 * 		  | new.GC == GC
	 */
	public AddToBodyCommand(SurroundingBlock surroundingBlock, SequenceBlock blockToConnect, SequenceBlock nextBlock,
			GameController GC) {
		this.blockToConnect = blockToConnect;
		this.surroundingBlock = surroundingBlock;
		this.nextBlock = nextBlock;
		this.GC = GC;
	}

	@Override
	public void execute() {
		GC.setBody(surroundingBlock, blockToConnect);
	}

	@Override
	public void undo() {
		GC.disconnect(blockToConnect);
		GC.disconnect(nextBlock);
		GC.setBody(surroundingBlock, nextBlock);
	}

}
