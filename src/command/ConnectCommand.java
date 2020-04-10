package command;

import domain.block.Block;
import domain.block.ImplementationBlock;

public class ConnectCommand implements Command{
	ImplementationBlock BF = new ImplementationBlock();
	//first block of group of blocks that gets connected.
	Block blockToConnect;
	//block before group of blocks connected
	Block blockToConnectTo;
	//block after group of blocks connected
	Block nextBlock;
	//last block of group of blocks
	Block lastBlock;
	
	/**
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect first block of group of blocks that gets connected.
	 * @param nextBlock block after group of blocks connected
	 * @param lastBlock last block of group of blocks
	 */
	public ConnectCommand(Block blockToConnectTo, Block blockToConnect, Block nextBlock, Block lastBlock) {
		this.blockToConnect = blockToConnect;
		this.blockToConnectTo = blockToConnectTo;
		this.nextBlock = nextBlock;
		this.lastBlock = lastBlock;
	}

	@Override
	public void execute() {
		BF.connect(blockToConnectTo, blockToConnect);		
	}

	@Override
	public void undo() {
		BF.disconnect(blockToConnect);
		BF.disconnect(nextBlock);
		BF.connect(blockToConnectTo, nextBlock);
	}
	
}
