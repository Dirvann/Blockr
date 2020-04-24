package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
/**
 * A class that holds all the information about the action where a block got
 * connected to another block. This information consists of
 * the objects blockToConnect, blockToConnectTo and nextBlock (the block that
 * was previously connected to blockToConnectTo). The class also specifies what must
 * happen to undo and execute this command.
 * 
 * @version 3.0
 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
 *
 */
public class ConnectCommand implements Command {
	ImplementationBlock BF = new ImplementationBlock();
	// first block of group of blocks that gets connected.
	Block blockToConnect;
	// block before group of blocks connected
	Block blockToConnectTo;
	// block after group of blocks connected
	Block nextBlock;
	// The gamecontroller wher the blocks exist
	GameController GC;
	ImplementationGameController GCF = new ImplementationGameController();

	/**
	 * Makes a new command consisting of all of the information to undo and redo
	 * this Command.
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect   first block of group of blocks that gets connected.
	 * @param nextBlock        block after group of blocks connected
	 * @param lastBlock        last block of group of blocks
	 * 
	 * @Post The objects blockToConnectTo, blockToConnect, nextBlock and GC are
	 *       saved in this command for later use.
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
		GCF.disconnect(blockToConnect, GC);
		GCF.disconnect(nextBlock, GC);
		GCF.connect(blockToConnectTo, nextBlock, GC);
	}

}
