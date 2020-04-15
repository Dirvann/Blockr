package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;

public class disconnectCommand implements Command{
	ImplementationBlock BF = new ImplementationBlock();
	//first block of group of blocks that gets connected.
	Block blockToDisconnect;
	//block before group of blocks connected
	Block blockToDisconnectTo;
	//The gamecontroller wher the blocks exist
	GameController GC;
	ImplementationGameController GCF = new ImplementationGameController();
	
	/**
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect first block of group of blocks that gets connected.
	 */
	public disconnectCommand(Block blockToDisconnectTo, Block blockToDisconnect, GameController GC) {
		this.blockToDisconnect = blockToDisconnect;
		this.blockToDisconnectTo = blockToDisconnectTo;
		this.GC = GC;
	}

	@Override
	public void execute() {
		GCF.disconnect(blockToDisconnect,GC);
	}

	@Override
	public void undo() {
		GCF.connect(blockToDisconnectTo, blockToDisconnect,GC);
		
	}
	

}
