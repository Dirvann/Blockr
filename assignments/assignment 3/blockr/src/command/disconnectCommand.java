package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
/**
 * A class that holds all the information about the action where a block got
 * disconnected to another block. This information consists of
 * the objects blockToDisconnect, blockToDiconnectTo and nextBlock (the block that
 * was previously connected to blockToConnectTo). The class also specifies what must
 * happen to undo and execute this command.
 * 
 * @version 3.0
 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
 *
 */
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
	 * Makes a disconnect block Commmand. This Command includes all of the info needed
	 * to undo and redo a block disconnecting Command.
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect first block of group of blocks that gets connected.
	 * 
	 * @Post The objects blockToDisconnectTo, blockToDisconnect and GC are stored in this command for later use.
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
