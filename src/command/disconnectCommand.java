package command;

import domain.block.Block;
import domain.block.ImplementationBlock;

public class disconnectCommand implements Command{
	ImplementationBlock BF = new ImplementationBlock();
	//first block of group of blocks that gets connected.
	Block blockToDisconnect;
	//block before group of blocks connected
	Block blockToDisconnectTo;
	
	/**
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect first block of group of blocks that gets connected.
	 */
	public disconnectCommand(Block blockToDisconnectTo, Block blockToDisconnect) {
		this.blockToDisconnect = blockToDisconnect;
		this.blockToDisconnectTo = blockToDisconnectTo;
	}

	@Override
	public void execute() {
		BF.disconnect(blockToDisconnect);
	}

	@Override
	public void undo() {
		BF.connect(blockToDisconnectTo, blockToDisconnect);
		
	}
	

}
