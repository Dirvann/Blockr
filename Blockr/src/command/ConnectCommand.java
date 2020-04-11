package command;

import domain.GameController;
import domain.ImplementationGameController;
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
	//The gamecontroller wher the blocks exist
	GameController GC;
	ImplementationGameController GCF = new ImplementationGameController();
	
	/**
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect first block of group of blocks that gets connected.
	 * @param nextBlock block after group of blocks connected
	 * @param lastBlock last block of group of blocks
	 */
	public ConnectCommand(Block blockToConnectTo, Block blockToConnect, Block nextBlock, GameController GC) {
		this.blockToConnect = blockToConnect;
		this.blockToConnectTo = blockToConnectTo;
		this.nextBlock = nextBlock;
		this.GC = GC;
	}

	@Override
	public void execute() {
		GCF.connect(blockToConnectTo, blockToConnect, GC);		
	}

	@Override
	public void undo() {
		GCF.disconnect(blockToConnect,GC);
		GCF.disconnect(nextBlock, GC);
		GCF.connect(blockToConnectTo, nextBlock,GC);
	}
	
}
