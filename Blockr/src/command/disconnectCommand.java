package command;

import domain.GameController;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.FunctionDefinition;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;

/**
 * A class that holds all the information about the action where a block got
 * disconnected to another block. This information consists of
 * the objects blockToDisconnect, blockToDiconnectTo and nextBlock (the block that
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
public class disconnectCommand implements Command {
	ImplementationBlock BF = new ImplementationBlock();
	Block blockToDisconnect;
	Block blockToDisconnectTo;
	GameController GC;
	FunctionDefinition function;
	SurroundingBlock surrounding;

	/**
	 * Makes a disconnect block Commmand. This Command includes all of the info
	 * needed to undo and redo a block disconnecting Command.
	 * 
	 * @param blockToConnectTo 
	 * 		  Block before group of blocks connected.
	 * @param blockToConnect   
	 * 		  First block of group of blocks that gets connected.
	 * @post  The objects blockToDisconnect and GC are stored in
	 *        this command for later use.
	 *        | new.blockToDisconnect == blockToDisconnect
	 *        | new.GC == GC
	 * @post  The blockToDisconnectTo, surrounding and function Blocks from the
	 * 		  the blockToDisconnect are stored in this command for later use.
	 *		  | new.blockToDisconnectTo = BF.getPreviousBlock(blockToDisconnect)
	 *  	  | new.surrounding = BF.getSurroundingBlock(blockToDisconnect)
	 *		  | new.function = BF.getFunctionBlock(blockToDisconnect)
	 */
	public disconnectCommand(Block blockToDisconnect, GameController GC) {
		this.blockToDisconnect = blockToDisconnect;
		this.blockToDisconnectTo = BF.getPreviousBlock(blockToDisconnect);
		this.surrounding = BF.getSurroundingBlock(blockToDisconnect);
		this.function = BF.getFunctionBlock(blockToDisconnect);
		this.GC = GC;
	}

	@Override
	public void execute() {
		GC.disconnect(blockToDisconnect);
	}

	@Override
	public void undo() {
		if (canReconnectPreviousAndCurrentBlock()) {
			GC.connect(blockToDisconnectTo, blockToDisconnect);
		}
		else if (surrounding != null) {
			GC.setBody(surrounding, (SequenceBlock) blockToDisconnect);
		}
		else {
			GC.setBody(function, (SequenceBlock) blockToDisconnect);
		}

	}
	
	private boolean canReconnectPreviousAndCurrentBlock() {
		return (blockToDisconnect instanceof ConditionBlock || (function == null && surrounding == null) || blockToDisconnectTo != null);
	}

}
