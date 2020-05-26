package command;

import domain.GameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
/**
 * A class that holds all the information about the action where a block got
 * connected to another block. This information consists of
 * the objects blockToConnect, blockToConnectTo and nextBlock (the block that
 * was previously connected to blockToConnectTo). The class also specifies what must
 * happen to undo and execute this command.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class ConnectCommand implements Command {
	ImplementationBlock BF = new ImplementationBlock();
	Block blockToConnect;
	Block blockToConnectTo;
	Block nextBlock;
	GameController GC;

	/**
	 * Makes a new command consisting of all of the information to undo and redo
	 * this Command.
	 * 
	 * @param blockToConnectTo 
	 * 		  Block before group of blocks connected.
	 * @param blockToConnect   
	 * 		  First block of group of blocks that gets connected.
	 * @param nextBlock        
	 * 		  Block after group of blocks connected.
	 * @param lastBlock        
	 * 		  Last block of group of blocks.
	 * @post  The objects blockToConnectTo, blockToConnect, nextBlock and GC are
	 *        saved in this command for later use.
	 *        | new.blockToConnectTo == blockToConnectTo
	 *        | new.blockToConnect == blockToConnect
	 * 		  | new.nextBlock == nextBlock
	 * 		  | new.GC == GC
	 */
	public ConnectCommand(Block blockToConnectTo, Block blockToConnect, Block nextBlock, GameController GC) {
		this.blockToConnect = blockToConnect;
		this.blockToConnectTo = blockToConnectTo;
		this.nextBlock = nextBlock;
		this.GC = GC;
	}

	@Override
	public void execute() {
		GC.connect(blockToConnectTo, blockToConnect);
	}

	@Override
	public void undo() {
		GC.disconnect(blockToConnect);
		GC.disconnect(nextBlock);
		GC.connect(blockToConnectTo, nextBlock);
	}

}
