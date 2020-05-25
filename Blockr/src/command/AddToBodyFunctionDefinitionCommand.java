package command;

import domain.GameController;
import domain.block.FunctionDefinition;
import domain.block.SequenceBlock;
/**
 * A class that holds all the information about the action where a block got
 * connected as a Body to a surroundingBlock. This information consists of
 * the objects blockToConnect, surroundingBlock and nextBlock (the block that
 * was previously connected as a body). The class also specifies what must
 * happen to undo and execute this command.
 * 
 * @version 3.0
 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
 *
 */
public class AddToBodyFunctionDefinitionCommand implements Command {

	// first block of group of blocks that gets connected.
	SequenceBlock blockToConnect;
	// the surrounding block blockToConnect will be connected to.
	FunctionDefinition definition;
	// block after group of blocks connected
	SequenceBlock nextBlock;
	// The gamecontroller wher the blocks exist
	GameController GC;

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
	public AddToBodyFunctionDefinitionCommand(FunctionDefinition definition, SequenceBlock blockToConnect, SequenceBlock nextBlock,
			GameController GC) {
		this.blockToConnect = blockToConnect;
		this.definition = definition;
		this.nextBlock = nextBlock;
		this.GC = GC;
	}

	@Override
	public void execute() {
		GC.setBody(definition, blockToConnect);
	}

	@Override
	public void undo() {
		GC.disconnect(blockToConnect);
		GC.disconnect(nextBlock);
		GC.setBody(definition, nextBlock);
		;
	}

}
