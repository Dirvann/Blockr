package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;

public class addToBodyCommand implements Command {

	// first block of group of blocks that gets connected.
	SequenceBlock blockToConnect;
	// the surrounding block blockToConnect will be connected to.
	SurroundingBlock surroundingBlock;
	// block after group of blocks connected
	SequenceBlock nextBlock;
	// The gamecontroller wher the blocks exist
	GameController GC;
	ImplementationGameController GCF = new ImplementationGameController();

	/**
	 * Makes a command for when a block is added to the body surrounding block. It
	 * includes all the info to undo and redo this action.
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect   first block of group of blocks that gets connected.
	 * @param nextBlock        block after group of blocks connected
	 * 
	 * @Post surroundingBlock, blockToConnect, NextBlock and GC objects will be
	 *       saved in this object for later use.
	 */
	public addToBodyCommand(SurroundingBlock surroundingBlock, SequenceBlock blockToConnect, SequenceBlock nextBlock,
			GameController GC) {
		this.blockToConnect = blockToConnect;
		this.surroundingBlock = surroundingBlock;
		this.nextBlock = nextBlock;
		this.GC = GC;
	}

	@Override
	public void execute() {
		GCF.setBody(surroundingBlock, blockToConnect, GC);
	}

	@Override
	public void undo() {
		GCF.disconnect(blockToConnect, GC);
		GCF.disconnect(nextBlock, GC);
		GCF.setBody(surroundingBlock, nextBlock, GC);
		;
	}

}
