package command;

import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;

public class addToBodyCommand implements Command{
	ImplementationBlock BF = new ImplementationBlock();
	
	//first block of group of blocks that gets connected.
	SequenceBlock blockToConnect;
	//the surrounding block blockToConnect will be connected to.
	SurroundingBlock surroundingBlock;
	//block after group of blocks connected
	SequenceBlock nextBlock;
	
	/**
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect first block of group of blocks that gets connected.
	 * @param nextBlock block after group of blocks connected
	 */
	public addToBodyCommand(SurroundingBlock surroundingBlock, SequenceBlock blockToConnect, SequenceBlock nextBlock, Block lastBlock) {
		this.blockToConnect = blockToConnect;
		this.surroundingBlock = surroundingBlock;
		this.nextBlock = nextBlock;
	}

	@Override
	public void execute() {
		BF.addBodyBlock(surroundingBlock, blockToConnect);	
	}

	@Override
	public void undo() {
		BF.disconnect(blockToConnect);
		BF.disconnect(nextBlock);
		BF.addBodyBlock(surroundingBlock, nextBlock);;
	}

}
